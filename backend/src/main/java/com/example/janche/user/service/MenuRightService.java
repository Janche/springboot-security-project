package com.example.janche.user.service;

import com.example.janche.common.core.Service;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.dto.MenuDTO;

import java.util.List;

/**
*
* @author CodeGenerator
* @date 2018-11-01 09:34:51
*/
public interface MenuRightService extends Service<MenuRight> {

    /**
     * 根据分页、排序信息和检索条件查询 数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<MenuRight> list(PageParam pageParam, String query);

    /**
     * 根据id查询权限菜单节点
     * @param id 权限菜单节点id
     * @return  MenuRight 权限菜单信息
     */
    MenuRight findOne(Long id);

    /**
     * 添加一个权限节点
     * @param menuRight  权限菜单实体对象
     */
     void addMenuRight(MenuRight menuRight);

    /**
     * 删除单个权限菜单节点
     * @param id  权限菜单id
     */
     void deleteMenuRight(Long id);

    /**
     * 修改单个权限菜单节点
     * @param menuRight  权限菜单节点对象
     */
    void updateMenuRight(MenuRight menuRight);

    /**
     * 获取用户所有权限
     * @param userId 用户ID
     * @return
     */
    List<MenuDTO> getUserMenus(Long userId);

    /**
     * 获取系统列表
     * @return
     */
    List<MenuRight> getSystemList();
}
