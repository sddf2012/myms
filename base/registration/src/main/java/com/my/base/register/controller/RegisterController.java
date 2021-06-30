package com.my.base.register.controller;

import com.registration.base.RegisterInstance;
import com.registration.base.RegisterInstanceConfig;
import com.registration.server.RegistrationServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author liu peng bo
 * date: 2021/6/22 11:01
 */
@RestController
@RequestMapping("/registration")
public class RegisterController {
    @Autowired
    private RegistrationServerService service;

    @PostMapping("/register")
    public Boolean register(@RequestBody RegisterInstanceConfig config) {
        return service.register(config);
    }

    @PostMapping("/instances/{serviceId}")
    public Set<RegisterInstance> instances(@PathVariable String serviceId) {
        return service.instances(serviceId);
    }

}
