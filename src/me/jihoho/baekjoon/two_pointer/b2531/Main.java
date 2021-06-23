package me.jihoho.baekjoon.two_pointer.b2531;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-23 Time: 오후 6:33
 */
public class Main {

    static int n;
    static int d;
    static int k;
    static int c;
    static int[] sushiBelt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        sushiBelt = new int[n];
        for (int i = 0; i < n; i++) {
            sushiBelt[i] = Integer.parseInt(br.readLine());
        }

        System.out.println(soluntion());
    }

    private static int soluntion() {
        int maxTypeOfSushi = 0;
        for (int i = 0; i < n; i++) {
            int start = i;
            int end = start + k;
            if (end > n) {
                end -= n;
            }
            maxTypeOfSushi = Math.max(typeOfSushi(start, end), maxTypeOfSushi);
        }
        return maxTypeOfSushi;
    }

    private static int typeOfSushi(int start, int end) {
        Set<Integer> set = new HashSet<>();
        if (start > end) {
            for (int i = start; i < n; i++) {
                set.add(sushiBelt[i]);
            }
            for (int i = 0; i < end; i++) {
                set.add(sushiBelt[i]);
            }
        } else {
            for (int i = start; i < end; i++) {
                set.add(sushiBelt[i]);
            }
        }
        set.add(c);

        return set.size();
    }
}
