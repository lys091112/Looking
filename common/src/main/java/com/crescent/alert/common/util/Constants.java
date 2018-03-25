package com.crescent.alert.common.util;

public class Constants {

    public final static String CONTINUE_DURATION = "continue_duration_time";
    public final static String STATE_DURATION_SUFFIX = "_duration_time";

    public final static String DEFAULT_REGION = "default_region";

    public final static String SEVERITY_VARIABLE = "severity";

    /**
     * 计算告警事件状态时，是否需要数据buffer全量匹配
     */
    public final static String TOTAL_MATCHING = "totalMatching";


    /**
     * 全量匹配时，对于有该标识的事件需要忽略
     */
    public final static String MATCHING_IGNORE_EVENT = "continueIgnoreEvent";

    /**
     * 数据存储key
     */
    public final static String KAFKA_CONSUMER_CONFIG = "lookingConsumerConfig";
    public final static String KAFKA_PRODUCER_CONFIG = "lookingProducerConfig";

}
