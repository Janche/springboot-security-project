package com.example.janche.user.dto.menuRightWeb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MeunWebDTO implements Serializable {

    private Long id;

    private String name;

    private String desc;

    private String roleIndex;

    private List<SecondMenuDTO> secondMenuDTOS;


}
