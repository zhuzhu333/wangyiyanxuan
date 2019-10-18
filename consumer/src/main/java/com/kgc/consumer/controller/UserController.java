package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kgc.consumer.contants.UserContant;
import com.kgc.consumer.utils.RedisUtils;
import com.kgc.consumer.utils.result.ReturnResult;
import com.kgc.consumer.utils.result.ReturnResultUtils;
import com.kgc.consumer.vo.UserVo;
import com.kgc.provider.dto.User;
import com.kgc.provider.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ReturnResult register(@Valid UserVo userVo){
        String namespace = UserContant.REDIS_USER_LOGIN_NAME_SPACE;
        Object obj = redisUtils.get(namespace+userVo.getUserName());
        if(null == obj){
            User user = new User();
            BeanUtils.copyProperties(userVo,user);
            user.setUserLevel(1);
            user.setIntegral(0);
            user.setGrowthValue(0);
            user.setSuperman(0);
            user.setIsDelete(0);
            userService.register(user);
            redisUtils.set(namespace+userVo.getUserName(),userVo.getUserName());
            return ReturnResultUtils.returnSuccess();
        }
        return ReturnResultUtils.returnFail(UserContant.USER_IS_REGISTER_FAIL_CODE,"用户已存在！");
    }



}
