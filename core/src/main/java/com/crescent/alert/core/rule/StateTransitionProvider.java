package com.crescent.alert.core.rule;

import com.crescent.alert.common.config.PolicyInfo;
import com.crescent.alert.common.config.PolicyInfo.OriginStatus;
import com.crescent.alert.common.config.PolicyInfo.Policy;
import com.crescent.alert.common.exception.IllegalParamException;
import com.crescent.alert.core.util.RuleParse;
import com.crescent.alert.engine.provider.parse.RuleTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
public class StateTransitionProvider {

    private PolicyInfo policyInfo;
    private Map<String, OriginStatus> originStatuses = new HashMap<>();
    private Map<OriginStatus, List<Pair<String, RuleTemplate>>> fixedRuleTemplates;
    private Map<String, Policy> policies = new HashMap<>();

    public StateTransitionProvider(PolicyInfo policyInfo) {
        if (null == policyInfo) {
            throw new IllegalParamException("初始化自定义告警规则失败! file: policies.json");
        }

        this.policyInfo = policyInfo;
        originStatuses = policyInfo.getOriginStatus().stream()
            .collect(Collectors.toMap(s -> s.getOriginSeverity(), s -> s));
        fixedRuleTemplates = RuleParse.parsePolicyState(policyInfo);
        policies = policyInfo.getPolicies().stream()
            .collect(Collectors.toMap(p -> p.getSeverity(), p -> p));
        log.info("Initialize policy information success!");
    }

    public OriginStatus findStatus(String severity) {
        return Optional.ofNullable(originStatuses.get(severity)).orElseThrow(() -> new IllegalArgumentException
            ("Invalid origin status! status:" + severity));
    }

    public Policy findPolicy(String severity) {
        return Optional.ofNullable(policies.get(severity))
            .orElseThrow(() -> new IllegalArgumentException("Invalid policy severity! severity:" + severity));

    }

    /**
     * 默认告警事件
     */
    public Optional<OriginStatus> findDefaultStatus() {
        return originStatuses.values().stream().filter(state -> state.isDefaultState()).findFirst();
    }

    public List<Pair<String, RuleTemplate>> findFixedRuleTemplate(String severity) {
        return Optional.ofNullable(fixedRuleTemplates.get(findStatus(severity)))
            .orElseThrow(() -> new IllegalArgumentException());
    }

    public SeverityParam severityParam(String severity) {

        Optional<OriginStatus> optional = policyInfo.getOriginStatus().stream().filter(status -> status.getOriginSeverity()
            .equalsIgnoreCase(severity)).findAny();

        if (optional.isPresent()) {
            return new SeverityParam(true, false);
        }

        Optional<Policy> policyOptional = policyInfo.getPolicies().stream().filter(policy -> policy.getSeverity()
            .equalsIgnoreCase(severity)).findAny();

        if (policyOptional.isPresent()) {
            return new SeverityParam(policyOptional.get().isIgnoreBeforeTrigger(), policyOptional.get().isIgnoreBeforeTrigger());
        }

        return null;
    }


    @Getter
    public static class SeverityParam {

        private boolean totalMatch;
        private boolean ignoreContinueTrigger;

        public SeverityParam(boolean totalMatch, boolean ignoreContinueTrigger) {
            this.totalMatch = totalMatch;
            this.ignoreContinueTrigger = ignoreContinueTrigger;
        }
    }
}
