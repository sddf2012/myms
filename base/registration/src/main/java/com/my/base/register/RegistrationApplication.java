package com.my.base.register;

import com.registration.spring.server.EnableRegistrationServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liu peng bo
 * date: 2021/6/22 11:12
 */
@Slf4j
@EnableRegistrationServer
@SpringBootApplication
public class RegistrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegistrationApplication.class, args);
        log.info("registration启动完成!");
    }
}
