package com.example.janche.user.dao;

import com.example.janche.common.core.Mapper;
import com.example.janche.user.domain.Role;
import com.example.janche.user.dto.role.RoleDTO;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends Mapper<Role> {

    /**
     * 获取所有角色信息与权限
     * @return ArrayList<Role> 角色信息集合
     */
    RoleDTO getMenusByRoleId(@Param("id") Long id);
}