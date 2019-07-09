package ${modulePackage}.${moduleName}.service.impl;

import ${basePackage}.common.core.AbstractService;
import ${modulePackage}.${moduleName}.dao.${modelNameUpperCamel}Mapper;
import ${modulePackage}.${moduleName}.domain.${modelNameUpperCamel};
import ${modulePackage}.${moduleName}.service.${modelNameUpperCamel}Service;
import ${basePackage}.common.restResult.PageParam;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
* @author ${author}
* @Description: // TODO 为类添加注释
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
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    @Override
    public List<${modelNameUpperCamel}> list(PageParam pageParam, String query) {
        Example example = new Example(${modelNameUpperCamel}.class);
        //TODO 设置查询字段
        //example.or().andLike("name", "%"+query+"%");
        //example.or().andLike("code", "%"+query+"%");

        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return ${modelNameLowerCamel}Mapper.selectByExample(example);
    }
}
