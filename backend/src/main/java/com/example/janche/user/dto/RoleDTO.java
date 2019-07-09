package com.example.janche.user.dto;

import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.domain.Role;
import com.example.janche.user.domain.User;
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
    private Long parentId;
    private String roleName;
    private String name;
    private Integer seq;
    private String description;
    private Date createTime;
    private Date modifyTime;

    private String roleIndex;

    //角色权限集合
    private List<MenuRight> menuRightList;

    //角色对应用户集合
    private List<User> userList;

    /**
     * javabean实体转化为DTO
     * @param role
     * @return
     */
    public static RoleDTO role2RoleDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .parentId(role.getParentId()==null ? null : role.getParentId())
                .roleName(role.getRoleName())
                .name(role.getName())
                .createTime(role.getCreateTime())
                .modifyTime(role.getModifyTime())
                .description(role.getDescription())
                .seq(role.getSeq())
                .roleIndex(role.getRoleIndex())
                .build();
    }

    /**
     * DTO转化为javabean实体
     * @param roleDTO
     * @return
     */
    public static  Role roleDTO2role(RoleDTO roleDTO) {
        return Role.builder()
                .id(roleDTO.getId())
                .name(roleDTO.getName())
                .roleName(roleDTO.getRoleName())
                .description(roleDTO.getDescription())
                .seq(roleDTO.getSeq())
                .parentId(roleDTO.getParentId())
                .createTime(roleDTO.getCreateTime())
                .modifyTime(roleDTO.getModifyTime())
                .createTime(roleDTO.getCreateTime())
                .roleIndex(roleDTO.getRoleIndex())
                .build();
    }

}
