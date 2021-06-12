package me.jihoho.baekjoon.bfs.b1525;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-12 Time: 오전 12:41
 */
public class Main {

    static int n = 3;
    static int[] dx = {-1, 0, 0, 1};
    static int[] dy = {0, -1, 1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            sb.append(br.readLine().replace(" ", ""));
        }
        if (sb.toString().equals("123456780")) {
            System.out.println("0");
        } else {
            System.out.println(bfs(sb.toString()));
        }
    }

    private static int bfs(String str) {
        Queue<String> q = new LinkedList<>();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(str, 0);
        q.add(str);

        while (!q.isEmpty()) {
            String curStr = q.poll();
            int idx = curStr.indexOf("0");
            int curX = idx / 3;
            int curY = idx % 3;
            for (int i = 0; i < dx.length; i++) {
                int nextX = curX + dx[i];
                int nextY = curY + dy[i];
                if (isValid(nextX, nextY)) {
                    StringBuilder sb = new StringBuilder(curStr);
                    int nextIdx = nextX * 3 + nextY;
                    char tmp = sb.charAt(idx);

                    sb.setCharAt(idx, sb.charAt(nextIdx));
                    sb.setCharAt(nextIdx, tmp);
                    if (sb.toString().equals("123456780")) {
                        return map.get(curStr) + 1;
                    }
                    if (!map.containsKey(sb.toString())) {
                        q.add(sb.toString());
                        map.put(sb.toString(), map.get(curStr) + 1);
                    }

                }
            }
        }
        return -1;

    }

    private static boolean isValid(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < n) {
            return true;
        }
        return false;
    }

}
