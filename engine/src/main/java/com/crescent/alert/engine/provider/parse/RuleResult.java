package com.crescent.alert.engine.provider.parse;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class RuleResult {

    private boolean success;
    private Map<String, String> content = new HashMap<>();
    private String alertStatus;

    public RuleResult(boolean success) {
        this(success, null);
    }

    public RuleResult(boolean success, Map<String, String> content) {
        this.success = success;
        this.content = content;
    }
}
