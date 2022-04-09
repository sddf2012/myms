package com.my.business.player.controller;

import com.my.business.player.config.PlayerBinding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liu peng bo
 * @date 2022/4/9 下午5:20
 */
@Slf4j
@RestController
@RequestMapping("/test/stream")
public class TestStreamController {
    @Autowired
    private PlayerBinding binding;

    @GetMapping("/send")
    public String send(@RequestParam(required = false) String message) {
        if (StringUtils.isEmpty(message)) {
            message = "default message " + System.currentTimeMillis();
        }
        binding.testStreamOutput().send(MessageBuilder.withPayload(message).build());
        log.info("send message:" + message);
        return "success";
    }

    @StreamListener(value = PlayerBinding.TEST_STREAM_INPUT)
    public void testStreamInput(String message) {
        log.info("receive message:" + message);
    }
}
