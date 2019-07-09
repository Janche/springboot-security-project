package com.example.janche.user.service;

import com.example.janche.user.domain.UserAndRole;
import com.example.janche.common.core.Service;
import com.example.janche.common.restResult.PageParam;

import java.util.List;

/**
* @author lirong
* @Description: // TODO 为类添加注释
* @date 2018-11-16 09:48:57
*/
public interface UserAndRoleService extends Service<UserAndRole> {

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<UserAndRole> list(PageParam pageParam, String query);

    /**
     * 使用用户id删除与之角色关联关系
     * @param id 业务组id
     */
    public void deleteByUserID(long id);
}
