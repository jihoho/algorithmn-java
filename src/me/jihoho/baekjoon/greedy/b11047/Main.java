package me.jihoho.baekjoon.greedy.b11047;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-14 Time: 오후 4:36
 */
public class Main {

    static int n;
    static int k;
    static int[] coin;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        coin = new int[n];
        for (int i = 0; i < n; i++) {
            coin[i] = Integer.parseInt(br.readLine());
        }

        System.out.println(solution());
    }

    private static int solution() {
        int remind = k;
        int idx = coin.length - 1;
        int countSum = 0;
        while (remind != 0) {
            if (coin[idx] <= remind) {
                int count = remind / coin[idx];
                countSum += count;
                remind -= coin[idx] * count;
            }
            idx--;
        }
        return countSum;
    }

}
