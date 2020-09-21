package com.company.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/convert-bst-to-greater-tree/
 */
public class ConvertBstToGreaterTree {

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
        public TreeNode convertBST(TreeNode root) {

            traversal(root);
            return root;
        }

        int sum = 0;
        private void traversal(TreeNode node) {

            if (node == null) return;

            traversal(node.right);
            sum += node.val;
            node.val = sum;
            traversal(node.left);
        }
    }

    public static void main(String[] args) {

        TreeNode treeNode = new TreeNode(4);
        treeNode.left = new TreeNode(2);
        treeNode.left.right = new TreeNode(3);
        treeNode.left.left = new TreeNode(1);
        treeNode.right = new TreeNode(8);
        treeNode.right.right = new TreeNode(9);
        treeNode.right.left = new TreeNode(7);
        treeNode.right.left.right  = new TreeNode(6);
        treeNode.right.left.left  = new TreeNode(5);

//        TreeNode treeNode = new TreeNode(5);
//        treeNode.left = new TreeNode(2);
//        treeNode.right = new TreeNode(13);

        printTreeLevel(treeNode);

        new Solution().convertBST(treeNode);

        printTreeLevel(treeNode);
    }

    public static void printTreeLevel(TreeNode treeNode) {

        Queue<TreeNode> q = new LinkedList<>();

        System.out.print("[");
        q.offer(treeNode);
        while(!q.isEmpty()) {
            TreeNode p = q.poll();

            System.out.print( (p == null ? "null" : p.val) + " ");

            if (p == null) continue;

            q.offer(p.left);
            q.offer(p.right);
        }
        System.out.println("]");
    }
}
