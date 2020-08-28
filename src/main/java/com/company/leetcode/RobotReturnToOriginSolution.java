package com.company.leetcode;

public class RobotReturnToOriginSolution {


    public boolean judgeCircle(String moves) {
        // 减去 A 为了缩小count 的分配大小
        int[] count = new int[26];

        for (char move : moves.toCharArray()) {
            count[move - 'A']++;
        }

        // 上下和左右移动的次数相同即回到原点
        return count['U' - 'A'] == count['D' - 'A'] && count['L' - 'A'] == count['R' - 'A'];
    }

    public static void main(String[] args) {

        final RobotReturnToOriginSolution a = new RobotReturnToOriginSolution();
        System.out.println(a.judgeCircle("DD"));

//        System.out.println(Long.valueOf('U' - 'A'));
//        System.out.println(Long.valueOf('D' - 'A'));
//        System.out.println(Long.valueOf('L' - 'A'));
//        System.out.println(Long.valueOf('R' - 'A'));
    }
}
