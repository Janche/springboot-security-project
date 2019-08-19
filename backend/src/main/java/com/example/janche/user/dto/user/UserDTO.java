package com.example.janche.user.dto.user;

import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO implements Serializable {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String actualName;

    /**
     * 性别
     */
    private int sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 职务Id
     */
    private Integer postId;

    /**
     * 职务名称
     */
    private String postName;

    /**
     * 启用状态(0-禁用，1-启用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 角色
     */
    private List<Role> roles;

    /**
     * 权限菜单
     */
    private List<MenuRight> menus;

}
