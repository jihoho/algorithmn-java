package me.jihoho.baekjoon.bruteforce.p로또의_최고_순위와_최저_순위;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-17 Time: 오후 8:11
 */
public class Solution {
    public int[] solution(int[] lottos, int[] win_nums) {
        int[] answer = calcMinAndMaxRank(lottos,win_nums);

        return answer;
    }

    public int[] calcMinAndMaxRank(int[] lottos,int[] win_nums){
        int correctCount=0;
        int zeroCount=0;
        for(int i=0;i<lottos.length;i++){
            if(lottos[i]==0){
                zeroCount++;
            }
            for(int j=0;j<win_nums.length;j++){
                if(lottos[i]==win_nums[j]){
                    correctCount++;
                }
            }
        }

        int[] ret=new int[2];
        if(correctCount>1){
            ret[1]=7-correctCount;
        }else{
            ret[1]=6;
        }
        System.out.printf("correctCount: %d, zeroCount: %d",correctCount,zeroCount);
        correctCount+=zeroCount;
        System.out.printf("correctCount: %d, zeroCount: %d",correctCount,zeroCount);
        if(correctCount>1){
            ret[0]=7-correctCount;
        }else{
            ret[0]=6;
        }
        return ret;

    }
}
