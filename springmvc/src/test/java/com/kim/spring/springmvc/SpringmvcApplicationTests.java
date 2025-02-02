package com.kim.spring.springmvc;

import com.kim.springmvc.SpringmvcApplication;
import com.kim.springmvc.event.UserChangePasswordEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest(classes = SpringmvcApplication.class)
class SpringmvcApplicationTests {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void contextLoads() {
        applicationEventPublisher.publishEvent(new UserChangePasswordEvent("123"));
    }


}
