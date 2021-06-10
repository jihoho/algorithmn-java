package me.jihoho.baekjoon.bfs.b16234;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int n;
    static int l;
    static int r;
    static short[][] a;
    static Queue<Point> sharedCountries;
    static int countrySize = -1;
    static int totalPeople;

    static int moveCount;
    static boolean[][] visited;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        a = new short[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                a[i][j] = Short.valueOf(st1.nextToken());
            }
        }
        bfsAll();
        System.out.println(moveCount);
    }

    static class Point {

        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static void bfsAll() {
        moveCount = 0;
        boolean flag = false;
        while (true) {
            flag = true;
            visited = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!visited[i][j]) {
                        countrySize = 0;
                        totalPeople = 0;
                        sharedCountries = new LinkedList<>();
                        bfs(i, j);
                        if (countrySize >= 2) {
                            movePeople();
                            flag = false;
                        }
                    }
                }
            }
            if (!flag) {
                moveCount++;
            } else {
                break;
            }

        }

    }

    private static void movePeople() {
        int avgPeople = totalPeople / countrySize;
        while (!sharedCountries.isEmpty()) {
            Point p = sharedCountries.poll();
            a[p.x][p.y] = (short) avgPeople;
        }
    }

    private static void bfs(int x, int y) {
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(x, y));
        addAdjCountry(x, y);
        visited[x][y] = true;

        while (!q.isEmpty()) {
            Point p = q.poll();
            int people = a[p.x][p.y];
            for (int i = 0; i < dx.length; i++) {
                int nextX = p.x + dx[i];
                int nextY = p.y + dy[i];
                if (isValidPoint(nextX, nextY) && !visited[nextX][nextY]) {
                    int diff = Math.abs(people - a[nextX][nextY]);
                    if (diff >= l && diff <= r) {
                        q.add(new Point(nextX, nextY));
                        visited[nextX][nextY] = true;
                        addAdjCountry(nextX, nextY);
                    }
                }
            }
        }

    }

    private static void addAdjCountry(int x, int y) {
        sharedCountries.add(new Point(x, y));
        countrySize++;
        totalPeople += a[x][y];
    }

    private static boolean isValidPoint(int nextX, int nextY) {
        if (nextX < n && nextX >= 0 && nextY >= 0 && nextY < n) {
            return true;
        }
        return false;
    }

}
