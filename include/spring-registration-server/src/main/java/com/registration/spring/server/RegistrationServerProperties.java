package com.registration.spring.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liu peng bo
 * date: 2021/7/14 11:48
 */
@Data
@ConfigurationProperties(prefix = "my.registration.server")
public class RegistrationServerProperties {
    private Integer instanceCheckIntervalTime;
}
