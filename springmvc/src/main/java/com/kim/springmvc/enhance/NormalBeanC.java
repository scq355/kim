package com.kim.springmvc.enhance;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，
 * 凡是继承该接口的类，在初始化bean的时候都会执行该方法。
 * 这个扩展点的触发时机在postProcessAfterInitialization之前。
 *
 * 使用场景：用户实现此接口，来进行系统启动的时候一些业务指标的初始化工作。
 */
@Component
public class NormalBeanC implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[InitializingBean] NormalBeanC");
    }
}
