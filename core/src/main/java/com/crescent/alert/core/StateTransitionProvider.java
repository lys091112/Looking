package com.crescent.alert.core;

import com.crescent.alert.common.config.PolicyInfo;
import com.crescent.alert.common.config.PolicyInfo.Policy;
import com.crescent.alert.common.config.PolicyInfo.PriorityStatus;
import com.crescent.alert.common.exception.IllegalParamException;
import com.crescent.alert.core.util.RuleParse;
import com.crescent.alert.engine.provider.parse.RuleTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
public class StateTransitionProvider {

    private StateTransitionProvider() {
    }

    private static StateTransitionProvider instance = new StateTransitionProvider();

    public static StateTransitionProvider getInstance() {
        return instance;
    }

    private Map<String, PriorityStatus> priorityStatuses = new HashMap<>();
    private Map<PriorityStatus, List<Pair<String, RuleTemplate>>> fixedRuleTemplates;
    private Map<String, Policy> policys = new HashMap<>();

    public void init(PolicyInfo policyInfo) {
        if (null == policyInfo) {
            throw new IllegalParamException("初始化自定义告警转化规则失败! file: policies.json");
        }

        priorityStatuses = policyInfo.getPriorityStatus().stream()
            .collect(Collectors.toMap(s -> s.getSeverity(), s -> s));
        fixedRuleTemplates = RuleParse.parsePolicyState(policyInfo);
        policys = policyInfo.getPolicies().stream()
            .collect(Collectors.toMap(p -> p.getSeverity(), p -> p));
        log.info("initialize policy information success!");
    }

    public PriorityStatus findStatus(String severity) {
        return Optional.ofNullable(priorityStatuses.get(severity)).orElseThrow(() -> new IllegalArgumentException
            ("invalid health status! status:" + severity));
    }

    public Policy findPolicy(String severity) {
        return Optional.ofNullable(policys.get(severity)).orElseThrow(() -> new IllegalArgumentException
            ("invalid health violation! status:" + severity));

    }

    /**
     * 默认告警事件
     */
    public Optional<PriorityStatus> findDefaultStatus() {
        return priorityStatuses.values().stream().filter(state -> state.isDefaultState()).findFirst();
    }

    public List<Pair<String, RuleTemplate>> findFixedRuleTemplate(String severity) {
        return Optional.ofNullable(fixedRuleTemplates.get(findStatus(severity)))
            .orElseThrow(() -> new IllegalArgumentException());
    }
}
