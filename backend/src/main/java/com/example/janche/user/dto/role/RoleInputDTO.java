package com.example.janche.user.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lirong
 * @ClassName: RoleInputDTO
 * @Description: 角色输入实体类
 * @date 2019-07-24 15:29
 */
@Data
@ApiModel(value="RoleInputDTO",description="角色输入类")
public class RoleInputDTO {

    @ApiModelProperty(value="主键ID, 新增不传值, 修改传值")
    private Long id;

    @ApiModelProperty(value="角色描述")
    private String description;

    @ApiModelProperty(value="角色名称")
    private String name;

    @ApiModelProperty(value="排序号")
    private Integer seq;

    @ApiModelProperty(value="启用状态(0-禁用，1-启用)")
    private Integer status;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    private Date modifyTime;

    @ApiModelProperty(value="权限ID集合")
    private String menuIds;
}
