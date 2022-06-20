package me.jihoho.programmers.p2020_KAKAO_INTERNSHIP.블록_이동하기;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    static int[][] map;

    public static void main(String[] args) {
        int[][] board = {{0, 0, 0, 1, 1}, {0, 0, 0, 1, 0}, {0, 1, 0, 1, 1}, {1, 1, 0, 0, 1},
            {0, 0, 0, 0, 0}};

        System.out.println(solution(board));
    }

    public static int solution(int[][] board) {
        map = board;
        int answer = bfsall();
        return answer;
    }

    private static int bfsall() {
        Location firstLocation = new Location(0, 0, 1, 0, 0);
        Point destination = new Point(map[0].length - 1, map.length - 1);

        List<Location> visited = new ArrayList<>(List.of(firstLocation));
        List<Location> queue = new ArrayList<>(List.of(firstLocation));

        while (!queue.isEmpty()) {

            Location location = queue.remove(0);
            List<Location> nextLocations = location.nextLocation();
            if (nextLocations != null) {
                for (Location nextLocation : nextLocations) {
                    if (nextLocation.include(destination)) {
                        return nextLocation.count;
                    }
                    if (!visited.contains(nextLocation)) {
                        visited.add(nextLocation);
                        queue.add(nextLocation);
                    }

                }

            }
        }

        return -1;

    }


    static class Location {

        Point p1;
        Point p2;
        int count;

        public Location(int x1, int y1, int x2, int y2, int count) {
            this.p1 = new Point(x1, y1);
            this.p2 = new Point(x2, y2);
            this.count = count;
        }

        public Location(Point p1, Point p2, int count) {
            this.p1 = p1;
            this.p2 = p2;
            this.count = count;
        }

        public List<Location> nextLocation() {
            List<Location> nextLocations = new ArrayList<>();
            if (p1.y == p2.y) { // 가로 배치인 경우
                int nextX = p1.x;
                int nextY = p1.y - 1;
                if (isValid(nextX, nextY) && isValid(nextX + 1, nextY)) {
                    nextLocations.add(new Location(new Point(nextX, nextY), p1, this.count + 1));
                }
                nextY = p1.y + 1;
                if (isValid(nextX, nextY) && isValid(nextX + 1, nextY)) {
                    nextLocations.add(new Location(p1, new Point(nextX, nextY), this.count + 1));
                }
                nextX = p2.x;
                nextY = p2.y - 1;
                if (isValid(nextX, nextY) && isValid(nextX - 1, nextY)) {
                    nextLocations.add(new Location(new Point(nextX, nextY), p2, this.count + 1));
                }
                nextY = p2.y + 1;
                if (isValid(nextX, nextY) && isValid(nextX - 1, nextY)) {
                    nextLocations.add(new Location(p2, new Point(nextX, nextY), this.count + 1));
                }
            } else if (p1.x == p2.x) { // 새로 배치인 경우
                int nextX = p1.x - 1;
                int nextY = p1.y;
                if (isValid(nextX, nextY) && isValid(nextX, nextY + 1)) {
                    nextLocations.add(new Location(new Point(nextX, nextY), p1, this.count + 1));
                }

                nextX = p1.x + 1;
                if (isValid(nextX, nextY) && isValid(nextX, nextY + 1)) {
                    nextLocations.add(new Location(p1, new Point(nextX, nextY), this.count + 1));
                }

                nextX = p2.x - 1;
                nextY = p2.y;
                if (isValid(nextX, nextY) && isValid(nextX, nextY - 1)) {
                    nextLocations.add(new Location(new Point(nextX, nextY), p2, this.count + 1));
                }

                nextX = p2.x + 1;

                if (isValid(nextX, nextY) && isValid(nextX, nextY - 1)) {
                    nextLocations.add(new Location(p2, new Point(nextX, nextY), this.count + 1));
                }


            }
            int[] dx = {-1, 0, 1, 0};
            int[] dy = {0, -1, 0, 1};
            for (int i = 0; i < dx.length; i++) {
                int nextX1 = p1.x + dx[i];
                int nextY1 = p1.y + dy[i];
                int nextX2 = p2.x + dx[i];
                int nextY2 = p2.y + dy[i];

                if (isValid(nextX1, nextY1) && isValid(nextX2, nextY2)) {
                    nextLocations.add(
                        new Location(new Point(nextX1, nextY1), new Point(nextX2, nextY2),
                            this.count + 1));
                }
            }
            return nextLocations;
        }


        private boolean isValid(int nextX, int nextY) {
            if (nextX >= 0 && nextX < map[0].length && nextY >= 0 && nextY < map.length
                && map[nextY][nextX] == 0) {
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Location location = (Location) o;

            return (this.p1.equals(location.p1) && this.p2.equals(location.p2))
                || (this.p1.equals(location.p2) && this.p2.equals(location.p1));
        }


        public boolean include(Point point) {
            return p1.equals(point) || p2.equals(point);
        }
    }

    private static class Point {

        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Point point = (Point) o;

            if (x != point.x) {
                return false;
            }
            return y == point.y;
        }


    }
}
