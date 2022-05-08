package me.jihoho.baekjoon.simulation.b21608;

import static java.lang.Math.max;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {

    static int N;
    static Student[] students;
    static HashMap<Integer, Student> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        students = new Student[N * N];

        ClassRoom classRoom = new ClassRoom(N);

        for (int i = 0; i < N * N; i++) {
            String[] tokens = br.readLine().split(" ");
            students[i] = new Student(tokens);
            map.put(Integer.parseInt(tokens[0]), students[i]);
        }

        for (int i = 0; i < students.length; i++) {
            classRoom.positioning(students[i]);
        }

        System.out.println(classRoom.getSatisfactionScore());


    }

    static class ClassRoom {

        Student[][] positions;
        boolean[][] assignedSeats;
        int N;

        public ClassRoom(int N) {
            this.N = N;
            positions = new Student[N][N];
            assignedSeats = new boolean[N][N];
        }

        int getSatisfactionScore() {
            int totalScore = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    totalScore += getScore(new Point(i, j));
                }
            }
            return totalScore;
        }

        int getScore(Point point) {
            List<Point> adjacentPoints = point.getAdjacentPoint();
            Student targetStudent = positions[point.x][point.y];
            int[] friendlyStudentNumbers = targetStudent.friendlyStudents;
            List<Student> friendlyStudents = new ArrayList<>();
            int cnt = 0;

            for (int studentNumber : friendlyStudentNumbers) {
                friendlyStudents.add(map.get(studentNumber));
            }

            for (Point adjacentPoint : adjacentPoints) {
                Student adjacentStudent = positions[adjacentPoint.x][adjacentPoint.y];
                if (friendlyStudents.contains(adjacentStudent)) {
                    cnt++;
                }
            }

            if (cnt == 1) {
                return 1;
            } else if (cnt == 2) {
                return 10;
            } else if (cnt == 3) {
                return 100;
            } else if (cnt == 4) {
                return 1000;
            } else {
                return 0;
            }
        }

        void positioning(Student student) {
            List<Point> favoritePoints = getFavoritePoints(student);
            Point point;
            if (favoritePoints.size() == 1) {
                point = favoritePoints.get(0);
            } else {
                List<Point> manyBlankPoints = getManyBlankPoints(favoritePoints);
                point = manyBlankPoints.get(0);
            }
            assignedSeats[point.x][point.y] = true;
            student.setPoint(point);
            positions[point.x][point.y] = student;
        }

        private List<Point> getFavoritePoints(Student student) {
            int[][] seatScore = new int[N][N];
            int max = 0;
            int[] friendlyStudents = student.friendlyStudents;

            List<Point> favoritePoints = new ArrayList<>();

            for (int studentNum : friendlyStudents) {
                Student friendlyStudent = map.get(studentNum);
                if (friendlyStudent.point != null) {
                    List<Point> adjacentPoint = friendlyStudent.point.getAdjacentPoint();
                    for (Point point : adjacentPoint) {
                        if (assignedSeats[point.x][point.y] == false) {
                            max = max(max, ++seatScore[point.x][point.y]);
                        }
                    }
                }
            }

            for (int i = 0; i < seatScore.length; i++) {
                for (int j = 0; j < seatScore[i].length; j++) {
                    if (seatScore[i][j] == max && assignedSeats[i][j] == false) {
                        favoritePoints.add(new Point(i, j));
                    }
                }
            }

//            if (favoritePoints.size() == 0) {
//                for (int i = 0; i < N; i++) {
//                    for (int j = 0; j < N; j++) {
//                        if (assignedSeats[i][j] == false) {
//                            favoritePoints.add(new Point(i, j));
//                        }
//                    }
//                }
//            }
            return favoritePoints;
        }

        private List<Point> getManyBlankPoints(List<Point> favoritePoints) {
            List<Point> manyBlanks = new ArrayList<>();
            int maxBlankCnt = 0;
            for (Point point : favoritePoints) {
                List<Point> adjacentPoints = point.getAdjacentPoint();
                int blankCnt = 0;
                for (Point adjacentPoint : adjacentPoints) {
                    if (assignedSeats[adjacentPoint.x][adjacentPoint.y] == false) {
                        blankCnt++;
                    }
                }
                if (maxBlankCnt == blankCnt) {
                    manyBlanks.add(point);
                } else if (maxBlankCnt < blankCnt) {
                    manyBlanks.clear();
                    manyBlanks.add(point);
                    maxBlankCnt = blankCnt;
                }
            }
            return manyBlanks;
        }


    }

    static class Student {

        int num;
        int[] friendlyStudents;

        Point point;

        public Student(String[] tokens) {
            this.num = Integer.parseInt(tokens[0]);
            this.friendlyStudents = Arrays.stream(Arrays.copyOfRange(tokens, 1, 5))
                    .mapToInt(Integer::parseInt).toArray();
        }

        void setPoint(Point point) {
            this.point = point;
        }
    }

    private static class Point {

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        List<Point> getAdjacentPoint() {
            List<Point> points = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                int nextX = this.x + dx[i];
                int nextY = this.y + dy[i];
                if (isValidPoint(nextX, nextY)) {
                    points.add(new Point(nextX, nextY));
                }
            }
            return points;
        }

        private boolean isValidPoint(int nextX, int nextY) {
            if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N) {
                return true;
            }
            return false;
        }
    }
}


