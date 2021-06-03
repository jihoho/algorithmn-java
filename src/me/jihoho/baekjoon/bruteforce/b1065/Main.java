package me.jihoho.baekjoon.bruteforce.b1065;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        System.out.println(solution(n));
    }

    private static int solution(int n) {
        int count = 0;
        for (int i = 1; i <= n; i++) {

            int digit1 = i / 1000;
            int remind = i - digit1 * 1000;
            int digit2 = remind / 100;
            remind = i - digit2 * 100;
            int digit3 = remind / 10;
            int digit4 = remind - digit3 * 10;
            if (digit1 == 0) {
                if (digit2 == 0) {
                    count++;
                } else {
                    if ((digit2 - digit3) == (digit3 - digit4)) {
                        count++;
                    }
                }
            } else {
                if ((digit1 - digit2) == (digit2 - digit3) && (digit2 - digit3) == (digit3
                        - digit4)) {
                    count++;
                }
            }
        }
        return count;
    }
}
