package me.jihoho.baekjoon.bfs.b16236;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int n;
    static short[][] map;
    static int sharkSize = 2;
    static int count = 0;
    static int totalTime = 0;
    static int minX;
    static int minY;
    static int minTime = 0;
    static Queue<Point> q;
    static boolean[][] visited;
    static int[] dx = {0, -1, 0, 1};
    static int[] dy = {-1, 0, 1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        map = new short[n][n];
        q = new LinkedList<>();
        int x, y;
        x = y = 0;
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Short.valueOf(st.nextToken());
                if (map[i][j] == 9) {
                    x = i;
                    y = j;
                    map[i][j] = 0;
                }
            }
        }
        visited = new boolean[n][n];
        bfsAll(x, y);
        System.out.println(totalTime);
    }

    static void bfsAll(int x, int y) {
        int sharkX, sharkY;
        sharkX = x;
        sharkY = y;
        while (true) {
            minX = Integer.MAX_VALUE;
            minY = Integer.MAX_VALUE;
            minTime = Integer.MAX_VALUE;
            visited = new boolean[n][n];
            q.clear();
            bfs(sharkX, sharkY);
            if (minX != Integer.MAX_VALUE && minY != Integer.MAX_VALUE) {
                count++;
                map[minX][minY] = 0;
                sharkX = minX;
                sharkY = minY;
                totalTime += minTime;
                if (count == sharkSize) {
                    sharkSize++;
                    count = 0;
                }
            } else {
                break;
            }
        }
    }

    static void bfs(int x, int y) {
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(x, y, 0));
        visited[x][y] = true;
        while (!q.isEmpty()) {
            Point p = q.poll();
            int value = map[p.x][p.y];
            if (value != 0 && value < sharkSize) {
                if (minTime > p.depth) {
                    minTime = p.depth;
                    minX = p.x;
                    minY = p.y;
                } else if (minTime == p.depth) {
                    if (minX == p.x) {
                        if (minY > p.y) {
                            minX = p.x;
                            minY = p.y;
                        }
                    } else if (minX > p.x) {
                        minX = p.x;
                        minY = p.y;
                    }
                }
            }

            for (int i = 0; i < dx.length; i++) {
                int nextX = p.x + dx[i];
                int nextY = p.y + dy[i];
                if (nextX < n && nextX >= 0 && nextY >= 0 && nextY < n
                        && map[nextX][nextY] <= sharkSize && !visited[nextX][nextY]) {
                    if (p.depth + 1 <= minTime) {
                        visited[nextX][nextY] = true;
                        q.add(new Point(nextX, nextY, p.depth + 1));
                    }
                }
            }

        }
    }

    static class Point {

        int x;
        int y;
        int depth;

        public Point(int x, int y, int depth) {
            this.x = x;
            this.y = y;
            this.depth = depth;
        }
    }
}
