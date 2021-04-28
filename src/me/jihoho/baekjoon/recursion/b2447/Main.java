package me.jihoho.baekjoon.recursion.b2447;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-04-26 Time: 오전 11:38
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        char[][] star = new char[n][n];
        drawStar(n, star, 0, 0);

        printStar(star);
    }

    private static void printStar(char[][] star) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 0; i < star.length; i++) {
            for (int j = 0; j < star[i].length; j++) {
                bw.write(star[i][j]);
            }
            bw.write("\n");
        }
        bw.flush();
        bw.close();
    }

    public static void drawStar(int n, char[][] star, int x, int y) {
        if (n == 3) {
            for (int i = x; i < x + 3; i++) {
                for (int j = y; j < 3 + y; j++) {
                    if (i == x + 1 && j == y + 1) {
                        drawSpace(i, j, 1, star);
                    } else {
                        star[i][j] = '*';
                    }

                }
            }
            return;
        }

        for (int i = x; i < x + n; i = i + n / 3) {
            for (int j = y; j < y + n; j = j + n / 3) {
                if (i == x + n / 3 && j == y + n / 3) {
                    drawSpace(i, j, n / 3, star);
                } else {
                    drawStar(n / 3, star, i, j);
                }
            }
        }
    }

    private static void drawSpace(int r, int c, int n, char[][] star) {
        for (int i = r; i < r + n; i++) {
            for (int j = c; j < c + n; j++) {
                star[i][j] = ' ';
            }
        }
    }


}
