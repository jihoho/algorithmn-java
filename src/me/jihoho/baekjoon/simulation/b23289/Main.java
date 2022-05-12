package me.jihoho.baekjoon.simulation.b23289;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    static Room room;

    static int R, C, K, W;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        room = new Room(R, C, K);
        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < C; j++) {
                int value = Integer.parseInt(st.nextToken());
                room.addCell(i, j, value);
            }
        }

        W = Integer.parseInt(br.readLine());
        for (int i = 0; i < W; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            room.addWall(x - 1, y - 1, t);
        }

        System.out.println(room.work());
    }

    static class Room {

        int r;
        int c;
        int k;
        Cell[][] cells;
        int[][] temperaturePerOneTime;
        List<Cell> heaterCells = new ArrayList<>();
        List<Cell> targetCells = new ArrayList<>();
        List<Wall> walls = new ArrayList<>();

        public Room(int r, int c, int k) {
            this.r = r;
            this.c = c;
            this.k = k;
            cells = new Cell[r][c];
            temperaturePerOneTime = new int[R][C];
        }

        void addCell(int x, int y, int value) {
            Cell cell = new Cell(x, y, value);
            if (value > 0 && value < 5) {
                heaterCells.add(cell);
            } else if (value == 5) {
                targetCells.add(cell);
            }
            cells[x][y] = cell;
        }

        public void addWall(int x, int y, int t) {
            this.walls.add(new Wall(x, y, t));
        }

        public int work() {
            initTemprPerOnetime();
            int chocoCount = 0;
            while (chocoCount <= 100) {
                heaterOn();

                controlTemp();
                decreaseOutsideTemperature();
                chocoCount++;
                if (isAboveTemperatureOfTargetCells()) {
                    break;
                }
            }
            return chocoCount;
        }

        private void initTemprPerOnetime() {
            for (int i = 0; i < heaterCells.size(); i++) {
                Cell heater = heaterCells.get(i);
                Point nextPoint = heater.getNextFrontPoint(heater.dir);
                if (nextPoint != null && isMovable(heater.point, nextPoint, heater.dir)) {
                    Set<Cell> nextCell = new HashSet<>();
                    nextCell.add(this.cells[nextPoint.x][nextPoint.y]);
                    heatCells(nextCell, heater.dir, 5);
                }
            }
        }

        private void printCell() {
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    System.out.printf("%2d ", cells[i][j].tempr);
                }
                System.out.println();
            }
            System.out.println();
        }

        private void controlTemp() {
            boolean[][] visited = new boolean[R][C];
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    Cell cell = this.cells[i][j];
                    List<Point> adjacentPoints = this.cells[i][j].getAdjacentPoints();
                    for (Point adjacentPoint : adjacentPoints) {
                        if (!visited[adjacentPoint.x][adjacentPoint.y] && !isBlock(cell.point,
                                adjacentPoint)) {
                            Cell adjacentCell = this.cells[adjacentPoint.x][adjacentPoint.y];
                            if (cell.tempr > adjacentCell.tempr) {
                                int diff = (cell.tempr - adjacentCell.tempr) / 4;
                                if (diff > 0) {
                                    cell.tmpTemprature -= diff;
                                    adjacentCell.tmpTemprature += diff;
                                }
                            } else {
                                int diff = (adjacentCell.tempr - cell.tempr) / 4;
                                if (diff > 0) {
                                    cell.tmpTemprature += diff;
                                    adjacentCell.tmpTemprature -= diff;
                                }

                            }
                        }
                    }
                    visited[i][j] = true;
                }
            }

            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    cells[i][j].changeTemperatureToTmpTemperature();
                }
            }
        }

        private boolean isBlock(Point p1, Point p2) {
            for (Wall wall : walls) {
                if (wall.isBlock(p1, p2)) {
                    return true;
                }
            }
            return false;
        }

        private void decreaseOutsideTemperature() {
            for (int i = 0; i < R; i++) {
                this.cells[i][0].decreaseTemperature();
                this.cells[i][C - 1].decreaseTemperature();
            }

            for (int i = 1; i < C-1; i++) {
                this.cells[0][i].decreaseTemperature();
                this.cells[R - 1][i].decreaseTemperature();
            }
        }

        private boolean isAboveTemperatureOfTargetCells() {
            for (Cell targetCell : this.targetCells) {
                if (targetCell.tempr < k) {
                    return false;
                }
            }
            return true;
        }

        public void heaterOn() {
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    cells[i][j].tempr += temperaturePerOneTime[i][j];
                }
            }
        }

        public void heatCells(Set<Cell> cells, int dir, int tempr) {
            if (cells.size() == 0 || tempr == 0) {
                return;
            }
            Set<Cell> nextCells = new HashSet<>();

            for (Cell targetCell : cells) {
                temperaturePerOneTime[targetCell.point.x][targetCell.point.y] += tempr;
                List<Point> nextPoints = targetCell.getNextCellPoints(dir);
                for (Point point : nextPoints) {
                    if (isMovable(targetCell.point, point, dir)) {
                        nextCells.add(this.cells[point.x][point.y]);
                    }
                }
            }
            heatCells(nextCells, dir, tempr - 1);
        }

        private boolean isMovable(Point from, Point to, int dir) {
            for (Wall wall : this.walls) {
                if (wall.isBlock(from, to, dir)) {
                    return false;
                }
            }
            return true;
        }
    }


    static class Cell {

        Point point;
        int tempr;
        int dir;
        boolean isTarget;
        int tmpTemprature;

        public Cell(int x, int y, int value) {
            if (value > 0 && value < 5) {
                this.dir = value;
            } else if (value == 5) {
                isTarget = true;
            }
            tempr = 0;
            this.point = new Point(x, y);

        }

        public List<Point> getNextCellPoints(int dir) {
            return this.point.getNextPoints(dir);
        }


        public Point getNextFrontPoint(int dir) {
            return this.point.getFrontPoint(dir);
        }

        public void decreaseTemperature() {
            tempr = tempr > 0 ? tempr - 1 : tempr;
        }

        public void changeTemperatureToTmpTemperature() {
            this.tempr = tempr + tmpTemprature;
            this.tmpTemprature = 0;
        }

        public List<Point> getAdjacentPoints() {
            return this.point.getAdjacentPoints();
        }
    }

    private static class Point {

        int x;
        int y;
        //        1: 오른쪽, 2: 왼쪽, 3: 위, 4: 아래
        int[][] dx = {{0, 0, 0}, {-1, 0, 1}, {-1, 0, 1}, {-1, -1, -1}, {1, 1, 1}};
        int[][] dy = {{0, 0, 0}, {1, 1, 1}, {-1, -1, -1}, {-1, 0, 1}, {-1, 0, 1}};

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        List<Point> getNextPoints(int dir) {
            List<Point> nextPoints = new ArrayList<>();
            for (int i = 0; i < dx[dir].length; i++) {
                for (int j = 0; j < dy[dir].length; j++) {
                    int nextX = this.x + dx[dir][i];
                    int nextY = this.y + dy[dir][i];
                    if (isValidPoint(nextX, nextY)) {
                        nextPoints.add(new Point(nextX, nextY));
                    }
                }
            }
            return nextPoints;
        }

        Point getFrontPoint(int dir) {
            int nextX = this.x + dx[dir][1];
            int nextY = this.y + dy[dir][1];
            if (isValidPoint(nextX, nextY)) {
                return new Point(nextX, nextY);
            }
            return null;
        }

        private boolean isValidPoint(int nextX, int nextY) {
            if (nextX >= 0 && nextX < R && nextY >= 0 && nextY < C) {
                return true;
            }
            return false;
        }

        public List<Point> getAdjacentPoints() {
            int dx[] = {0, 0, -1, 1};
            int dy[] = {-1, 1, 0, 0};
            List<Point> adjacentPoints = new ArrayList<>();
            for (int i = 0; i < dx.length; i++) {
                int nextX = this.x + dx[i];
                int nextY = this.y + dy[i];
                if (isValidPoint(nextX, nextY)) {
                    adjacentPoints.add(new Point(nextX, nextY));
                }
            }
            return adjacentPoints;
        }

        @Override
        public boolean equals(Object o) {
            Point p = (Point) o;
            if (this == p) {
                return true;
            } else if (this.x == p.x && this.y == p.y) {
                return true;
            }

            return false;
        }

    }

    private static class Wall {

        int x;
        int y;
        int t;

        List<Moving> blockMovings = new ArrayList<>();


        public Wall(int x, int y, int t) {
            this.x = x;
            this.y = y;
            this.t = t;
            setBlockPoints();
        }

        private void setBlockPoints() {
            int fromX, fromY, toX, toY, dir;
            if (t == 0) {
                // 오른쪽 dir:1
                dir = 1;
                fromX = x;
                fromY = y;
                toX = x - 1;
                toY = y + 1;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x - 1;
                fromY = y;
                toX = x;
                toY = y + 1;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                // 왼쪽 dir: 2
                dir = 2;
                fromX = x;
                fromY = y;
                toX = x - 1;
                toY = y - 1;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x - 1;
                fromY = y;
                toX = x;
                toY = y - 1;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                //  위쪽 dir: 3
                dir = 3;
                fromX = x;
                fromY = y;
                toX = x - 1;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x;
                fromY = y + 1;
                toX = x - 1;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x;
                fromY = y - 1;
                toX = x - 1;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));

                //  아래쪽 dir: 4
                dir = 4;
                fromX = x - 1;
                fromY = y;
                toX = x;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x - 1;
                fromY = y - 1;
                toX = x;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x - 1;
                fromY = y + 1;
                toX = x;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
            } else if (t == 1) {
                // 오른쪽 dir:1
                dir = 1;
                fromX = x;
                fromY = y;
                toX = x;
                toY = y + 1;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x - 1;
                fromY = y;
                toX = x;
                toY = y + 1;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x + 1;
                fromY = y;
                toX = x;
                toY = y + 1;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                // 오른쪽 dir:2
                dir = 2;
                fromX = x;
                fromY = y + 1;
                toX = x;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x - 1;
                fromY = y + 1;
                toX = x;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x + 1;
                fromY = y + 1;
                toX = x;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                // 위쪽 dir:3
                dir = 3;
                fromX = x;
                fromY = y + 1;
                toX = x - 1;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x;
                fromY = y;
                toX = x - 1;
                toY = y + 1;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                // 아래쪽 dir:4
                dir = 4;
                fromX = x;
                fromY = y + 1;
                toX = x + 1;
                toY = y;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
                fromX = x;
                fromY = y;
                toX = x + 1;
                toY = y + 1;
                blockMovings.add(new Moving(new Point(fromX, fromY), new Point(toX, toY), dir));
            }

        }

        public boolean isBlock(Point from, Point to, int dir) {
            for (Moving moving : this.blockMovings) {
                if (moving.isEqual(from, to, dir)) {
                    return true;
                }
            }
            return false;

        }

        public boolean isBlock(Point target1, Point target2) {
            Point p1 = new Point(this.x, this.y);
            Point p2;
            if (this.t == 0) {
                p2 = new Point(p1.x - 1, p1.y);
            } else {
                p2 = new Point(p1.x, p1.y + 1);
            }

            if ((p1.equals(target1) && p2.equals(target2)) || (p2.equals(target1) && p1
                    .equals(target2))) {
                return true;
            }
            return false;
        }

        private static class Moving {

            Point from;
            Point to;
            int dir;

            public Moving(Point from, Point to, int dir) {
                this.from = from;
                this.to = to;
                this.dir = dir;
            }

            public boolean isEqual(Point from, Point to, int dir) {
                if (this.from.equals(from) && this.to.equals(to) && this.dir == dir) {
                    return true;
                }
                return false;
            }
        }
    }
}
