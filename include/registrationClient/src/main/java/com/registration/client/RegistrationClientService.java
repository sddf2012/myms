package com.registration.client;

import com.alibaba.fastjson.JSON;
import com.my.common.instrument.http.HttpClientUtils;
import com.my.common.instrument.http.HttpRespInfo;
import com.registration.base.RegisterInstance;
import com.registration.base.RegistrationConst;
import com.registration.base.config.RegisterConfig;
import com.registration.base.config.RegisterInstanceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liu peng bo
 * date: 2021/6/29 16:12
 */
@Slf4j
public class RegistrationClientService {
    private RegisterConfig config;

    private AtomicBoolean addRenewTask = new AtomicBoolean(false);

    public void setConfig(RegisterConfig config) {
        this.config = config;
    }

    public void register() {
        RegisterInstanceConfig instanceConfig = config.getInstance();
        try {
            HttpRespInfo respInfo = HttpClientUtils.doPostWithBody(config.getClient().getServiceUrl() + RegistrationConst.REGISTER_PATH_SUFFIX, null, JSON.toJSONString(instanceConfig));
            if (!respInfo.isSuccess()) {
                log.error("instance注册失败!{},error:{}", instanceConfig.getInstanceInfo(), respInfo.getContent());
            }
        } catch (Exception e) {
            log.error("instance注册失败!{}", instanceConfig.getInstanceInfo(), e);
        }
        log.info("instance注册成功!{}", instanceConfig.getInstanceInfo());
        if (addRenewTask.compareAndSet(false, true)) {
            addRenewTask();
        }
    }

    private void renew() {
        RegisterInstanceConfig instanceConfig = config.getInstance();
        try {
            HttpRespInfo respInfo = HttpClientUtils.doPostWithBody(config.getClient().getServiceUrl() + RegistrationConst.RENEW_PATH_SUFFIX, null, JSON.toJSONString(instanceConfig));
            if (!respInfo.isSuccess()) {
                log.error("instance续约失败!{},error:{}", instanceConfig.getInstanceInfo(), respInfo.getContent());
            }
        } catch (Exception e) {
            log.error("instance续约失败!{}", instanceConfig.getInstanceInfo(), e);
        }
        log.info("instance续约成功!{}", instanceConfig.getInstanceInfo());
    }

    public List<RegisterInstance> getInstance(String serviceId) {
        try {
            String instances = HttpClientUtils.doPost(config.getClient().getServiceUrl() + RegistrationConst.GET_INSTANCES_MIDDLE + serviceId).getContent();
            if (StringUtils.hasText(instances)) {
                return JSON.parseArray(instances, RegisterInstance.class);
            }
            throw new RuntimeException("instance(" + serviceId + ") not exist");
        } catch (Exception e) {
            log.error("获取实例失败!serviceId:{}", serviceId, e);
            throw new RuntimeException(e);
        }

    }

    private void addRenewTask() {
        RegisterInstanceConfig instanceConfig = config.getInstance();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, r -> new Thread(r, "renew-1"));
        executor.scheduleAtFixedRate(this::renew, instanceConfig.getRenewIntervalTime(), instanceConfig.getRenewIntervalTime(), TimeUnit.SECONDS);
    }
}
