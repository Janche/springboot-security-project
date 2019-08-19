package com.example.janche.user.domain;

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
@Table(name = "role")
@ApiModel(value="Role",description="角色实体类")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="角色主键ID")
    private Long id;

    @ApiModelProperty(value="角色名称")
    private String name;

    @ApiModelProperty(value="角色描述")
    private String description;

    @ApiModelProperty(value="排序")
    private Integer seq;

    @ApiModelProperty(value="状态：0-禁用，1-启用")
    private Integer status;

    @Column(name = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @Column(name = "modify_time")
    @ApiModelProperty(value="修改时间")
    private Date modifyTime;
}