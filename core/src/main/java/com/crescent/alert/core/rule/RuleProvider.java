package com.crescent.alert.core.rule;

import static com.crescent.alert.common.util.Constants.STATE_DURATION_SUFFIX;
import static java.util.stream.Collectors.toMap;

import com.crescent.alert.common.config.PolicyInfo;
import com.crescent.alert.common.config.PolicyInfo.OriginStatus;
import com.crescent.alert.common.dto.Rule;
import com.crescent.alert.common.util.Constants;
import com.crescent.alert.core.util.RuleParse;
import com.crescent.alert.engine.provider.parse.RuleTemplate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
public class RuleProvider extends AbstractRuleProvider {
    /**
     * 存储Rule数据
     * key: ruleId, Value: rule
     */
    private ConcurrentHashMap<String, Rule> ruleDatas = new ConcurrentHashMap<>();

    /**
     * 存储规则的ruleTemplate
     * key: ruleId , Value: list of rule dsl grammar
     */
    private ConcurrentHashMap<String, List<TransGrammar>> ruleTemplates = new ConcurrentHashMap<>();

    /**
     * 记录告警规则DSL语句中设置的事件buffer的大小
     */
    private ConcurrentHashMap<String, Map<String, String>> ruleDslPeriodInfo = new ConcurrentHashMap<>();

    /**
     * 添加规则处理数据时，需要的buffer最大长度
     * key: ruleId , Value: max length of rule buffer
     */
    private ConcurrentHashMap<String, Integer> ruleWindowSize = new ConcurrentHashMap<>();

    /**
     * 记录告警stream对象的规则id列表
     * key streamId, value ruleIds
     */
    private ConcurrentHashMap<String, Set<String>> ruleStreamMap = new ConcurrentHashMap<>();

    public RuleProvider(PolicyInfo policyInfo) {
        super(policyInfo);
    }

    @Override
    public void registryRule(Rule rule) {
        if (null == rule || CollectionUtils.isEmpty(rule.getInputStreams())) {
            log.error("inputStreams can't be null");
            return;
        }
        List<TransGrammar> grammars = RuleParse.parseRuleOrderByPriorityLevel(this, rule);
        ruleTemplates.put(rule.getRuleId(), grammars);
        ruleDatas.put(rule.getRuleId(), rule);
        statePeriods(rule, grammars);
        registryStream(rule, bufferLength(grammars));
    }

    private synchronized void registryStream(Rule rule, int bufferLength) {
        List<String> streamIds = rule.getInputStreams();
        for (String streamId : streamIds) {
            if (!ruleStreamMap.containsKey(streamId)) {
                ruleStreamMap.put(streamId, new HashSet<>());
            }
            ruleStreamMap.get(streamId).add(rule.getRuleId());

            // 更新streamId对象需要维护的buffer长度
            if (ruleWindowSize.getOrDefault(streamId, 0) < bufferLength) {
                ruleWindowSize.put(streamId, bufferLength);
            }
        }
    }

    private void statePeriods(Rule rule, List<TransGrammar> grammars) {
        Map<String, String> ruleDurationInfo = grammars.stream()
            .collect(toMap(grammar -> grammar.getOriginStatus().getOriginSeverity().toLowerCase() + STATE_DURATION_SUFFIX,
                grammar -> String.valueOf(grammar.getRuleTemplate().getBoundingPeriodWithTimeUnit(grammar.getParameters())
                    .getKey())));
        ruleDurationInfo.put(Constants.CONTINUE_DURATION, String.valueOf(rule.getContinueDuration()));

        ruleDslPeriodInfo.put(rule.getRuleId(), ruleDurationInfo);
    }

    /**
     * 维护的buffer长度
     * 如果是count类型，那么代表的是条数
     * 如果是window_size类型，那么代表毫秒数
     */
    private int bufferLength(List<TransGrammar> grammars) {
        return grammars.stream()
            .map(grammar -> {
                Pair<Long, TimeUnit> period = grammar.getRuleTemplate().getBoundingPeriodWithTimeUnit(grammar
                    .getParameters());
                if (null != period.getValue()) {
                    return period.getValue().toMillis(period.getKey());
                }
                return period.getKey();
            }).max(Long::compareTo).get().intValue();
    }

    @Override
    public void unRegistryRule(String ruleId) {
        if (!ruleDatas.containsKey(ruleId)) {
            return;
        }
        unRegistryStreamId(ruleId);
        ruleTemplates.remove(ruleId);
        ruleDatas.remove(ruleId);
    }

    private synchronized void unRegistryStreamId(String ruleId) {
        Rule rule = ruleDatas.get(ruleId);
        List<String> streamIds = rule.getInputStreams();
        if (CollectionUtils.isEmpty(streamIds)) {
            return;
        }

        for (String streamId : streamIds) {
            if (!ruleStreamMap.containsKey(streamId)) {
                ruleStreamMap.get(streamId).remove(ruleId);
            }

            ruleWindowSize.remove(streamId);
        }
    }


    /**
     * 获取告警规则解析后的dsl， 用于告警计算
     */
    @Override
    public Optional<List<TransGrammar>> getRuleTemplates(String ruleId) {
        return Optional.ofNullable(ruleTemplates.get(ruleId));
    }

    /**
     * 获取告警规则设置的事件计算的时间或数量跨度， 主要用于状态配置 ``policies.json``
     * 定义的dsl中的时间窗口大小
     */
    @Override
    public Optional<Map<String, String>> getRuleDslPeriodInfo(String ruleId) {
        return Optional.ofNullable(ruleDslPeriodInfo.get(ruleId));
    }

    /**
     * 获取告警事件计算时，需要维护的buffer的最小长度，以便用于告警计算
     */
    @Override
    public int getBufferSizeByStreamId(String streamId) {
        return ruleWindowSize.getOrDefault(streamId, 0);
    }

    @Override
    public Set<String> getRuleIdsByStreamId(String streamId) {
        return ruleStreamMap.getOrDefault(streamId, new HashSet<>());
    }

    @Override
    public Optional<Rule> getRule(String ruleId) {
        return Optional.ofNullable(ruleDatas.get(ruleId));
    }

    @Data
    public static class TransGrammar {

        /**
         * template 参数
         */
        private Map<String, String> parameters;

        private RuleTemplate ruleTemplate;

        private OriginStatus originStatus;

        public TransGrammar(Map<String, String> parameters, RuleTemplate parse,
            OriginStatus originStatus) {
            if (null == originStatus) {
                throw new IllegalArgumentException("transGrammar priorityStatus can't be empty!");
            }
            this.parameters = null == parameters ? new HashMap<>() : parameters;
            this.ruleTemplate = parse;
            this.originStatus = originStatus;
        }
    }
}
