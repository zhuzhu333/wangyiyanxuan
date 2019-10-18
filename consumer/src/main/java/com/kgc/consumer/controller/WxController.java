package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kgc.consumer.utils.RedisUtils;
import com.kgc.consumer.utils.UrlUtils;
import com.kgc.consumer.wx.WxModel;
import com.kgc.provider.dto.WxUser;
import com.kgc.provider.service.WxUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Campbell on 2019/10/18 14:32
 */
@Api(tags = "微信测试")
@Controller
@RequestMapping(value = "/wx")
public class WxController {

    String namespace = "WX:";

    private String wxToken;

    @Autowired
    private WxModel wxModel;
    @Reference
    private WxUserService wxUserService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation("微信登录")
    @GetMapping(value = "/getCode")
    public String getCode(){
        return "redirect:" + wxModel.reqCodeUrl();
    }

    @ResponseBody
    @GetMapping(value = "/callBack")
    public String callBack(String code) {
        String accessTokenUrl = wxModel.reqAccessTokenUrl(code);
        String resultJson = UrlUtils.loadURL(accessTokenUrl);
        JSONObject jsonObject = JSON.parseObject(resultJson);
        String accessToken = jsonObject.getString("access_token");
        String openId = jsonObject.getString("openid");
        String userInfo = UrlUtils.loadURL(wxModel.reqUserInfoUrl(accessToken,openId));
        WxUser wxUser = JSON.parseObject(userInfo,WxUser.class);
        wxToken = wxUser.getOpenid();
        String str = (String) redisUtils.get(namespace + wxToken);
        if(null == str){
            wxUserService.add(wxUser);
            redisUtils.set(namespace + wxToken,userInfo);
        }
        redisUtils.set(wxToken,userInfo,180);
        return wxToken;
    }
}
