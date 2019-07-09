package com.example.janche.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_role")
public class UserAndRole implements Serializable {

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "user_id")
    private Long userId;


}