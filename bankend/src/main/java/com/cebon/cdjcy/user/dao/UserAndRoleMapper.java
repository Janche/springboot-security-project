package com.cebon.cdjcy.user.dao;

import com.cebon.cdjcy.common.core.Mapper;
import com.cebon.cdjcy.user.domain.UserAndRole;

public interface UserAndRoleMapper extends Mapper<UserAndRole> {


    /**
     * 使用用户id删除与之角色关联关系
     * @param id 业务组id
     */
    public void deleteByUserID(long id);
}