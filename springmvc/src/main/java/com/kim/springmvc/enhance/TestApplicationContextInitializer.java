package com.kim.springmvc.enhance;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 整个spring容器在刷新之前初始化ConfigurableApplicationContext的回调接口，
 * 简单来说，就是在容器刷新之前调用此类的initialize方法。
 * 这个点允许被用户自己扩展。用户可以在整个spring容器还没被初始化之前做一些事情。
 */
@Component
public class TestApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("[ApplicationContextInitializer]");
    }
}
