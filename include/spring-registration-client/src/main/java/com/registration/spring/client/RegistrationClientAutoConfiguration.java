package com.registration.spring.client;

import com.registration.base.config.RegisterConfig;
import com.registration.base.config.RegisterInstanceConfig;
import com.registration.client.RegistrationClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liu peng bo
 * date: 2021/7/2 14:36
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = RegistrationProperties.class)
//@ConditionalOnProperty(value = "my.registration.client.enable",matchIfMissing = true)
public class RegistrationClientAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RegisterConfig registerConfig(RegistrationProperties properties, Environment environment) {
        RegisterInstanceConfig instanceConfig = properties.getInstance();
        if (instanceConfig == null) {
            instanceConfig = new RegisterInstanceConfig();
        }
        if (!StringUtils.hasText(instanceConfig.getHost())) {
            try {
                InetAddress addr = InetAddress.getLocalHost();
                instanceConfig.setHost(addr.getHostAddress());
            } catch (UnknownHostException e) {
                log.error("获取Ip地址失败!", e);
                throw new RuntimeException(e);
            }
        }
        if (instanceConfig.getPort() == null) {
            String port = environment.getProperty("server.port");
            if (!StringUtils.hasText(port)) {
                log.error("server.port未设置!");
                throw new RuntimeException("server.port未设置");
            }
            instanceConfig.setPort(Integer.parseInt(port));
        }
        if (!StringUtils.hasText(instanceConfig.getServiceId())) {
            String serviceId = environment.getProperty("spring.application.name");
            if (!StringUtils.hasText(serviceId)) {
                log.error("serviceId未设置!");
                throw new RuntimeException("serviceId未设置");
            }
            instanceConfig.setServiceId(serviceId);
        }
        RegisterConfig config = new RegisterConfig();
        config.setInstance(instanceConfig);
        config.setClient(properties.getClient());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistrationClientService clientService(RegisterConfig config) {
        RegistrationClientService clientService = new RegistrationClientService();
        clientService.setConfig(config);
        return clientService;
    }

    @Bean
    public RegisterClientListener registerClientListener() {
        return new RegisterClientListener();
    }

}
