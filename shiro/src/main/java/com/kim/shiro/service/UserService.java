package com.kim.shiro.service;

import com.kim.shiro.domain.User;
import com.kim.shiro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author scq
 * @date 2020-08-04 13:09:00
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }

}
