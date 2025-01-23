package com.kim.drools;

import com.kim.drools.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class DroolsApplicationTests {


    @Resource
    private KieContainer kieContainer;

    @Test
    public void test() {
        KieSession session = kieContainer.newKieSession();

        Order order = new Order();
        order.setAmount(1300);

        session.insert(order);

        session.fireAllRules();

        session.dispose();;

        log.info("订单打折后金额={},积分={}", order.getAmount(), order.getScore());
    }

}
