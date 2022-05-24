package me.jihoho.programmers.p월간_코드_챌린지_시즌2.괄호_회전하기;

import java.util.Stack;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-07-02 Time: 오후 7:14
 */
public class Solution {

    public int solution(String s) {
        int answer = 0;
        for (int i = 0; i < s.length(); i++) {
            String lotationStr = getLotationString(s, i);
            if (isRightBrackets(lotationStr)) {
                answer++;
            }
        }
        return answer;
    }

    public String getLotationString(String s, int i) {
        return s.substring(i, s.length()) + s.substring(0, i);
    }

    public boolean isRightBrackets(String str) {
        Stack<Character> s = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (s.empty()) {
                s.push(ch);
            } else {
                char top = s.peek();
                switch (ch) {
                    case ')':
                        if (top == '(') {
                            s.pop();
                        }
                        break;
                    case ']':
                        if (top == '[') {
                            s.pop();
                        }
                        break;
                    case '}':
                        if (top == '{') {
                            s.pop();
                        }
                        break;
                    default:
                        s.push(ch);
                        break;
                }
            }
        }

        if (s.empty()) {
            return true;
        } else {
            return false;
        }

    }
}
