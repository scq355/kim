package com.kim.springmvc.listener;

import com.kim.springmvc.event.UserChangePasswordEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 事件监听器：注解形式
 */
@Component
public class KimEventListener {

    @Async
    @EventListener({UserChangePasswordEvent.class})
    public void logListener(UserChangePasswordEvent event) {
        System.out.println("收到事件:" + event);
        System.out.println("开始执行业务操作生成关键日志。用户userId为：" + event.getUserId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, value = UserChangePasswordEvent.class)
    public void messageListener(UserChangePasswordEvent event) {
        System.out.println("收到事件:" + event);
        System.out.println("开始执行业务操作给用户发送短信。用户userId为：" + event.getUserId());
    }
}
