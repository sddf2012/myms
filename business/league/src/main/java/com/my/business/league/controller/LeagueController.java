package com.my.business.league.controller;

import com.my.business.league.service.LeagueService;
import com.my.common.domain.vo.league.LeagueVo;
import com.my.common.instrument.host.HostUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liu peng bo
 * @date 2022/3/31 下午6:18
 */
@Slf4j
@ApiOperation("league controller")
@RestController
@RequestMapping("/league/base")
public class LeagueController {
    @Autowired
    private LeagueService leagueService;

    @GetMapping("/get")
    public String get() {
        log.info("获取俱乐部:{}", "123123");
        return HostUtils.getHost() + " 法甲";
    }

    @GetMapping("/getAll")
    public List<LeagueVo> getAll() {
        return leagueService.getAll();
    }
}
