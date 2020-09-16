package com.company.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/invert-binary-tree
 */
public class InvertBinaryTree {
    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    static class Solution {
        public TreeNode invertTree(TreeNode root) {

            if (root != null) {
                invert(root);
            }

            return root;
        }

        private void invert(TreeNode node) {
            if (node == null) return;

            TreeNode temp = node.right;
            node.right = node.left;
            node.left = temp;

            invert(node.left);
            invert(node.right);
        }
    }

    public static void main(String[] args) {

        TreeNode treeNode = new TreeNode(-64);
        treeNode.left = new TreeNode(2);
        treeNode.left.right = new TreeNode(5);
        treeNode.left.left = new TreeNode(4);
        treeNode.right = new TreeNode(3);
        treeNode.right.right = new TreeNode(6);

        final TreeNode inverted = new Solution().invertTree(treeNode);

        Queue<TreeNode> q = new LinkedList<>();

        q.offer(inverted);
        while(!q.isEmpty()) {
            TreeNode p = q.poll();

            System.out.print( (p == null ? "null" : p.val) + " ");

            if (p == null) continue;

            q.offer(p.left);
            q.offer(p.right);
        }

    }
}
