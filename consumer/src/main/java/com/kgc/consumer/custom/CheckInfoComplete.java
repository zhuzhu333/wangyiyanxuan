package com.kgc.consumer.custom;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.kgc.consumer.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created By: YunCeng
 * Created on: 2019/10/18:17:17
 */
public class CheckInfoComplete implements HandlerInterceptor {
    /*怎么和它的接口映射的？*/
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //（本质）判断方法上有没有写上自定义注解,这里是判断接口是否需要登录
        CheckInfo methodAnnotation = method.getAnnotation(CheckInfo.class);
        if (methodAnnotation != null){
            String token = request.getHeader("token");  // 从 http 请求头中取出 token

            if(!StringUtils.isBlank(token)){
                //登录检查
                String userToken=(String) redisUtils.get("token");

                if(!StringUtils.isBlank(userToken)){

                    //地址检查



                    //手机号检查


                    //收件人姓名检查



                    return true;
                }else{
                    return false;
                }




            }else{
                return false;
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
