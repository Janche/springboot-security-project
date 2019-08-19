package com.example.janche.user.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lirong
 * @ClassName: RoleConditionDTO
 * @Description: 角色筛选条件
 * @date 2019-07-25 10:43
 */
@Data
@ApiModel(value="RoleConditionDTO",description="角色筛选条件")
public class RoleConditionDTO {

    @ApiModelProperty(value="角色名称")
    private String name;

    @ApiModelProperty(value="启用状态(0-禁用，1-启用)")
    private Integer status;
}
