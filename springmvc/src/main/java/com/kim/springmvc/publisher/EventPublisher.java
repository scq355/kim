package com.kim.springmvc.publisher;

import com.kim.springmvc.event.UserChangePasswordEvent;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 事件发布器
 */
@Component
public class EventPublisher {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void pushListener(String msg) {
        applicationEventPublisher.publishEvent(new UserChangePasswordEvent(msg));
    }

}
