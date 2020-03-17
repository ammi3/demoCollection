package com.shiro.test.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("index.html")
    public String index() {
        return "index";
    }

    @RequestMapping("error.html")
    public String error() {
        return "error";
    }
}
