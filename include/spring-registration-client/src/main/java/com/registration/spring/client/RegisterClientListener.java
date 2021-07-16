package com.registration.spring.client;

import com.registration.client.RegistrationClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author liu peng bo
 * date: 2021/7/2 16:40
 */
@Slf4j
public class RegisterClientListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("ApplicationContext initialized,begin register!");
        ApplicationContext applicationContext = event.getApplicationContext();
        RegistrationClientService clientService = applicationContext.getBean(RegistrationClientService.class);
        clientService.register();
    }
}
