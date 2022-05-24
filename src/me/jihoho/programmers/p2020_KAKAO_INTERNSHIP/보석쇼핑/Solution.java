package me.jihoho.programmers.p2020_KAKAO_INTERNSHIP.보석쇼핑;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    public static void main(String[] args) {
//        String[] gems = {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"};
//        String[] gems = {"A", "B", "B", "C", "B", "C", "A", "C", "B", "B", "A"};
        String[] gems = {"A", "B", "B", "B", "B", "B", "B", "C", "B", "A"};
        int[] answer = solution(gems);
        System.out.println(answer[0] + "," + answer[1]);
    }

    public static int[] solution(String[] gems) {
        Map<String, Integer> gemsMap = new HashMap<>();
        for (int i = 0; i < gems.length; i++) {
            int count = gemsMap.getOrDefault(gems[i], 0);
            gemsMap.put(gems[i], count + 1);
        }

        return getShortestInterval(gemsMap, gems);
    }

    public static int[] getShortestInterval(Map<String, Integer> map, String[] gems) {
        int endIdx = gems.length - 1;
        while (0 <= endIdx) {
            String gem = gems[endIdx];
            int count = map.get(gem);
            if (count == 1) {
                break;
            }
            map.put(gem, count - 1);
            endIdx--;
        }

        int startIdx = 0;
        while (startIdx <= endIdx) {
            String gem = gems[startIdx];
            int count = map.get(gem);
            if (count == 1) {
                break;
            }
            map.put(gem, count - 1);
            startIdx++;
        }

        int size = endIdx - startIdx;
        int[] result = new int[]{startIdx, endIdx};

        while (endIdx < gems.length&& startIdx <= endIdx) {
            String gem = gems[startIdx];
            int cnt = map.get(gem);
            if (cnt > 1) {
                startIdx++;
                map.put(gem, cnt - 1);
                if (size > endIdx - startIdx) {
                    size = endIdx - startIdx;
                    result[0] = startIdx;
                    result[1] = endIdx;
                }
                continue;
            }
            if(endIdx+1 >= gems.length){
                break;
            }
            gem = gems[++endIdx];
            map.put(gem, map.get(gem) + 1);
        }
        result[0]++;
        result[1]++;
        return result;
    }
}
