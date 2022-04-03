package com.my.business.player.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liu peng bo
 * @date 2022/3/30 下午4:51
 */
@RestController
@RequestMapping("/test/configCenter")
public class TestConfigCenterController {
    @Value("${test.configCenter.key1}")
    private String configCenter;

    @GetMapping("/isValid")
    public String getClub() {
        return configCenter;
    }
}
