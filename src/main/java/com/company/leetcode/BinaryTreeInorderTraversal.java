package com.company.leetcode;

import java.util.*;


/**
 * https://leetcode-cn.com/problems/binary-tree-inorder-traversal
 */
public class BinaryTreeInorderTraversal {


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
        public List<Integer> inorderTraversal(TreeNode root) {
            if (root == null) return Collections.emptyList();

            List<Integer> ans = new ArrayList<>();
            inorderRecur(root, ans);
            return ans;

//            return inorderForeach(root);
        }

        private List<Integer> inorderForeach(TreeNode root) {

            List<Integer> ans = new ArrayList<>();
            Stack<TreeNode> s = new Stack<>();

            s.push(root);
            TreeNode cur = root;
            while (!s.isEmpty()) {

                if (cur.left != null) {
                    s.push(cur);
                    cur = cur.left;
                    continue;
                }

                // left is null
                while (!s.isEmpty()) {
                    cur = s.pop(); // parent
                    ans.add(cur.val);

                    if (cur.right != null) {
                        cur = cur.right;
                        s.push(cur);
                        break;
                    }
                }
            }

            return ans;
        }


        private void inorderRecur(TreeNode node, List<Integer> ans) {

            if (node.left != null) {
                inorderRecur(node.left, ans);
            }

            ans.add(node.val);

            if (node.right != null) {
                inorderRecur(node.right, ans);
            }
        }

    }

    public static void main(String[] args) {

        TreeNode treeNode = new TreeNode(-64);
//        treeNode.left = new TreeNode(2);
//        treeNode.left.right = new TreeNode(5);
//        treeNode.left.left = new TreeNode(4);
        treeNode.right = new TreeNode(3);
        treeNode.right.right = new TreeNode(6);

        final List<Integer> lists = new Solution().inorderTraversal(treeNode);
        System.out.print("[");
        lists.stream().map(item -> item + " ").forEach(System.out::print);
        System.out.println("]");

    }
}
