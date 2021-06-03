package me.jihoho.baekjoon.bruteforce.b15686;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    static Stack<Node> selectChicken;
    static int n;
    static int m;
    static int minDis;

    static List<Node> chickens;
    static List<Node> houses;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        chickens = new ArrayList<>();
        houses = new ArrayList<>();
        selectChicken = new Stack<>();
        for (int i = 1; i < n + 1; i++) {
            StringTokenizer st2 = new StringTokenizer(br.readLine());
            for (int j = 1; j < n + 1; j++) {
                int value = Short.valueOf(st2.nextToken());
                if (value == 2) {
                    chickens.add(new Node(i, j));
                } else if (value == 1) {
                    houses.add(new Node(i, j));
                }
            }
        }

        select(0, 0);
        System.out.println(minDis);
    }

    private static void select(int start, int count) {
        if (count == m) {
            calcChickenDis();
            return;
        }
        for (int i = start; i < chickens.size(); i++) {
            selectChicken.push(chickens.get(i));
            select(i + 1, count + 1);
            selectChicken.pop();
        }
    }

    private static void calcChickenDis() {

        int result = 0;
        for (Node house : houses) {
            house.score = 0;
            for (Node chicken : selectChicken) {
                int dis = Math.abs(house.r - chicken.r) + Math.abs(house.c - chicken.c);
                if (house.score == 0 || house.score > dis) {
                    house.score = dis;
                }
            }
        }

        for (Node house : houses) {
            result += house.score;
        }
        if (minDis == 0) {
            minDis = result;
        } else {
            minDis = result < minDis ? result : minDis;
        }
    }

    static class Node {

        int r;
        int c;
        int score;

        public Node(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }


}
