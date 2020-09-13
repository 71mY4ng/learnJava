package com.company.leetcode;

/**
 * https://leetcode-cn.com/problems/word-search/
 *
 *
 * backtrack
 */
public class WordSearch {
    static class Solution {
        int xlen;
        int ylen;
        public boolean exist(char[][] board, String word) {
            this.xlen = board.length;
            this.ylen = board[0].length;

            boolean ans = false;
            boolean[][] visited = new boolean[xlen][ylen];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == word.charAt(0)) {
                        if (ans = backtrack(board, i, j, word, 0, visited)) break;
                    }
                }
                if (ans) break;
            }

            return ans;
        }

        private boolean backtrack(char[][] board, int x, int y, String word, int index, boolean[][] memo) {
            if (x >= xlen || y >= ylen || y < 0 || x < 0) return false;
            if (board[x][y] != word.charAt(index)) {
                return false;
            }

            if (!memo[x][y]) {
                memo[x][y] = true;

                if (index == word.length() - 1) {
                    return true;
                }

                System.out.println("index: " + index + ", x: " + x + ", y: " + y + ", track: " + word.charAt(index));

                if (    (backtrack(board, x + 1, y, word, index + 1, memo)) ||
                        (backtrack(board, x, y + 1, word, index + 1, memo)) ||
                        (backtrack(board, x, y - 1, word, index + 1, memo)) ||
                        (backtrack(board, x - 1, y, word, index + 1, memo))
                ) {

                    return true;
                }

                memo[x][y] = false;
            }

            return false;
        }
    }

    public static void main(String[] args) {
        final Solution solution = new Solution();
        final char[][] chars = {
                new char[]{'A', 'A', 'A', 'A'},
                new char[]{'A', 'A', 'A', 'A'},
                new char[]{'A', 'A', 'A', 'A'}
        };

        System.out.println(solution.exist(chars, "AAAAAAAAAAAAA"));
        System.out.println("========================================");
        System.out.println(solution.exist(chars, "SEE"));
        System.out.println("========================================");
        System.out.println(solution.exist(chars, "ABCB"));
    }

}
