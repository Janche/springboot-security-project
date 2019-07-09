package com.example.janche.user.dto;

import com.example.janche.user.domain.MenuRight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MenuRightDTO implements Serializable{
    private Long id;
    private Long parentId;
    private String name;
    private Date modifyTime;
    private Date createTime;
    private Integer seq;
    private String url;
    private Integer isvisible = 1;
    private String icon;
    private String method;
    private Integer grades;
    private List<MenuRightDTO> children = new ArrayList<>();
    private List<RoleDTO> roles = new ArrayList<>();

    public static MenuRightDTO menuRight2MenuRightDTO(MenuRight menuRight) {
        return MenuRightDTO.builder()
                .id(menuRight.getId())
                .name(menuRight.getName())
                .parentId(menuRight.getParentId())
                .url(menuRight.getUrl())
                .isvisible(menuRight.getIsvisible())
                .icon(menuRight.getIcon())
                .seq(menuRight.getSeq())
                // 判断权限节点是否有子节点，若存在子节点，循环读取子节点信息
//                .children(menuRight.getChildren() == null ? null : menuRight.getChildren()
//                        .stream().map(children ->
//                                MenuRightDTO.builder()
//                                        .id(children.getId())
//                                        .name(children.getName())
//                                        .method(children.getMethod())
//                                        .url(children.getUrl())
//                                        .build())
//                        .collect(Collectors.toList()))
//                .roles(menuRight.getRoles().stream().map(role ->
//                        RoleDTO.builder().id(role.getId())
//                                .rolename(role.getRolename())
//                                .description(role.getDescription())
//                                .build())
//                        .collect(Collectors.toList()))
                .createTime(menuRight.getModifyTime())
                .modifyTime(menuRight.getModifyTime())
                .build();
    }
}
