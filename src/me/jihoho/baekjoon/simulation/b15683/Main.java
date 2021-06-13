package me.jihoho.baekjoon.simulation.b15683;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int n, m;
    static short[][] map;
    static List<Camera> cameras;
    static boolean[][] visited;
    static int[] cameraDir = {0, 4, 2, 4, 4, 1};
    static int minBlindSpot = 64;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new short[n][m];
        cameras = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Short.valueOf(st.nextToken());
                if (map[i][j] > 0 && map[i][j] < 6) {
                    cameras.add(new Camera(i, j, map[i][j]));
                }
            }
        }
        dfsAll();
        System.out.println(minBlindSpot);
    }

    private static int simul() {
        short[][] copyMap = new short[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                copyMap[i][j] = map[i][j];
            }
        }
        for (Camera camera : cameras) {
            checkWatchPoint(copyMap, camera);
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (copyMap[i][j] == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private static void checkWatchPoint(short[][] copyMap, Camera camera) {
        int x = camera.x;
        int y = camera.y;
        switch (camera.num) {
            case 1:
                if (camera.dir == 0) {
//                    상
                    checkSubWatchPoint(copyMap, 0, x, y);
                } else if (camera.dir == 1) {
//                    하
                    checkSubWatchPoint(copyMap, 1, x, y);
                } else if (camera.dir == 2) {
//                    좌
                    checkSubWatchPoint(copyMap, 2, x, y);
                } else if (camera.dir == 3) {
//                    우
                    checkSubWatchPoint(copyMap, 3, x, y);
                }
                break;
            case 2:
                if (camera.dir == 0) {
//                    좌우
                    checkSubWatchPoint(copyMap, 2, x, y);
                    checkSubWatchPoint(copyMap, 3, x, y);
                } else if (camera.dir == 1) {
//                    상하
                    checkSubWatchPoint(copyMap, 0, x, y);
                    checkSubWatchPoint(copyMap, 1, x, y);
                }
                break;
            case 3:
                if (camera.dir == 0) {
//                    상우

                    checkSubWatchPoint(copyMap, 0, x, y);
                    checkSubWatchPoint(copyMap, 3, x, y);
                } else if (camera.dir == 1) {
//                    우하
                    checkSubWatchPoint(copyMap, 3, x, y);
                    checkSubWatchPoint(copyMap, 1, x, y);
                } else if (camera.dir == 2) {
//                    하좌
                    checkSubWatchPoint(copyMap, 1, x, y);
                    checkSubWatchPoint(copyMap, 2, x, y);
                } else if (camera.dir == 3) {
//                    좌상
                    checkSubWatchPoint(copyMap, 2, x, y);
                    checkSubWatchPoint(copyMap, 0, x, y);
                }
                break;
            case 4:
                if (camera.dir == 0) {
//                    좌상우
                    checkSubWatchPoint(copyMap, 2, x, y);
                    checkSubWatchPoint(copyMap, 0, x, y);
                    checkSubWatchPoint(copyMap, 3, x, y);
                } else if (camera.dir == 1) {
//                    상우하
                    checkSubWatchPoint(copyMap, 0, x, y);
                    checkSubWatchPoint(copyMap, 3, x, y);
                    checkSubWatchPoint(copyMap, 1, x, y);
                } else if (camera.dir == 2) {
//                    우하좌
                    checkSubWatchPoint(copyMap, 3, x, y);
                    checkSubWatchPoint(copyMap, 1, x, y);
                    checkSubWatchPoint(copyMap, 2, x, y);
                } else if (camera.dir == 3) {
//                    하좌상
                    checkSubWatchPoint(copyMap, 1, x, y);
                    checkSubWatchPoint(copyMap, 2, x, y);
                    checkSubWatchPoint(copyMap, 0, x, y);
                }
                break;

            case 5:
                checkSubWatchPoint(copyMap, 0, x, y);
                checkSubWatchPoint(copyMap, 1, x, y);
                checkSubWatchPoint(copyMap, 2, x, y);
                checkSubWatchPoint(copyMap, 3, x, y);
                break;
        }
    }

    // 0:상, 1:하, 2:좌, 3:우
    static void checkSubWatchPoint(short[][] map, int type, int x, int y) {
        switch (type) {
            case 0:
                for (int i = x; i >= 0; i--) {
                    if (map[i][y] == 6) {
                        break;
                    } else if (map[i][y] == 0) {
                        map[i][y] = -1;
                    }
                }
                break;
            case 1:
                for (int i = x; i < n; i++) {
                    if (map[i][y] == 6) {
                        break;
                    } else if (map[i][y] == 0) {
                        map[i][y] = -1;
                    }
                }
                break;
            case 2:
                for (int i = y; i >= 0; i--) {
                    if (map[x][i] == 6) {
                        break;
                    } else if (map[x][i] == 0) {
                        map[x][i] = -1;
                    }
                }
                break;
            case 3:
                for (int i = y; i < m; i++) {
                    if (map[x][i] == 6) {
                        break;
                    } else if (map[x][i] == 0) {
                        map[x][i] = -1;
                    }
                }
                break;
            default:
                break;
        }
    }

    private static void dfsAll() {
        if (cameras.size() > 0) {
            Camera firstCamera = cameras.get(0);
            for (int i = 0; i < cameraDir[firstCamera.num]; i++) {
                firstCamera.dir = i;
                dfs(0, i);

            }
        } else {
            int blindSpot = simul();
            minBlindSpot = minBlindSpot > blindSpot ? blindSpot : minBlindSpot;
        }

    }

    private static void dfs(int cameraIdx, int dir) {
        if (cameraIdx == cameras.size() - 1) {
            int blindSpot = simul();
            minBlindSpot = minBlindSpot > blindSpot ? blindSpot : minBlindSpot;
            return;
        }
        for (int i = cameraIdx + 1; i < cameras.size(); i++) {
            Camera camera = cameras.get(i);
            for (int j = 0; j < cameraDir[camera.num]; j++) {
                camera.dir = j;
                dfs(i, j);
            }
        }

    }

    static class Camera {

        int x;
        int y;
        int num;
        int dir;

        public Camera(int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }
    }
}
