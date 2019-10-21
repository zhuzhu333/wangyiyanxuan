package com.kgc.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.User;
import com.kgc.provider.dto.UserExample;
import com.kgc.provider.mapper.UserMapper;
import com.kgc.provider.service.ChooseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created BY: WYM
 * Created on: 2019/10/21 11:51
 */
@Service
public class ChooseServiceImpl implements ChooseService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public int updateIntergral(String phone, int reIntegral) {
        return userMapper.updateIntergral(phone,reIntegral);
    }
}
