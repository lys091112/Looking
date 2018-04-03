package com.crescent.alert.core.rule;

import com.crescent.alert.common.config.PolicyInfo;
import com.crescent.alert.common.dto.Rule;
import com.crescent.alert.core.rule.RuleProvider.TransGrammar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 */
public abstract class AbstractRuleProvider extends StateTransitionProvider implements RuleRegister {


    public AbstractRuleProvider(PolicyInfo policyInfo) {
        super(policyInfo);
    }

    /**
     * 获取告警规则解析后的dsl， 用于告警计算
     */
    public abstract Optional<List<TransGrammar>> getRuleTemplates(String ruleId);

    /**
     * 获取告警规则设置的事件计算的时间或数量跨度， 主要用于状态配置 ``policies.json``
     * 定义的dsl中的时间窗口大小
     */
    public abstract Optional<Map<String, String>> getRuleDslPeriodInfo(String ruleId);

    /**
     * 获取告警事件计算时，需要维护的buffer的最小长度，以便用于告警计算
     */
    public abstract int getBufferSizeByStreamId(String streamId);

    /**
     * 根据streamId获取对象的规则列表
     */
    public abstract Set<String> getRuleIdsByStreamId(String streamId);

    /**
     * 获取规则数据
     */
    public abstract Optional<Rule> getRule(String ruleId);
}
