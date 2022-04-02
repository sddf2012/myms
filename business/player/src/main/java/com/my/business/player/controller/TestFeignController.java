package com.my.business.player.controller;

import com.my.common.server.feign.club.ClubControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liu peng bo
 * @date 2022/3/30 下午4:51
 */
@RestController
@RequestMapping("/test/feign")
public class TestFeignController {
    @Autowired
    private ClubControllerApi clubControllerApi;

    @GetMapping("/getClub")
    public String getClub(@RequestParam(value = "timeout", required = false) Long timeout) {
        return clubControllerApi.getClub(timeout);
    }
}
