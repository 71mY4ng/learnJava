package com.company.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/n-queens/submissions/
 *
 * 回溯法 遍历决策树
 */
public class NQueens {

    int n;
    List<List<String>> ans = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {

        this.n = n;

        char[][] board = createBoard(n);

        backtrack(board, 0);

        return ans;
    }

    char[][] createBoard(int n) {

        char[][] board = new char[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        return board;
    }

    void backtrack(char[][] board, int row) {
        if (row == n) {
            // END backtrack
            ans.add(Arrays.stream(board)
                    .map(String::new)
                    .collect(Collectors.toList()));
            return;
        }

        for (int col = 0; col < board[row].length; col++) {
            if (isValid(board, row, col)) {

                board[row][col] = 'Q';

                backtrack(board, row + 1);

                board[row][col] = '.';
            }
        }
    }

    boolean isValid(char[][] board, int row, int col) {

        int lrow;
        int lcol;
        int rCol;
        for (int ri = 0; ri < row; ri++) {
            // 竖排上有
            if (board[ri][col] == 'Q') {
                return false;
            }

            lrow = row - ri - 1;
            lcol = col - ri - 1;
            rCol = col + ri + 1;

            if (lrow >= 0) {
                // 左上方有
                if (lcol >= 0 && board[lrow][lcol] == 'Q') {
                    return false;
                }

                // 右上方有
                if (rCol < n && board[lrow][rCol] == 'Q') {
                    return false;
                }
            }
        }

        return true;
    }


    public static void main(String[] args) {
        final List<List<String>> ans = new NQueens().solveNQueens(4);
        System.out.println("[");
        for (List<String> an : ans) {
            System.out.println("[");
            for (String s : an) {
                System.out.println(s);
            }
            System.out.println("],");
        }
        System.out.println("]");
    }
}
