package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kgc.consumer.config.custom.CurrentUser;
import com.kgc.consumer.config.custom.LoginRequired;
import com.kgc.consumer.contants.UserContant;
import com.kgc.consumer.model.WXPayModel;
import com.kgc.consumer.service.SuperManPayService;
import com.kgc.consumer.utils.RedisUtils;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.utils.wxPayUtils.WxPayUtils;
import com.kgc.consumer.vo.SumVo;
import com.kgc.consumer.vo.SuperManVo;
import com.kgc.consumer.vo.UpdateVo;
import com.kgc.provider.dto.Member;
import com.kgc.provider.dto.Order;
import com.kgc.provider.dto.User;
import com.kgc.provider.service.PersonnalService;
import com.kgc.provider.service.UserService;
import com.kgc.provider.util.CalendarUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/19 8:57
 * @since
 */
@RestController
@Api(tags = "个人中心")
@RequestMapping(value = "/personnalCenter")
public class personnalController {

    @Reference
    private PersonnalService personnalService;
    @Reference
    private UserService userService;
    @Autowired
    private SuperManPayService superManPayService;
    @Autowired
    private WXPayModel wxPayModel;
    @Autowired
    private RedisUtils redisUtils;

    @LoginRequired
    @ApiOperation("积分计算")
    @GetMapping(value = "/integral")
    public ReturnResult integral(@CurrentUser SumVo sumVo) {
        String phone;
        if (null == sumVo.getPhone()) {
            phone = sumVo.getUserPhone();
        } else {
            phone = sumVo.getPhone();
        }
        List<Order> list = personnalService.serchPaiedOrder(phone);
        double totalPrice = 0;
        for (Order obj : list) {
            totalPrice = totalPrice + obj.getGoodPrice();
        }
        int integral = (int) (totalPrice * 0.1);
        boolean flag = personnalService.updateIntegral(phone, integral);
        if (flag) {
            return ReturnResultUtils.returnSuccess(UserContant.USER_UPDATE_INTEGRAL_SUCCESS);
        }
        return ReturnResultUtils.returnFail(UserContant.USER_UPDATE_INTEGRAL_FALIED_CODE, UserContant.USER_UPDATE_INTEGRAL_FALIED);
    }

    @LoginRequired
    @ApiOperation("会员等级计算")
    @GetMapping(value = "/countLevel")
    public ReturnResult countLevel(@CurrentUser SumVo sumVo) {
        String phone;
        if (null == sumVo.getPhone()) {
            phone = sumVo.getUserPhone();
        } else {
            phone = sumVo.getPhone();
        }
        List<Order> list = personnalService.serchPaiedOrder(phone);
        double totalPrice = 0;
        for (Order obj : list) {
            totalPrice = totalPrice + obj.getGoodPrice();
        }
        int userBuyNumber = list.size();
        User user = userService.searchUser(phone);
        int userExp = user.getExp();
        int totalExp = (int) (100 * userBuyNumber + totalPrice * 0.5) + userExp;
        int level = 0;
        if (totalExp >= 30 && totalExp < 90) {
            level = 1;
        } else if (totalExp >= 90 && totalExp < 180) {
            level = 2;
        } else if (totalExp >= 180) {
            level = 3;
        }
        personnalService.updateLevel(phone, level);
        return ReturnResultUtils.returnSuccess(level);

    }

    @LoginRequired
    @ApiOperation("开通超级会员")
    @GetMapping(value = "/openSuperman")
    public String openSuperman(@Valid SuperManVo superManVo,
                               @ApiParam(value = "phone", required = true) @RequestParam(value = "phone", required = true) String phone,
                               HttpServletRequest request) throws Exception {

        redisUtils.set("member", phone, 6000);
        return superManPayService.wxPay(superManVo, phone);
    }


    @RequestMapping(value = "/wxPayNotify")
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        inputStream.close();
        Map<String, String> resultMap = WxPayUtils.xmlToMap(sb.toString());
        //成功回调了
        if ("SUCCESS".equals(resultMap.get("return_code"))) {
            //验证签名与金额
            boolean isCheckSign = WxPayUtils.checkSign(resultMap, wxPayModel.getKey());
            if (isCheckSign) {
                //todo
                String phone = (String) redisUtils.get("member");
                Member member = personnalService.searchMember(phone);
                if (null == member) {
                    personnalService.createMember(phone, 30);
                }

                //xxxx();
                Map<String, String> rMap = Maps.newHashMap();
                rMap.put("return_code", "SUCCESS");
                rMap.put("return_msg", "OK");
                String xml = WxPayUtils.mapToXml(rMap);
                response.getWriter().write(xml);
            }
        }
    }

    @LoginRequired
    @ApiOperation("修改个人信息")
    @GetMapping(value = "/updatePersonnal")
    public ReturnResult updatePersonnal(@CurrentUser SumVo sumVo, @Valid UpdateVo updateVo) {
        String phone;
        if (null == sumVo.getPhone()) {
            phone = sumVo.getUserPhone();
        } else {
            phone = sumVo.getPhone();
        }
        User user = userService.searchUser(phone);
        int birthmodify = user.getBirthmodify();

        if (0 == birthmodify) {
            if (null == updateVo.getBirthday()) {
                BeanUtils.copyProperties(updateVo, user);
                personnalService.updateUserInformation(user);
                return ReturnResultUtils.returnSuccess(UserContant.USER_UPDATE_IMFORMATION);
            } else {
                BeanUtils.copyProperties(updateVo, user);
                user.setBirthmodify(1);
                personnalService.updateUserInformation(user);

                return ReturnResultUtils.returnSuccess(UserContant.USER_UPDATE_IMFORMATION);
            }
        }
        String address = user.getAdress();
        BeanUtils.copyProperties(updateVo, user);
        user.setAdress(address);
        personnalService.updateUserInformation(user);
        return ReturnResultUtils.returnFail(UserContant.USER_UPDATE_IMFORMATION_NO_ADDRESS_CODE, UserContant.USER_UPDATE_IMFORMATION_NO_ADDRESS);
    }

    @LoginRequired
    @ApiOperation("是否为会员")
    @GetMapping(value = "/isMember")
    public ReturnResult isMember(@CurrentUser SumVo sumVo) {
        String phone;
        if (null == sumVo.getPhone()) {
            phone = sumVo.getUserPhone();
        } else {
            phone = sumVo.getPhone();
        }
        Member member = personnalService.searchMember(phone);
        Long createTime = member.getCreateTime().getTime();
        Long endTime = member.getEndTime().getTime();
        Long nowTime = new Date().getTime();
        Long todayTime = CalendarUtil.getStartTimeOfDay(nowTime).getTime();
        if (nowTime > todayTime && nowTime < endTime) {
            return ReturnResultUtils.returnSuccess(UserContant.USER_IS_MEMBER);
        }
        return ReturnResultUtils.returnFail(UserContant.USER_NOT_MEMBER_CODE, UserContant.USER_NOT_MEMBER);
    }
}
