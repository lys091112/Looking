package com.crescent.alert.core.dispatch.handler;

import com.crescent.alert.common.util.Constants;
import com.crescent.alert.core.rule.AlertEvent;
import com.crescent.alert.core.rule.RuleProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RuleHandler {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RuleHandler.class);

    private RuleHandler childHandler;

    protected RuleProvider ruleProvider;

    public RuleHandler(RuleProvider ruleProvider) {
        this.ruleProvider = ruleProvider;
    }

    public void execute(AlertEvent event) {
        Optional<AlertEvent> next = handle(event);

        if (childHandler != null && next.isPresent()) {
            childHandler.handle(next.get());
        }
    }

    protected abstract Optional<AlertEvent> handle(AlertEvent event);

    public void setChildHandler(RuleHandler childHandler) {
        this.childHandler = childHandler;
    }

    protected Map<String, String> fileSeverity(String severity, Map<String, String> originParam) {
        Map<String, String> res = new HashMap<>(originParam);
        originParam.put(Constants.CONCURRENT_PARAMETER_SEVERITY, severity);
        return res;
    }
}
