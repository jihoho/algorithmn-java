package me.jihoho.baekjoon.recursion.p다단계_칫솔_판매;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-18 Time: 오후 5:16
 */
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] emroll = {"john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"};
        String[] referral = {"-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"};
        String[] seller = {"young", "john", "tod", "emily", "mary"};
        int[] amount = {12, 4, 2, 5, 10};
        int profits[] = solution.solution(emroll, referral, seller, amount);
        System.out.println(profits.toString());
    }

    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] profits = new int[enroll.length];
        HashMap<String,Integer> enrollMap = new HashMap<>();
        HashMap<Integer,String> referralMap=new HashMap<>() ;
        for (int i = 0; i <enroll.length; i++) {
            enrollMap.put(enroll[i],i);
        }
        for (int i = 0; i < seller.length; i++) {
            recursion(enrollMap,referral, profits, seller[i], amount[i] * 100);
        }
        return profits;
    }


    public void recursion(HashMap<String, Integer> enrollMap, String[] referral, int[] profits,
            String name, int profit) {
        if(enrollMap.containsKey(name)) {
            int idx = enrollMap.get(name);
            if (profit * 0.1 < 1) {
                profits[idx] += profit;
            } else {
                profits[idx] += profit - (int) (profit * 0.1);
                recursion(enrollMap, referral, profits, referral[idx], (int) (profit * 0.1));
            }
        }

        // for (int j = 0; j < enroll.length; j++) {
        //     if (enroll[j].equals(name)) {
        //         if (profit * 0.1 < 1) {
        //             profits[j] += profit;
        //         } else {
        //             profits[j] += profit - (int) (profit * 0.1);
        //             recursion(enroll, referral, profits, referral[j], (int) (profit * 0.1));
        //         }
        //         break;
        //     }
        // }

    }
}
