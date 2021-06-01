package me.jihoho.baekjoon.dfs.b1167;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-01 Time: 오후 8:06
 */
public class Main {

    static int v;
    static boolean[] visited;
    static int farthestIdx;
    static int totalMax;
    static HashMap<Integer, List<Child>> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        v = Integer.parseInt(br.readLine());
        for (int i = 0; i < v; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            while (true) {
                int child = Integer.parseInt(st.nextToken());
                if (child == -1) {
                    break;
                }

                int wegiht = Integer.parseInt(st.nextToken());

                if (map.containsKey(parent)) {
                    List<Child> childList = map.get(parent);
                    childList.add(new Child(child, wegiht));
                    map.put(parent, childList);
                } else {
                    List<Child> childList = new ArrayList<>();
                    childList.add(new Child(child, wegiht));
                    map.put(parent, childList);
                }

            }
        }

        visited = new boolean[v + 1];
        dfs(1, 0);
        visited = new boolean[v + 1];
        dfs(farthestIdx, 0);
        System.out.println(totalMax);


    }

    static class Child {

        int child;
        int weight;

        public Child(int child, int weight) {
            this.child = child;
            this.weight = weight;
        }
    }

    private static void dfs(int here, int max) {
        visited[here] = true;
        // System.out.printf("[visited:%d]",here);
        if (max > totalMax) {
            farthestIdx = here;
        }

        List<Child> childList = map.get(here);

        for (int i = 0; i < childList.size(); i++) {
            Child child = childList.get(i);
            if (!visited[child.child]) {
                max += child.weight;
                dfs(child.child, max);
                max -= child.weight;
            }
        }
        // System.out.println("max:"+max);
        totalMax = max > totalMax ? max : totalMax;
    }


}
