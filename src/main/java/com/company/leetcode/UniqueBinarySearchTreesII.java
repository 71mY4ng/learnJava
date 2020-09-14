package com.company.leetcode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/unique-binary-search-trees-ii
 *
 * dp
 */
public class UniqueBinarySearchTreesII {
    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static class Solution {
        public List<TreeNode> generateTrees(int n) {
            if (n <= 0) return Collections.emptyList();
            return genTree(1, n);
        }

        private List<TreeNode> genTree(int left, int right) {
            List<TreeNode> subTrees = new ArrayList<>();
            if (left > right) {
                subTrees.add(null);
                return subTrees;
            }
            for (int i = left; i <= right; i++) {
                List<TreeNode> lTrees = genTree(left, i - 1);
                List<TreeNode> rTrees = genTree(i + 1, right);

                for (TreeNode lt : lTrees) {
                    for (TreeNode rt : rTrees) {
                        TreeNode p = new TreeNode(i);
                        p.left = lt;
                        p.right = rt;
                        subTrees.add(p);
                    }
                }
            }

            return subTrees;
        }
    }

    public static void main(String[] args) {
        final List<TreeNode> trees = new Solution().generateTrees(0);

        Queue<TreeNode> q = new LinkedList<>();
        for (TreeNode tree : trees) {

            System.out.print("[");
            q.offer(tree);
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

}
