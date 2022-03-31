package com.my.common.instrument.host;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liu peng bo
 * @date 2022/3/31 下午6:25
 */
public class HostUtils {
    public static String getHost() {
        String host = "";
        try {
            InetAddress address = InetAddress.getLocalHost();
            host = address.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }
}
