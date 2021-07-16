package com.registration.base.config;

import lombok.Data;

/**
 * @author liu peng bo
 * date: 2021/6/29 17:17
 */
@Data
public class RegisterInstanceConfig {
    private String serviceId;

    private String host;

    private Integer port;

    /**
     * 续约间隔时间 单位秒
     */
    private Integer renewIntervalTime = 30;

    /**
     * 实例下线时间，即指定时间未续约则下线
     */
    private Integer minHeartbeatTime = 90;

    public String getInstanceInfo() {
        return serviceId + "/" + host + ":" + port;
    }

    public String getInstanceUrl() {
        return host + ":" + port;
    }
}
