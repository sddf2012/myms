package com.my.business.player.config;

import com.registration.base.RegisterConfig;
import com.registration.base.RegisterInstanceConfig;
import com.registration.base.RegisterServerConfig;
import com.registration.client.RegistrationClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liu peng bo
 * date: 2021/6/30 11:52
 */
@Configuration
public class PlayerConfig {
    /*@Value("${server.port}")
    private Integer port;

    @Value("${spring.application.name}")
    private String serviceId;

    @Bean
    public RegisterConfig registerConfig() {
        RegisterConfig config = new RegisterConfig();
        RegisterInstanceConfig instanceConfig = new RegisterInstanceConfig();
        instanceConfig.setServiceId(serviceId);
        instanceConfig.setHost("localhost");
        instanceConfig.setPort(port);
        config.setInstance(instanceConfig);
        RegisterServerConfig serverConfig = new RegisterServerConfig();
        serverConfig.setUrl("http://localhost:7000");
        config.setServer(serverConfig);
        return config;
    }

    @Bean
    public RegistrationClientService clientService(RegisterConfig config) {
        RegistrationClientService clientService = new RegistrationClientService();
        clientService.setConfig(config);
        return clientService;
    }*/
}
