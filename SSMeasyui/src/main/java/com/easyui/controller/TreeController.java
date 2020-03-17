package com.easyui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.easyui.pojo.TreeNode;
import com.easyui.service.TreeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TreeController {

    @Autowired
    private TreeService treeService;

    @RequestMapping("tree.json")
    @ResponseBody
    public List<TreeNode> tree() {
        return treeService.getTree();
    }

    @RequestMapping("edit_tree.json")
    @ResponseBody
    public Map<String, String> edit(TreeNode node) {
        Map<String, String> result = new HashMap<>();
        try{
            if (node.getId() == null || node.getId() == 0) {
                treeService.addTree(node);
            } else {
                treeService.updateTree(node);
            }

            result.put("status","true");
            result.put("mseeage","编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status","false");
            result.put("mseeage","编辑失败");
        }
        return result;
    }

    @RequestMapping("delete_tree.json") //批量删除树
    @ResponseBody
    public Map<String, String> delete(int[] ids) {
        Map<String, String> result = new HashMap<>();
        try{
            treeService.deleteTree(ids);
            result.put("status","true");
            result.put("mseeage","删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status","false");
            result.put("mseeage","删除失败");
        }
        return result;
    }



}
