package com.kim.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author scq
 * @date 2020-08-04 10:36:00
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getFactoryBean(@Qualifier("shiroSecurityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        /**
         * shiro内置过滤器，实现URL拦截
         * anon:无需认证即可访问，
         * authc:必须认证才可访问，
         * user:记住我可以直接访问，
         * perms:资源权限必须授予才可访问，
         * role:资源必须得到角色授权才可访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/user/add", "authc");
//        filterMap.put("/user/update", "authc");
        filterMap.put("/user/index", "anon");
        filterMap.put("/login", "anon");
        // 授权过滤器,授权拦截后shiro会自动跳转到未授权的页面
        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update", "perms[user:update]");
        filterMap.put("/user/*", "authc");

        factoryBean.setLoginUrl("/login");
        factoryBean.setUnauthorizedUrl("/auth");

        factoryBean.setFilterChainDefinitionMap(filterMap);

        return factoryBean;
    }

    @Bean(name = "shiroSecurityManager")
    public DefaultWebSecurityManager getSecurityManager(@Qualifier("userRealm") UserRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean(name = "userRealm")
    public UserRealm getUserRealm() {
        return new UserRealm();
    }

    /**
     * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

}
