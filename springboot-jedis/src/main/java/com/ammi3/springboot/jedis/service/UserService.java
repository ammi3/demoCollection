package com.ammi3.springboot.jedis.service;

import com.ammi3.springboot.jedis.domain.User;

public interface UserService {

    public String getString(String key);

    public void expireStr(String key, String value);


    public User selectById(String id);
}
