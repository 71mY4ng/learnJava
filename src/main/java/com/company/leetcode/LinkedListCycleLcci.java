package com.company.leetcode;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle-lcci/
 */
public class LinkedListCycleLcci {

    /**
     * Definition for singly-linked list.
     */
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
    public static class Solution {

        public ListNode detectCycle(ListNode head) {

            if (head == null || head.next == null) return null;

            boolean isCycle = false;

            ListNode p1 = head.next, p2 = head.next.next;
            for (;
                 p1 != null && p2 != null;
                 p1 = p1.next, p2 = p2.next.next) {

                if (p1 == p2) {
                    isCycle = true;
                    break;
                }
            }

            if (isCycle) {
                p2 = head;
                while (p1 != p2) {
                    p1 = p1.next;
                    p2 = p2.next;
                }

                return p1;
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        ListNode cycleStart = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);
        ListNode f = new ListNode(6);
        a.next = cycleStart;
        cycleStart.next = a;
        c.next = d;
        d.next = e;
        e.next = f;
        f.next = cycleStart;

        final ListNode listNode = new Solution().detectCycle(a);

        System.out.println(listNode == null ? null : listNode.val);
    }
}
