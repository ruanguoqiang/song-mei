package com.xuezhijian.service.Imp;

import com.xuezhijian.mapper.UserMapper;
import com.xuezhijian.model.User;
import com.xuezhijian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    public UserMapper userMapper;

    @Override
    public User findUserByName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public Boolean insertUser(User user) {
        int uid=userMapper.gettotalNumber()+1;
        user.setUid(uid);
        userMapper.insertUser(user);
        userMapper.insertRole(uid);
        return true;
    }
}
