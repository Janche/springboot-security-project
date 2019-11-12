package com.example.janche.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lirong
 * @ClassName: TreeNodeDTO
 * @Description: 树节点
 * @date 2019-7-24 17:03:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNodeDTO implements Serializable, Comparable<TreeNodeDTO> {

    private Long id;
    private Long parentId;
    private String name;
    private Boolean checked;
    private List<TreeNodeDTO> children = new ArrayList<>();

    public void add(TreeNodeDTO node) {
        //递归添加节点
        if (0 == node.parentId) {
            this.children.add(node);
        } else if (node.parentId.equals(this.id)) {
            this.children.add(node);
        } else {
            // 递归
            for (TreeNodeDTO tmp_node : children) {
                tmp_node.add(node);
            }
        }
    }

    /**
     * 按照 ID 排序
     */
    @Override
    public int compareTo(TreeNodeDTO o) {
        return this.getId().compareTo(o.getId());
    }
}
