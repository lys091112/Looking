package com.crescent.alert.core.rule;

import com.crescent.alert.common.dto.Rule;

public interface RuleRegister {

    /**
     * 注册规则
     */
    void registryRule(Rule rule);

    void unRegistryRule(String ruleId);

}
