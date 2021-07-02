package me.jihoho.baekjoon.p월간_코드_챌린지_시즌2.음양_더하기;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-07-02 Time: 오후 6:46
 */
public class Solution {

    public int solution(int[] absolutes, boolean[] signs) {
        int answer = 0;
        for (int i = 0; i < absolutes.length; i++) {
            if (signs[i]) {
                answer += absolutes[i];
            } else {
                answer -= absolutes[i];
            }
        }
        return answer;
    }
}
