package me.jihoho.baekjoon.bruteforce.p행렬_테두리_회전하기;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-17 Time: 오후 8:48
 */
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] queries = {{2, 2, 5, 4}, {3, 3, 6, 6}, {5, 1, 6, 3}};
        int[] answer = solution.solution(6, 6, queries);
        System.out.println(answer.toString());
    }

    public int[] solution(int rows, int columns, int[][] queries) {
        System.out.println("ba");
        int[][] matrix = new int[rows + 1][columns + 1];
        init(matrix);
        int[] answer = new int[queries.length];
        System.out.println("ba");
        for (int i = 0; i < queries.length; i++) {
            answer[i] = lotationMatrix(matrix, queries[i]);
        }
        return answer;
    }

    void init(int[][] matrix) {
        int num = 1;
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                matrix[i][j] = num++;
            }
        }
        System.out.println("aaa");
    }

    int lotationMatrix(int[][] matrix, int[] query) {
        int minValue = 10000;
        List<Point> points = new ArrayList<>();
        Point startPoint = new Point(query[0], query[1]);
        Point endPoint = new Point(query[2], query[3]);
        for (int i = startPoint.y; i < endPoint.y; i++) {
            Point point = new Point(startPoint.x, i);
            points.add(point);
            int value = matrix[point.x][point.y];
            minValue = Math.min(value, minValue);
        }

        for (int i = startPoint.x; i < endPoint.x; i++) {
            Point point = new Point(i, endPoint.y);
            points.add(point);
            int value = matrix[point.x][point.y];
            minValue = Math.min(value, minValue);
        }

        for (int i = endPoint.y; i > startPoint.y; i--) {
            Point point = new Point(endPoint.x, i);
            points.add(point);
            int value = matrix[point.x][point.y];
            minValue = Math.min(value, minValue);
        }
        for (int i = endPoint.x; i > startPoint.x; i--) {
            Point point = new Point(i, startPoint.y);
            points.add(point);
            int value = matrix[point.x][point.y];
            minValue = Math.min(value, minValue);
        }
        if (points.size() > 0) {
            int tmp = matrix[points.get(points.size() - 1).x][points.get(points.size() - 1).y];
            for (int i = points.size() - 2; i >= 0; i--) {
                matrix[points.get(i + 1).x][points.get(i + 1).y] = matrix[points.get(i).x][points
                        .get(i).y];
            }
            matrix[points.get(0).x][points.get(0).y] = tmp;
        }
        return minValue;
    }

    class Point {

        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
