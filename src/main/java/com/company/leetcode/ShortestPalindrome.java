package com.company.leetcode;


/**
 * https://leetcode-cn.com/problems/shortest-palindrome/
 */
public class ShortestPalindrome {

    int Manacher(char[] s_new) {
        // 取得新字符串长度并完成向 s_new 的转换
        int len = s_new.length;
        int[] p = new int[s_new.length];
        // 最长回文长度
        int maxLen = -1;

        int id = 0;
        int mx = 0;

        for (int i = 1; i < len; i++) {
            if (i < mx)
                p[i] = Math.min(p[2 * id - i], mx - i);  // 需搞清楚上面那张图含义，mx 和 2*id-i 的含义
            else
                p[i] = 1;

            while (s_new[i - p[i]] == s_new[i + p[i]])  // 不需边界判断，因为左有 $，右有 ^
                p[i]++;

            // 我们每走一步 i，都要和 mx 比较，我们希望 mx 尽可能的远，
            // 这样才能更有机会执行 if (i < mx)这句代码，从而提高效率
            if (mx < i + p[i]) {
                id = i;
                mx = i + p[i];
            }

            maxLen = Math.max(maxLen, p[i] - 1);
        }

        return maxLen;
    }

    public String shortestPalindrome(String s) {

        final char[] chars = s.toCharArray();
        final int newLen = chars.length * 2 + 3;
        char[] s_new = new char[newLen];
        s_new[0] = '$';
        s_new[1] = '#';
        int j = 2;

        for (char aChar : chars) {
            s_new[j++] = aChar;
            s_new[j++] = '#';
        }

        s_new[j++] = '^';  // 别忘了哦

        System.out.println(Manacher(s_new));

        return null;
    }

    public static void main(String[] args) {
        System.out.println(new ShortestPalindrome().shortestPalindrome("aacecaaa"));
    }
}
