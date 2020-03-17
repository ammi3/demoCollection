package com.shiro.test.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


@Controller
public class LoginController {
    @RequestMapping(value = "gologin.html")
    public String goLogin() {
        return "gologin";
    }

    @RequestMapping("login.html")
    public String login(String username, String password, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin@shiro.com", "admin");
        try {
            subject.login(token);
            return "redirect:index.html";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            request.setAttribute("error","用户名或密码错误");
            return "login";
        }
    }
}
