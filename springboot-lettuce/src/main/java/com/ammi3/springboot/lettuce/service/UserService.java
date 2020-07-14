package com.ammi3.springboot.lettuce.service;

import com.ammi3.springboot.lettuce.domain.User;
import org.springframework.stereotype.Service;


public interface UserService {
    public String getString(String key);

    public void expireStr(String key, String value);

    public User selectById(String id);
}
