package com.example.janche.user.dto.menuRightWeb;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lirong
 * @ClassName: TreeNodeDTO
 * @Description:
 * @date 2018-11-14 16:51
 */
@Data
public class TreeNodeDTO implements Serializable{
    
    private String id;
    private String parentId;
    private String name;
    private String checked;
    private List<TreeNodeDTO> children = new ArrayList<>();

    public void add(TreeNodeDTO node) {
        //递归添加节点
        if ("0".equals(node.parentId)) {
            this.children.add(node);
        } else if (node.parentId.equals(this.id)) {
            this.children.add(node);
        } else {
            for (TreeNodeDTO tmp_node : children) {
                tmp_node.add(node);
            }
        }
    }

}
