package com.my.business.league;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liu peng bo
 * @date 2022/3/31 下午6:14
 */
@EnableFeignClients(basePackages ={"com.my.common.server.feign"} )
@SpringCloudApplication
public class LeagueApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeagueApplication.class, args);
    }
}
