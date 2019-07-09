package com.example.janche.user.dto;


import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginUserDTO implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Integer levelId;

    private String truename;

    private String userNum;

    private String address;

    private Integer state;

    private Date createTime;

    private List<Role> roles = new ArrayList<>();

    private List<MenuRight> menus = new ArrayList<>();


    public static LoginUserDTO user2LoginUserDTO(UserDTO user) {
        return LoginUserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .address(user.getAddress())
                .truename(user.getTruename())
                .userNum(user.getUserNum())
                .email(user.getEmail())
                .levelId(user.getLevelId())
                .phone(user.getPhone())
                .createTime(user.getCreateTime())
                .state(user.getState())
                .roles(user.getRoleList())
                .menus(user.getMenuRightList())
                .build();
    }

}
