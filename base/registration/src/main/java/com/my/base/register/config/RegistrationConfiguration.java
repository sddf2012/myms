package com.my.base.register.config;

import com.registration.server.RegistrationServerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liu peng bo
 * date: 2021/6/22 15:25
 */
@Configuration
public class RegistrationConfiguration {
    @Bean
    public RegistrationServerService serverService(){
        return new RegistrationServerService();
    }

}
