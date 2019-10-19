package com.kgc.consumer.config.custom;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;

import com.kgc.consumer.utils.RedisUtils;
import com.kgc.consumer.vo.SumVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class LoginReqComplete implements HandlerInterceptor {
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        if (methodAnnotation != null) {
            String token = request.getHeader("token");
            String wxToken = request.getHeader("wxToken");
            if (StringUtils.isNotEmpty(token) || StringUtils.isNotEmpty(wxToken)) {
                String userToken = (String) redisUtils.get(StringUtils.isNotEmpty(token) ? token : wxToken);
                if (StringUtils.isNotEmpty(userToken)) {
                    SumVo sumVo = JSONObject.parseObject(userToken, SumVo.class);

                    request.setAttribute("userToken", sumVo);
                    return true;
                } else {
                    throw new RuntimeException("login error");
                }

            } else {
                throw new Exception("token is blank");
            }
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
