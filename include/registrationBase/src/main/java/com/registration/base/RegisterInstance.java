package com.registration.base;

import lombok.Data;

import java.util.Date;

/**
 * @author liu peng bo
 * date: 2021/6/29 16:14
 */
@Data
public class RegisterInstance {
    private String serviceId;

    private String host;

    private Integer port;

    private Integer renewIntervalTime = 30;

    /**
     * 实例下线时间，即指定时间未续约则下线
     */
    private Integer minHeartbeatTime = 90;

    private Date registerTime;

    private Date lastRenewTime;

    private Long lastHeartbeatTime;

    public String getInstanceUrl() {
        return host + ":" + port;
    }

}