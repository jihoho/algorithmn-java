package me.jihoho.baekjoon.simulation.b17825;

import static java.lang.Math.max;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int[] dice = new int[10];
    static int maxScore = 0;
    static Board board = new Board();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < dice.length; i++) {
            dice[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < 4; i++) {
            board.recursion(i, 0, 0);
        }

        System.out.println(maxScore);
    }


    static class Board {

        Node head;
        Horse[] horses;

        public Board() {
            this.head = new RedNode(0);
            Node root1 = this.head;
            root1.setNext(new RedNode(2));
            root1 = root1.getNext();
            root1.setNext(new RedNode(4));
            root1 = root1.getNext();
            root1.setNext(new RedNode(6));
            root1 = root1.getNext();
            root1.setNext(new RedNode(8));
            root1 = root1.getNext();
            root1.setNext(new BlueNode(10));
            root1 = root1.getNext();

            root1.setBlueNext(new RedNode(13));
            Node root2 = root1.getBlueNext();
            root2.setNext(new RedNode(16));
            root2 = root2.getNext();
            root2.setNext(new RedNode(19));
            root2 = root2.getNext();
            Node node25 = new RedNode(25);
            root2.setNext(node25);

            root1.setNext(new RedNode(12));
            root1 = root1.getNext();
            root1.setNext(new RedNode(14));
            root1 = root1.getNext();
            root1.setNext(new RedNode(16));
            root1 = root1.getNext();
            root1.setNext(new RedNode(18));
            root1 = root1.getNext();
            root1.setNext(new BlueNode(20));
            root1 = root1.getNext();

            root1.setBlueNext(new RedNode(22));
            Node root3 = root1.getBlueNext();
            root3.setNext(new RedNode(24));
            root3 = root3.getNext();
            root3.setNext(node25);

            root1.setNext(new RedNode(22));
            root1 = root1.getNext();
            root1.setNext(new RedNode(24));
            root1 = root1.getNext();
            root1.setNext(new RedNode(26));
            root1 = root1.getNext();
            root1.setNext(new RedNode(28));
            root1 = root1.getNext();

            root1.setNext(new BlueNode(30));

            root1 = root1.getNext();

            root1.setBlueNext(new RedNode(28));
            Node root4 = root1.getBlueNext();
            root4.setNext(new RedNode(27));
            root4 = root4.getNext();
            root4.setNext(new RedNode(26));
            root4 = root4.getNext();
            root4.setNext(node25);
            root4 = root4.getNext();

            root4.setNext(new RedNode(30));
            root4 = root4.getNext();
            root4.setNext(new RedNode(35));
            root4 = root4.getNext();
            Node node40 = new RedNode(40);
            root4.setNext(node40);
            root4 = root4.getNext();
            root4.setNext(new RedNode(0));

            root1.setNext(new RedNode(32));
            root1 = root1.getNext();
            root1.setNext(new RedNode(34));
            root1 = root1.getNext();
            root1.setNext(new RedNode(36));
            root1 = root1.getNext();
            root1.setNext(new RedNode(38));
            root1 = root1.getNext();
            root1.setNext(node40);

            horses = new Horse[4];
            for (int i = 0; i < horses.length; i++) {
                horses[i] = new Horse(this.head);
            }
        }

        boolean isMovable(Horse horse, Node targetNode) {
            if (targetNode == null) {
                return false;
            }
            for (int i = 0; i < horses.length; i++) {
                try {

                    if (horses[i] != horse && horses[i].curr == targetNode
                            && horses[i].curr.getScore() != 0) {
                        return false;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println(horse);
                    System.out.println(targetNode);
                    System.out.printf("index: %d", i);

                }
            }
            return true;
        }

        void recursion(int horsIdx, int diceOffset, int acc) {
            if (diceOffset >= 10) {
                maxScore = max(acc, maxScore);
                return;
            }
            Horse targetHorse = horses[horsIdx];
            Node destNode = targetHorse.getDestination(dice[diceOffset]);
            if (isMovable(targetHorse, targetHorse.getDestination(dice[diceOffset]))) {
                acc += targetHorse.move(destNode);
                for (int i = 0; i < horses.length; i++) {
                    recursion(i, diceOffset + 1, acc);
                }
                targetHorse.back();
            }
        }
    }

    static interface Node {

        Node getDestination(int offset);

        Node getNext();

        Node getBlueNext();

        void setNext(Node node);

        void setBlueNext(Node node);

        int getScore();
    }

    static class BlueNode implements Node {

        Node blueNext;
        Node next;

        int score;

        BlueNode(int score) {
            this.score = score;
        }

        @Override
        public Node getDestination(int offset) {
            Node curr = blueNext;
            Node prev = this;
            for (int i = 1; i < offset; i++) {
                if (curr == null) {
                    return prev;
                }
                prev = curr;
                curr = curr.getNext();
            }
            return curr;
        }

        @Override
        public Node getNext() {
            return this.next;
        }

        @Override
        public Node getBlueNext() {
            return this.blueNext;
        }

        @Override
        public void setNext(Node node) {
            this.next = node;
        }

        @Override
        public void setBlueNext(Node node) {
            this.blueNext = node;
        }


        @Override
        public int getScore() {
            return this.score;
        }


    }

    static class RedNode implements Node {

        Node blueNext;
        Node next;

        int score;

        public RedNode(int score) {
            this.score = score;
        }


        @Override
        public Node getDestination(int offset) {
            Node curr = next;
            Node prev = this;
            for (int i = 1; i < offset; i++) {
                if (curr == null) {
                    return prev;
                }
                prev = curr;
                curr = curr.getNext();
            }
            return curr;
        }

        @Override
        public Node getNext() {
            return this.next;
        }

        @Override
        public Node getBlueNext() {
            return this.blueNext;
        }

        @Override
        public void setNext(Node node) {
            this.next = node;
        }

        @Override
        public void setBlueNext(Node node) {
            this.blueNext = node;
        }

        @Override
        public int getScore() {
            return this.score;
        }
    }

    private static class Horse {

        Node curr;
        List<Node> moveHistroy = new ArrayList<>();

        public Horse(Node node) {
            this.curr = node;
            moveHistroy.add(node);
        }

        public Node getDestination(int offset) {
            return curr == null ? null : curr.getDestination(offset);
        }

        public int move(Node node) {
            if (node != null) {
                moveHistroy.add(curr);
                curr = node;
                return curr.getScore();
            }
            return 0;

        }

        public void back() {
            this.curr = moveHistroy.remove(moveHistroy.size() - 1);
        }
    }
}
