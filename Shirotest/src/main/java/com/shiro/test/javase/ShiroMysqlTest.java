package com.shiro.test.javase;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class ShiroMysqlTest {
    public static void main(String[] args) {
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-mysql.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin@shiro.com", "admin");
        try {
            subject.login(token);
            if(subject.isAuthenticated()) {
                System.out.println("登录成功");
                if(subject.hasRole("admin")) {
                    System.out.println("有admin这个角色");
                } else {
                    System.out.println("没有admin这个角色");
                }
                if (subject.isPermitted("/add.html")) {
                    System.out.println("有add权限");
                } else {
                    System.out.println("无add权限");
                }
                if (subject.isPermittedAll("add","update")) {
                    System.out.println("有\"add\",\"update\"权限");
                } else {
                    System.out.println("没有\"add\",\"update\"权限");
                }
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("用户名或密码错误，登陆失败");
        }



    }
}
