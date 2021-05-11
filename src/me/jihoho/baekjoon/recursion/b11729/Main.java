package me.jihoho.baekjoon.recursion.b11729;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-04-26 Time: 오전 11:09
 */
public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        List<Move> result = honoiStart(n);

        System.out.println(result.size());
        for (Move move : result) {
            System.out.println(move.x + " " + move.y);
        }

    }

    public static List<Move> honoiStart(int n) {
        List<Move> list = new ArrayList<>();
        if (n == 1) {
            list.add(new Move(1, 3));
            return list;
        }
        if (n % 2 != 0) {
            list.add(new Move(1, 3));
            list.add(new Move(1, 2));
            list.add(new Move(3, 2));
            honoi(n - 2, list);
        } else {
            list.add(new Move(1, 2));
            list.add(new Move(1, 3));
            list.add(new Move(2, 3));
            honoi(n - 2, list);
        }
        return list;
    }

    private static void honoi(int n, List<Move> list) {
        if (n == 0) {
            return;
        }
        Move lastMove = list.get(list.size() - 1);
        if (lastMove.y == 3) {
            list.add(new Move(1, 2));
            list.add(new Move(3, 2));
        } else if (lastMove.y == 2) {
            list.add(new Move(1, 3));
            list.add(new Move(2, 3));
        }

        honoi(n - 1, list);
    }

    public static class Move {

        int x;
        int y;

        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
