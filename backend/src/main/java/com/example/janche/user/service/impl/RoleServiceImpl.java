package com.example.janche.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.example.janche.common.core.AbstractService;
import com.example.janche.common.model.Constant;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.user.dao.MenuRightMapper;
import com.example.janche.user.dao.RoleAndMenuMapper;
import com.example.janche.user.dao.RoleMapper;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.domain.Role;
import com.example.janche.user.domain.RoleAndMenu;
import com.example.janche.user.dto.LoginUserDTO;
import com.example.janche.user.dto.TreeNodeDTO;
import com.example.janche.user.dto.role.RoleConditionDTO;
import com.example.janche.user.dto.role.RoleDTO;
import com.example.janche.user.dto.role.RoleInputDTO;
import com.example.janche.user.dto.role.RoleOutpDTO;
import com.example.janche.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author lirong
* @Description:
* @date 2018-11-08 09:37:24
*/
@Slf4j
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleAndMenuMapper roleAndMenuMapper;
    @Resource
    private MenuRightMapper menuRightMapper;

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @return
     */
    @Override
    public List<Role> list(PageParam pageParam,
                           RoleConditionDTO dto,
                           LoginUserDTO userDTO) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(),pageParam.getOrderBy());
        Example example = new Example(Role.class);
        if (StringUtils.isNotEmpty(dto.getName())){
            example.and().andLike("name", "%"+dto.getName()+"%");
        }
        if (null != dto.getStatus()){
            example.and().andEqualTo("status", dto.getStatus());
        }
        return roleMapper.selectByExample(example);
    }

    /**
     * 下拉框角色列表
     * @return
     */
    @Override
    public List<Role> listAll() {
        Example example = new Example(Role.class);
        example.and().andEqualTo("status", Constant.STATUS_ENABLE);
        return roleMapper.selectByExample(example);
    }

    /**
     * 批量插入角色权限关联记录
     * @param menuIds
     * @param role
     */
    private void insertRoleAndRights(String menuIds, Role role) {
        List<RoleAndMenu> list = new ArrayList<>();
        Arrays.stream(menuIds.split(",")).forEach(e -> {
            RoleAndMenu build = RoleAndMenu.builder()
                    .roleId(role.getId())
                    .menuId(Long.parseLong(e))
                    .build();
            list.add(build);
        });
        roleAndMenuMapper.insertList(list);
    }

    /**
     * 添加角色
     * @param inputDTO 角色信息
     */
    @Override
    public void addRole(RoleInputDTO inputDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(inputDTO, role);
        role.setCreateTime(new Date());
        role.setModifyTime(new Date());
        roleMapper.insert(role);

        // 新增角色权限表
        String menuIds = inputDTO.getMenuIds();
        if (StringUtils.isNotEmpty(menuIds)){
            insertRoleAndRights(menuIds, role);
        }
    }

    /**
     * 删除角色
     * @param id 角色id 
     */
    @Override
    public void deleteRole(Long id) {
        // 删除角色权限关联表
        Example example = new Example(RoleAndMenu.class);
        example.and().andEqualTo("roleId", id);
        roleAndMenuMapper.deleteByExample(example);
        // 删除角色
        roleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除角色
     * @param ids
     */
    @Override
    public void deleteRole(String ids) {
        List<String> Ids = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        // 删除角色权限关联表
        Example example = new Example(RoleAndMenu.class);
        example.and().andIn("roleId", Ids);
        roleAndMenuMapper.deleteByExample(example);
        // 删除角色
        roleMapper.deleteByIds(ids);
    }

    /**
     * 批量冻结成功
     * @param ids
     */
    @Override
    public void frozeRole(String ids, Integer status) {
        List<String> Ids = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        Example example = new Example(Role.class);
        example.and().andIn("id", Ids);
        List<Role> roles = roleMapper.selectByExample(example);
        for (Role role :roles) {
            role.setStatus(status);
            role.setModifyTime(new Date());
            roleMapper.updateByPrimaryKeySelective(role);
        }
    }

    /**
     * 更新角色
     * @param inputDTO 角色信息
     */
    @Override
    public void updateRole(RoleInputDTO inputDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(inputDTO, role);
        role.setModifyTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);

        // 更新角色权限关联记录
        Example example = new Example(RoleAndMenu.class);
        example.and().andEqualTo("roleId", role.getId());
        roleAndMenuMapper.deleteByExample(example);

        String menuIds = inputDTO.getMenuIds();
        if (StringUtils.isNotEmpty(menuIds)){
            insertRoleAndRights(menuIds, role);
        }
    }

    /**
     * 根据角色Ids获取权限Ids
     * @param ids
     * @return
     */
    @Override
    public Set<Long> getMenuIdsByRoleIds(String ids) {
        List<String> Ids = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        Example example = new Example(RoleAndMenu.class);
        example.and().andIn("roleId", Ids);
        List<RoleAndMenu> list = roleAndMenuMapper.selectByExample(example);
        Set<Long> menuIds = new HashSet<>();
        if (null != list && list.size()>0){
            list.stream().forEach(rm ->{
                menuIds.add(rm.getMenuId());
            });
        }
        return menuIds;
    }

    /**
     * 获取角色详情
     * @param id
     * @return
     */
    @Override
    public RoleOutpDTO findOne(Long id) {
        RoleDTO roleDTO = getMenusByRoleId(id);
        RoleOutpDTO outpDTO = new RoleOutpDTO();
        BeanUtils.copyProperties(roleDTO, outpDTO);
        // 获取系统所有权限
        List<MenuRight> allMenus = menuRightMapper.selectAll();

        // 获取此角色所拥有的权限
        List<MenuRight> menus = roleDTO.getMenus();
        TreeNodeDTO rootNode = new TreeNodeDTO();
        for (MenuRight menu: allMenus) {
            Boolean isChecked = false;
            for (MenuRight m: menus) {
                if(m.getId().equals(menu.getId())){
                    isChecked = true;
                }
            }
            addTreeNode(rootNode, menu, isChecked);
        }
        // dfsTreeNodes(rootNode);
        outpDTO.setMenus(rootNode.getChildren());
        return outpDTO;
    }

    /**
     * 获取权限树
     * @return
     */
    @Override
    public TreeNodeDTO getMenuTree() {
        List<MenuRight> menus = menuRightMapper.selectAll();
        TreeNodeDTO rootNode = new TreeNodeDTO();
        menus.stream().forEach(m -> addTreeNode(rootNode, m, false));
        // dfsTreeNodes(rootNode);
        return rootNode;
    }

    /**
     * 递归遍历 树节点 将多余的子节点替换为查看和编辑
     * @param rootNode
     * @return
     */
    public TreeNodeDTO dfsTreeNodes(TreeNodeDTO rootNode) {
        if (null == rootNode.getChildren() || rootNode.getChildren().size() == 0) {
            return rootNode;
        }

        List<TreeNodeDTO> list = rootNode.getChildren();
        for (TreeNodeDTO node : list) {
            this.dfsTreeNodes(node);
        }
        if (null != list && null != list.get(0).getIds()) {
            List<TreeNodeDTO> children = new ArrayList<>();
            Map<String, TreeNodeDTO> map = new HashMap<>();
            for (TreeNodeDTO node : list) {
                String key = node.getParentId() + node.getName();
                TreeNodeDTO nodeDTO = map.get(key);
                if (null != nodeDTO) {
                    List<Long> ids = node.getIds();
                    ids.addAll(nodeDTO.getIds());
                    node.setIds(ids);
                    node.setChecked(nodeDTO.getChecked() ? nodeDTO.getChecked() : node.getChecked());
                }
                map.put(key, node);
            }
            map.forEach((k, v) -> children.add(v));
            rootNode.setChildren(children);
        }
        return rootNode;
    }

    /**
     * 添加树节点
     * @param rootNode
     * @param menu
     * @param isChecked
     */
    private void addTreeNode(TreeNodeDTO rootNode, MenuRight menu, Boolean isChecked) {
        TreeNodeDTO node = new TreeNodeDTO();
        // if (menu.getGrades() == 3){
        //     node.setId(menu.getId());
        //     node.setParentId(menu.getParentId());
        //     if (menu.getSeq() == 1){
        //         node.setName("查看");
        //     } else {
        //       node.setName("编辑");
        //     }
        //     node.setChecked(isChecked);
        //     List<Long> ids = new ArrayList<>();
        //     ids.add(menu.getId());
        //     node.setIds(ids);
        // }else{
            node.setId(menu.getId());
            node.setParentId(menu.getParentId());
            node.setName(menu.getName());
            node.setChecked(isChecked);
            node.setIds(null);
        // }
        rootNode.add(node);
    }

    /**
     * 递归获取TreeNode节点中的menuId
     * @param rootNode
     * @return
     */
    public Set<Long> parseTreeNode(TreeNodeDTO rootNode){
        Set<Long> set = new HashSet<>();
        if (null == rootNode.getChildren() || rootNode.getChildren().size() == 0) {
            return set;
        }
        for (TreeNodeDTO node :rootNode.getChildren()) {
            if (node.getChecked()){
                set.add(node.getId());
                if (null != node.getIds()) {
                    set.addAll(node.getIds());
                }
            }
            Set<Long> nodeSets = this.parseTreeNode(node);
            set.addAll(nodeSets);
        }
        return set;
    }

    /**
     * 根据角色ID获取权限
     * @param id
     * @return
     */
    @Override
    public RoleDTO getMenusByRoleId(Long id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        if (null != role && Constant.ADMIN_NAME.equals(role.getName())){
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(role, roleDTO);
            List<MenuRight> menus = menuRightMapper.selectAll();
            roleDTO.setMenus(menus);
            return roleDTO;
        }
        return roleMapper.getMenusByRoleId(id);
    }
}
