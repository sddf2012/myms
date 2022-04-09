package com.my.business.player.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author liu peng bo
 * @date 2022/4/9 下午5:22
 */
public interface PlayerBinding {
    String TEST_STREAM_OUTPUT = "testStreamOutput";
    String TEST_STREAM_INPUT = "testStreamInput";

    @Output(value = TEST_STREAM_OUTPUT)
    MessageChannel testStreamOutput();

    @Input(value = TEST_STREAM_INPUT)
    SubscribableChannel testStreamInput();
}
