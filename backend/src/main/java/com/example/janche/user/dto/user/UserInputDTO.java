package com.example.janche.user.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lirong
 * @ClassName: UserInputDTO
 * @Description: 用户输入类
 * @date 2019-07-24 11:21
 */
@Data
@ApiModel(value="UserInputDTO",description="用户输入类")
public class UserInputDTO {

    @ApiModelProperty(value="主键ID, 新增不传值, 修改传值")
    private Long id;

    @ApiModelProperty(value="用户名")
    private String username;

    @ApiModelProperty(value="密码")
    private String password;

    @ApiModelProperty(value="真实姓名")
    private String actualName;

    @ApiModelProperty(value="性别")
    private int sex;

    @ApiModelProperty(value="电话")
    private String phone;

    @ApiModelProperty(value="邮箱")
    private String email;

    @ApiModelProperty(value="地址")
    private String address;

    @ApiModelProperty(value="职务Id")
    private Integer postId;

    @ApiModelProperty(value="职务名称")
    private String postName;

    @ApiModelProperty(value="启用状态(0-禁用，1-启用)")
    private Integer status;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    private Date modifyTime;

    @ApiModelProperty(value="角色ID 集合(eg: 1,2,5,6)")
    private String roleIds;

    @ApiModelProperty(value="权限ID 集合(eg: 1,2,5,6)")
    private String menuIds;

}
