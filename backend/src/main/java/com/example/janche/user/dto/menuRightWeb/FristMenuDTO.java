package com.example.janche.user.dto.menuRightWeb;


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
public class FristMenuDTO implements Serializable {

    private Long id;


    // 父节点id
    private Long parentId;


    //节点名称
    private String name;


   // 修改时间
    private Date modifyTime;


  // 创建时间
    private Date createTime;


     // 排序号
    private Integer seq;


  // 访问地址
    private String url;


  // 是否有效 1-有效 -1无效（不可使用）
    private Integer isvisible;


   //图标样式
    private String icon;

    //方法（get、post等）
    private String method;


   // 层级
    private Integer grades;

    //查看权限id集合
    private List<Long> lookids;

    //操作权限id集合
    private List<Long> handleIds;

    //权限类型 （1，查看 2修改（查看，修改，添加，删除））
    private Integer type=1;  //默认为1

    private Integer fixedType=1; //固定类型（用于显示）

   // 二级权限集合
    private List<SecondMenuDTO> SecondMenus;


    public static FristMenuDTO menuRight2MenuRightDTO(MenuRight menuRight) {
        return FristMenuDTO.builder()
                .id(menuRight.getId())
                .name(menuRight.getName())
                .parentId(menuRight.getParentId())
                .url(menuRight.getUrl())
                .isvisible(menuRight.getIsvisible())
                .icon(menuRight.getIcon())
                .seq(menuRight.getSeq())
                .grades(menuRight.getGrades())
                .createTime(menuRight.getModifyTime())
                .modifyTime(menuRight.getModifyTime())
                .build();
    }

}
