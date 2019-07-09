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
public class MeunRightWebDTO implements Serializable {

    private String name;

    private String desc;
    //一级菜单集合
    private List<FristMenuDTO> FristMenus;

}
