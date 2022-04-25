package me.jihoho.baekjoon.simulation.b17140;

import static java.lang.Math.max;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int r, c, k;
    static int[][] A = new int[101][101];
    static int maxCntOfRow = 3;
    static int maxCntOfCol = 3;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 3; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int time = simulation(r, c, k);
        System.out.println(time);
    }

    private static int simulation(int r, int c, int k) {
        int time = 0;
        if (A[r][c] == k) {
            return time;
        }
//        printA();
        while (time < 100) {

            time++;
            if (maxCntOfRow >= maxCntOfCol) {
                for (int i = 1; i <= maxCntOfRow; i++) {
                    if (i == 1) {
                        maxCntOfCol = sort(i, true);
                    } else {
                        maxCntOfCol = max(maxCntOfCol, sort(i, true));
                    }
                }
            } else {
                for (int i = 1; i <= maxCntOfCol; i++) {
                    if (i == 1) {
                        maxCntOfRow = sort(i, false);
                    } else {

                        maxCntOfRow = max(maxCntOfRow, sort(i, false));
                    }
                }
            }
//            printA();
            if (A[r][c] == k) {
                return time;
            }
        }
        return -1;
    }

    private static void printA() {
        for (int i = 1; i <= maxCntOfRow; i++) {
            for (int j = 1; j <= maxCntOfCol; j++) {
                System.out.print(A[i][j] + " ");
            }
            if (A[i][1] != 0) {
                System.out.println();
            }
        }
        System.out.printf("\n\n");
    }


    static class NumberSorter {

        NumberCounter head = null;

        public void add(int num) {
            if (num == 0) {
                return;
            }
            NumberCounter curr = head;
            while (curr != null) {
                if (curr.num == num) {
                    increase(curr);
                    return;
                }
                curr = curr.next;
            }
            push(new NumberCounter(num));
        }

        public void increase(NumberCounter node) {
            node.increase();
            pop(node);
            push(node);
        }

        private void push(NumberCounter node) {
            if (head == null) {
                head = node;
                return;
            }

            NumberCounter curr = head;
            NumberCounter prev = null;
            while (curr != null) {
                if (curr.getCnt() >= node.getCnt()) {
                    if (curr.getCnt() == node.getCnt()) {
                        if (curr.getNum() > node.getNum()) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                prev = curr;
                curr = curr.next;
            }
            node.next = curr;
            if (prev == null) {
                head = node;
            } else {
                prev.next = node;
            }
        }

        private void pop(NumberCounter node) {
            NumberCounter curr = this.head;
            NumberCounter prev = null;
            while (curr != null) {
                if (curr.num == node.num) {
                    if (prev == null) {
                        head = curr.next;
                    } else {
                        prev.next = curr.next;
                    }
                    break;
                }
                prev = curr;
                curr = curr.next;
            }

            node.next = null;
        }

    }

    static class NumberCounter {

        private int num;
        private int cnt;
        NumberCounter next;

        public int getNum() {
            return this.num;
        }

        public int getCnt() {
            return this.cnt;
        }

        public NumberCounter(int num) {
            this.num = num;
            this.cnt = 1;
        }

        public void increase() {
            cnt++;
        }
    }

    private static int sort(int idx, boolean isRow) {
        NumberSorter sorter = new NumberSorter();
        int j = 1;
        if (isRow) {
            for (int i = 1; i <= 100; i++) {
                sorter.add(A[idx][i]);

            }
            for (int i = 1; i <= 100; i++) {
                A[idx][i] = 0;
            }
            NumberCounter curr = sorter.head;
            while (curr != null && j <= 100) {
                A[idx][j] = curr.getNum();
                A[idx][j + 1] = curr.getCnt();
                curr = curr.next;
                j += 2;
            }
        } else {
            for (int i = 1; i <= 100; i++) {
                if (A[i][idx] != 0) {
                    sorter.add(A[i][idx]);
                }
            }
            for (int i = 1; i <= 100; i++) {
                A[i][idx] = 0;
            }
            NumberCounter curr = sorter.head;
            while (curr != null && j <= 100) {
                A[j][idx] = curr.getNum();
                A[j + 1][idx] = curr.getCnt();
                curr = curr.next;
                j += 2;
            }

        }

        return j - 1;
    }

}
