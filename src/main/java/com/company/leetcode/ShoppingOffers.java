package com.company.leetcode;

import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/shopping-offers
 *
 */
public class ShoppingOffers {

    static class Solution {

        public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {

            int money = 0;

            // 全部用单价买是最贵的结果
            for (int i = 0; i < price.size(); i++) {
                money += needs.get(i) * price.get(i);
            }

//            if (needs.stream().reduce(0, Integer::sum) == 0) {
//                return money;
//            }

            for (int i = 0; i < special.size(); i++) {
                boolean useCoupon = true;

                for (int j = 0; j < price.size(); j++) {
                    Integer itemSpecial = special.get(i).get(j);

                    if (needs.get(j) < itemSpecial) {
                        useCoupon = false;
                    }
                    needs.set(j, needs.get(j) - itemSpecial);
                }

                if (useCoupon) {
                    Integer specialMoney = special.get(i).get(special.get(i).size() - 1);

                    money = Math.min(money, specialMoney + shoppingOffers(price, special, needs));
                }

                for (int j = 0; j < price.size(); j++) {
                    Integer itemSpecial = special.get(i).get(j);
                    needs.set(j, needs.get(j) + itemSpecial);
                }

//                if (!useCoupon) {
//
//
//                    for (int pi = 0; pi < price.size(); pi++) {
//                        needs.set(pi, needs.get(pi) - 1);
//                        money += price.get(pi);
//                    }
//                }

            }

            return money;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().shoppingOffers(Arrays.asList(2, 3, 4), Arrays.asList(Arrays.asList(1, 1, 0, 4), Arrays.asList(2, 2, 1, 9)), Arrays.asList(1, 2, 1)));
    }
}
