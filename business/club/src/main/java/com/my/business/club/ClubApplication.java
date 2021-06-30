package com.my.business.club;

import com.registration.client.RegistrationClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author liu peng bo
 * date: 2021/6/30 11:15
 */
@SpringBootApplication
public class ClubApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ClubApplication.class, args);
        RegistrationClientService clientService = context.getBean(RegistrationClientService.class);
        clientService.register();
    }
}
