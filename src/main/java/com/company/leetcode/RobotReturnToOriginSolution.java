package com.company.leetcode;

public class RobotReturnToOriginSolution {


    public boolean judgeCircle(String moves) {

        int x = 0, y = 0;
        for (char move : moves.toCharArray()) {
            switch (move) {
                case 'L': x--; break;
                case 'R': x++; break;
                case 'U': y++; break;
                case 'D': y--; break;
                default:
                    break;
            }
        }

        return x == 0 && y == 0;
    }

    public static void main(String[] args) {

        final RobotReturnToOriginSolution a = new RobotReturnToOriginSolution();
        System.out.println(a.judgeCircle("UD"));
    }
}
