package com.example.janche.user.service;

import com.example.janche.common.core.Service;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.domain.Role;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.MenuRightDTO;
import com.example.janche.user.dto.menuRightWeb.MeunRightWebDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
     * 根据角色信息获取权限节点
     * @param roles
     * @return
     */
    public List<MenuRight> findByRoles(Set<Role> roles);

    /**
     * 根据用户信息获取权限节点
     * @param user
     * @return
     */
    public List<MenuRight> findByUser(User user);

    /**
     * 获取所有权限节点
     *
     */
    List<MenuRight> queryList(MenuRightDTO menuRightDTO);


    /**
     * 根据url获取与url匹配的所有节点
     *
     */
    List<MenuRight> queryListByUrl(String url);
    /**
     * 按条件查询权限信息，并进行分页
     * @param menuRightDTO
     * @param roleIds
     * @param pageParam
     * @return
     */
    List<MenuRightDTO> queryList(MenuRightDTO menuRightDTO,
                                 List<Long> roleIds,
                                 PageParam pageParam);


    /**
     * 搜索所有权限菜单节点、
     * return  ArrayList<MenuRight>  权限菜单节点集合
     */
    public ArrayList<MenuRight> findAllMenuRight();

    public List<MenuRight> findAllMenuRightByUrl(String url);
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
     * 获取当前用户权限菜单节点书
     * @param id 用户id
     * @return MeunRightWebDTO权限书
     */
    public MeunRightWebDTO FindMenuRightWebDtO(long id);

    //获取所有权限菜单节点树
    public MeunRightWebDTO FindAllMenuRightWebDtO();

    /**
     * 通过角色查询权限
     * @param id  角色id
     * @return 权限菜单集合
     */
    public MeunRightWebDTO findListByRoleId(long id);
}
