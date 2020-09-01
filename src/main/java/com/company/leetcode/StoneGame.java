package com.company.leetcode;


/**
 * https://leetcode-cn.com/problems/stone-game/
 *
 * 偶数堆先手必赢
 *
 * @see PredictTheWinner
 */
public class StoneGame {

    public boolean stoneGame(int[] piles) {
        boolean ans;
        ans = dynamicProgramming(piles);
//        ans = recurSubProb(piles);
        return ans;
    }

    /**
     * 动态规划
     */
    int[][] dp;
    boolean dynamicProgramming(int[] nums) {
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

    Integer memo[][];
    /**
     * 递归法
     * @param nums
     * @return
     */
    boolean recurSubProb(int[] nums) {

        memo = new Integer[nums.length][nums.length];
        return subProb(nums, 0, nums.length - 1) >= 0;
    }

    /**
     * 递归子问题
     *
     * memo 数组缓存结果
     * @param nums
     * @param i 头指针
     * @param j 尾指针
     * @return
     */
    int subProb(int[] nums, int i, int j) {

        // 重复子问题直接取算过的结果
        if (memo[i][j] != null) return memo[i][j];

        // 因为p1 先手，如果p1, p2 pick 同一个, 那么p1 优先
        if (i == j) {
            memo[i][j] = nums[i];
            return nums[i];
        }

        // pick i, 下一轮只能在 i + 1, j 间选, 因为下一轮是另一个对手，所以本轮需要比下一轮赢得多
        int pickI = nums[i] - subProb(nums, i + 1, j);
        // pick j, 下一轮只能在 i, j - 1 间选
        int pickJ = nums[j] - subProb(nums, i, j - 1);
        int picked = Math.max(pickI, pickJ);

        memo[i][j] = picked;
        return picked;
    }

    public static void main(String[] args) {
        final StoneGame stoneGame = new StoneGame();
        System.out.println(stoneGame.stoneGame(new int[]{5,3,4,5}));

//        final Integer[][] memo = stoneGame.memo;
//        for (int i = 0; i < memo.length; i++) {
//            for (int j = 0; j < memo.length; j++) {
//                if (j != 0) System.out.print(",\t\t");
//                System.out.print(memo[i][j] + " (i: " + i + ", j:" + j + ")");
//            }
//            System.out.println();
//        }

        final int[][] dp = stoneGame.dp;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                if (j != 0) System.out.print(",\t\t");
                System.out.print(dp[i][j] + " (i: " + i + ", j:" + j + ")");
            }
            System.out.println();
        }
    }
}
