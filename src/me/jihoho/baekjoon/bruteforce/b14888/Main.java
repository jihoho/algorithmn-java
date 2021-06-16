package me.jihoho.baekjoon.bruteforce.b14888;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int n;
    static int[] operands;
    //    +,-,x,/
    static int[] operators;
    static int maxValue = -1000000000;
    static int minValue = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        operators = new int[4];
        n = Integer.parseInt(br.readLine());
        operands = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            operands[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < operators.length; i++) {
            operators[i] = Integer.parseInt(st.nextToken());
        }

        solution();
        System.out.println(maxValue);
        System.out.println(minValue);
    }

    private static void solution() {
        char[] operatorArr = new char[n - 1];
        int idx = 0;
        for (int i = 0; i < operators.length; i++) {
            for (int j = 0; j < operators[i]; j++) {
                if (i == 0) {
                    operatorArr[idx++] = '+';
                } else if (i == 1) {
                    operatorArr[idx++] = '-';
                } else if (i == 2) {
                    operatorArr[idx++] = '*';
                } else if (i == 3) {
                    operatorArr[idx++] = '/';
                }
            }
        }

        permutation(operatorArr, 0, operatorArr.length, operatorArr.length);
    }

    private static void permutation(char[] operatorArr, int depth, int n, int r) {
        if (depth == r) {
            calculation(operatorArr);
            return;
        }

        for (int i = depth; i < n; i++) {
            swap(operatorArr, depth, i);
            permutation(operatorArr, depth + 1, n, r);
            swap(operatorArr, depth, i);
        }
    }

    private static void calculation(char[] operatorArr) {
        int result = operands[0];

        for (int i = 1; i < n; i++) {
            int operand = operands[i];
            switch (operatorArr[i - 1]) {
                case '+':
                    result += operand;
                    break;
                case '-':
                    result -= operand;
                    break;
                case '*':
                    result *= operand;
                    break;
                case '/':
                    result /= operand;
                    break;
                default:
                    System.out.println("연산자 에러");
                    break;
            }
        }
        maxValue = Math.max(maxValue, result);
        minValue = Math.min(minValue, result);

    }

    private static void swap(char[] operatorArr, int depth, int i) {
        char tmp = operatorArr[depth];
        operatorArr[depth] = operatorArr[i];
        operatorArr[i] = tmp;
    }
}
