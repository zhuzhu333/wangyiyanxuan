package com.kgc.provider.service;

import com.kgc.provider.dto.WxUser;

/**
 * Created by Campbell on 2019/10/18 14:59
 */
public interface WxUserService {

    int add(WxUser wxUser);
    public boolean isExit(WxUser wxUser);
    WxUser getWxUser(String openId);
    boolean bindPhone(String phone,String openId);
    void bindUser(String phone);
}
