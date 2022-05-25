package me.jihoho.programmers.p2020_KAKAO_BLIND_RECRUITMENT.자물쇠와_열쇠;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        int[][] key = {{0, 0, 0}, {1, 0, 0}, {0, 1, 1}};
        int[][] lock = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};

        System.out.println(solution(key, lock));
    }

    public static boolean solution(int[][] key, int[][] lock) {
        Lock lockObj = new Lock(lock);
        Key keyObj = new Key(key);

        return lockObj.open(keyObj);
    }

    static class Lock {

        int[][] frame;
        int totalDentCnt;

        public Lock(int[][] frame) {
            this.frame = frame;
            for (int i = 0; i < frame.length; i++) {
                for (int j = 0; j < frame[i].length; j++) {
                    if (frame[i][j] == 0) {
                        totalDentCnt++;
                    }
                }
            }
        }

        public boolean open(Key keyObj) {
            List<Key> keysForAllDir = keyObj.getKeysForAllDir();
            for (Key key : keysForAllDir) {
                if (isSuitable(key)) {
                    return true;
                }
            }
            return false;
        }

        private boolean isSuitable(Key key) {
            for (int i = -key.frame.length + 1; i < this.frame.length; i++) {
                for (int j = -key.frame[0].length + 1; j < this.frame[0].length; j++) {
                    if (isSuitable(key, i, j)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean isSuitable(Key key, int row, int col) {
            int dentCnt = 0;
            int rowSize, colSize;
            int rockRowOffset, keyRowOffset, rockColOffset, keyColOffset;
            if (row >= 0) {
                rowSize = min(frame.length - row, key.frame.length);
                rockRowOffset = row;
                keyRowOffset = 0;
            } else {
                rowSize = min(frame.length, key.frame.length + row);
                rockRowOffset = 0;
                keyRowOffset = -row;
            }

            if (col >= 0) {
                colSize = min(frame[0].length - col, key.frame[0].length);
                rockColOffset = col;
                keyColOffset = 0;
            } else {
                colSize = min(frame[0].length, key.frame[0].length+col);
                rockColOffset = 0;
                keyColOffset = -col;
            }

            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < colSize; j++) {
                    if (frame[rockRowOffset + i][rockColOffset + j] == key.getPeak(keyRowOffset+i, keyColOffset+j)) {
                        return false;
                    } else if (frame[rockRowOffset + i][rockColOffset + j] == 0) {
                        dentCnt++;
                    }
                }
            }
            if (dentCnt < this.totalDentCnt) {
                return false;
            }

            return true;
        }
    }

    static class Key {

        int[][] frame;

        public Key(int[][] frame) {
            this.frame = frame;
        }

        public List<Key> getKeysForAllDir() {
            List<Key> keys = new ArrayList<>();
            int[][] newFrame = this.frame;
            for (int i = 0; i < 4; i++) {
                newFrame = rotateClockwise(newFrame);
                keys.add(new Key(newFrame));
            }

            return keys;
        }

        public int[][] rotateClockwise(int[][] target) {
            int[][] newFrame = new int[target[0].length][target.length];
            for (int i = 0; i < target.length; i++) {
                for (int j = 0; j < target[i].length; j++) {
                    newFrame[j][target.length - i - 1] = target[i][j];
                }
            }
            return newFrame;
        }

        public int getPeak(int i, int j) {
            return this.frame[i][j];
        }
    }
}
