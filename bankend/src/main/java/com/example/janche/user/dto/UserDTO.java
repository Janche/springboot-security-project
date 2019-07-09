package com.example.janche.user.dto;

import com.example.janche.common.config.ApplicationConfig;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.domain.Role;
import com.example.janche.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO implements Serializable {

    @Autowired
    private static ApplicationConfig applicationConfig;

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Integer levelId;

    /**
     * 职务名称
     */
    private String level;

    private String truename;

    private String userNum;

    private String address;

    private int sex;

    private Date createTime;

    private Date modifyTime;

    private Integer state;//状态 （0禁止，1正常）

    private Long areaId;

    private Integer priority; //优先级

    // 角色
    private Role role;

    private List<Role> roleList; //角色列表

    private String rolename;//角色名
    // 权限菜单
    private List<MenuRight> menuRightList;

    /**
     * DTO 转化为javabean
     *
     * @param userDTO
     * @return
     */
    public static User userDTO2User(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .truename(userDTO.getTruename())
                .userNum(userDTO.getUserNum())
                .email(userDTO.getEmail())
                .sex(userDTO.getSex())
                .phone(userDTO.getPhone())
                .levelId(userDTO.levelId)
                .createTime(userDTO.getCreateTime())
                .modifyTime(userDTO.getModifyTime())
                .state(userDTO.getState())
                .build();
    }

    public static UserDTO showUser(User user) {
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .address(user.getAddress())
                .truename(user.getTruename())
                .createTime(user.getCreateTime())
                .userNum(user.getUserNum())
                .email(user.getEmail())
                .sex(user.getSex())
                .levelId(user.getLevelId())
                .phone(user.getPhone())
                .modifyTime(user.getModifyTime())
                .state(user.getState())
                .build();
                return userDTO;
    }

    public static UserDTO showInCase(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .levelId(user.getLevelId())
                .level(applicationConfig.getUserlevel().get(user.getLevelId()))
                .sex(user.getSex())
                .build();
    }
}
