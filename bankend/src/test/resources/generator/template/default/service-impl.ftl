package ${modulePackage}.${moduleName}.service.impl;

import ${basePackage}.common.core.AbstractService;
import ${modulePackage}.${moduleName}.dao.${modelNameUpperCamel}Mapper;
import ${modulePackage}.${moduleName}.domain.${modelNameUpperCamel};
import ${modulePackage}.${moduleName}.service.${modelNameUpperCamel}Service;
import ${basePackage}.common.utils.CoreUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
*
* @author ${author}
* @date ${date}
*/
@Slf4j
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     *
     * @param page 第几页
     * @param size 每页有多少记录
     * @param sortField 排序字段
     * @param sortOrder 升序or降序
     * @param query  查询关键字
     * @return
     */
    @Override
    public List<${modelNameUpperCamel}> search(Integer page,
                                                Integer size,
                                                String sortField,
                                                String sortOrder,
                                                String query) {
        Example example = new Example(${modelNameUpperCamel}.class);
        //TODO 6. 设置查询字段
        //example.or().andLike("itemName", "%"+query+"%");
        //example.or().andLike("itemCode", "%"+query+"%");
        //example.or().andLike("itemInputCode", "%"+query+"%");

        String orderBy = CoreUtils.getOrderBy(sortField, sortOrder);
        PageHelper.startPage(page, size, orderBy);
        return ${modelNameLowerCamel}Mapper.selectByExample(example);
    }


}
