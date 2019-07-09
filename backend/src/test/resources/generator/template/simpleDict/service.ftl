package ${modulePackage}.${moduleName}.service;

import ${modulePackage}.${moduleName}.domain.${modelNameUpperCamel};
import ${basePackage}.common.core.Service;
import ${basePackage}.common.restResult.PageParam;
import java.util.List;

/**
* @author ${author}
* @Description: // TODO 为类添加注释
* @date ${date}
*/
public interface ${modelNameUpperCamel}Service extends Service<${modelNameUpperCamel}> {

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<${modelNameUpperCamel}> list(PageParam pageParam, String query);

}
