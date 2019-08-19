package com.example.janche.user.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lirong
 * @ClassName: UserConditionDTO
 * @Description: 用户列表筛选条件
 * @date 2019-07-25 10:06
 */
@Data
@ApiModel(value="UserConditionDTO",description="用户筛选条件")
public class UserConditionDTO {

    @ApiModelProperty(value="用户名")
    private String username;

    @ApiModelProperty(value="真实姓名")
    private String actualName;

    @ApiModelProperty(value="账号状态")
    private Integer status;
}
