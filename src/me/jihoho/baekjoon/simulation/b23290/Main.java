package me.jihoho.baekjoon.simulation.b23290;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int M, S;

    static FishBowl fishBowl;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        fishBowl = new FishBowl(M, S);
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            fishBowl.addFish(x - 1, y - 1, d);
        }

        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        fishBowl.setShark(x - 1, y - 1);
        System.out.println(fishBowl.magicStart());
    }

    static class FishBowl {

        int M, S;

        int[][][] cells;
        int[][][] copyOfCells;
        List<SmellyPoint> smellyPoints;

        Point sharkPoint;
        List<Point> sharkBestRoute;

        int maxScore = -1;

        public FishBowl(int m, int s) {
            M = m;
            S = s;
            cells = new int[4][4][9];
            copyOfCells = new int[4][4][9];
            smellyPoints = new ArrayList<>();
        }

        void addFish(int x, int y, int d) {
            cells[x][y][d] += 1;
        }


        public int magicStart() {
            int cnt = 0;
            while (cnt < S) {
//                System.out.printf("--------------%d--\n", cnt);
                replicatedFishes();
                moveFish();
//                printCell();
                moveSharkOptimally();
//                printCell();
                smellOut();
                reflectReplicatedFishes();
//                printCell();
                cnt++;
            }
            return getFishCount();
        }

        private int getFishCount() {
            int total = 0;
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    total += Arrays.stream(cells[i][j]).sum();
                }
            }
            return total;

        }

        private void moveSharkOptimally() {
            int[][] scores = new int[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    scores[i][j] = Arrays.stream(this.cells[i][j]).sum();
                }
            }

            this.maxScore = -1;
            List<Point> route = new ArrayList<>();
            sharkMove(scores, sharkPoint, route, 0, 3);
            for (Point point : this.sharkBestRoute) {
                if (Arrays.stream(cells[point.x][point.y]).sum() > 0) {
                    this.smellyPoints.add(new SmellyPoint(point));
                    Arrays.fill(cells[point.x][point.y], 0);
                }
            }

            sharkPoint = sharkBestRoute.get(2);
        }

        private void printCell() {

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.printf("%3d", Arrays.stream(cells[i][j]).sum());
                }
                System.out.println();
            }
            System.out.println();
        }

        private void sharkMove(int[][] cells, Point point, List<Point> route, int score,
                int moveCnt) {

            if (moveCnt <= 0) {
                if (maxScore < score) {
                    sharkBestRoute = new ArrayList<>();
                    sharkBestRoute.addAll(route);
                    maxScore = score;
                }
                return;
            }

            List<Point> adjacentPoints = point.getAdjacentPoint();
            for (Point adjacentPoint : adjacentPoints) {
                int cellScore = cells[adjacentPoint.x][adjacentPoint.y];
                score += cellScore;
                cells[adjacentPoint.x][adjacentPoint.y] = 0;
                route.add(adjacentPoint);

                sharkMove(cells, adjacentPoint, route, score, moveCnt - 1);
                score -= cellScore;
                cells[adjacentPoint.x][adjacentPoint.y] = cellScore;
                route.remove(route.size() - 1);
            }
        }

        private void smellOut() {
            List<SmellyPoint> newSmellyPoint = new ArrayList<>();

            for (SmellyPoint smellyPoint : this.smellyPoints) {
                if (--smellyPoint.time >= 0) {
                    newSmellyPoint.add(smellyPoint);
                }
            }
            this.smellyPoints = newSmellyPoint;
        }

        private void moveFish() {
            int[][][] newCells = new int[4][4][9];
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    for (int k = 0; k < cells[i][j].length; k++) {
                        if (cells[i][j][k] > 0) {
                            PointDir nextPointdir = getNextPoints(i, j, k);
                            newCells[nextPointdir.point.x][nextPointdir.point.y][nextPointdir.dir] += cells[i][j][k];
                        }
                    }
                }
            }
            this.cells= newCells;
        }

        private PointDir getNextPoints(int x, int y, int d) {
            int[] dx = {0, 0, -1, -1, -1, 0, 1, 1, 1};
            int[] dy = {0, -1, -1, 0, 1, 1, 1, 0, -1};
            for (int i = d; i >= d-8; --i) {
                int dir = i;
                if (i <= 0) {
                    dir += 8;
                }
                int nextX = x + dx[dir];
                int nextY = y + dy[dir];
                if (isMovable(nextX, nextY)) {
                    return new PointDir(new Point(nextX, nextY), dir);
                }
            }
            return new PointDir(new Point(x, y), d);
        }

        private boolean isMovable(int nextX, int nextY) {
            if (nextX >= 0 && nextX < 4 && nextY >= 0 && nextY < 4 && !isSharkPoint(nextX, nextY)
                    && isNoSmell(nextX, nextY)) {
                return true;
            }
            return false;
        }

        private boolean isSharkPoint(int nextX, int nextY) {
            if (nextX == sharkPoint.x && nextY == sharkPoint.y) {
                return true;
            }
            return false;
        }

        private boolean isNoSmell(int x, int y) {
            Point targetPoint = new Point(x, y);
            for (SmellyPoint spoint : this.smellyPoints) {
                if (spoint.equals(targetPoint)) {
                    return false;
                }
            }
            return true;
        }

        private void replicatedFishes() {
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    for (int k = 0; k < cells[i][j].length; k++) {
                        copyOfCells[i][j][k] = cells[i][j][k];
                    }
                }
            }
        }

        private void reflectReplicatedFishes() {
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    for (int k = 0; k < cells[i][j].length; k++) {
                        cells[i][j][k] += copyOfCells[i][j][k];
                    }
                }
            }
        }


        public void setShark(int x, int y) {
            this.sharkPoint = new Point(x, y);
        }

        private class SmellyPoint extends Point {

            int time;

            public SmellyPoint(Point p) {
                super(p.x, p.y);
                time = 2;
            }
        }
    }

    private static class Point implements Cloneable {

        int x;
        int y;
        int[] dx = {0, 0, -1, -1, -1, 0, 1, 1, 1};
        int[] dy = {0, -1, -1, 0, 1, 1, 1, 0, -1};

        int[] dx2 = {-1, 0, 1, 0};
        int[] dy2 = {0, -1, 0, 1};

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            Point p = (Point) o;
            if (this == p) {
                return true;
            }
            if (this.x == p.x && this.y == p.y) {
                return true;
            }
            return false;
        }

        public List<Point> getAdjacentPoint() {
            List<Point> adjacentPoints = new ArrayList<>();

            for (int i = 0; i < dx2.length; i++) {
                int nextX = this.x + dx2[i];
                int nextY = this.y + dy2[i];
                if (nextX >= 0 && nextX < 4 && nextY >= 0 && nextY < 4) {
                    adjacentPoints.add(new Point(nextX, nextY));
                }
            }
            return adjacentPoints;
        }
    }

    private static class PointDir {

        Point point;
        int dir;

        public PointDir(Point point, int dir) {
            this.point = point;
            this.dir = dir;
        }
    }
}
