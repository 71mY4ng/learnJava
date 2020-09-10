package com.company.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/combination-sum-iii/
 *
 * backtrack
 */
public class CombinationSumIII {

    static class Solution {

        int kSize;
        List<List<Integer>> ans = new ArrayList<>();
        public List<List<Integer>> combinationSum3(int k, int n) {
            final int[] candidates = createArray();
            this.kSize = k;

            backtrace(candidates, n, k, new Integer[k], 0);

            return ans;
        }

        private int[] createArray() {
            int[] arr = new int[9];
            for (int i = 0; i < 9; i++) {
                arr[i] = i + 1;
            }
            return arr;
        }

        private void backtrace(int[] candidates, int sum, int k, Integer[] track, int index) {
            if (sum == 0 && k == 0) {
                ans.add(Arrays.asList(track.clone()));
                return;
            }

            if (sum > 0 && k > 0) {
                for (int i = index; i < candidates.length; i++) {
                    track[kSize - k] = candidates[i];
                    backtrace(candidates, sum - candidates[i], k - 1, track, i + 1);
                    track[kSize - k] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {

        final List<List<Integer>> lists = new CombinationSumIII.Solution().combinationSum3(1, 7);

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
