package me.jihoho.baekjoon.simulation.b20056;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static int K;
    static Board board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new Board(N);

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            board.addFireBall(new FireBall(r - 1, c - 1, m, s, d));
        }
        for (int i = 0; i < K; i++) {
            board.move();
        }
        System.out.println(board.getAllFireBallsMass());
    }

    static class Board {

        List<FireBall> fireBallList = new ArrayList<>();
        int N;

        Board(int N) {
            this.N = N;
        }

        private Location[][] getNewLocations() {
            Location[][] newLocations = new Location[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    newLocations[i][j] = new Location();
                }
            }
            return newLocations;
        }


        void addFireBall(FireBall fireBall) {
            this.fireBallList.add(fireBall);
        }


        void move() {
            Location[][] locations = getNewLocations();

            for (FireBall fireBall : this.fireBallList) {
                fireBall.move();
                locations[fireBall.r][fireBall.c].addFireBall(fireBall);
            }

            List<FireBall> tmpFireBallList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    List<FireBall> splitBalls = locations[i][j].splitFireBall();
                    tmpFireBallList.addAll(splitBalls);
                }
            }

            this.fireBallList = tmpFireBallList;

        }


        int getAllFireBallsMass() {
            return this.fireBallList.stream().mapToInt(FireBall::getM).sum();
        }

    }

    static class Location {

        List<FireBall> fireBalls = new ArrayList<>();

        void addFireBall(FireBall fireBall) {
            fireBalls.add(fireBall);
        }

        List<FireBall> splitFireBall() {
            if (fireBalls.isEmpty() || fireBalls.size() == 1) {
                return this.fireBalls;
            }

            int r, c, totalM = 0, totalS = 0, totalD = 0;
            r = fireBalls.get(0).r;
            c = fireBalls.get(0).c;

            for (FireBall ball : fireBalls) {
                totalM += ball.m;
                totalS += ball.s;
                totalD += ball.d % 2;
            }

            boolean isAllOddOrEven = totalD == 0 || totalD == fireBalls.size();

            int[] dir;
            if (isAllOddOrEven) {
                dir = new int[]{0, 2, 4, 6};
            } else {
                dir = new int[]{1, 3, 5, 7};
            }
            List<FireBall> splitBalls = new ArrayList<>();
            if (totalM / 5 > 0) {
                for (int d : dir) {
                    splitBalls
                            .add(new FireBall(r, c, totalM / 5, totalS / this.fireBalls.size(), d));
                }
            }
            return splitBalls;
        }
    }

    static class FireBall {

        int r;
        int c;
        int m; // 질량

        public int getM() {
            return m;
        }

        int s; // 속력
        int d; // 방향

        int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

        FireBall(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }

        void move() {
            int nextR = checkBoardRange(this.r + this.s * dx[d]);
            int nextC = checkBoardRange(this.c + this.s * dy[d]);
            this.r = nextR;
            this.c = nextC;
        }


        private int checkBoardRange(int index) {
            if (index < 0) {
                if (index % N == 0) {
                    return 0;
                }
                index = N + index % N;
            } else if (index >= N) {
                index = index % N;
            }
            return index;
        }
    }
}
