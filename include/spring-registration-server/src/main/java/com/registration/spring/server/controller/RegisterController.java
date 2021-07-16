package com.registration.spring.server.controller;

import com.registration.base.RegisterInstance;
import com.registration.base.RegisterResult;
import com.registration.base.RegistrationConst;
import com.registration.base.config.RegisterInstanceConfig;
import com.registration.server.RegistrationServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    public RegisterResult register(@RequestBody RegisterInstanceConfig config) {
        return service.registerOrRenew(config, RegistrationConst.TYPE_REGISTER);
    }

    @PostMapping("/renew")
    public RegisterResult renew(@RequestBody RegisterInstanceConfig config) {
        return service.registerOrRenew(config,RegistrationConst.TYPE_RENEW);
    }

    @PostMapping("/allInstances")
    public Map<String, Map<String, RegisterInstance>> instances() {
        return service.allInstances();
    }

    @PostMapping("/instances/{serviceId}")
    public List<RegisterInstance> instances(@PathVariable String serviceId) {
        return service.instances(serviceId);
    }
}
