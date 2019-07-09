package com.example.janche.common.core;


import com.example.janche.common.exception.CustomException;
import com.example.janche.common.utils.CoreUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
@Slf4j
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;
    private Class<T> modelClass;

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T model) {
        mapper.insertSelective(model);
    }

    @Override
    public void save(List<T> models) {
        mapper.insertList(models);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    /**
     * 通过字段和值删除数据
     *
     * @param domainField 实体类字段
     * @param value
     * @author lirong
     * @since 2018/6/01 18:28
     */
    @Override
    public void delete(String domainField, String value) {
        try {
            Example example = new Example(modelClass);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo(domainField, value);
            mapper.deleteByExample(example);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    @Override
    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public T findById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (/*ReflectiveOperationException*/ Exception e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    /**
     * 查找符合条件的集合
     *
     * @param fieldName Model中某个成员变量名称（非数据表中column的名称）
     * @param value
     * @return
     * @author lirong
     * @since 2018-6-27 11:44:14
     */
    @Override
    public List<T> findAllBy(String fieldName, Object value) {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.select(model);
        } catch (/*ReflectiveOperationException*/ Exception e) {
            throw new CustomException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> findAll(int page, int size) {
        return this.findAll(page, size, null);
    }

    @Override
    public List<T> findAll(int page, int size, String orderBy) {
        PageHelper.startPage(page, size, orderBy);
        List<T> list = mapper.selectAll();
        return list;
    }

    /**
     * 根据分页和排序信息查询size条数据
     *
     * @param page
     * @param size
     * @param sortField 排序字段
     * @param sortOrder 排序类型
     * @return
     */
    @Override
    public List<T> findAll(int page, int size, String sortField, String sortOrder) {
        String orderBy = CoreUtils.getOrderBy(sortField, sortOrder);

        PageHelper.startPage(page, size, orderBy);
        List<T> list = mapper.selectAll();
        return list;
    }

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
    @Override
    public List<T> findAll(int page, int size, String sortField, String sortOrder, Map query) {
        //TODO
        return null;
    }

    /**
     * 检查对应的记录是否存在
     *
     * @param domainField
     * @param value
     * @return
     */
    @Override
    public Integer keyIsExist(String domainField, String value) {
        Example example = new Example(modelClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(domainField, value);
        return mapper.selectCountByExample(example);
    }
}
