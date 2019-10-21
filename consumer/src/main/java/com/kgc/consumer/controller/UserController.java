package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.kgc.consumer.contants.UserContant;
import com.kgc.consumer.utils.RedisUtils;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.UserVo;
import com.kgc.provider.dto.User;
import com.kgc.provider.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Campbell on 2019/10/18 10:52
 */
@Api(tags = "用户测试")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Reference
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "用户注册")
    @GetMapping(value = "/register")
    public ReturnResult register(@Valid UserVo userVo) {
        String namespace = UserContant.REDIS_USER_LOGIN_NAME_SPACE;
        Object obj = redisUtils.get(namespace + userVo.getUserName());
        if (null == obj) {
            User user = new User();
            BeanUtils.copyProperties(userVo, user);
            user.setUserLevel(0);
            user.setIntegral(0);
            user.setGrowthValue(0);
            user.setSuperman(0);
            user.setIsDelete(0);
            user.setBirthmodify(0);
            user.setExp(0);
            userService.register(user);
            redisUtils.set(namespace + userVo.getUserName(), userVo.getUserName());
            return ReturnResultUtils.returnSuccess();
        }
        return ReturnResultUtils.returnFail(UserContant.USER_IS_REGISTER_FAIL_CODE, "用户已存在！");
    }

    @ApiOperation(value = "用户登录")
    @GetMapping(value = "login")
    public ReturnResult login(@ApiParam(value = "手机号", required = true) @RequestParam(value = "phone") String phone,
                              @ApiParam(value = "密码", required = true) @RequestParam(value = "userPassword") String userPassword,
                              @ApiParam(value = "是否同意", required = true, defaultValue = "1") @RequestParam(value = "isAgree") int isAgree,
                              HttpServletRequest request) {
        String token = request.getSession().getId();
        User user = userService.login(phone, userPassword);
        if (null != user) {
            String str = JSONObject.toJSONString(user);
            redisUtils.set(token, str, 600);
            userService.updateExp(phone);
            return ReturnResultUtils.returnSuccess(token);
        }
        return ReturnResultUtils.returnFail(UserContant.USER_IS_LOGIN_FAIL_CODE, "用户名或密码错误！");
    }

}
