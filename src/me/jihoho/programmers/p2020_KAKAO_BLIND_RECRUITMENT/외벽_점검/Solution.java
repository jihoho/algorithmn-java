package me.jihoho.programmers.p2020_KAKAO_BLIND_RECRUITMENT.외벽_점검;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
//        int n = 12;
//        int[] weak = new int[]{1, 5, 6, 10};
//        int[] dist = new int[]{1, 2, 3, 4};
//        int[] weak = new int[]{1, 3, 4, 9};
//        int[] dist = new int[]{3,5,7};

//        int n = 200;
//        int[] weak = new int[]{0, 10, 50, 80, 120, 160};
//        int[] dist = new int[]{1, 10, 5, 40, 30};

//        int n=200;
//        int[] weak = new int[]{0, 100};
//        int[] dist = new int[]{1,1};
//        int n=200;
//        int[] weak = new int[]{1};
//        int[] dist = new int[]{6};
        int n=200;
        int[] weak = new int[]{0, 1, 198, 199};
        int[] dist = new int[]{4};
        System.out.println(solution(n, weak, dist));
    }

    public static int solution(int n, int[] weak, int[] dist) {
        List<Integer> weakList = Arrays.stream(weak).boxed().collect(Collectors.toList());
        List<Integer> distList = Arrays.stream(dist).boxed().sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        return getNumberOfChecker(n, weakList, distList,0,0);
    }

    public static int getNumberOfChecker(int n, List<Integer> weak, List<Integer> sortedDist,
            int idx, int acc) {
        if (weak.isEmpty()) {
            return acc;
        }

        if (idx >= sortedDist.size()) {
            return -1;
        }
        List<Integer> copiedWeak = new ArrayList<>(weak);

        int minNumberOfChecker = Integer.MAX_VALUE;
        for (int i = 0; i < copiedWeak.size(); i++) {
            int startPoint = copiedWeak.get(i);
            int endPoint = startPoint + sortedDist.get(idx);
            List<Integer> newWeak = checkWeakness(n, copiedWeak, startPoint, endPoint);
            if (newWeak.size() != copiedWeak.size()) {
                int numberOfChecker = getNumberOfChecker(n, newWeak, sortedDist, idx + 1, acc + 1);
                if(numberOfChecker!=-1){
                    minNumberOfChecker = min(minNumberOfChecker, numberOfChecker);
                }
            }
        }

        if(minNumberOfChecker>sortedDist.size()){
            return -1;
        }
        return minNumberOfChecker;
    }

    private static List<Integer> checkWeakness(int n, List<Integer> weak, int startPoint,
            int endPoint) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < weak.size(); i++) {
            int target = weak.get(i);
            if (endPoint>=n){
                if(target>endPoint-n&&target<startPoint){
                    result.add(target);
                }
            }else{
                if(target<startPoint||target>endPoint){
                    result.add(target);
                }
            }
        }
        return result;
    }

}
