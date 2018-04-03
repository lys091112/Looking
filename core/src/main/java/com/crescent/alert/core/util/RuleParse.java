package com.crescent.alert.core.util;


import static com.crescent.alert.engine.provider.parse.ParserEngineFactory.getParseEngine;

import com.crescent.alert.common.config.PolicyInfo;
import com.crescent.alert.common.config.PolicyInfo.OriginStatus;
import com.crescent.alert.common.config.PolicyInfo.Policy;
import com.crescent.alert.common.dto.Rule;
import com.crescent.alert.common.exception.InitializationException;
import com.crescent.alert.core.rule.RuleProvider.TransGrammar;
import com.crescent.alert.core.rule.StateTransitionProvider;
import com.crescent.alert.engine.provider.parse.RuleTemplate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 解析规则dsl
 */
@Slf4j
public class RuleParse {


    // TODO 添加异常处理
    public static List<TransGrammar> parseRuleOrderByPriorityLevel(StateTransitionProvider transitionProvider, Rule rule) {
        List<TransGrammar> rules = rule.getMatchingGrammar().stream()
            .map(grammar -> {
                OriginStatus originStatus = transitionProvider.findStatus(grammar.getState());
                return new TransGrammar(grammar.getParameters(), getParseEngine().parse(grammar.getDsl()), originStatus);
            })
            .collect(Collectors.toList());

        Collections.sort(rules, Comparator.comparingInt(grammar -> grammar.getOriginStatus().getPriorityLevel()));
        return rules;
    }

    /**
     * 获取状态转换的dsl语法解析，以及相关间的关联关系
     */
    public static Map<OriginStatus, List<Pair<String, RuleTemplate>>> parsePolicyState(
        PolicyInfo policyInfo) {
        if (CollectionUtils.isEmpty(policyInfo.getOriginStatus())) {
            throw new InitializationException("Initial policies info error!");
        }

        return policyInfo.getOriginStatus().stream()
            .collect(() -> new HashMap<OriginStatus, List<Pair<String, RuleTemplate>>>(),
                (map, status) -> map.put(status, status.getChildSeverity().stream()
                    .map(severity -> findSeverityDsl(policyInfo, severity))
                    .filter(Objects::nonNull)
                    .map(policy -> Pair.of(policy.getSeverity(), getParseEngine().parse(policy.getDsl())))
                    .collect(Collectors.toList())),
                HashMap::putAll);
    }

    private static Policy findSeverityDsl(PolicyInfo infos, String severity) {
        if (CollectionUtils.isEmpty(infos.getPolicies())) {
            log.warn("Can't find policies from policyInfo ");
            throw new InitializationException("Initial policies info error!");
        }
        return infos.getPolicies().stream()
            .filter(policy -> policy.getSeverity().equalsIgnoreCase(severity))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid severity! severity=" + severity));
    }

}
