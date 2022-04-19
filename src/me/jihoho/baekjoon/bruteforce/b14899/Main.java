package me.jihoho.baekjoon.bruteforce.b14899;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int n;
    static int[][] s;

    static Team startTeam = new Team();
    static Team linkTeam = new Team();


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        s = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                s[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        System.out.println(solution());

    }

    private static int solution() {
        char[][] assignedMap = new char[2][n];
        int minScore = Integer.MAX_VALUE;

        minScore = min(assign(0, startTeam), assign(0, linkTeam));

        return minScore;
    }

    private static int assign(int i, Team team) {
        int result;
        team.set(i);
        if (i == n - 1) {
            result = abs(startTeam.getScore() - linkTeam.getScore());
        } else {
            if (!startTeam.isFull() && !linkTeam.isFull()) {
                result = min(assign(i + 1, startTeam), assign(i + 1, linkTeam));
            } else if (startTeam.isFull()) {
                result = assign(i + 1, linkTeam);
            } else {
                result = assign(i + 1, startTeam);
            }
        }
        team.pop();
        return result;
    }


    public static class Team {

        private List<Integer> list = new ArrayList<>();

        public void set(int i) {
            list.add(i);
        }

        public void reset() {
            list = new ArrayList<>();
        }

        public boolean isFull() {
            return list.size() == n / 2;
        }

        public void pop() {
            list.remove(list.size() - 1);
        }

        public int getScore() {
            int sum = 0;
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    sum += s[this.list.get(i)][this.list.get(j)] + s[this.list.get(j)][this.list.get(i)];
                }
            }
            return sum;
        }

        public void printList() {
            System.out.println(this.list);
        }
    }

}
