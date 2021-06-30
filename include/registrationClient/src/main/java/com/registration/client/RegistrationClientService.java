package com.registration.client;

import com.alibaba.fastjson.JSON;
import com.my.common.instrument.http.HttpClientUtils;
import com.registration.base.RegisterConfig;
import com.registration.base.RegisterInstance;
import com.registration.base.RegisterInstanceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author liu peng bo
 * date: 2021/6/29 16:12
 */
@Slf4j
public class RegistrationClientService {
    private RegisterConfig config;

    public void setConfig(RegisterConfig config) {
        this.config = config;
    }

    public boolean register() {
        RegisterInstanceConfig instanceConfig = config.getInstance();
        try {
            HttpClientUtils.doPostWithBody(config.getServer().getUrl() + "/registration/register", null, JSON.toJSONString(instanceConfig));
        } catch (Exception e) {
            log.error("注册失败!", e);
            return false;
        }
        return true;
    }

    public List<RegisterInstance> getInstance(String serviceId) {
        try {
            String instances = HttpClientUtils.doPost(config.getServer().getUrl() + "/registration/instances/" + serviceId).getContent();
            if (StringUtils.hasText(instances)) {
                return JSON.parseArray(instances, RegisterInstance.class);
            }
            throw new RuntimeException("instance not exist");
        } catch (Exception e) {
            log.error("获取实例失败!", e);
            throw new RuntimeException(e);
        }

    }
}
