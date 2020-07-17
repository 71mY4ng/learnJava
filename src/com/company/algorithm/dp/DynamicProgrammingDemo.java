package com.company.algorithm.dp;

import java.util.*;

/**
 * 动态规划
 *
 * 最少硬币找零问题
 */
public class DynamicProgrammingDemo {

    public static void main(String[] args) {

        int amount = 122;
        final List<Integer> coins = Arrays.asList(5, 10, 1);

        System.out.println("为了凑 " + amount + " 元，面值" + coins + "元的硬币数量至少有 "
                + new DynamicCoinChangeSolver(coins, amount, true).coinChange());
    }

    static class DynamicCoinChangeSolver {
        private final List<Integer> coins;
        private final int amount;
        private final boolean arraySolution;
        private Map<Integer, Double> memo = new HashMap<>();

        private List<Integer> dpList;

        /**
         * @param coins 可选硬币面值
         * @param amount 目标金额
         */
        public DynamicCoinChangeSolver(List<Integer> coins, int amount) {
            this(coins, amount, false);
        }

        /**
         * 设 amount 为目标金额, coins 是给定的硬币面值, 那么至少需要dp(amount) 个coins 面值的硬币才能达到目标金额<br/>
         * 状态转移方程:
         * <pre>{@code
         *      dp(amount) {
         *          if (amount == 0) return 0;
         *          if (amount < 0) return -1;
         *          return min{dp(amount - coin) + 1 | coin ∈ coins}
         *      }
         * }</pre>
         * @param coins 可选硬币面值
         * @param amount 目标金额
         * @param arraySolution 使用自底向上的列表法
         */
        public DynamicCoinChangeSolver(List<Integer> coins, int amount, boolean arraySolution) {
            this.coins = coins;
            this.amount = amount;
            this.arraySolution = arraySolution;

            if (arraySolution) {
                // 将列表初始化为 amount + 1 的原因是，目标的最大就是 amount, amount + 1 就代表了当前的无穷大
                this.dpList = new ArrayList<>(amount + 1);
                for (int i = 0; i < amount + 1; i++) {
                    this.dpList.add(amount + 1);
                }
            }
        }

        /**
         * @return 最少使用的硬币数
         */
        public int coinChange() {
            if (arraySolution) {
                return dpWithArray();
            }

            final double dp = dp(amount);
            return (int) dp;
        }

        /**
         * 递归法 + memo 跳过无用迭代
         * @param amount
         * @return
         */
        private double dp(int amount) {
            // 已经计算过 跳过
            if (memo.containsKey(amount)) return memo.get(amount);

            if (amount == 0) return 0;
            if (amount < 0) return -1;
            double result = Double.POSITIVE_INFINITY;

            for (Integer coin : coins) {
                double subprob = dp(amount - coin);
                if (result == -1) continue;
                result = Math.min(result, subprob + 1);
            }

            // 每次的分支做savepoint, 用以跳过无用递归
            memo.put(amount, result != Double.POSITIVE_INFINITY ? result : -1);
            return memo.get(amount);
        }

        private int dpWithArray() {
            this.dpList.set(0, 0);
            for (int i = 0; i < dpList.size(); i++) {
                for (Integer coin : coins) {
                    if (i - coin < 0) continue;
                    dpList.set(i, Math.min(dpList.get(i), 1 + dpList.get(i - coin)));
                }
            }

            return (dpList.get(amount) == amount + 1) ? -1 : dpList.get(amount);
        }

    }

}
