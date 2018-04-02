package com.crescent.alert.core;

import com.crescent.alert.common.config.PolicyInfo;
import com.crescent.alert.core.collector.producer.Sender;

public class AlertProvider {

    private StateTransitionProvider stateTransitionProvider;

    private RuleManager ruleManager;

    private Sender sender;

    public AlertProvider(PolicyInfo policyInfo, Sender sender) {
        stateTransitionProvider = new StateTransitionProvider(policyInfo);
        ruleManager = new RuleManager();
        this.sender = sender;
    }


    public RuleManager getRuleManager() {
        return ruleManager;
    }

    public StateTransitionProvider getStateTransitionProvider() {
        return stateTransitionProvider;
    }

    public Sender getSender() {
        return sender;
    }
}
