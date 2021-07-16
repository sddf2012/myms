package com.registration.base.config;

import lombok.Data;

/**
 * @author liu peng bo
 * date: 2021/7/13 17:33
 */
@Data
public class RegisterServerConfig {
    /**
     * 实例检测间隔时间 单位秒
     */
    private Integer instanceCheckIntervalTime = 5;


}
