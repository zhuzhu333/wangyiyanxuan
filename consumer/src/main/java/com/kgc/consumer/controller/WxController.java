package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kgc.consumer.config.custom.CurrentUser;
import com.kgc.consumer.config.custom.LoginRequired;
import com.kgc.consumer.contants.UserContant;
import com.kgc.consumer.utils.RedisUtils;
import com.kgc.consumer.utils.UrlUtils;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.SumVo;
import com.kgc.consumer.wx.WxModel;
import com.kgc.provider.dto.WxUser;
import com.kgc.provider.mapper.UserMapper;
import com.kgc.provider.service.PersonnalService;
import com.kgc.provider.service.UserService;
import com.kgc.provider.service.WxUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Reference
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;


    @ApiOperation("微信登录")
    @GetMapping(value = "/getCode")
    public String getCode() {
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
        String userInfo = UrlUtils.loadURL(wxModel.reqUserInfoUrl(accessToken, openId));
        WxUser wxUser = JSON.parseObject(userInfo, WxUser.class);
        wxToken = wxUser.getOpenid();
        boolean isEixt = wxUserService.isExit(wxUser);
        if (isEixt == false) {
            wxUserService.add(wxUser);
        }
        WxUser getWxUser = wxUserService.getWxUser(openId);
        if (!getWxUser.getPhone().isEmpty()) {
            userService.updateExp(getWxUser.getPhone());
        }
        String wxUserStr = JSONObject.toJSONString(getWxUser);
        redisUtils.set(wxToken, wxUserStr, 180);
        return wxToken;
    }

    @LoginRequired
    @ApiOperation(value = "测试登录状态")
    @GetMapping(value = "/testLogin")
    public void TestLogin(@CurrentUser SumVo sumVo) {
        if (1 == 1) {
            int a = 2;
        }
    }

    @LoginRequired
    @ResponseBody
    @ApiOperation(value = "判断微信用户是否绑定了手机号")
    @GetMapping(value = "/isBindPhone")
    public ReturnResult isBindPhone(@CurrentUser SumVo sumVo) {
        //微信手机号
        String phone = sumVo.getPhone();
        if (null == phone) {
            return ReturnResultUtils.returnFail(UserContant.NOT_BIND_PHONE_CODE, UserContant.NOT_BIND_PHONE);
        }
        return ReturnResultUtils.returnSuccess(UserContant.YES_BIND_PHONE);
    }

    @LoginRequired
    @ResponseBody
    @ApiOperation(value = "绑定手机号/绑定用户信息")
    @GetMapping(value = "/bindPhone")
    public ReturnResult bindPhone(@CurrentUser SumVo sumVo,
                                  @ApiParam(value = "手机号", required = true) @RequestParam(value = "phone") String phone) {
        String openID = sumVo.getOpenid();
        boolean flag = wxUserService.bindPhone(phone, openID);
        if (flag) {
            wxUserService.bindUser(phone);
            return ReturnResultUtils.returnSuccess(UserContant.BIND_PHONE_SUCCESS);
        }
        return ReturnResultUtils.returnFail(UserContant.BIND_PHONE_FAILED_CODE, UserContant.BIND_PHONE_FAILED);
    }
}
