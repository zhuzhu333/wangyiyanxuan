package com.kgc.consumer.custom;



import com.kgc.provider.dto.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created By: YunCeng
 * Created on: 2019/10/19:13:55
 */
public class CurrentComplete implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class)//判断参数的类型，与括号里面的是否相同（User）,如果相同，走下面（resolveArgument）的方法，如果不相同就不走
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        User user = (User) webRequest.getAttribute("userToken", RequestAttributes.SCOPE_REQUEST);
        if (user != null) {
            return user;
        }
        return null;
    }
}
