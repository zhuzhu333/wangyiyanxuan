package com.kgc.provider.service;

import com.kgc.provider.dto.User;

/**
 * Created by Campbell on 2019/10/18 10:37
 */
public interface UserService {

    int register(User user);

    User login(String userName,String userPassword);
}
