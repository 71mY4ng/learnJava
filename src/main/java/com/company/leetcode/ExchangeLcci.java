package com.company.leetcode;

/**
 * https://leetcode-cn.com/problems/exchange-lcci/
 *
 * swapping bits
 */
public class ExchangeLcci {

    class Solution {
        public int exchangeBits(int num) {
            int e = num & 0xaaaaaaaa;
            int o = num & 0x55555555;
            return (e >> 1) + (o << 1);
        }
    }
}
