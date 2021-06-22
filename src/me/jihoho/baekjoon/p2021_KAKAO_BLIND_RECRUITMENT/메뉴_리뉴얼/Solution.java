package me.jihoho.baekjoon.p2021_KAKAO_BLIND_RECRUITMENT.메뉴_리뉴얼;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

    static HashMap<String, Integer> map = new HashMap<>();
    static int[] courseSize = new int[11];

    public static void main(String[] args) {
        Solution sol = new Solution();
//        String[] orders = {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"};
//        int[] course = {2, 3, 4};
        String[] orders = {"ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD"};
        int[] course = {2, 3, 5};

//        String[] orders = {"XYZ", "XWY", "WXA"};
//        int[] course = {2, 3, 4};

        String[] answer = sol.solution(orders, course);
        for (int i = 0; i < answer.length; i++) {
            System.out.print(answer[i] + ",");
        }
    }

    public String[] solution(String[] orders, int[] course) {
        for (int i = 0; i < orders.length; i++) {
            char[] charArr = orders[i].toCharArray();
            Arrays.sort(charArr);
            for (int j = 0; j < course.length; j++) {
                combination(charArr, charArr.length, course[j], 0, "");
            }
        }
        Set<String> menuSet = map.keySet().stream()
                .filter(s -> map.get(s) >= courseSize[s.length()] && map.get(s) >= 2)
                .collect(Collectors.toSet());
        String[] answer = new String[menuSet.size()];
        menuSet.toArray(answer);
        Arrays.sort(answer);
        return answer;
    }

    private static void combination(char[] charArr, int n, int r, int depth,
            String s) {
        if (r == 0) {
            map.put(s, map.getOrDefault(s, 0) + 1);
            courseSize[s.length()] = Math.max(courseSize[s.length()], map.get(s));
            return;
        }

        for (int i = depth; i < n; i++) {
            combination(charArr, n, r - 1, i + 1, s + charArr[i]);
        }
    }

}

