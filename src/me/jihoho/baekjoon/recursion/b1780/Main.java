package me.jihoho.baekjoon.recursion.b1780;

import java.util.Scanner;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-05-11 Time: 오후 5:49
 */
public class Main {

    static short[][] paper;

    static int[] result = new int[3];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        paper = new short[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                paper[i][j] = (short) sc.nextInt();
            }
        }

        recursion(0, 0, size);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }


    }

    public static void recursion(int startX, int startY, int size) {
        int resultIdx = checkPaper(startX, startY, size) + 1;
        if (resultIdx >= 0) {
            result[resultIdx] += 1;
            return;
        }

        if (size == 1) {
            return;
        }
        for (int i = startX; i < startX + size; i += size / 3) {
            for (int j = startY; j < startY + size; j += size / 3) {
                recursion(i, j, size / 3);
            }
        }

    }

    private static int checkPaper(int startX, int startY, int size) {
        int check = paper[startX][startY];
        for (int i = startX; i < startX + size; i++) {
            for (int j = startY; j < startY + size; j++) {
                if (check != paper[i][j]) {
                    return -2;
                }
            }

        }
        return check;
    }
}
