package com.company.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/permutation-sequence
 */
public class PermutationSequence {

    List<String> ans = new ArrayList<>();
    int k;

    /**
     * 回溯法
     * @param n
     * @param k
     * @return
     */
    public String getPermutation(int n, int k) {
        if (n == 0) {
            return "";
        }
        this.k = k;

        char[] seq = createSeq(n);
        backtrace(seq, new char[n], new boolean[n], 0);
        return ans.get(k - 1);
    }

    char[] createSeq(int n) {
        char[] ints = new char[n];
        char ch = '1';
        for (int i = 0; i < n; i++, ch++) {
            ints[i] = ch;
        }
        return ints;
    }

    void backtrace(char[] seq, char[] track, boolean[] used, int level) {
        if (ans.size() == k) return;

        if (level == seq.length) {
            ans.add(new String(track));
            return;
        }

        for (int i = 0; i < seq.length; i++) {
            if (!used[i]) {
                track[level++] = seq[i];
                used[i] = true;
                backtrace(seq, track, used, level);
                track[--level] = 0;
                used[i] = false;
            }
        }
    }

    /**
     * 根据阶乘结果计算区间下标的的方法
     * @param n
     * @param k
     * @return
     */
    public String ans(int n, int k) {

        // factorials : (n - 1)! 的缓存, 决策树从上往下n 递减, 代表该层一个结点的子节点总数(即: 涉及的可能结果数)
        final int[] factorials = getFactorials(n);
        char[] ans = new char[n];
        List<Integer> remains = getCandidates(n);

        // k 需要用于计算下标所以 - 1
        k -= 1;
        for (int i = 0; i < n; i++) {
            // 每次选取后剩下的sublist 都是有序的, sublist每个元素的结果数跨度是 fact[i]
            // 所以 k / fact[i] 来判断 k 会落在当前的哪个区间上
            // 由于区间不超过 sublist 的 size 所以需要做个溢出循环 (index = hash % len)
            int index = (k / factorials[i]) % remains.size();
            ans[i] = (char) ('0' + remains.remove(index));
        }

        return new String(ans);
    }


    int[] getFactorials(int n) {
        int[] factorials = new int[n];
        for (int i = 0; i < n; i++) {

            int factor = 1;
            for (int j = n - i - 1; j > 0; j--) {
                factor *= j;
            }
            factorials[i] = factor;
        }

        return factorials;
    }

    List<Integer> getCandidates(int n) {
        List<Integer> candi = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            candi.add(i + 1);
        }
        return candi;
    }

    public static void main(String[] args) {

//        System.out.println(new PermutationSequence().getPermutation(7, 3));

        System.out.println(new PermutationSequence().ans(7, 3));
//        System.out.println(Arrays.toString(new PermutationSequence().getFactorios(7)));
    }
}
