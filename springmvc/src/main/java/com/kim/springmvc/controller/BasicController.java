package com.kim.springmvc.controller;

import com.kim.springmvc.publisher.EventPublisher;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;


@Slf4j
@Controller
public class BasicController {

    @Resource
    private EventPublisher eventPublisher;

    // http://localhost:8080/c1/param8?birthday=2025-01-01
    @RequestMapping("/c1/param8")
    public void dateParam(Date birthday) {
        log.info("birthday:{}", birthday);
    }

    // 使用Servlet原生对象：http://localhost:8080/c1/param9?name=bjsxt
    @RequestMapping("/c1/param9")
    public void servletParam(HttpServletRequest request,
                             HttpServletResponse response,
                             HttpSession session){
        // 原生对象获取参数
        System.out.println(request.getParameter("name"));
        System.out.println(response.getCharacterEncoding());
        System.out.println(session.getId());
    }

    @GetMapping("/c1/param10")
    public void publishEvent(@RequestParam String msg) {
        eventPublisher.pushListener("302492");
        log.info("msg:{}", msg);
    }

}
