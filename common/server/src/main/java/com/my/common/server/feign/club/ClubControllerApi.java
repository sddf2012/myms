package com.my.common.server.feign.club;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liu peng bo
 * @date 2022/3/31 下午6:27
 */
@FeignClient(name = "business-club", path = "/club/base")
public interface ClubControllerApi {
    @GetMapping("/getClub")
    String getClub();
}