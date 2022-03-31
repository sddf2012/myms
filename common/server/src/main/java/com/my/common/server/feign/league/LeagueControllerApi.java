package com.my.common.server.feign.league;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liu peng bo
 * @date 2022/3/31 下午6:20
 */
@FeignClient(value = "business-league",path = "/league/path")
public interface LeagueControllerApi {
    @GetMapping("/get")
    String get();
}
