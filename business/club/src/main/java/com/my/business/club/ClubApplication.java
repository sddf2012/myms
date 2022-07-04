package com.my.business.club;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liu peng bo
 * date: 2021/6/30 11:15
 */
@SpringCloudApplication
@EnableFeignClients(basePackages ={"com.my.common.server.feign"} )
public class ClubApplication {
    public static void main(String[] args) {
        /*ApplicationContext context = SpringApplication.run(ClubApplication.class, args);
        RegistrationClientService clientService = context.getBean(RegistrationClientService.class);
        clientService.register();*/
        SpringApplication.run(ClubApplication.class, args);
    }
}
