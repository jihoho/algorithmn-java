package me.jihoho.baekjoon.two_pointer.b2003;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-23 Time: 오후 5:49
 */
public class Main {

    static int n;
    static int m;
    static int[] a;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }
        System.out.println(solution());
    }

    private static int solution() {
        int startIdx = 0;
        int endIdx = 0;
        int sum = a[startIdx];
        int count = 0;
        while (startIdx < n && endIdx < n) {
            if (sum <= m) {
                if (sum == m) {
                    count++;
                }
                if (endIdx + 1 >= n) {
                    break;
                }
                sum += a[++endIdx];
            } else {
                sum -= a[startIdx++];
            }
        }
        return count;
    }


}
