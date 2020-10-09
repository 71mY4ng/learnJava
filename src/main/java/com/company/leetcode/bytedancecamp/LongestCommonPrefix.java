package com.company.leetcode.bytedancecamp;

/**
 * https://leetcode-cn.com/explore/interview/card/bytedance/242/string/1014/
 */
public class LongestCommonPrefix {

    static class Solution {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) return "";

            int i = 0;
            char cmp;
            while (true) {
                cmp = '\000';
                for (String str : strs) {

                    if (i > str.length() - 1) {
                        return strs[0].substring(0, i);
                    }

                    if (cmp == '\000') {
                        cmp = str.charAt(i);
                    } else if (cmp != str.charAt(i)) {
                        return strs[0].substring(0, i);
                    }
                }
                i++;
            }
        }
    }

    public static void main(String[] args) {

        System.out.println(new Solution().longestCommonPrefix(new String[]{ }));
    }
}
