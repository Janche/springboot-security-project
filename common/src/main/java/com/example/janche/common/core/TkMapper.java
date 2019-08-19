package com.example.janche.common.core;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * <P></P>
 *
 * @author lirong
 * @version v1.0
 * @since 2018/1/25 11:45
 */
public interface TkMapper<T> extends
        BaseMapper<T>,
        MySqlMapper<T>,
        ConditionMapper<T>,
        ExampleMapper<T>{
}
