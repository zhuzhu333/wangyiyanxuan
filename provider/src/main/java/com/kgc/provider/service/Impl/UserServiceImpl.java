package com.kgc.provider.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.User;
import com.kgc.provider.mapper.UserMapper;
import com.kgc.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Campbell on 2019/10/18 10:38
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public int register(User user){
        return userMapper.insertSelective(user);
    }
}
