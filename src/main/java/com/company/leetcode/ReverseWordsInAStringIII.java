package com.company.leetcode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/reverse-words-in-a-string-iii/
 */
public class ReverseWordsInAStringIII {

    public String reverseWords(String s) {

        final String[] words = s.split(" ");
        StringBuilder ans = new StringBuilder();
        for (int w = 0; w < words.length; w++) {

            final char[] chars = words[w].toCharArray();
            for (int i = (chars.length - 1); i >= 0; --i) {
                ans.append(chars[i]);
            }
            if (w + 1 < words.length)
                ans.append(" ");
        }

        return ans.toString();
    }

    public String reverseWords2(String s) {

        final char[] schars = s.toCharArray();
        char[] ans = new char[schars.length];
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < schars.length; i++) {

            if (schars[i] != ' ') stack.push(schars[i]);

            // break or ! hasNext()
            if (schars[i] == ' ' || (i + 1 >= schars.length && (i = i + 1) >= schars.length)) {
                for (int j = i - stack.size(); !stack.isEmpty() || j < i; j++) {
                    if (i < schars.length) {
                        ans[i] = ' ';
                    }
                    ans[j] = stack.pop();
                }
            }
        }

        return new String(ans);
    }

    public String reverseWords3(String s) {

        final char[] schars = s.toCharArray();
        StringBuilder ans = new StringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < schars.length; i++) {

            if (schars[i] != ' ') word.append(schars[i]);

            // break or ! hasNext()
            if (schars[i] == ' ' || (i + 1 >= schars.length && (i = i + 1) >= schars.length)) {

                ans.append(word.reverse());
                if (i < schars.length)
                    ans.append(" ");
                word.delete(0, word.length());
            }
        }

        return ans.toString();
    }

    public String reverseWords4(String s) {

        return Arrays.stream(s.split(" "))
                .map(word -> new StringBuilder(word).reverse().append(' '))
                .reduce(StringBuilder::append)
                .orElse(new StringBuilder())
                .toString().trim();
    }


    public static void main(String[] args) {
        final String s = "Let's take LeetCode contest";

        System.out.println(new ReverseWordsInAStringIII().reverseWords4(s));
    }
}
