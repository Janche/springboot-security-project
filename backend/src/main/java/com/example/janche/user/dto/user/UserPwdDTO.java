package com.example.janche.user.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lirong
 * @ClassName: UserPwdDTO
 * @Description: 用户密码DTO
 * @date 2019-07-25 15:36
 */
@Data
@ApiModel(value="UserPwdDTO",description="用户密码类")
public class UserPwdDTO {

    @ApiModelProperty(value="主键ID")
    private Long id;

    @ApiModelProperty(value="旧密码")
    private String password;

    @ApiModelProperty(value="新密码")
    private String newPassword;
}
