package com.company.leetcode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/shortest-palindrome/
 */
public class ShortestPalindrome {

    public String shortestPalindrome(String s) {
        LinkedList<Character> stack = new LinkedList<>();

        final char[] chars = s.toCharArray();

        int sameCached = 0;
        for (int l = 0, r = (chars.length - 1); l < chars.length && r > 0 && l != r ; r--) {

            stack.push(chars[r]);
            if (chars[l] == chars[r]) {
                l++;
                sameCached++;
                continue;
            }

            sameCached = 0;
            l = 0;
        }
        if (sameCached != 0) {
            for (int i = 0; i < sameCached; i++) {
                stack.pop();
            }
        }

        StringBuilder prefix = new StringBuilder();
        Collections.reverse(stack);
        for (Character ch : stack) {
            prefix.append(ch);
        }

        return prefix.toString() + s;
    }

    public static void main(String[] args) {
        System.out.println(new ShortestPalindrome().shortestPalindrome("aacecaaa"));
    }
}
