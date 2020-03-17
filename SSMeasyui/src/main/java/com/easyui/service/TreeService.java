package com.easyui.service;

import com.easyui.dao.TreeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.easyui.pojo.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreeService {
    @Autowired
    private TreeDAO treeDAO;

    public List<TreeNode> getTree() {
        List<TreeNode> nodes = treeDAO.getTreeNodes();
        Map<Integer,TreeNode> map = new HashMap<>();
        List<TreeNode> parents = new ArrayList<>();
        for(TreeNode node:nodes) {
            map.put(node.getId(),node);
            if(node.getParentid() == null) {
                parents.add(node);
            }
        }
        for(TreeNode node:nodes) {
            TreeNode parent = map.get(node.getParentid());
            if(parent != null) {
                parent.getChildren().add(node);
            }
        }
        return parents;
    }

    public void addTree(TreeNode node) {
        treeDAO.addTree(node);
    }

    public void updateTree(TreeNode node) {
        treeDAO.updateTree(node);
    }

    public void deleteTree(int[] ids) {
        for(int id:ids) {
            treeDAO.deleteTree(id);
            treeDAO.updateParentid(id);
        }
    }
}
