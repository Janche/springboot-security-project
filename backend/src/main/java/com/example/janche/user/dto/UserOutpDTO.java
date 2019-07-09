package com.example.janche.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserOutpDTO implements Serializable {

    private Long id;

    private String userNum;

    private String truename;

    private String username;

    private Object levelId; //职位 integer转String

    private String phone;

    private String rolename;//角色名

    private Object state;//状态 integer转String（0禁止，1运行）

//    private String email;
//
//    private Integer levelId;
//
//    private String address;
//
//    private int sex;



}
