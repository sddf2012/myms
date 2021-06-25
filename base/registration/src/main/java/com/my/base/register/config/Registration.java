package com.my.base.register.config;

import com.my.base.register.domain.RegisterInfo;
import com.my.common.domain.register.RegisterBo;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liu peng bo
 * date: 2021/6/22 15:25
 */
public class Registration {
    public static final ConcurrentHashMap<String, Set<RegisterInfo>> REGISTRATION = new ConcurrentHashMap<>(8);
}
