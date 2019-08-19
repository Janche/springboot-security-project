package com.example.janche.user.dao;

import com.example.janche.common.core.Mapper;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.user.UserDTO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends Mapper<User> {
    /**
     * 根据用户名获取权限
     * @param username
     * @return
     */
    UserDTO getRolesByUsername(@Param("username") String username);
}