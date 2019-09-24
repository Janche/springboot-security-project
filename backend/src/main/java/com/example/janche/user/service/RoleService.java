package com.example.janche.user.service;

import com.example.janche.common.core.Service;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.domain.Role;
import com.example.janche.user.dto.LoginUserDTO;
import com.example.janche.user.dto.TreeNodeDTO;
import com.example.janche.user.dto.role.RoleConditionDTO;
import com.example.janche.user.dto.role.RoleDTO;
import com.example.janche.user.dto.role.RoleInputDTO;
import com.example.janche.user.dto.role.RoleOutpDTO;

import java.util.List;
import java.util.Set;

/**
* @author lirong
* @Description:
* @date 2019-7-18 18:21:55
*/
public interface RoleService extends Service<Role> {

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @return
     */
    List<Role> list(PageParam pageParam, RoleConditionDTO dto, LoginUserDTO userDTO);

    /**
     * 下拉框角色列表
     * @return
     */
    List<Role> listAll();

    /**
     * 添加单个角色信息
     * @param inputDTO  角色信息
     */
    void addRole(RoleInputDTO inputDTO);

    /**
     * 删除单个角色
     * @param id 角色id
     */
    void deleteRole(Long id);

    /**
     * 修改单个角色
     * @param inputDTO 角色信息
     */
    void updateRole(RoleInputDTO inputDTO);

    /**
     * 获取角色详情
     * @param id
     * @return
     */
    RoleOutpDTO findOne(Long id);

    /**
     * 获取角色的权限信息
     * @return
     */
    RoleDTO getMenusByRoleId(Long id);

    /**
     * 获取权限树
     * @return
     */
    TreeNodeDTO getMenuTree();

    /**
     * 批量删除角色
     * @param ids
     */
    void deleteRole(String ids);

    /**
     * 批量冻结成功
     * @param ids
     * @param status
     */
    void frozeRole(String ids, Integer status);

    /**
     * 根据角色Ids获取权限Ids
     * @param ids
     * @return
     */
    Set<Long> getMenuIdsByRoleIds(String ids);
}
