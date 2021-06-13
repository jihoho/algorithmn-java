package me.jihoho.baekjoon.simulation.b14503;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[][] map;
    static int n, m;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        st = new StringTokenizer(br.readLine());
        int startX = Integer.parseInt(st.nextToken());
        int startY = Integer.parseInt(st.nextToken());
        int dir = Integer.parseInt(st.nextToken());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        System.out.println(simulation(startX, startY, dir));
    }

    private static int simulation(int startX, int startY, int dir) {
        int currDir = dir;
        boolean[][] visited = new boolean[n][m];
        int count = 0;
        int flag = 0;
        if (map[startX][startY] == 0) {
            count++;
        }
        visited[startX][startY] = true;
        while (true) {
            int leftDir = (currDir + 3) % 4;
            int nextX = startX + dx[leftDir];
            int nextY = startY + dy[leftDir];
            // 모든 방향 모두 청소가 되어 있거나 벽인 경우
            if (flag == 4) {
                int backDir = (currDir + 2) % 4;
                nextX = startX + dx[backDir];
                nextY = startY + dy[backDir];
                flag = 0;
                // 뒤쪽 방향이 벽이라 후진할 수 없는 경우
                if (map[nextX][nextY] == 1) {
                    break;
                } else {
                    startX = nextX;
                    startY = nextY;
                    visited[nextX][nextY] = true;
                    continue;
                }
            }
            currDir = leftDir;
            if (!visited[nextX][nextY] && map[nextX][nextY] == 0) {
                startX = nextX;
                startY = nextY;
                visited[nextX][nextY] = true;
                count++;
                flag = 0;
                continue;
            } else {
                flag++;
                continue;
            }
        }

        return count;
    }
}
