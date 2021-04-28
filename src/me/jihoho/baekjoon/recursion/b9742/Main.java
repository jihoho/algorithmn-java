package me.jihoho.baekjoon.recursion.b9742;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-04-27 Time: 오전 10:27
 */
public class Main {

    private static int index = 0;
    private static String result = "No permutation";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {

            String s = sc.next();
            int count = sc.nextInt();

            int n = s.length();
            boolean[] visited = new boolean[n];
            Arrays.fill(visited, false);
            char[] ouput = new char[n];

            permutation(s.toCharArray(), ouput, visited, 0, n, n, count);

            // for (int i = 0; i < list.size(); i++) {
            //     System.out.printf("%d. %s\n", i + 1, list.get(i));
            // }

            System.out.println(s + " " + count + " = " + result);
            // System.out.printf("%s %d = %s\n", s, count, result); // 이렇게 출력하면 출력형식 오류 뜸 이유 모르겠음
            result = "No permutation";
            index = 0;
        }

    }


    public static void permutation(char[] charArr, char[] output, boolean[] visited, int depth,
            int n, int r, int count) {
        if (depth == r) {
            index++;
            // System.out.printf("%d. %s\n", index, new String(output));
            if (index == count) {
                result = new String(output);
            }
            return;
        }
        for (int i = 0; i < n; i++) {
            if (visited[i] == false) {
                visited[i] = true;
                output[depth] = charArr[i];
                permutation(charArr, output, visited, depth + 1, n, r, count);
                output[depth] = 0;
                visited[i] = false;

            }
        }

    }


}
