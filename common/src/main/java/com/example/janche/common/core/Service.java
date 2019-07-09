package com.example.janche.common.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;
import java.util.Map;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {

    void save(T model);//持久化

    void save(List<T> models);//批量持久化

    void deleteById(Long id);//通过主鍵刪除

    /**
     * 通过字段和值删除数据
     *
     * @param field
     * @param value
     * @author lirong
     * @since 2018/6/01 18:28
     */
    void delete(String field, String value);

    void deleteByIds(String ids);//批量刪除 eg：ids -> “1,2,3,4”

    void update(T model);//更新

    T findById(Long id);//通过ID查找

    T findBy(String fieldName, Object value) throws TooManyResultsException;

    /**
     * 查找符合条件的集合
     *
     * @param fieldName Model中某个成员变量名称（非数据表中column的名称）
     * @param value
     * @return
     * @author lirong
     * @since 2018-6-27 11:44:14
     */
    List<T> findAllBy(String fieldName, Object value);

    List<T> findByIds(String ids);//通过多个ID查找//eg：ids -> “1,2,3,4”

    List<T> findByCondition(Condition condition);//根据条件查找

    List<T> findAll();//获取所有

    List<T> findAll(int page, int size);//获取所有

    List<T> findAll(int page, int size, String orderBy);//获取所有,加排序

    /**
     * 根据分页和排序信息查询size条数据
     *
     * @param page
     * @param size
     * @param sortField 排序字段
     * @param sortOrder 排序类型
     * @return
     */
    List<T> findAll(int page, int size, String sortField, String sortOrder);

    /**
     * 根据分页、排序信息和检索条件查询 size 条数据
     *
     * @param page
     * @param size
     * @param sortField
     * @param sortOrder
     * @param query
     * @return
     */
    List<T> findAll(int page, int size, String sortField, String sortOrder, Map query);

    /**
     * 检查对应的记录是否存在,在过滤条件中添加机构号
     *
     * @param domainField
     * @param value
     * @return
     */
    Integer keyIsExist(String domainField, String value);

}
