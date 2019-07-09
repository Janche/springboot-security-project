package ${modulePackage}.${moduleName}.service;

import ${modulePackage}.${moduleName}.domain.${modelNameUpperCamel};
import ${basePackage}.common.core.Service;
import java.util.List;

/**
*
* @author ${author}
* @date ${date}
*/
public interface ${modelNameUpperCamel}Service extends Service<${modelNameUpperCamel}> {

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
    List<${modelNameUpperCamel}> search(Integer page,
                                    Integer size,
                                    String sortField,
                                    String sortOrder,
                                    String query);

}
