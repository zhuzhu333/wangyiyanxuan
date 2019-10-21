package com.kgc.consumer.custom;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;

import com.kgc.consumer.utils.RedisUtils;
import com.kgc.provider.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created By: YunCeng
 * Created on: 2019/10/19:13:55
 */
public class LoginReqComplete implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录，本质是判断方法上有没有写上自定义注解
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        if (methodAnnotation != null) {
            String token = request.getHeader("token");  // 从 http 请求头中取出 token
            if (!StringUtils.isBlank(token)) {
              String userToken = (String) redisUtils.get(token);
                if (StringUtils.isBlank(userToken)) {
                    throw new RuntimeException("login error");
                } else {
                    User user = JSONObject.parseObject(userToken, User.class);
                    request.setAttribute("userToken", user);
                }
            } else {
                //throw new BusinessException(BusinessEnum.TOKEN_IS_NULL,"token is blank");
                 }
        return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
