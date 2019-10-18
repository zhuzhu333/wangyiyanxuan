package com.kgc.consumer.contants;

import org.springframework.stereotype.Component;

/**
 * Created by boot on 2019/9/28.
 */

public final class UserContant {
    //用户注册时判断用户名是否重复的namespace
    public static final String REDIS_USER_LOGIN_NAME_SPACE = "USER_REGISTER_NAME:";
    public static final int USER_IS_LOGIN_FAIL_CODE = 250;
    public static final int USER_IS_REGISTER_FAIL_CODE = 350;
    public static final int USER_IS_DEL_FAIL_CODE = 450;

}
