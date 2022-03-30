package com.my.business.player;

import com.registration.client.RegistrationClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

/**
 * @author liu peng bo
 * date: 2021/6/22 10:45
 */
@SpringCloudApplication
public class PlayerApplication {
    public static void main(String[] args) {
        /*ApplicationContext context = SpringApplication.run(PlayerApplication.class, args);
        RegistrationClientService clientService = context.getBean(RegistrationClientService.class);
        clientService.register();*/
        SpringApplication.run(PlayerApplication.class, args);
    }
}
