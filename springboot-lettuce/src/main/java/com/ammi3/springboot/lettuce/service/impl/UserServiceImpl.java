package com.ammi3.springboot.lettuce.service.impl;

import com.ammi3.springboot.lettuce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String getString(String key) {
        System.out.println(redisTemplate);
        log.info("RedisTemplate测试");
        return null;
    }
}
