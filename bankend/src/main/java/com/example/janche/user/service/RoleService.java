package com.example.janche.user.service;

import com.example.janche.user.domain.Role;
import com.example.janche.common.core.Service;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.dto.RoleDTO;

import java.util.List;

/**
* @author CodeGenerator
* @Description: // TODO 为类添加注释
* @date 2018-11-08 09:37:24
*/
public interface RoleService extends Service<Role> {

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @return
     */
    List<Role> list(PageParam pageParam);


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
     * 获取所有角色信息
     * @return ArrayList<Role> 角色信息集合
     */
    public List<RoleDTO> findAllRole();


    /**
     * 通过角色id获取所有用户
     * @param id 角色id
     * @return roleDTO 角色对象
     */
    public RoleDTO findUsersByRoleId(long id);


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
    public List<Role> findByRoleIndex(String index,PageParam pageParam);

}
