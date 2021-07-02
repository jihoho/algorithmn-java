package me.jihoho.baekjoon.p월간_코드_챌린지_시즌2.두개_이하로_다른_비트;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-07-02 Time: 오후 7:45
 */
public class Solution {

    public long[] solution(long[] numbers) {
        long[] answer = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            answer[i] = getNumberByBitCalc(numbers[i]);
        }
        return answer;
    }

    public long getNumberByBitCalc(long number) {
        if (number % 2 == 0) {
            return number + 1L;
        } else {
            String binaryStr = Long.toBinaryString(number);
            String fNumber = "";
            if (!binaryStr.contains("0")) {
                fNumber += "10";
                fNumber += binaryStr.substring(1);
            } else {
                int lastZeroIdx = binaryStr.lastIndexOf('0');
                int firstOneIdx = binaryStr.indexOf("1", lastZeroIdx);
                fNumber += binaryStr.substring(0, lastZeroIdx) + "1";
                fNumber += "0" + binaryStr.substring(firstOneIdx + 1);

            }
            return Long.parseLong(fNumber, 2);

        }
    }
}
