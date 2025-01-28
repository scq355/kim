package com.kim.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author scq
 * @date 2020-08-04 10:57:00
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @RequestMapping(value = "/index")
    public String index() {
        return "user";
    }

    @RequestMapping(value = "/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping(value = "/update")
    public String update() {
        return "user/update";
    }
}
