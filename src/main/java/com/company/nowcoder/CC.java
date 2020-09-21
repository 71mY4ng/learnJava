package com.company.nowcoder;

public class CC {

    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
    public static class Solution {
        public ListNode Merge(ListNode list1,ListNode list2) {
            ListNode ans = new ListNode(0);
            ListNode head = ans;

            while (list1 != null || list2 != null) {
                if (list1 != null && list1.val <= list2.val) {
                    ans.next = new ListNode(list1.val);
                    list1 = list1.next;
                } else {
                    ans.next = new ListNode(list2.val);

                    list2 = list2.next;
                }
                ans = ans.next;
            }
            return head.next;
        }

    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        a.next = new ListNode(2);
        a.next.next = new ListNode(3);
        a.next.next.next = new ListNode(4);
        a.next.next.next.next = new ListNode(5);

        ListNode b = new ListNode(1);
        b.next = new ListNode(2);
        b.next.next = new ListNode(3);
        b.next.next.next = new ListNode(4);
        b.next.next.next.next = new ListNode(5);

        ListNode merge = new Solution().Merge(a, b);

        while (merge != null) {
            System.out.print(merge.val);
            merge = merge.next;
        }
        System.out.println();

    }
}
