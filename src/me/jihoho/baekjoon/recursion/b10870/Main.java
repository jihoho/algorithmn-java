package me.jihoho.baekjoon.recursion.b10870;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-04-26 Time: 오전 10:42
 */
public class Main {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        System.out.println(pivo(n));
    }

    public static int pivo(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        return pivo(n - 1) + pivo(n - 2);
    }

}
