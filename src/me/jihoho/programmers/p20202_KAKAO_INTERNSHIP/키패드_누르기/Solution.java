package me.jihoho.programmers.p20202_KAKAO_INTERNSHIP.키패드_누르기;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-24 Time: 오후 2:42
 */
public class Solution {

    int[][] numberPadPoint = {
            {3, 1}, // 0
            {0, 0}, // 1
            {0, 1}, // 2
            {0, 2}, // 3
            {1, 0}, // 4
            {1, 1}, // 5
            {1, 2}, // 6
            {2, 0}, // 7
            {2, 1}, // 8
            {2, 2}, // 9
    };

    int[] leftThumbPoint = {3, 0};
    int[] rightThumbPoint = {3, 2};

    public static void main(String[] args) {
        int[] numbers = {7, 0, 8, 2, 8, 3, 1, 5, 7, 6, 2};
        String hand = "left";
        Solution sol = new Solution();

        System.out.println(sol.solution(numbers, hand));
    }

    public String solution(int[] numbers, String hand) {
        String answer = "";
        hand = hand.equals("right") ? "R" : "L";
        for (int number : numbers) {
            String leftOrRight = moveThumb(number, hand);
            answer += leftOrRight;

            if (leftOrRight.equals("L")) {
                leftThumbPoint = numberPadPoint[number];
            } else {
                rightThumbPoint = numberPadPoint[number];
            }
        }
        return answer;
    }

    private String moveThumb(int number, String hand) {
        if (number % 3 == 1) {
            return "L";
        }
        if (number != 0 && number % 3 == 0) {
            return "R";
        }
        if (getDistFromThumbToNum(leftThumbPoint, number) < getDistFromThumbToNum(rightThumbPoint,
                number)) {
            return "L";
        } else if (getDistFromThumbToNum(leftThumbPoint, number) > getDistFromThumbToNum(
                rightThumbPoint, number)) {
            return "R";
        } else {
            return hand;
        }
    }

    private int getDistFromThumbToNum(int[] point, int number) {
        return Math.abs(point[0] - numberPadPoint[number][0]) + Math
                .abs(point[1] - numberPadPoint[number][1]);
    }

}
