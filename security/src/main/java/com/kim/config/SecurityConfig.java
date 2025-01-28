package com.kim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 开启登录功能
     * 1./login 请求来到登录页
     * 2.重定向到/login?error表示登录失败
     * 3.默认post方式的/login代表处理登录
     * 4.一旦定制loginPage，loginPage的post请求就是登录
     * <p>
     * 开启自动配置注销功能
     * 1.访问/logout 表示注销用户，清空session
     * 2.注销成功会返回/login?logout 页面
     * <p>
     * 开启记住我，登录成功将cookie保存到浏览器，注销会删除cookie
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 定制请求的授权规则
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/userlogin").permitAll()
                        .requestMatchers("/level1/**").hasRole("VIP1")
                        .requestMatchers("/level2/**").hasRole("VIP2")
                        .requestMatchers("/level3/**").hasRole("VIP3")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .usernameParameter("user")
                        .passwordParameter("pwd")
                        .loginPage("/userlogin")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )
                .rememberMe(remember -> remember
                        .rememberMeParameter("remember")
                );

        return http.build();
    }


    /**
     * 指定密码的加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("scq")
                .password(bCryptPasswordEncoder.encode("scq355"))
                .roles("VIP1", "VIP2")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("scq355"))
                .roles("VIP3")
                .build());
        manager.createUser(User.withUsername("guest")
                .password(bCryptPasswordEncoder.encode("scq355"))
                .roles("VIP1")
                .build());
        return manager;
    }

}
