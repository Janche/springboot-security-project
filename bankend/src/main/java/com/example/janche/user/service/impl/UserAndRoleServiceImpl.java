package com.example.janche.user.service.impl;

import com.example.janche.common.core.AbstractService;
import com.example.janche.user.dao.UserAndRoleMapper;
import com.example.janche.user.domain.UserAndRole;
import com.example.janche.user.service.UserAndRoleService;
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
* @date 2018-11-16 09:48:57
*/
@Slf4j
@Service
@Transactional
public class UserAndRoleServiceImpl extends AbstractService<UserAndRole> implements UserAndRoleService {
    @Resource
    private UserAndRoleMapper userRoleMapper;

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    @Override
    public List<UserAndRole> list(PageParam pageParam, String query) {
        Example example = new Example(UserAndRole.class);
        //TODO 设置查询字段
        //example.or().andLike("name", "%"+query+"%");
        //example.or().andLike("code", "%"+query+"%");

        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return userRoleMapper.selectByExample(example);
    }

    //使用用户id删除与之角色关联关系
    @Override
    public void deleteByUserID(long id) {
        userRoleMapper.deleteByUserID(id);
    }
}
