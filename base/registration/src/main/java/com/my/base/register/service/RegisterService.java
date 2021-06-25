package com.my.base.register.service;

import com.my.base.register.config.Registration;
import com.my.base.register.domain.RegisterInfo;
import com.my.common.domain.register.RegisterBo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author liu peng bo
 * date: 2021/6/22 11:11
 */
@Service
public class RegisterService {


    public boolean register(RegisterBo bo) {
        RegisterInfo info = generateRegisterInfo(bo);
        Set<RegisterInfo> registerInfos = Registration.REGISTRATION.putIfAbsent(info.getServiceId(), Collections.synchronizedSet(new HashSet<>()));
        if (registerInfos.contains(info)) {
            return true;
        }
        registerInfos.add(info);
        return true;
    }

    private RegisterInfo generateRegisterInfo(RegisterBo bo) {
        RegisterInfo info = new RegisterInfo();
        BeanUtils.copyProperties(bo, info);
        info.setRegisterTime(new Date());
        return info;
    }
}
