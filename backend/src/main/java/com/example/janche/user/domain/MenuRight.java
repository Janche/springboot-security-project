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
@Table(name = "menu_right")
@ApiModel(value="MenuRight",description="权限实体类")
public class MenuRight implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="权限主键ID")
    private Long id;

    @Column(name = "parent_id")
    @ApiModelProperty(value="父ID")
    private Long parentId;

    @ApiModelProperty(value="权限名称")
    private String name;

    @ApiModelProperty(value="排序")
    private Integer seq;

    @ApiModelProperty(value="访问地址")
    private String url;

    @ApiModelProperty(value="是否启用 ")
    private Integer status;

    @ApiModelProperty(value="图标样式")
    private String icon;

    @ApiModelProperty(value="方法(get、post)")
    private String method;

    @ApiModelProperty(value="层级")
    private Integer grades;

    @Column(name = "modify_time")
    @ApiModelProperty(value="修改时间")
    private Date modifyTime;

    @Column(name = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}