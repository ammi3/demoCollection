package com.ammi3.springboot.lettuce.service.impl;

import com.ammi3.springboot.lettuce.domain.User;
import com.ammi3.springboot.lettuce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getString(String key) {
        String res = null;
        log.info("RedisTemplate测试");
        ValueOperations<String, String> result = redisTemplate.opsForValue();
        if(redisTemplate.hasKey(key)) {
            String value = result.get(key);
            System.out.println(key+"的值为:"+value);
            res = value;
        } else {
            String val = "Test";
            result.set(key, val);
            System.out.println("到Mysql中查询数据");
            res = val;
        }
        return res;
    }

    @Override
    public void expireStr(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 25, TimeUnit.SECONDS); // 设置生存时间
    }

    @Override
    public User selectById(String id) {
        User user = new User();
        if(redisTemplate.opsForHash().hasKey("user", id)) {
            user = (User) redisTemplate.opsForHash().get("user", id);
        } else {
            log.info("查询Mysql");
            user.setId(id);
            user.setName("赵小爷");
            user.setAge(100);
            redisTemplate.opsForHash().put("user", id, user);
        }
        return user;
    }
}
