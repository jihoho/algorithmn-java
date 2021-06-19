package me.jihoho.baekjoon.simulation.p순위_검색;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-19 Time: 오후 12:57
 */
public class Solution {

    public static void main(String[] args) {
        String[] info = {"java backend junior pizza 150", "python frontend senior chicken 210",
                "python frontend senior chicken 150", "cpp backend senior pizza 260",
                "java backend junior chicken 80", "python backend senior chicken 50"};
        String[] query = {"java and backend and junior and pizza 100",
                "python and frontend and senior and chicken 200",
                "cpp and - and senior and pizza 250", "- and backend and senior and - 150",
                "- and - and - and chicken 100", "- and - and - and - 150"};
        Solution sol = new Solution();
        int answer[] = sol.solution(info, query);
        System.out.println(answer);
    }

    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];
        List<Info> infoList = new ArrayList<>();
        for (int i = 0; i < info.length; i++) {
            String applicantInfo = info[i];
            StringTokenizer st = new StringTokenizer(applicantInfo);
            String appLan = st.nextToken();
            String appField = st.nextToken();
            String appCareer = st.nextToken();
            String appFood = st.nextToken();
            int appScore = Integer.parseInt(st.nextToken());
            infoList.add(new Info(appLan,appField,appCareer,appFood,appScore));
        }
        for (int i = 0; i < query.length; i++) {
            String q = query[i];
            StringTokenizer st = new StringTokenizer(q);
            String lan = st.nextToken();
            st.nextToken();
            String field = st.nextToken();
            st.nextToken();
            String career = st.nextToken();
            st.nextToken();
            String food = st.nextToken();
            int score = Integer.parseInt(st.nextToken());
            int count = 0;
            for (int j = 0; j < infoList.size(); j++) {
                Info info1=infoList.get(j);
                if ((lan.equals("-") || lan.equals(info1.appLan)) && (field.equals("-") || field
                        .equals(info1.appField))
                        && (career.equals("-") || career.equals(info1.appCareer)) && (food.equals("-")
                        || food.equals(info1.appFood)) && info1.appScore >= score) {
                    count++;
                }

            }
            answer[i] = count;
        }
        return answer;
    }

    class Info {

        String appLan;
        String appField;
        String appCareer;
        String appFood;
        int appScore;

        public Info(String appLan, String appField, String appCareer, String appFood,
                int appScore) {
            this.appLan = appLan;
            this.appField = appField;
            this.appCareer = appCareer;
            this.appFood = appFood;
            this.appScore = appScore;
        }
    }
}
