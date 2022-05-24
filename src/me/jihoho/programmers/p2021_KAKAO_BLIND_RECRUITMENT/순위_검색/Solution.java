package me.jihoho.programmers.p2021_KAKAO_BLIND_RECRUITMENT.순위_검색;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-07-01 Time: 오후 7:36
 */
public class Solution {

    HashMap<String, List<Integer>> map = new HashMap<>();

    public static void main(String[] args) {
        String[] info = {"java backend junior pizza 150", "python frontend senior chicken 210",
                "python frontend senior chicken 150", "cpp backend senior pizza 260",
                "java backend junior chicken 80", "python backend senior chicken 50"};
        String[] query = {"java and backend and junior and pizza 100",
                "python and frontend and senior and chicken 200",
                "cpp and - and senior and pizza 250", "- and backend and senior and - 150",
                "- and - and - and chicken 100", "- and - and - and - 150"};
        Solution sol = new Solution();
        sol.solution(info, query);
    }

    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];
        for (String infoStr:info) {
            String[] infoArr = infoStr.split(" ");
            int score = Integer.parseInt(infoArr[4]);
            dfs(0, "", infoArr, score);
        }
        for (String key : map.keySet()) {
            Collections.sort(map.get(key));
        }
        for (int i = 0; i < query.length; i++) {
            String key = "";
            String[] queryArr = query[i].split(" ");
            for (int j = 0; j < queryArr.length - 1; j++) {
                String field = queryArr[j];
                if (!field.equals("and")) {
                    key += field;
                }
            }

            int score = Integer.parseInt(queryArr[queryArr.length - 1]);
            List<Integer> scores = map.getOrDefault(key, new ArrayList<>());
            answer[i] = getNumberOfApplicantsByBinarySearch(scores, score);
        }
        return answer;
    }

    private int getNumberOfApplicantsByBinarySearch(List<Integer> scores, int score) {
        int mid;
        int left = 0;
        int right = scores.size() - 1;

        while (left <= right) {
            mid = (left + right) / 2;
            if (score > scores.get(mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return scores.size() - left;
    }

    private void dfs(int level, String key, String[] infoArr, int score) {
        if (level == 4) {
            map.computeIfAbsent(key, s -> new ArrayList<>()).add(score);
            return;
        }
        dfs(level + 1, key + infoArr[level], infoArr, score);
        dfs(level + 1, key + "-", infoArr, score);
    }


}
