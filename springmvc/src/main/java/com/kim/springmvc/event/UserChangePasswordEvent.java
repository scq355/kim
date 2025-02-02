package com.kim.springmvc.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 */
public class UserChangePasswordEvent extends ApplicationEvent {

    private String userId;

    public UserChangePasswordEvent(String userId) {
        super(new Object());
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
