package com.kgc.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.User;
import com.kgc.provider.dto.UserExample;
import com.kgc.provider.dto.WxUser;
import com.kgc.provider.dto.WxUserExample;
import com.kgc.provider.mapper.UserMapper;
import com.kgc.provider.mapper.WxUserMapper;
import com.kgc.provider.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Campbell on 2019/10/18 14:59
 */
@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private UserMapper userMapper;

    public int add(WxUser wxUser){
        return wxUserMapper.insertSelective(wxUser);
    }

    @Override
    public boolean isExit(WxUser wxUser) {
        WxUserExample wxUserExample=new WxUserExample();
        wxUserExample.createCriteria().andOpenidEqualTo(wxUser.getOpenid());
        List<WxUser> list=wxUserMapper.selectByExample(wxUserExample);
        if(list.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public WxUser getWxUser(String openId) {
        WxUserExample wxUserExample=new WxUserExample();
        wxUserExample.createCriteria().andOpenidEqualTo(openId);
        List<WxUser> list=wxUserMapper.selectByExample(wxUserExample);
        return list.get(0);
    }

    @Override
    public boolean bindPhone(String phone, String openId) {
        WxUserExample wxUserExample=new WxUserExample();
        wxUserExample.createCriteria().andOpenidEqualTo(openId);
        WxUser wxUser=new WxUser();
        wxUser.setPhone(phone);
        boolean flag=wxUserMapper.updateByExampleSelective(wxUser,wxUserExample)==1?true:false;
        return flag;
    }

    @Override
    public void bindUser(String phone) {
        User user=new User();
        user.setUserPhone(phone);
        user.setExp(0);
        user.setBirthmodify(0);
        user.setUserLevel(0);
        user.setIntegral(0);
        userMapper.insert(user);

    }
}
