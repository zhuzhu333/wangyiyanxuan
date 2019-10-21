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
    public static final String BIND_PHONE_FAILED = "绑定用户失败！";
    public static final int BIND_PHONE_FAILED_CODE = 601;
    public static final String BIND_PHONE_SUCCESS = "绑定用户成功！";
    public static final String NOT_BIND_PHONE = "该帐号未绑定！";
    public static final String YES_BIND_PHONE = "该帐号已经绑定！";
    public static final int NOT_BIND_PHONE_CODE = 602;
    public static final String USER_UPDATE_INTEGRAL_SUCCESS = "更新积分成功！";
    public static final String USER_UPDATE_INTEGRAL_FALIED = "更新积分失败！";
    public static final int USER_UPDATE_INTEGRAL_FALIED_CODE = 603;
    public static final String USER_UPDATE_IMFORMATION = "更新用户信息成功！";
    public static final String USER_UPDATE_IMFORMATION_NO_ADDRESS = "更新用户信息成功,但是地址已经更新过，只能修改一次！";
    public static final int USER_UPDATE_IMFORMATION_NO_ADDRESS_CODE = 604;
    public static final int USER_NOT_MEMBER_CODE = 605;
    public static final String USER_NOT_MEMBER ="您不是会员！";
    public static final String USER_IS_MEMBER="您是会员！";
    public static final String USER_BUY="USER_BUY";
    public static final String BUY_SUCCESS="抢购成功！";
    public static final String BUY_FAIL="抢购失败！";
    public static final String STOCK_NULL="库存不足！";

}
