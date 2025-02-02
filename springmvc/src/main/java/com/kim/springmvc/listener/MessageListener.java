package com.kim.springmvc.listener;

import com.kim.springmvc.event.UserChangePasswordEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 事件监听器：继承实现方式
 */
@Component
public class MessageListener implements ApplicationListener<UserChangePasswordEvent> {
    @Override
    public void onApplicationEvent(UserChangePasswordEvent event) {
        System.out.println("收到事件:" + event);
        System.out.println("开始执行业务操作给用户发送短信。用户userId为：" + event.getUserId());
    }
}
