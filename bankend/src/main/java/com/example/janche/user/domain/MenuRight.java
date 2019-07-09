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
@Table(name = "menu_right")
@ApiModel(value="MenuRight",description="权限实体类")
public class MenuRight implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="权限主键ID",name="id")
    private Long id;

    @Column(name = "parent_id")
    @ApiModelProperty(value="父ID",name="parentId")
    private Long parentId;

    @ApiModelProperty(value="权限名称",name="name")
    private String name;

    @ApiModelProperty(value="排序",name="seq")
    private Integer seq;

    @ApiModelProperty(value="访问地址",name="url")
    private String url;

    @ApiModelProperty(value="是否有效 ",name="isvisible",example = "1-有效,-1无效")
    private Integer isvisible;

    @ApiModelProperty(value="图标样式",name="icon")
    private String icon;

    @ApiModelProperty(value="方法(get、post)",name="method")
    private String method;

    @ApiModelProperty(value="层级",name="grades")
    private Integer grades;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "modify_time")
    @ApiModelProperty(value="修改时间",name="modifyTime")
    private Date modifyTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

}