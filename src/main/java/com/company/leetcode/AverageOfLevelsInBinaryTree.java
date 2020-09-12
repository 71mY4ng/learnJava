package com.company.leetcode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/average-of-levels-in-binary-tree
 *
 * BFS
 */
public class AverageOfLevelsInBinaryTree {
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
        public List<Double> averageOfLevels(TreeNode root) {
            if (root == null) return Collections.emptyList();

            Queue<TreeNode> queue = new LinkedList<>();
            List<Double> ans = new ArrayList<>();

            queue.offer(root);
            while (!queue.isEmpty()) {

                long adjustSum = 0;
                int levelSize = queue.size();
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    adjustSum += node.val;

                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                ans.add((double) adjustSum / levelSize);
            }

            return ans;
        }
    }

    public static void main(String[] args) {

        TreeNode treeNode = new TreeNode(2147483647);
        treeNode.left = new TreeNode(2147483647);
        treeNode.right = new TreeNode(2147483647);

        final List<Double> ans = new Solution().averageOfLevels(treeNode);

        System.out.print("[");
        for (Double an : ans) {
            System.out.print(an + " ");
        }
        System.out.println("]");

    }
}
