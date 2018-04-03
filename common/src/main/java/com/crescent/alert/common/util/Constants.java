package com.crescent.alert.common.util;

public class Constants {

    public final static String CONTINUE_DURATION = "continue_duration_time";
    public final static String STATE_DURATION_SUFFIX = "_duration_time";

    public final static String SEVERITY_VARIABLE = "severity";

    /**
     * 告警计算时，传递的当前ruleTemp对应的severity
     */
    public final static String CONCURRENT_PARAMETER_SEVERITY = "concurrentParameterSeverity";


    /**
     * 全量匹配时，对于有该标识的事件需要忽略
     */
    public final static String CONTINUE_IGNORE_EVENT = "continueIgnoreEvent";

}
