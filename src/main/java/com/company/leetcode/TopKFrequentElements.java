package com.company.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/top-k-frequent-elements
 */
public class TopKFrequentElements {

    static class Solution {

        public int[] topKFrequentPriorityQueueVer(int[] nums, int k) {
            int[] ans = new int[k];

            Map<Integer, Integer> map = new HashMap<>();
            PriorityQueue<Map.Entry<Integer, Integer>> kRank = new PriorityQueue<>(
                    (entryA, entryB) -> Integer.compare(entryB.getValue(), entryA.getValue())
            );

            for (int num : nums) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            }

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                kRank.offer(entry);
            }

            for (int i = 0; i < k; i++) {
                ans[i] = kRank.poll().getKey();
            }

            return ans;
        }

        public int[] topKFrequentSlowVer(int[] nums, int k) {
            Map<Integer, Integer> map = new HashMap<>();

            for (int num : nums) {
                map.putIfAbsent(num, 0);
                map.computeIfPresent(num, (key, ov) -> ov + 1);
            }

            final List<Integer> collect = map.entrySet()
                    .stream()
                    .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            int[] ans = new int[k];

            for (int i = 0; i < k; i++) {
                ans[i] = collect.get(i);
            }

            return ans;
        }

        public int[] topKFrequent(int[] nums, int k) {

//            return topKFrequentSlowVer(nums, k);
            return topKFrequentPriorityQueueVer(nums, k);
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution().topKFrequent(new int[]{-1,-1, 1,1,1,2,2,3}, 3)));
    }
}
