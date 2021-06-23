package me.jihoho.baekjoon.saramin;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-19 Time: 오후 2:29
 */
public class Main2 {

    public static Scanner scanner = new Scanner(System.in);

    public static void testCase(int caseNum) {
        int byteNum = scanner.nextInt();
        int[][] chip = new int[3][byteNum];
        for (int i = 0; i < 3; i++) {
            String str = scanner.next();
            StringTokenizer st = new StringTokenizer(str, "-");
            for (int j = 0; j < byteNum; j++) {
                chip[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        System.out.println(solution(chip, byteNum));
    }

    private static int solution(int[][] chip, int byteNum) {
        int result = 0;
        for (int i = 0; i < byteNum; i++) {
            int comp1 = chip[0][i] ^ chip[1][i];
            int comp2 = chip[0][i] ^ chip[2][i];
            int comp3 = chip[1][i] ^ chip[2][i];
            int comp4 = comp1 | comp2 | comp3;
            result += countChar(Integer.toBinaryString(comp4), '1');
        }
        return result;
    }

    private static int countChar(String toBinaryString, char c) {
        int count = 0;
        for (int i = 0; i < toBinaryString.length() ; i++) {
            if(toBinaryString.charAt(i)==c){
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int tn = scanner.nextInt();
        for (int caseNum = 1; caseNum <= tn; caseNum++) {
            testCase(caseNum);
        }
    }
}
