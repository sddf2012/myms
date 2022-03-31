package com.my.business.player;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liu peng bo
 * date: 2021/6/22 10:45
 */
@EnableFeignClients(basePackages = {"com.my.common.server.feign"})
@SpringCloudApplication
public class PlayerApplication {
    public static void main(String[] args) {
        /*ApplicationContext context = SpringApplication.run(PlayerApplication.class, args);
        RegistrationClientService clientService = context.getBean(RegistrationClientService.class);
        clientService.register();*/
        SpringApplication.run(PlayerApplication.class, args);
    }
}
