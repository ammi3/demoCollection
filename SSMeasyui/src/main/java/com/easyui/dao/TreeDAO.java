package com.easyui.dao;

import com.easyui.pojo.TreeNode;

import java.util.List;

public interface TreeDAO {
    List<TreeNode> getTreeNodes();//查询所有树节点

    void addTree(TreeNode treeNode);

    void updateTree(TreeNode treeNode);

    void updateParentid(int parentid);

    void deleteTree(int id);
}
