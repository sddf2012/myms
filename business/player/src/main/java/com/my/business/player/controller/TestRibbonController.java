package com.my.business.player.controller;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author liu peng bo
 * @date 2022/3/30 下午4:51
 */
@RestController
@RequestMapping("/test/ribbon")
public class TestRibbonController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getClub")
    public String getClub() throws URISyntaxException {
        return restTemplate.getForEntity(new URI("http://BUSINESS-CLUB/club/base/getClub"), String.class ).getBody();
    }
}
