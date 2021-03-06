package com.company.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/combination-sum-ii/
 *
 * backtrack
 */
public class CombinationSumII {

    static class Solution {

        int target;
        List<List<Integer>> ans = new ArrayList<>();
        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
            Arrays.sort(candidates);
            this.target = target;

            final int candiSize = candidates.length;

            backtrace(candidates, target, new int[candiSize], 0);

            return ans;
        }

        private void backtrace(int[] candidates, int sum, int[] count, int index) {
            if (sum == 0) {
                List<Integer> sub = new ArrayList<>();
                for (int i = 0; i < count.length; i++) {
                    for (int j = 0; j < count[i]; j++) {
                        sub.add(candidates[i]);
                    }
                }
                ans.add(sub);
                return;
            }


            if (sum > 0) {
                for (int i = index; i < candidates.length; i++) {

                    if (i > index && candidates[i] == candidates[i - 1]) {
                        continue;
                    }

                    count[i]++;
                    backtrace(candidates, sum - candidates[i], count, i + 1);
                    count[i]--;
                }
            }
        }
    }

    public static void main(String[] args) {

        final List<List<Integer>> lists = new CombinationSumII.Solution().combinationSum2(new int[]{2,5,2,1,2}, 5);

        System.out.println("[");
        for (List<Integer> list : lists) {
            System.out.print("\t[");
            for (Integer integer : list) {
                System.out.print(integer + " ");
            }
            System.out.println("]");
        }
        System.out.println("]");
    }

}
