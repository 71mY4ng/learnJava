package com.company.leetcode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii
 *
 * BFS
 */
public class BinaryTreeLevelOrderTraversalII {

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

        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> ans = new LinkedList<>();

        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            if (root == null) return Collections.emptyList();

            queue.offer(root);
            while(!queue.isEmpty()) {

                List<Integer> level = new ArrayList<>();
                int levelSize = queue.size();
                for (int i = 0; i < levelSize; i++) {
                    TreeNode cur = queue.poll();
                    level.add(cur.val);

                    // adjust nodes
                    if (cur.left != null) {
                        queue.offer(cur.left);
                    }
                    if (cur.right != null) {
                        queue.offer(cur.right);
                    }
                }
                ans.add(0, level);
            }

            return ans;
        }
    }

    public static void main(String[] args) {

        TreeNode treeNode = new TreeNode(-64);
        treeNode.left = new TreeNode(2);
        treeNode.left.right = new TreeNode(5);
        treeNode.left.left = new TreeNode(4);
        treeNode.left.right = new TreeNode(6);
        treeNode.right = new TreeNode(3);

        final List<List<Integer>> lists = new Solution().levelOrderBottom(null);

        for (List<Integer> list : lists) {
            System.out.print("[");
            for (Integer integer : list) {
                System.out.print(integer + ", ");
            }
            System.out.println("],");
        }
    }
}
