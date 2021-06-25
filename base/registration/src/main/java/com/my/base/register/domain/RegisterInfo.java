package com.my.base.register.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author liu peng bo
 * date: 2021/6/22 15:30
 */
@Data
public class RegisterInfo {
    private String serviceId;

    private String host;

    private String port;

    private Date registerTime;

}
