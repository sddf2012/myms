package com.my.business.league;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liu peng bo
 * @date 2022/3/31 下午6:14
 */
@SpringCloudApplication
@ComponentScan({"com.my"})
@EntityScan(value = {"com.my.business.league.domain.entity"})
@EnableFeignClients(basePackages ={"com.my.common.server.feign"} )
public class LeagueApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeagueApplication.class, args);
    }
}
