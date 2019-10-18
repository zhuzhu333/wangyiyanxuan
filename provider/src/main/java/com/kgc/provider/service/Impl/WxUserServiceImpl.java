package com.kgc.provider.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.WxUser;
import com.kgc.provider.mapper.WxUserMapper;
import com.kgc.provider.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Campbell on 2019/10/18 14:59
 */
@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserMapper wxUserMapper;

    public int add(WxUser wxUser){
        return wxUserMapper.insertSelective(wxUser);
    }
}
