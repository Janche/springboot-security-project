package com.example.janche.user.service.impl;

import com.example.janche.common.core.AbstractService;
import com.example.janche.user.dao.RoleMapper;
import com.example.janche.user.domain.Role;
import com.example.janche.user.dto.RoleDTO;
import com.example.janche.user.service.RoleService;
import com.example.janche.common.restResult.PageParam;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author CodeGenerator
* @Description: // TODO 为类添加注释
* @date 2018-11-08 09:37:24
*/
@Slf4j
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;


    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @return
     */
    @Override
    public List<Role> list(PageParam pageParam) {
        Example example = new Example(Role.class);
        //TODO 设置查询字段
        //example.or().andLike("name", "%"+query+"%");
        //example.or().andLike("code", "%"+query+"%");

        pageParam.setSortField("id");
        pageParam.setSortOrder("desc");
        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(),pageParam.getOrderBy());

        return roleMapper.selectAll();
    }

    //添加单个角色信息
    @Override
    public void addRole(Role role) {
        role.setName("ROLE_"+role.getRoleName());
        role.setCreateTime(new Date());
        role.setModifyTime(new Date());
        roleMapper.insert(role);
        Long roleId=role.getId();

        String index=role.getRoleIndex();
        role.setRoleIndex(index+","+roleId);
        roleMapper.updateByPrimaryKey(role);
    }

    //删除单个角色
    @Override
    public void deleteRole(long id) {
        roleMapper.deleteRole(id);
    }

    //修改单个角色
    @Override
    public void updateRole(Role role) {
        role.setName("ROLE_"+role.getRoleName());
        role.setModifyTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    //通过id获取角色信息
    @Override
    public Role findSingleRole(long id) {
        return roleMapper.findSingleRole(id);
    }

    //获取所有角色信息
    @Override
    public List<RoleDTO> findAllRole() {
        return roleMapper.findAllRole();
    }


    //通过角色id获取所有用户
    @Override
    public RoleDTO findUsersByRoleId(long id) {
        return roleMapper.findUsersByRoleId(id);
    }

   //查询用户添加的角色集合
    @Override
    public List<Role> findByParentId(Long id) {
        return roleMapper.findByParentId(id);
    }

    //根据角色名查询是存在
    @Override
    public Integer findCountByName(String rolername) {
        return roleMapper.findCountByName(rolername);
    }

//    查询角色所有下级角色集合
    @Override
    public List<Role> findByRoleIndex(String index,PageParam pageParam) {

        pageParam.setSortField("id");
        pageParam.setSortOrder("desc");
        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(),pageParam.getOrderBy());
        return roleMapper.findByRoleIndex(index);
    }
}
