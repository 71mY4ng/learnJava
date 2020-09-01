package com.company.leetcode;


/**
 * https://leetcode-cn.com/problems/predict-the-winner/
 */
public class PredictTheWinner {

    int[][] dp;
    public boolean PredictTheWinner(int[] nums) {
        this.dp = new int[nums.length][nums.length];

        for (int i = 0; i < nums.length; i++) {
            dp[i][i] = nums[i];
        }
        for (int i = nums.length - 2; i >= 0; i--) {
            for (int j = i + 1; j < nums.length; j++) {

                int pickI = nums[i] - dp[i + 1][j];
                int pickJ = nums[j] - dp[i][j - 1];
                dp[i][j] = Math.max(pickI, pickJ);
            }
        }

        return dp[0][nums.length - 1] >= 0;
    }

    public static void main(String[] args) {

        System.out.println(new PredictTheWinner().PredictTheWinner(new int[]{0}));

    }
}
