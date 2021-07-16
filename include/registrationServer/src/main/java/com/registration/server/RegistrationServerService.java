package com.registration.server;

import com.registration.base.RegisterInstance;
import com.registration.base.RegisterResult;
import com.registration.base.config.RegisterInstanceConfig;
import com.registration.base.config.RegisterServerConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liu peng bo
 * date: 2021/6/29 16:58
 */
@Data
@Slf4j
public class RegistrationServerService {
    private RegisterServerConfig serverConfig;


    public RegisterResult registerOrRenew(RegisterInstanceConfig instanceConfig, String type) {
        RegisterResult registerResult = new RegisterResult();
        try {
            Map<String, RegisterInstance> registerInfos = Registration.REGISTRATION.computeIfAbsent(instanceConfig.getServiceId(), a -> new ConcurrentHashMap<>());
            registerInfos.compute(instanceConfig.getInstanceUrl(), (key, oldValue) -> {
                RegisterInstance instance;
                if (oldValue != null) {
                    instance = oldValue;
                    instance.setLastRenewTime(new Date());
                } else {
                    instance = new RegisterInstance();
                    BeanUtils.copyProperties(instanceConfig, instance);
                    instance.setRegisterTime(new Date());
                }
                instance.setLastHeartbeatTime(System.currentTimeMillis());
                return instance;
            });
        } catch (Exception e) {
            log.error("instance {} fail!{}", type, instanceConfig.getInstanceInfo(), e);
            registerResult.setSuccess(false);
            registerResult.setErr(e.getMessage());
        }
        log.info("instance {} success!{}", type, instanceConfig.getInstanceInfo());
        return registerResult;
    }

    public List<RegisterInstance> instances(String serviceId) {
        Map<String, RegisterInstance> registerInfos = Registration.REGISTRATION.get(serviceId);
        if (registerInfos == null) {
            return Collections.emptyList();
        }
        List<RegisterInstance> list = new ArrayList<>();
        registerInfos.forEach((url, instance) -> {
            long lastHeartbeatTime = instance.getLastHeartbeatTime();
            if (lastHeartbeatTime < System.currentTimeMillis() - instance.getMinHeartbeatTime() * 1000L) {
                registerInfos.remove(url);
            } else {
                list.add(instance);
            }
        });
        return list;
    }

    public Map<String, Map<String, RegisterInstance>> allInstances() {
        return Registration.REGISTRATION;
    }

    public void autoCheckInstances() {
        long intervalTime = serverConfig.getInstanceCheckIntervalTime();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, r -> new Thread(r, "checkInstances-1"));
        executor.scheduleAtFixedRate(this::checkInstances, intervalTime, intervalTime, TimeUnit.SECONDS);
    }

    private void checkInstances() {
        Registration.REGISTRATION.forEach((serviceId, instances) -> instances.forEach((url, instance) -> {
            long lastHeartbeatTime = instance.getLastHeartbeatTime();
            if (lastHeartbeatTime < System.currentTimeMillis() - instance.getMinHeartbeatTime() * 1000L) {
                instances.remove(url);
            }
        }));
    }
}
