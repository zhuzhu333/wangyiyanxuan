package com.kgc.provider.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.User;
import com.kgc.provider.dto.UserExample;
import com.kgc.provider.mapper.UserMapper;
import com.kgc.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

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

    public User login(String userName,String userPassword){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (!CollectionUtils.isEmpty(userList)){
            if (userPassword.equals(userList.get(0).getUserPassword())){
                return userList.get(0);
            }
        }
        return null;
    }
}
