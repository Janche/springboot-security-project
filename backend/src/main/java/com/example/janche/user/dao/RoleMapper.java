package com.example.janche.user.dao;

import com.example.janche.common.core.Mapper;
import com.example.janche.user.domain.Role;
import com.example.janche.user.dto.RoleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleMapper extends Mapper<Role> {


    /**
     * 添加单个角色信息
     * @param role  角色信息
     */
    public void addRole(Role role);

    /**
     * 删除单个角色
     * @param id 角色id
     */
    public void deleteRole(long id);

    /**
     * 修改单个角色
     * @param role 角色信息
     */
    public void updateRole(Role role);

    /**
     * 通过id获取角色信息
     * @param id 角色id
     * @return Role  角色信息
     */
    public Role findSingleRole(long id);

    /**
     * 获取所有角色信息与权限
     * @return ArrayList<Role> 角色信息集合
     */
    public List<RoleDTO> findAllRole();



    /**
     * 通过角色id获取所有用户
     * @param id 角色id
     * @return List<User> 用户列表
     */
    public RoleDTO findUsersByRoleId(long id);


    /**
     * 根据权限ID获取角色
     * @param menuId
     * @return
     */
    Set<Role> getRoleByMenuId(@Param("menuId") Long menuId);

    /**
     * 根据URL获取角色
     */
    Set<Role> getRolesByUrl(@Param("url") String url);
    /**
     * 根据角色名查询是存在
     * @param rolername 角色名
     * @return Integer 该角色数量
     */
    public Integer findCountByName(String rolername);

    /**
     * 查询用户添加的角色集合
     * @param id
     * @return
     */
    public List<Role> findByParentId(Long id);

    /**
     * 查询角色所有下级角色集合
     * @param index
     * @return  List<Role>
     */
    public List<Role> findByRoleIndex(String index);

}