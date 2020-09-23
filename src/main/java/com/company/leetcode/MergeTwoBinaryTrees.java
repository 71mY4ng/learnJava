package com.company.leetcode;

import java.util.LinkedList;
import java.util.Queue;


public class MergeTwoBinaryTrees {

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
        public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
            if (t1 == null && t2 == null) return null;

            int val = 0;
            TreeNode l1 = null, l2 = null;
            TreeNode r1 = null, r2 = null;
            if (t1 != null) {
                val += t1.val;
                l1 = t1.left;
                r1 = t1.right;
            }
            if (t2 != null) {
                val += t2.val;
                l2 = t2.left;
                r2 = t2.right;
            }

            TreeNode root = new TreeNode(val);

            root.left = mergeTrees(l1, l2);
            root.right = mergeTrees(r1, r2);

            return root;
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

        TreeNode t2 = new TreeNode(4);
        t2.left = new TreeNode(2);
        t2.left.right = new TreeNode(3);
        t2.left.left = new TreeNode(1);

        final TreeNode merge = new Solution().mergeTrees(treeNode, t2);

        printTreeLevel(merge);
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
