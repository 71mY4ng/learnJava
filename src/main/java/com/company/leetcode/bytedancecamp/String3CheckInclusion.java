package com.company.leetcode.bytedancecamp;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/explore/interview/card/bytedance/242/string/1016/
 */
public class String3CheckInclusion {

    static class Solution {
        public boolean checkInclusion(String s1, String s2) {

            if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) return false;
            if (s1.length() > s2.length()) return false;

            int[] cnt = new int[26];
            int[] cntCmp = new int[26];
            for (int i = 0; i < s1.length(); i++) {
                cnt[s1.charAt(i) - 'a']++;
            }

            boolean result = false;
            for (int i = 0; i < s2.length(); i++) {

                if (s2.length() - i < s1.length()) {
                    // not len for sub
                    return false;
                }

                if (cnt[s2.charAt(i) - 'a'] == 0) {
                    // not ex
                    continue;
                }

                for (int j = i, a = 0; j < s2.length() && a < s1.length(); j++, a++) {
                    cntCmp[s2.charAt(j) - 'a']++;
                    int count = cnt[s2.charAt(j) - 'a'];

                    if (count == 0 || count < cntCmp[s2.charAt(j) - 'a']) {
                        // not existed
                        Arrays.fill(cntCmp, 0);
                        result = false;
                        break;
                    }

                    result = true;
                }
                if (result) break;
            }

            return result;
        }
    }

    public static void main(String[] args) {

        System.out.println(new Solution().checkInclusion("a", "b"));

    }
}
