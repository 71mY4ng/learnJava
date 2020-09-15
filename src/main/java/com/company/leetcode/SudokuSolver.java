package com.company.leetcode;

/**
 * https://leetcode-cn.com/problems/sudoku-solver
 *
 * backtrack
 */
public class SudokuSolver {

    static class Solution {
        public void solveSudoku(char[][] board) {

            backtrack(board,  0);
            printBoard(board);
        }

        private boolean isValid(char[][] board, int row, int col, int val) {

            for (int i = 0; i < 9; i++) {
                if (board[row][i] == val || board[i][col] == val) return false;
            }

            int chunkRowL = row / 3 * 3;
//            int chunkRowR = chunkRowL + 3;

            int chunkColL = col / 3 * 3;
//            int chunkColR = chunkColL + 3;

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (board[chunkRowL + r][chunkColL + c] == val) return false;
                }
            }

            return true;
        }

        private boolean backtrack(char[][] board, int val) {
            if (val == 81) {
                // end
                return true;
            }
//            if (row >= board.length || col >= board.length) return false;

            int row = val / 9;
            int col = val % 9;
            if (board[row][col] != '.') {
                return backtrack(board, val + 1);
            }

            for (int i = 1; i <= 9; i++) {
                char c = (char) ('0' + i);
                if (isValid(board, row, col, c)) {
                    board[row][col] = c;
                    if (backtrack(board,val + 1)) return true;
                    board[row][col] = '.';
                }
            }

            return false;
        }

        private void printBoard(char[][] board) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        new SudokuSolver.Solution().solveSudoku(board);
    }
}
