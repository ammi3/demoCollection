package com.ammi3.springboot.jedis;

import com.ammi3.springboot.jedis.domain.User;
import com.ammi3.springboot.jedis.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class SpringbootJedisApplicationTests {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        System.out.println(jedisPool);
    }

    @Test
    void getStringTest() {
        String result = userService.getString("name");
        System.out.println(result);
    }

    @Test
    void expireStrTest() {
        String test = "testKey";
        String value = "测试数据";
        userService.expireStr(test, value);
    }

    @Test
    void selectByIdTest() {
        User user = userService.selectById("1002");
        System.out.println(user);
    }

}
