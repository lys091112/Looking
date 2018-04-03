package com.crescent.alert.common.dto;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Rule {

    /**
     * 规则的唯一标识
     */
    private String ruleId;

    /**
     * 规则关联监控的对象唯一标识
     */
    private List<String> inputStreams;

    /**
     * 告警规则的匹配语法
     */
    private List<Grammar> matchingGrammar;

    /**
     * 同种类型的警报的周期持续时间
     */
    private long continueDuration;

    /**
     * 隔离时间
     */
    private String isolateTime;

    /**
     * 规则所属的域
     */
    private String region;


    @Data
    public static class Grammar {

        /**
         * 状态机的状态标识
         */
        private String state;

        /**
         * dsl 参数
         */
        private Map<String, String> parameters;

        private String dsl;
    }
}
