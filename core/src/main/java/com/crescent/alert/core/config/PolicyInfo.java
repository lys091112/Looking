package com.crescent.alert.core.config;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class PolicyInfo {

    private List<PriorityStatus> priorityStatus;

    private List<Policy> policies;

    @Data
    @EqualsAndHashCode
    public static class PriorityStatus {

        private int priorityLevel;

        private String severity;

        private List<String> childSeverity;

        private boolean defaultState = false;
    }

    @Data
    public static class Policy {

        private String severity;

        private String dsl;

        /**
         * 对于某些告警状态，计算是需要忽略上一次的告警状态，因此需要清空已有的告警数据
         */
        private boolean ignoreBeforeTrigger = false;
    }

}
