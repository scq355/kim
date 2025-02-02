package com.kim.springmvc.enhance;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 为要实例化的bean作一个代理，比如为该对象的所有的方法作一个拦截，
 * 在调用前后输出一行log，模仿ProxyFactoryBean的功能。
 */
@Component
public class TestFactoryBean implements FactoryBean<TestFactoryBean.TestFactoryInnerBean> {
    @Override
    public TestFactoryBean.TestFactoryInnerBean getObject() throws Exception {
        System.out.println("[FactoryBean] getObject");
        return new TestFactoryBean.TestFactoryInnerBean();
    }

    @Override
    public Class<?> getObjectType() {
        return TestFactoryBean.TestFactoryInnerBean.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static class TestFactoryInnerBean{

    }
}
