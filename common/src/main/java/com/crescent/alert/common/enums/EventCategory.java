package com.crescent.alert.common.enums;

/**
 * 告警事件的种类
 * RawMetric 告警原始数据，用于计算告警
 * HealthStatus 告警中间状态，这个是状态机配置的几个状态
 * HealthViolation 状态机转化过程中产生的状态转变事件
 */
public enum EventCategory {
    RawMetric,
    HealthStatus,
    HealthViolation;
}
