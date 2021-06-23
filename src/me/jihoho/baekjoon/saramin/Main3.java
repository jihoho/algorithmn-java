package me.jihoho.baekjoon.saramin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-19 Time: 오후 2:59
 */
public class Main3 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int[] arr = createArr(n, a, b);
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            int[] copiedArr = Arrays.copyOf(arr, i);
            int median = getMedian(copiedArr);
            sum += median;
        }
        System.out.println(sum % 20090711);
    }

    private static int getMedian(int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        if (n % 2 == 0) {
            return arr[n / 2 - 1];
        } else {
            return arr[n / 2];
        }
    }

    private static int[] createArr(int n, int a, int b) {
        int[] arr = new int[n];
        arr[0] = 1983;
        for (int i = 1; i < n; i++) {
            arr[i] = arr[i - 1] * (a + b) % 20090711;
        }
        return arr;
    }


}
