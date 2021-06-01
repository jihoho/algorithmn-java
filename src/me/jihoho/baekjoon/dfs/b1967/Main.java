package me.jihoho.baekjoon.dfs.b1967;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-01 Time: 오후 3:36
 */
public class Main {

    static int n;
    static short[][] tree;
    static boolean[] visited;
    static int farthestIdx;
    static int totalMax;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        tree = new short[n + 1][n + 1];

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            short weight = Short.valueOf(st.nextToken());
            tree[parent][child] = weight;
            tree[child][parent] = weight;
        }

        visited = new boolean[n + 1];
        dfs(1,0);
        visited = new boolean[n + 1];
        dfs(farthestIdx,0);

        System.out.println(totalMax);

    }

    static void dfsAll(int root) {
        //    visited false 초기화;
        visited = new boolean[n + 1];
        // System.out.printf("root: %d ", root);
        if (!visited[root]) {
            dfs(root, 0);
        }
        // System.out.println("total max: " + totalMax);
    }

    private static void dfs(int here, int max) {
        visited[here] = true;
        // System.out.printf("[visit : %d]", here);
        if(max>totalMax){
            farthestIdx=here;
        }
        for (int i = 0; i < n + 1; i++) {
            if (tree[here][i] > 0) {
                if (!visited[i]) {
                    max += tree[here][i];
                    dfs(i, max);
                    max -= tree[here][i];
                }
            }
        }
        totalMax = max > totalMax ? max : totalMax;
        // System.out.printf("[result:%d]", max);
    }


}
