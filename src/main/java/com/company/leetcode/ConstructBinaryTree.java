package com.company.leetcode;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * https://leetcode-cn.com/problems/zhong-jian-er-cha-shu-lcof/submissions/
 * https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 */
public class ConstructBinaryTree {
    /**
     * Definition for a binary tree node.
     */
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
    static class Solution {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            final int n = preorder.length;
            if (n == 0)
                return null;

            TreeNode root = build(0, n - 1, 0, preorder, inorder);

            return root;
        }

        private int getInRootIndex(int preRoot, int left, int right, int[] inorder) {
            for (int i = left; i < right; i++) {
                if (inorder[i] == preRoot) {
                    return i;
                }
            }
            return right;
        }

        private TreeNode build(int left, int right, int preIndex, int[] preorder, int[] inorder) {
//            System.out.printf("n: %s, l: %s, r: %s\n", preIndex, left, right);
            if (left > right)
                return null;

            final int preRoot = preorder[preIndex];
            int inRootIndex = getInRootIndex(preRoot, left, right, inorder);
//            System.out.printf("n: %s, l: %s, r: %s, root: %s (%s)\n", preIndex, left, right, inRootIndex, preRoot);

            TreeNode root = new TreeNode(preRoot);
            root.left = build(left, inRootIndex - 1, preIndex + 1, preorder, inorder);
            // leftOffset = inRootIndex - left
            // (preIndex + 1) + (inRootIndex - left), skip steps for inorder left subtree
            root.right = build(inRootIndex + 1, right, preIndex + 1 + inRootIndex - left, preorder, inorder);

            return root;
        }
    }

    public static void main(String[] args) {
//        final TreeNode treeNode = new Solution().buildTree(new int[]{-1}, new int[]{-1});
//        final TreeNode treeNode = new Solution().buildTree(new int[]{1,2}, new int[]{2,1});
//        final TreeNode treeNode = new Solution().buildTree(new int[]{1,2}, new int[]{1,2});
//        final TreeNode treeNode = new Solution().buildTree(new int[]{1,2,3}, new int[]{3,2,1});
        final TreeNode treeNode = new Solution().buildTree(new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7});
        System.out.println(ReflectionToStringBuilder.toString(treeNode));
    }
}
