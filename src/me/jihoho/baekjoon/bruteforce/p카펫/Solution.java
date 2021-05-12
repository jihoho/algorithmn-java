package me.jihoho.baekjoon.bruteforce.p카펫;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] answer = solution.solution(24, 24);
        System.out.println(answer[0] + "," + answer[1]);

        answer = solution.solution(12, 3);
        System.out.println(answer[0] + "," + answer[1]);

        answer = solution.solution(210, 300);
        System.out.println(answer[0] + "," + answer[1]);

        answer = solution.solution(56, 21 * 5);
        System.out.println(answer[0] + "," + answer[1]);

        answer = solution.solution(250, 600);
        System.out.println(answer[0] + "," + answer[1]);
    }

    public int[] solution(int brown, int yellow) {
        int[] answer = bruteForce(brown, yellow);
        return answer;
    }

    private int[] bruteForce(int brown, int yellow) {
        int[] result = new int[2];
        for (int yellowRow = 1; yellowRow <= yellow; yellowRow++) {
            if (yellow % yellowRow == 0) {
                int yellowColumn = yellow / yellowRow;
                int calcBrown = (yellowColumn + 1) * 2 + (yellowRow + 1) * 2;
                if (calcBrown == brown) {
                    result[0] = yellowColumn + 2;
                    result[1] = yellowRow + 2;
                    return result;
                }
            }

        }
        return result;
    }

}
