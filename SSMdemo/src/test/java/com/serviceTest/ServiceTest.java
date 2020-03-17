package com.serviceTest;

import com.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ServiceTest {
    @Test
    public void run() {
        //获取context上下文
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        //得到service对象
        UserService service = (UserService)context.getBean("userService");
        //调用测试
        service.queryAll();
    }
}
