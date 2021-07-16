package com.registration.spring.server;

import com.registration.base.config.RegisterServerConfig;
import com.registration.server.RegistrationServerService;
import com.registration.spring.server.controller.RegisterController;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liu peng bo
 * date: 2021/7/14 11:38
 */
@Configuration
@EnableConfigurationProperties(value = RegistrationServerProperties.class)
public class RegistrationServerAutoConfiguration {
    @Bean
    public RegistrationServerService serverService(RegistrationServerProperties properties) {
        RegisterServerConfig config = new RegisterServerConfig();
        if (properties.getInstanceCheckIntervalTime() != null) {
            config.setInstanceCheckIntervalTime(properties.getInstanceCheckIntervalTime());
        }
        RegistrationServerService service = new RegistrationServerService();
        service.setServerConfig(config);
        service.autoCheckInstances();
        return service;
    }

    @Bean
    public RegisterController registerController() {
        return new RegisterController();
    }
}
