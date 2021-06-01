package me.jihoho.baekjoon.bruteforce.b14501;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Main {

    static int max = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] t = new int[n];
        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            t[i] = sc.nextInt();
            p[i] = sc.nextInt();
        }
        bruteforce(n, 0, 0, t, p);
        System.out.println(max);
    }

    private static void bruteforce(int n, int i, int money, int[] t, int[] p) {
        if (i >= n) {
            max = max > money ? max : money;
            return;
        }
        int nextIdx = i + 1;
        if (nextIdx <= n) {
            bruteforce(n, nextIdx, money, t, p);
        }

        nextIdx = i + t[i];
        if (nextIdx <= n) {
            money += p[i];
            bruteforce(n, nextIdx, money, t, p);
        }

    }
}
