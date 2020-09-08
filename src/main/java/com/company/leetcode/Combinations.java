package com.company.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/combinations
 *
 * backtrace
 */
public class Combinations {

    static class Solution {

        private List<List<Integer>> ans = new ArrayList<>();

        public List<List<Integer>> combine(int n, int k) {

            // init
            int[] alternatives = new int[n];
            for (int i = 0; i < n; i++) {
                alternatives[i] = i + 1;
            }

            backtrace(alternatives, k, new LinkedList<>(),0);

            return ans;
        }

        private void backtrace(int[] choices, int k, LinkedList<Integer> sub, int lastIndex) {

            if (k == 0) {
                ans.add(new ArrayList<>(sub));
                return;
            }

            for (int i = lastIndex; i < choices.length; i++) {
                sub.add(choices[i]);
                // choices[i] 已用在组合中所以下一次在剩下的里面选
                backtrace(choices, k - 1, sub,i + 1);
                sub.removeLast();
            }
        }

    }

    public static void main(String[] args) {

        final List<List<Integer>> combine = new Solution().combine(4, 3);

        final List<List<Integer>> lists = new ArrayList<>();
        int[] a = new int[]{1,2,34};
        lists.add(Arrays.stream(a).boxed().collect(Collectors.toList()));

        System.out.println("[");
        for (List<Integer> integers : combine) {
            System.out.print("\t[");
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println("]");
        }
        System.out.println("]");
    }
}
