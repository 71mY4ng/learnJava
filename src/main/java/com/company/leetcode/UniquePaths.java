package com.company.leetcode;

/**
 * https://leetcode-cn.com/problems/unique-paths/
 */
public class UniquePaths {

    static class Solution {
        public int uniquePaths(int m, int n) {

            int[][] board = new int[m][n];

            return 0;
        }

        void dfs(int[][] board, int m, int n) {
            if (board[m][n] == 1) {
                return;
            }

            if (m == 0 && n == 0) {

                return;
            }

            for (int i = m; i > 0; i--) {
                for (int j = n; j > 0; j--) {
                    board[i][j] = 1;
                    dfs(board, m++, n);
                    dfs(board, m, n++);
                    board[i][j] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
