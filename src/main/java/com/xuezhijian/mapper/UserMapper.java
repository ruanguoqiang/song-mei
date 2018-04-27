package com.xuezhijian.mapper;

import com.xuezhijian.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    public User findByUserName(String username);

    int insertUser(User user);

    int insertRole(@Param("uid")Integer uid);

    int gettotalNumber();
}
