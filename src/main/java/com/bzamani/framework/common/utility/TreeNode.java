package com.bzamani.framework.common.utility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode {
    private String id;
    private String text;
    private int open;
    private String im0 = "folderClosed.gif";
    private String im1 = "folderOpen.gif";
    private String im2 = "folderClosed.gif";
    private String tooltip;
    @JsonIgnore
    private TreeNode parent;
    @JsonIgnore
    private List<TreeNode> childs;
    private Integer childCount;
    @JsonIgnore
    private Map<String, String> attrMap = new HashMap<String, String>();

    public TreeNode() {
    }

    public TreeNode(String id, String text) {
        this.id = id;
        this.text = text;
        this.childCount = 0;
        this.open = 0;
    }

    public TreeNode(String id, String text, String tooltip) {
        this.id = id;
        this.text = text;
        this.childCount = 0;
        this.open = 0;
        this.tooltip = tooltip;
    }

    public TreeNode addAttr(String attrName, String attrValue) {
        attrMap.put(attrName, attrValue);
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIm0() {
        return im0;
    }

    public void setIm0(String im0) {
        this.im0 = im0;
    }

    public String getIm1() {
        return im1;
    }

    public void setIm1(String im1) {
        this.im1 = im1;
    }

    public String getIm2() {
        return im2;
    }

    public void setIm2(String im2) {
        this.im2 = im2;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    @JsonSerialize
    @JsonProperty("item")
    public List<TreeNode> getChilds() {
        if (childs == null) {
            childs = new ArrayList<TreeNode>();
        }
        return childs;
    }

    public void setChilds(List<TreeNode> childs) {
        this.childs = childs;
    }

    @JsonProperty("userdata")
    public Map<String, String> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(Map<String, String> attrMap) {
        this.attrMap = attrMap;
    }

    @JsonSerialize
    @JsonProperty("child")
    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }
}

