package com.registration.server;

import com.registration.base.RegisterInstance;
import com.registration.base.RegisterInstanceConfig;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liu peng bo
 * date: 2021/6/29 16:58
 */
public class RegistrationServerService {
    public boolean register(RegisterInstanceConfig instanceConfig) {
        RegisterInstance instance = new RegisterInstance();
        BeanUtils.copyProperties(instanceConfig, instance);
        Set<RegisterInstance> registerInfos = Registration.REGISTRATION.computeIfAbsent(instanceConfig.getServiceId(), a->Collections.synchronizedSet(new HashSet<>()));
        if (registerInfos.contains(instance)) {
            return true;
        }
        registerInfos.add(instance);
        return true;
    }

    public Set<RegisterInstance> instances(String serviceId) {
        return Registration.REGISTRATION.get(serviceId);
    }
}
