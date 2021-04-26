package me.jihoho.baekjoon.recursion.b9095;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-04-26 Time: 오전 10:50
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        int[] nArray = new int[t];
        for (int i = 0; i < t; i++) {
            nArray[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 0; i < t; i++) {
            System.out.println(getCount(nArray[i]));
        }


    }

    public static int getCount(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (n == 3) {
            return 4;
        }
        // 111  1 2  21 3
        return getCount(n - 1) + getCount(n - 2) + getCount(n - 3);
    }
}
