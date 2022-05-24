package me.jihoho.programmers.p2021_KAKAO_BLIND_RECRUITMENT.신규_아이디_추천;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-21 Time: 오후 10:57
 */
public class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.solution("...!@BaT#*..y.abcdefghijklm"));
    }

    public String solution(String new_id) {
        String answer = new_id.toLowerCase();
        answer = answer.replaceAll("[^a-z0-9_.-]", "");
        answer = answer.replaceAll("[.]{2,}", ".");
        answer = answer.replaceAll("^[.]|[.]$", "");
        if (answer.equals("")) {
            answer = "a";
        }
        if (answer.length() >= 16) {
            answer = answer.substring(0, 15);
            answer = answer.replaceAll("^[.]|[.]$", "");
        }
        while (answer.length() <= 2) {
            answer += answer.charAt(answer.length() - 1);
        }
        return answer;
    }

}
