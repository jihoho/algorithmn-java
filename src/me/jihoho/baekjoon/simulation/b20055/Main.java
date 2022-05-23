package me.jihoho.baekjoon.simulation.b20055;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int N, K;
    static ConveyorBelt conveyorBelt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        conveyorBelt = new ConveyorBelt(N, K);

        int[] durabilityList = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt)
                .toArray();

        conveyorBelt.setDurabilityOfBeltPoint(durabilityList);

        System.out.println(conveyorBelt.run());

    }


    static class ConveyorBelt {

        int size;
        BeltPoint startPoint;
        BeltPoint endPoint;
        private List<Robot> robots;
        private int targetCountOfZeroDurability;


        public ConveyorBelt(int conveyorSize, int targetCountOfZeroDurability) {
            this.size = conveyorSize;
            this.targetCountOfZeroDurability = targetCountOfZeroDurability;
            this.robots = new ArrayList<>();
        }

        public void setDurabilityOfBeltPoint(int[] durabilityList) {
            BeltPoint curr, prev;
            curr = prev = null;
            for (int i = 0; i < durabilityList.length; i++) {
                curr = new BeltPoint(durabilityList[i]);
                if (prev != null) {
                    prev.setNext(curr);
                    curr.setPrev(prev);
                    if (i == this.size - 1) {
                        endPoint = curr;
                    }
                } else {
                    startPoint = curr;
                }
                prev = curr;
            }
            startPoint.setPrev(curr);
            curr.setNext(startPoint);
        }

        public int run() {
            int step = 0;
            while (true) {
                step++;
                moveConveyor();
                moveRobots();
                putRobotOnStartPoint();
                if (this.targetCountOfZeroDurability <= getNumberOfZeroDurability()) {
                    break;
                }
            }
            return step;
        }

        private int getNumberOfZeroDurability() {
            int cnt = 0;
            BeltPoint curr = this.startPoint;
            for (int i = 0; i < this.size * 2; i++) {
                if (curr.getDurability() == 0) {
                    cnt++;
                }
                curr = curr.next;
            }
            return cnt;
        }

        private void putRobotOnStartPoint() {
            if (this.startPoint.isValid()) {
                Robot robot = new Robot();
                this.robots.add(robot);
                this.startPoint.putRobot(robot);
            }
        }

        private void moveRobots() {
            for (Robot robot : this.robots) {
                robot.move();
            }
            removeRobotOnEndPoint();
        }

        private void moveConveyor() {
            startPoint = startPoint.getPrev();
            endPoint = endPoint.getPrev();
            removeRobotOnEndPoint();
        }

        private void removeRobotOnEndPoint() {
            if (!endPoint.isEmpty()) {
                robots.remove(endPoint.robot);
                endPoint.popRobot();
            }
        }
    }

    static class BeltPoint {


        private int durability;
        private Robot robot;
        private BeltPoint next;
        private BeltPoint prev;


        public BeltPoint(int durability) {
            this.durability = durability;
        }

        public void putRobot(Robot robot) {

            this.robot = robot;
            robot.beltPoint = this;
            this.durability--;
        }

        public void popRobot() {
            this.robot = null;
        }

        public void setNext(BeltPoint next) {
            this.next = next;
        }

        public BeltPoint getNext() {
            return this.next;
        }

        public BeltPoint getPrev() {
            return prev;
        }

        public void setPrev(BeltPoint prev) {
            this.prev = prev;
        }

        public int getDurability() {
            return durability;
        }

        public boolean isValid() {
            return this.durability > 0;
        }

        public boolean isEmpty() {
            return this.robot == null;
        }
    }

    static class Robot {

        BeltPoint beltPoint;

        public void move() {
            BeltPoint nextPoint = beltPoint.getNext();
            if (nextPoint.isValid() && nextPoint.isEmpty()) {
                this.beltPoint.popRobot();
                this.beltPoint = nextPoint;
                this.beltPoint.putRobot(this);
            }
        }
    }

}
