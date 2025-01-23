/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kim.spring.springmvc.demos.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;


@Slf4j
@Controller
public class BasicController {

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


}
