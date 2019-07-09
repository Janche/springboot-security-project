package com.example.janche.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
@ApiModel(value="User",description="用户实体类")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="用户主键ID",name="id")
    private Long id;

    @ApiModelProperty(value="地址",name="address")
    private String address;

    @ApiModelProperty(value="邮箱",name="email")
    private String email;

    @ApiModelProperty(value="编号",name="userNum")
    private String userNum;

    @Column(name = "level_id")
    @ApiModelProperty(value="级别",name="levelId")
    private Integer levelId;

    @ApiModelProperty(value="密码",name="password")
    private String password;

    @ApiModelProperty(value="电话",name="phone")
    private String phone;

    @ApiModelProperty(value="性别",name="sex")
    private Integer sex;

    @ApiModelProperty(value="真实姓名",name="truename")
    private String truename;

    @ApiModelProperty(value="用户名",name="username")
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "modify_time")
    @ApiModelProperty(value="修改时间",name="modifyTime")
    private Date modifyTime;

    @ApiModelProperty(value="状态",name="state",example = "0-禁止，1-正常")
    private Integer state;

    @ApiModelProperty(value="优先级",name="priority")
    private Integer priority;

}