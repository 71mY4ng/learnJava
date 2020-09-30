package com.company.leetcode.bytedancecamp;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/explore/interview/card/bytedance/242/string/1012/
 */
public class LongestSubString {

    static class Solution {

        public int lengthOfLongestSubstring(String s) {

            Queue<Character> q = new LinkedList<>();
            int cnt = 0, max = 0;

            for (int i = 0; i < s.length(); i++) {

                while (q.contains(s.charAt(i))) {
                    q.poll();
                }
                cnt = q.size();

                q.offer(s.charAt(i));
                cnt++;
                max = Math.max(max, cnt);
            }

            return max;
        }
    }

    public static void main(String[] args) {
        final Solution sol = new Solution();
        System.out.println(sol.lengthOfLongestSubstring("bbbbb"));
        System.out.println(sol.lengthOfLongestSubstring("pwwkew"));
        System.out.println(sol.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(sol.lengthOfLongestSubstring(""));
        System.out.println(sol.lengthOfLongestSubstring(" "));
        System.out.println(sol.lengthOfLongestSubstring("dvdf"));

        /*
         * dvdf
         * dv 2
         * vdf 3
         *
         * pwwkew
         *
         * pw 2 w
         * ww 1 k
         * wke 3 w
         * kew 3 $
         */

    }
}
