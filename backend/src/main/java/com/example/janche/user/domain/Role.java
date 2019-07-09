package com.example.janche.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "role")
@ApiModel(value="Role",description="角色实体类")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="角色主键ID",name="id")
    private Long id;

    @Column(name = "parent_id")
    @ApiModelProperty(value="角色父ID",name="parentId")
    private Long parentId;

    @ApiModelProperty(value="角色描述",name="description")
    private String description;

    /**
     * 角色名称（role_开头）
     */
    @ApiModelProperty(value="(用于security判断的名称)",name="name")
    private String name;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    @ApiModelProperty(value="角色名称",name="roleName")
    private String roleName;

    @ApiModelProperty(value="排序",name="seq")
    private Integer seq;

    /**
     * 所有父级角色，及当前角色
     */
    @Column(name = "role_index")
    @ApiModelProperty(value="角色ID索引(父级角色Id集合)",name="roleIndex")
    private String roleIndex;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "modify_time")
    @ApiModelProperty(value="修改时间",name="modifyTime")
    private Date modifyTime;
}