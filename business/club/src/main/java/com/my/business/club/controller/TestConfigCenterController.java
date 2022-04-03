package com.my.business.club.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liu peng bo
 * @date 2022/3/30 下午4:51
 */
@RefreshScope
@RestController
@RequestMapping("/test/configCenter")
public class TestConfigCenterController {
    @Value("${test.configCenter.key1}")
    private String key1;

    @GetMapping("/key1")
    public String getClub() {
        return key1;
    }
}
