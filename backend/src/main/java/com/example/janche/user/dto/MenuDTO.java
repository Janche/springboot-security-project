package com.example.janche.user.dto;

import com.example.janche.user.domain.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lirong
 * @ClassName: MenuDTO
 * @Description: 前端展示的菜单
 * @date 2018-12-14 11:16
 */
@Data
public class MenuDTO implements Serializable {

    private Long id;

    private Long parentId;

    private String name;

    private Integer seq;

    private String url;

    private Integer status;

    private String icon;

    private String method;

    private Integer grades;

    private List<Role> roles;
}
