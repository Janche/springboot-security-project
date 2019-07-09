package com.example.janche.user.dao;

import com.example.janche.common.core.Mapper;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.domain.MenuRight;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface MenuRightMapper extends Mapper<MenuRight> {
    /**
     * 根据分页、排序信息和检索条件查询 数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<MenuRight> list(PageParam pageParam, String query);

    /**
     * 搜索所有权限菜单节点、
     * return  ArrayList<MenuRight>  权限菜单节点集合
     */
    public ArrayList<MenuRight> findAllMenuRight();

    /**
     * 根据id查询权限菜单节点
     * @param id 权限菜单节点id
     * @return  MenuRight 权限菜单信息
     */
    public MenuRight findMenuRightById(long id);

    /**
     * 添加一个权限节点
     * @param menuRight  权限菜单实体对象
     */
    public  void addMenuRight(MenuRight menuRight);

    /**
     * 删除单个权限菜单节点
     * @param id  权限菜单id
     */
    public  void deleteMenuRight(long id);

    /**
     * 修改单个权限菜单节点
     * @param menuRight  权限菜单节点对象
     */
    public void updateMenuRight(MenuRight menuRight);

    /**
     * 通过用户id查询权限菜单节点树
     * @param id 用户id
     * @return  权限菜单集合
     */
    public List<MenuRight> findListByUserId(long id);

    //获取所有权限菜单节点树
    public List<MenuRight> findAllMenuRightWeb();
    /**
     * 通过角色查询权限
     * @param id  角色id
     * @return 权限菜单集合
     */
    public List<MenuRight> findListByRoleId(long id);

    List<MenuRight> findAllMenuRightByUrl(@Param("url") String url);
}