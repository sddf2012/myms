package com.my.business.player;

import com.my.business.player.config.PlayerBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liu peng bo
 * date: 2021/6/22 10:45
 */
@EnableBinding(PlayerBinding.class)
@EnableFeignClients(basePackages = {"com.my.common.server.feign"})
@SpringCloudApplication
@ComponentScan({"com.my"})
public class PlayerApplication {
    public static void main(String[] args) {
        /*ApplicationContext context = SpringApplication.run(PlayerApplication.class, args);
        RegistrationClientService clientService = context.getBean(RegistrationClientService.class);
        clientService.register();*/
        SpringApplication.run(PlayerApplication.class, args);
    }
}
