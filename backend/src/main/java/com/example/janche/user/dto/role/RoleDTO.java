package com.example.janche.user.dto.role;

import com.example.janche.user.domain.MenuRight;
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
public class RoleDTO implements Serializable {
    private Long id;
    private String name;
    private Integer seq;
    private String description;
    private Integer status;
    private Date createTime;
    private Date modifyTime;

    private List<MenuRight> menus;
}
