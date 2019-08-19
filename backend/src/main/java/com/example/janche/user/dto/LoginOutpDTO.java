package com.example.janche.user.dto;

import com.example.janche.user.domain.MenuRight;
import lombok.Data;

import java.util.List;

/**
 * @author lirong
 * @ClassName: LoginOutpDTO
 * @Description: TODO
 * @date 2019-08-15 15:39
 */
@Data
public class LoginOutpDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

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
     * 前端需要显示的菜单
     */
    private List<MenuRight> menus;
}
