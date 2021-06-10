package me.jihoho.baekjoon.bfs.b3055;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-10 Time: 오후 11:43
 */
public class Main {

    static int r;
    static int c;
    static char[][] map;
    static HashMap<Integer, List<Point>> floodMap;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        int startX, startY;
        startX = startY = 0;
        map = new char[r][c];
        for (int i = 0; i < r; i++) {
            String str = br.readLine();
            for (int j = 0; j < c; j++) {
                map[i][j] = str.charAt(j);
                if (map[i][j] == 'S') {
                    startX = i;
                    startY = j;
                    map[i][j] = '.';
                }
            }
        }

        bfsFlood();
        int result = bfs(startX, startY);
        if (result == -1) {
            System.out.println("KAKTUS");
        } else {
            System.out.println(result);
        }
    }

    private static void bfsFlood() {
        floodMap = new HashMap<>();
        Queue<Point> q = new LinkedList<>();
        boolean[][] visited = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (map[i][j] == '*') {
                    Point point = new Point(i, j, 0);
                    addFloodMap(point);
                    q.add(point);
                    visited[i][j] = true;
                }
            }
        }

        while (!q.isEmpty()) {
            Point p = q.poll();
            for (int i = 0; i < dx.length; i++) {
                int nextX = p.x + dx[i];
                int nextY = p.y + dy[i];
                if (isValidPoint(nextX, nextY) && !visited[nextX][nextY]
                        && map[nextX][nextY] == '.') {
                    Point nextPoint = new Point(nextX, nextY, p.depth + 1);
                    q.add(nextPoint);
                    addFloodMap(nextPoint);
                    visited[nextX][nextY] = true;
                }
            }
        }


    }

    private static boolean isValidPoint(int nextX, int nextY) {
        if (nextX >= 0 && nextX < r && nextY >= 0 && nextY < c) {
            return true;
        }
        return false;
    }

    private static void addFloodMap(Point point) {
        if (floodMap.containsKey(point.depth)) {
            List<Point> points = floodMap.get(point.depth);
            points.add(point);
            floodMap.put(point.depth, points);
        } else {
            List<Point> points = new ArrayList<>();
            points.add(point);
            floodMap.put(point.depth, points);
        }
    }

    private static int bfs(int x, int y) {
        Queue<Point> q = new LinkedList<>();
        boolean[][] visited = new boolean[r][c];
        q.add(new Point(x, y, 0));
        visited[x][y] = true;

        while (!q.isEmpty()) {
            Point p = q.poll();
            if (map[p.x][p.y] == 'D') {
                return p.depth;
            }
            for (int i = 0; i < dx.length; i++) {
                int nextX = p.x + dx[i];
                int nextY = p.y + dy[i];
                if (isValidPoint(nextX, nextY) && !visited[nextX][nextY] && (
                        map[nextX][nextY] == 'D' || map[nextX][nextY] == '.')) {
                    Point nextPoint = new Point(nextX, nextY, p.depth + 1);
                    if (isSafePoint(nextPoint)) {
                        q.add(nextPoint);
                        visited[nextX][nextY] = true;
                    }
                }
            }
        }

        return -1;
    }

    private static boolean isSafePoint(Point nextPoint) {
        for (int i = 0; i <= nextPoint.depth; i++) {
            List<Point> points = floodMap.get(i);
            if (points == null) {
                return true;
            }
            for (Point floodPoint : points) {
                if (nextPoint.x == floodPoint.x && nextPoint.y == floodPoint.y) {
                    return false;
                }
            }
        }

        return true;
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
