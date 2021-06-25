package com.my.base.register.controller;

import com.my.base.register.service.RegisterService;
import com.my.common.domain.register.RegisterBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liu peng bo
 * date: 2021/6/22 11:01
 */
@RestController
@RequestMapping("/registration")
public class RegisterController {
    @Autowired
    private RegisterService service;

    @PostMapping("/register")
    public String register(RegisterBo info) {

        return null;
    }

}
