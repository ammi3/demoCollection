package com.ammi3.springboot.jedis.service.impl;

import com.ammi3.springboot.jedis.config.JedisUtil;
import com.ammi3.springboot.jedis.domain.User;
import com.ammi3.springboot.jedis.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@Service
@Log
public class UserServiceImpl implements UserService {

    @Autowired
    private JedisPool jedisPool; // Jedis连接池

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public String getString(String key) {
        String res = null;
        // 得到Jedis对象
        Jedis jedis = jedisPool.getResource();
        //  判断key是否存在
        if(jedis.exists(key)) {
            log.info("查询Redis中的数据");
            res = jedis.get(key);
        } else {
            String val = "ammi3";
            log.info("查询Mysql数据库"+val);
            jedis.set(key, val);
            res = val;
        }
        // 关闭连接
        jedis.close();
        return res;
    }

    @Override
    public void expireStr(String key, String value) {
        Jedis jedis = jedisUtil.getJedis();
        jedis.set(key, value); //永久有效
        jedis.expire(key, 20); //设置生存时间ttl
        log.info(key+"\t 设置值：" + value + "\t" + 20);
        jedisUtil.close(jedis);
    }

    //hash
    @Override
    public User selectById(String id) {
        User user = new User();
        String key = "user:" + id;
        Jedis jedis = jedisUtil.getJedis();
        if(jedis.exists(key)) {
            log.info("查询redis的数据");
            Map<String, String> map = jedis.hgetAll(key);
            System.out.println(map.get("age")+";"+map.get("id")+";"+map.get("name"));
            user.setId(map.get("id"));
            user.setName(map.get("name"));
            user.setAge(Integer.valueOf(map.get("age")));
        } else {
            log.info("查询Mysql数据库");
            user.setId(id);
            user.setName("赵大爷");
            user.setAge(19);
            Map<String, String> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("name", user.getName());
            map.put("age", user.getAge()+"");
            jedis.hmset(key, map);
            log.info("数据存入Redis中");
        }

        jedisUtil.close(jedis);
        return user;
    }

}
