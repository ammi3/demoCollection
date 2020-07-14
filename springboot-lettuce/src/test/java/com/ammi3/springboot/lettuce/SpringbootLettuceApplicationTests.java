package com.ammi3.springboot.lettuce;

import com.ammi3.springboot.lettuce.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootLettuceApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        userService.getString("aaa");
    }

}
