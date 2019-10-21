package com.kgc.consumer.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created By: YunCeng
 * Created on: 2019/10/19:13:56
 */
@Target(ElementType.PARAMETER)//代表了这个自定义注解必须要加在参数列表上
@Retention(RetentionPolicy.RUNTIME)//运行时候有效
public @interface CurrentUser {

}
