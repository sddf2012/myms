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

    private Date registerTime;
}
