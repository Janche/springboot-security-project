package com.example.janche.user.service.impl;

import com.example.janche.common.core.AbstractService;
import com.example.janche.user.dao.RoleAndMenuMapper;
import com.example.janche.user.domain.RoleAndMenu;
import com.example.janche.user.service.RoleAndMenuService;
import com.example.janche.common.restResult.PageParam;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
* @author lirong
* @Description: // TODO 为类添加注释
* @date 2018-11-14 02:17:34
*/
@Slf4j
@Service
@Transactional
public class RoleAndMenuServiceImpl extends AbstractService<RoleAndMenu> implements RoleAndMenuService {
    @Resource
    private RoleAndMenuMapper roleRightMapper;

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    @Override
    public List<RoleAndMenu> list(PageParam pageParam, String query) {
        Example example = new Example(RoleAndMenu.class);
        //TODO 设置查询字段
        //example.or().andLike("name", "%"+query+"%");
        //example.or().andLike("code", "%"+query+"%");

        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return roleRightMapper.selectByExample(example);
    }

   // 根据角色id删除对应权限
    @Override
    public void deleteByRoleId(long id) {
        roleRightMapper.deleteByRoleId(id);
    }

    /**
     * 批量添加角色对应权限
     * @param roleAndMenuList
     */
    @Override
    public void addList(List<RoleAndMenu> roleAndMenuList) {
//        for (int i = 0; i < roleAndMenuList.size(); i++) {
//            roleRightMapper.insert(roleAndMenuList.get(i));
//        }
        roleRightMapper.addList(roleAndMenuList);

    }
}
