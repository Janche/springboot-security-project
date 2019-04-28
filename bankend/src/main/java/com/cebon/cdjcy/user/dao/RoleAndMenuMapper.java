package com.cebon.cdjcy.user.dao;

import com.cebon.cdjcy.common.core.Mapper;
import com.cebon.cdjcy.user.domain.RoleAndMenu;

import java.util.List;

public interface RoleAndMenuMapper extends Mapper<RoleAndMenu> {


    /**
     * 批量添加角色对应权限
     * @param roleAndMenuList
     */
    public void addList(List<RoleAndMenu> roleAndMenuList);

    /**
     * 根据角色id删除对应权限
     * @param id 角色id
     */
    public void deleteByRoleId(long id);

}