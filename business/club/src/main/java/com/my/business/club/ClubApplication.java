package com.my.business.club;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liu peng bo
 * date: 2021/6/30 11:15
 */
@Slf4j
@SpringBootApplication
public class ClubApplication {
    public static void main(String[] args) {
       SpringApplication.run(ClubApplication.class, args);
       log.info("club started!");
    }
}
