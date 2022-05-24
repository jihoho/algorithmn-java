package me.jihoho.programmers.p월간_코드_챌린지_시즌2.약수의_개수와_덧셈;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-07-02 Time: 오후 6:51
 */
public class Solution {
    public int solution(int left, int right) {
        int answer = 0;
        for (int i = left; i<=right;i++){
            if(isEvenCount(i)){
                answer += i;
            }else{
                answer -= i;
            }
        }
        return answer;
    }

    public boolean isEvenCount(int number){
        int count = 0;
        for(int i=1;i<=number;i++){
            if(number%i==0){
                count++;
            }
        }
        if(count%2==0){
            return true;
        }else{
            return false;
        }
    }
}
