package com.example.janche.user.dto.loginweb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lirong
 * @ClassName: MenuDTO
 * @Description: 前端展示的菜单
 * @date 2018-12-14 11:16
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuDTO implements Serializable, Comparable<MenuDTO>{

    private Long id;

    @JSONField(serialize = false)
    private Long parentId;

    private String name;

    private String path;

    private Boolean disabled;

    private List<MenuDTO> childs = new ArrayList<>();

    public void add(MenuDTO node) {
        //递归添加节点
        if (0 == node.parentId) {
            this.childs.add(node);
        } else if (node.parentId.equals(this.id)) {
            this.childs.add(node);
        } else {
            for (MenuDTO tmp_node : childs) {
                tmp_node.add(node);
            }
        }
    }

    /**
     * 按照
     */
    @Override
    public int compareTo(MenuDTO o) {
        return this.getId().compareTo(o.getId());
    }
}
