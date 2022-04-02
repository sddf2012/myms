package com.my.common.server.feign.club.fallback;

import com.my.common.instrument.host.HostUtils;
import com.my.common.server.feign.club.ClubControllerApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author liu peng bo
 * @date 2022/4/2 下午5:54
 */
@Component
public class ClubControllerFallbackFactory implements FallbackFactory<ClubControllerApi> {
    @Override
    public ClubControllerApi create(Throwable cause) {
        return timeout -> HostUtils.getHost() + " request error:" + cause.getMessage();
    }
}
