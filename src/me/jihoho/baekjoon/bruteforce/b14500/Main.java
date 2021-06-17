package me.jihoho.baekjoon.bruteforce.b14500;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int[][] map;
    static int n;
    static int m;
    static int maxSum = 0;
    static int[][] dx = {
//            ㅁ
            {0, 0, 1, 1},
//            ㅡ,ㅣ
            {0, 0, 0, 0}, {0, 1, 2, 3},
//          ㄴ
            {0, 1, 2, 2}, {0, 1, 2, 2}, {0, 0, 1, 2}, {0, 0, 1, 2},
            {0, 0, 0, 1}, {0, 1, 0, 0}, {0, 1, 1, 1}, {0, 0, 0, -1},
//            z
            {0, 1, 1, 2}, {0, 1, 1, 2}, {0, 0, 1, 1}, {0, 0, -1, -1},
//            ㅗ
            {0, -1, 0, 0}, {0, 1, 0, 0}, {0, 1, 1, 2}, {0, 1, 1, 2}

    };
    static int[][] dy = {
            {0, 1, 0, 1},

            {0, 1, 2, 3}, {0, 0, 0, 0},

            {0, 0, 0, 1}, {0, 0, 0, -1}, {0, 1, 0, 0}, {0, 1, 1, 1},
            {0, 1, 2, 2}, {0, 0, 1, 2}, {0, 0, 1, 2}, {0, 1, 2, 2},

            {0, 0, 1, 1}, {0, -1, 0, -1}, {0, 1, 1, 2}, {0, 1, 1, 2},

            {0, 1, 1, 2}, {0, 1, 1, 2}, {0, 0, 1, 0}, {0, -1, 0, 0}


    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        bruteForce();
        System.out.println(maxSum);

    }

    private static void bruteForce() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                calcMaxSumByPoint(i, j);
            }
        }
    }

    private static void calcMaxSumByPoint(int x, int y) {
        int[] figureX = new int[4];
        int[] figureY = new int[4];
//        도형의 네개의 포인트가 유효 할 경우 true
        boolean isValidFigure;
        for (int i = 0; i < dx.length; i++) {
            int figureSum = 0;
            isValidFigure = true;
            int[] relativeX = dx[i];
            int[] relativeY = dy[i];
            for (int j = 0; j < relativeX.length; j++) {
                figureX[j] = x + relativeX[j];
                figureY[j] = y + relativeY[j];
                if (!isValid(figureX[j], figureY[j])) {
                    isValidFigure = false;
                    break;
                }
            }
            if (isValidFigure) {
                for (int j = 0; j < figureX.length; j++) {
                    figureSum += map[figureX[j]][figureY[j]];
                }
                maxSum = Math.max(maxSum, figureSum);
            }


        }
    }

    private static boolean isValid(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }
}
