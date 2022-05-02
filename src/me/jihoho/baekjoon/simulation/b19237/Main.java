package me.jihoho.baekjoon.simulation.b19237;

import static java.lang.System.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N, M, K;
    static Board board;
    static Shark[] sharks;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new Board(N);
        sharks = new Shark[M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int sharkNum = Integer.parseInt(st.nextToken());
                if (sharkNum != 0) {
                    sharks[sharkNum - 1] = new Shark(i, j, board);
                    board.positioning(sharks[sharkNum - 1]);
                }
            }
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < sharks.length; i++) {
            sharks[i].dir = Integer.parseInt(st.nextToken());
        }

        for (int sharkIdx = 0; sharkIdx < sharks.length; sharkIdx++) {
            for (int dirIdx = 0; dirIdx < 4; dirIdx++) {
                String dirStr = br.readLine();
                int[] dirArr = Arrays.stream(dirStr.split(" ")).mapToInt(Integer::parseInt)
                        .toArray();
                if (dirArr.length != 4) {
                    System.out.println("방향 별 우선순위 입력 오류!");
                    System.exit(-1);
                }
                sharks[sharkIdx].addPriority(dirIdx, dirArr);
            }
        }
        System.out.println(simulation());


    }

    private static int simulation() {
        int cnt = 1;
        while (board.getTime() < 1000) {
//            board.print(cnt++);
            if (board.move()) {
                return board.getTime();
            }

        }
//        board.print(cnt);
        return -1;
    }

    static class Board {

        private Location[][] locations;
        private int time;

        public Location getLocations(int x, int y) {
            return this.locations[x][y];
        }

        public int getTime() {
            return time;
        }

        public Board(int n) {
            this.locations = new Location[n][n];
            this.time = 0;
        }

        public void positioning(Shark shark) {
            this.locations[shark.point.x][shark.point.y] = new Location(
                    shark);
        }

        public boolean move() {
            for (int sharkIdx = 0; sharkIdx < sharks.length; sharkIdx++) {
                if (sharks[sharkIdx].point != null) {
                    sharks[sharkIdx].move();
                }
            }

            smellOut();

            for (int sharkIdx = 0; sharkIdx < sharks.length; sharkIdx++) {
                Shark shark = sharks[sharkIdx];
                if (shark.point != null) {
                    Location location = getLocations(shark.point.x, shark.point.y);
                    if (location != null && location.shark != shark) {
                        shark.point = null;
                    } else {
                        positioning(shark);
                    }
                }
            }
            this.time++;
            if (isLeftOnlyOneShark()) {
                return true;
            }
            return false;
        }

        private void smellOut() {
            for (int i = 0; i < locations.length; i++) {
                for (int j = 0; j < locations[i].length; j++) {
                    if (locations[i][j] != null) {
                        if (locations[i][j].time == 1) {
                            locations[i][j] = null;
                            locations[i][j] = null;
                        } else {
                            locations[i][j].time -= 1;
                        }
                    }
                }
            }
        }

        private boolean isLeftOnlyOneShark() {
            boolean isLeftOneShark = sharks[0].point != null;
            if (!isLeftOneShark) {
                return false;
            }

            for (int i = 1; i < sharks.length; i++) {
                if (sharks[i].point != null) {
                    return false;
                }
            }
            return true;
        }

        public void print(int index) {
            System.out.printf("(%d)\n", index);
            for (int i = 0; i < locations.length; i++) {
                for (int j = 0; j < locations[i].length; j++) {
                    System.out.print(locations[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }


        private class Location {

            Shark shark;
            int time;

            public Location(Shark shark) {
                this.shark = shark;
                this.time = K;
            }

            @Override
            public String toString() {
                return "{" +
                        "shark=" + shark +
                        ", time=" + time +
                        '}';
            }
        }
    }

    static class Shark {

        Point point;
        int dir;
        Board board;

        // 상하좌우
        int[][] movePriority = new int[4][4];

        public Shark(int x, int y, Board board) {
            this.point = new Point(x, y);
            this.board = board;
        }

        public void addPriority(int dir, int[] priority) {
            movePriority[dir] = priority;
        }

        Point getNextPoint() {
            Point nextPoint;
            for (int i = 0; i < this.movePriority[this.dir - 1].length; i++) {
                nextPoint = point.getNextPoint(this.movePriority[this.dir - 1][i]);
                if (nextPoint != null && isEmpty(nextPoint)) {
                    return nextPoint;
                }
            }

            for (int i = 0; i < this.movePriority[this.dir - 1].length; i++) {
                nextPoint = point.getNextPoint(this.movePriority[this.dir - 1][i]);
                if (nextPoint != null && isMySmell(nextPoint)) {
                    return nextPoint;
                }
            }
            return null;
        }

        public void move() {
            Point nextPoint = getNextPoint();
            if (nextPoint != null) {
                this.dir = this.point.getDir(nextPoint);
                this.point = nextPoint;
//                this.board.positioning(this);
            } else {
                this.point = null;
            }
        }

        public boolean isEmpty(Point point) {
            return this.board.getLocations(point.x, point.y) == null;
        }

        public boolean isMySmell(Point point) {
            return this.board.getLocations(point.x, point.y).shark == this;
        }
    }

    static class Point {

        int x;
        int y;
        int dx[] = {0, -1, 1, 0, 0};
        int dy[] = {0, 0, 0, -1, 1};

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point getNextPoint(int dir) {
            int nextX = this.x + dx[dir];
            int nextY = this.y + dy[dir];
            if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N) {
                return new Point(nextX, nextY);
            }
            return null;
        }


        public int getDir(Point nextPoint) {
            int dx = nextPoint.x - this.x;
            int dy = nextPoint.y - this.y;
            if (dx == -1) {
                return 1;
            } else if (dx == 1) {
                return 2;
            } else if (dy == -1) {
                return 3;
            } else if (dy == 1) {
                return 4;
            } else {
                return -1;
            }

        }
    }

}
