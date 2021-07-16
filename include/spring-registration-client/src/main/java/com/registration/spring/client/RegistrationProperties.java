package com.registration.spring.client;

import com.registration.base.config.RegisterClientConfig;
import com.registration.base.config.RegisterInstanceConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liu peng bo
 * date: 2021/7/2 14:37
 */
@Data
@ConfigurationProperties(prefix = "my.registration")
public class RegistrationProperties {
    private RegisterInstanceConfig instance;

    private RegisterClientConfig client;
}
