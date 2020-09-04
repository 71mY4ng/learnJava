package com.company.leetcode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/binary-tree-paths/
 *
 * dfs
 */
public class BinaryTreePaths {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    List<String> ans = new ArrayList<>();
    public List<String> binaryTreePaths(TreeNode root) {

        if (root == null) {
            return Collections.emptyList();
        }

        traversal(root, new StringBuilder());

        return ans;
    }

    void traversal(TreeNode node, StringBuilder path) {
        path.append(node.val);

        if (node.left == null && node.right == null) {
            ans.add(path.toString());
            return;
        }

        if (node.left != null) {
            traversal(node.left, (node.right == null ? path : new StringBuilder(path)).append("->"));
        }
        if (node.right != null) {
            traversal(node.right, (node.left == null ? path : new StringBuilder(path)).append("->"));
        }
    }

    public static void main(String[] args) {

        TreeNode treeNode = new TreeNode(-64);
        treeNode.left = new TreeNode(2);
        treeNode.left.right = new TreeNode(5);
        treeNode.left.left = new TreeNode(4);
        treeNode.left.right = new TreeNode(6);
        treeNode.right = new TreeNode(3);

        System.out.println(new BinaryTreePaths().binaryTreePaths(treeNode));
    }
}
