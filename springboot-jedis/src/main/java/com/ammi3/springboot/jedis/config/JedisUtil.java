package com.ammi3.springboot.jedis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void close(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }

    public long caclTimeHour(int hours) {
        long second = hours*60*60;
        return second;
    }
}

