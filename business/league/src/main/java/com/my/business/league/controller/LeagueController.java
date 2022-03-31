package com.my.business.league.controller;

import com.my.common.instrument.host.HostUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liu peng bo
 * @date 2022/3/31 下午6:18
 */
@RestController
@RequestMapping("/league/base")
public class LeagueController {
    @GetMapping("/get")
    public String get() {
        return HostUtils.getHost() + " 法甲";
    }
}
