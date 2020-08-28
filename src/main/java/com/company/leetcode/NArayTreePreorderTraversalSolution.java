package com.company.leetcode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/n-ary-tree-preorder-traversal/
 */
public class NArayTreePreorderTraversalSolution {
    // Definition for a Node.
    static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    static class Stack<T> {
        private StackNode top;
        private int depth;

        class StackNode {
            T value;
            StackNode next;
            StackNode pre;

            public StackNode(T value) {
                this.value = value;
            }
        }

        T pop() {
            if (depth == 0) return null;

            T v = top.value;
            StackNode p = top.pre;
            if (p == null) {
                top = null;
            } else {
                p.next = null;
                top.pre = null;
                top = p;
            }

            depth--;

            return v;
        }

        void push(T v) {
            final StackNode node = new StackNode(v);

            if (depth == 0 && top == null) {
                top = node;
            } else {
                top.next = node;
                node.pre = top;
                top = node;
            }

            depth++;
        }

        boolean isEmpty() {
            return depth == 0;
        }
    }


    static class Solution {

        /**
         * 迭代法 输出N叉树前驱顺序
         * @param root
         * @return
         */
        public List<Integer> preorder(Node root) {
            Stack<Node> stack = new Stack<>();
            List<Integer> result = new ArrayList<>();

            for (Node p = root; p != null;) {
                if (p == root) {
                    result.add(p.val);
                }
                if (p.children != null) {
                    Collections.reverse(p.children);
                    p.children.forEach(stack::push);
                }
                if (!stack.isEmpty()) {
                    final Node lf;
                    lf = stack.pop();
                    result.add(lf.val);

                    p = lf;
                    continue;
                }
                break;
            }
            return result;
        }

        public List<Integer> preorderRecursion(Node root) {
            List<Integer> result = new ArrayList<>();

            traverse(root, result);
            return result;
        }

        void traverse(Node p, List<Integer> result) {
            if (p == null) return;

            result.add(p.val);
            if (p.children != null) {
                for (Node child : p.children) {
                    traverse(child, result);
                }
            }
        }
    }

    public static void main(String[] args) throws IllegalAccessException {

        Node root = new Node(1,
                Arrays.asList(new Node(3,
                                Arrays.asList(new Node(5), new Node(6))),
                        new Node(2), new Node(4)));

//        new Solution().preorder(root).forEach(System.out::println);
        new Solution().preorderRecursion(root).forEach(System.out::println);

    }
}
