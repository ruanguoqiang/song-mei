package com.xuezhijian.service;

import com.xuezhijian.model.User;

public interface UserService {
    public User findUserByName(String username);

    public Boolean insertUser(User user);
}
