package com.ammi3.springboot.lettuce;

import com.ammi3.springboot.lettuce.domain.User;
import com.ammi3.springboot.lettuce.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootLettuceApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void getString() {
        String result = userService.getString("123");
        System.out.println(result);
    }

    @Test
    void expireStr() {
        userService.expireStr("test", "测试有效期");
    }

    @Test
    void selectById() {
        User user = userService.selectById("1004");
        System.out.println(user);
    }

}
