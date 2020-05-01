package com.springboot.elasticsearch.web.config.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 拦截器注解
 * @Author: BilBo BaGGins
 * @Date: 2020/4/30 12:52
 */
@Target({ElementType.METHOD})//可以用在方法上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
public @interface LoginRequired {

}
