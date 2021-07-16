package com.registration.server;

import com.registration.base.RegisterInstance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liu peng bo
 * date: 2021/6/22 15:25
 */
public class Registration {
    public static final ConcurrentHashMap<String, Map<String,RegisterInstance>> REGISTRATION = new ConcurrentHashMap<>(8);
}
