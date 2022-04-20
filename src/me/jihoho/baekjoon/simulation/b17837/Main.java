package me.jihoho.baekjoon.simulation.b17837;

import static java.lang.System.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int K;
    static Piece[] pieces;
    static Chess chess;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        chess = new Chess(N);
        pieces = new Piece[K];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                chess.setLocations(i, j, Integer.parseInt(st.nextToken()));
            }
        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            pieces[i] = new Piece(row, col, dir);
            chess.locations[row][col].pieceStack.add(pieces[i]);

        }

        System.out.println(simulation());

    }

    private static int simulation() {
        int cnt = 0;

        while (cnt <= 1000) {
            cnt++;
            boolean isOver = false;
            for (int i = 0; i < K; i++) {
                isOver = chess.move(pieces[i]);
                if (isOver) {
                    return cnt;
                }
            }
        }
        return -1;
    }

    public static class Chess {

        int N;
        Location[][] locations;

        public void setLocations(int r, int c, int color) {
            this.locations[r][c] = new Location(new Point(r, c), color);
        }

        public Chess(int N) {
            this.N = N;
            this.locations = new Location[N + 1][N + 1];
        }


        public boolean move(Piece targetPiece) {
            Point currPoint = targetPiece.getPoint();
            List<Piece> targetPieces = locations[currPoint.r][currPoint.c]
                    .getAndRemoveStackedUp(targetPiece);

            Point nextPoint = currPoint.getNextPoint(targetPiece.getDir());

            int color = checkNextPoint(nextPoint);

            switch (color) {
                case 0: // white
                    locations[nextPoint.r][nextPoint.c].stack(targetPieces);

                    break;
                case 1: // red
                    reverseStack(targetPieces);
                    locations[nextPoint.r][nextPoint.c].stack(targetPieces);
                    break;
                case 2: // blue
                    targetPiece.reverseDir();
                    Point nextPoint2 = currPoint.getNextPoint(targetPiece.getDir());
                    int color2 = checkNextPoint(nextPoint2);
                    switch (color2) {
                        case 0: // white
                            locations[nextPoint2.r][nextPoint2.c].stack(targetPieces);
                            break;
                        case 1: // red
                            reverseStack(targetPieces);
                            locations[nextPoint2.r][nextPoint2.c].stack(targetPieces);
                            break;
                        case 2: // blue
                            locations[currPoint.r][currPoint.c].stack(targetPieces);
                            break;

                    }
                    break;
            }

            return isOver();
        }

        private boolean isValidPoint(Point point) {
            if (point.r < 1 || point.r > N || point.c < 1 || point.c > N) {
                return false;
            }
            return true;
        }

        private void reverseStack(List<Piece> targetPieces) {
            Collections.reverse(targetPieces);
        }


        private int checkNextPoint(Point nextPoint) {
            if (nextPoint.r < 1 || nextPoint.r > N || nextPoint.c < 1 || nextPoint.c > N) {
                return 2;
            }
            return locations[nextPoint.r][nextPoint.c].color;

        }

        public boolean isOver() {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (locations[i][j].getStackSize() >= 4) {
                        return true;
                    }
                }
            }
            return false;
        }


        public static class Location {

            List<Piece> pieceStack = new ArrayList<>();
            int color;
            Point point;

            public Location(Point point, int color) {
                this.point = point;
                this.color = color;
            }

            public void move(Piece piece) {

            }

            public List<Piece> getAndRemoveStackedUp(Piece targetPiece) {
                int idx = pieceStack.indexOf(targetPiece);
                List<Piece> targetList = pieceStack.subList(idx, pieceStack.size());
                this.pieceStack = pieceStack.subList(0, idx);
                return targetList;

            }

            public void stack(List<Piece> pieceStack) {
                this.pieceStack.addAll(pieceStack);
                this.pieceStack.forEach(p -> p.setPoint(this.point));
            }

            public int getStackSize() {
                return this.pieceStack.size();
            }
        }
    }


    public static class Piece {

        // 방향 1:->, 2:<- , 3:^,4:v
        private int dir;
        private Point point;

        public Piece(int r, int c, int dir) {
            this.point = new Point(r, c);
            this.dir = dir;
        }

        public int getDir() {
            return dir;
        }

        public void setDir(int dir) {
            this.dir = dir;
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public void reverseDir() {
            if (this.dir % 2 == 0) {
                this.dir -= 1;
            } else {
                this.dir += 1;
            }
        }
    }

    public static class Point {

        int r;
        int c;
        int[] dx = {0, 0, 0, -1, 1};
        int[] dy = {0, 1, -1, 0, 0};

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }


        public Point getNextPoint(int dir) {
            return new Point(this.r + this.dx[dir], this.c + this.dy[dir]);
        }
    }
}
