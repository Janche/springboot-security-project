package com.example.janche.user.dto.loginweb;


import com.example.janche.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author lirong
 * @ClassName: LoginOutpDTO
 * @Description: 登录后返回前端的信息
 * @date 2018-12-14 10:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginOutpDTO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户真实姓名
     */
    private String truename;


    /**
     * 用户所拥有的模块
     */
    private List<MenuDTO> menus;

    /**
     * 用户的角色
     */
    private List<Role> roles;
}
