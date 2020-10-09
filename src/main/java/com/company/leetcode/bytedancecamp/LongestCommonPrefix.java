package com.company.leetcode.bytedancecamp;

/**
 * https://leetcode-cn.com/explore/interview/card/bytedance/242/string/1014/
 */
public class LongestCommonPrefix {

    static class Solution {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null) return "";

            StringBuilder sb = new StringBuilder();

            int i = 0;
            while (true) {
                char cmp = '\000';
                for (String str : strs) {

                    if (i > str.length() - 1) {
                        return sb.toString();
                    }

                    if (cmp == '\000') {
                        cmp = str.charAt(i);
                    } else if (cmp != str.charAt(i)) {
                        return sb.toString();
                    }
                }
                i++;
                sb.append(cmp);
            }
        }
    }

    public static void main(String[] args) {

        System.out.println(new Solution().longestCommonPrefix(new String[]{ "dog","racecar","car" }));
    }
}
