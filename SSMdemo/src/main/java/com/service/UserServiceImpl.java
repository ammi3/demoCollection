package com.service;

import com.dao.UserDAO;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void insertUser(User user) {
        System.out.println("service插入数据");
        userDAO.insertUser(user);
    }

    @Override
    public List<User> queryAll() {
        System.out.println("service查询全部数据");
        return userDAO.queryAll();
    }



}
