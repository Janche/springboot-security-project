package com.example.janche.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInputDTO implements Serializable {

    //区域id
    private  Long areaId;

    //区域名字
    private String areaName;

    //关键字查询
    private String query;

    //用户区域id
    private  Long userAreaId;

    //用户状态（1允许登录，0不允许登录）
    private Long userState;
}
