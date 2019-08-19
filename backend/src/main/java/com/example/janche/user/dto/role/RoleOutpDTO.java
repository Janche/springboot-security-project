package com.example.janche.user.dto.role;

import com.example.janche.user.dto.TreeNodeDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author lirong
 * @ClassName: RoleOutpDTO
 * @Description: TODO
 * @date 2019-07-24 15:30
 */
@Data
public class RoleOutpDTO {
    /**
     * Id
     */
    private Long id;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 排序号
     */
    private Integer seq;

    /**
     * 启用状态(0-禁用，1-启用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 权限树
     */
    private List<TreeNodeDTO> menus;
}
