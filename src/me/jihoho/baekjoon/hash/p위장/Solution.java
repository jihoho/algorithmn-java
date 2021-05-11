package me.jihoho.baekjoon.hash.p위장;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-05-11 Time: 오후 6:38
 */
public class Solution {

    public int solution(String[][] clothes) {
        int answer = 1;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < clothes.length; i++) {
            if (map.containsKey(clothes[i][1])) {
                map.put(clothes[i][1], map.get(clothes[i][1]) + 1);
            } else {
                map.put(clothes[i][1], 1);
            }
        }

        Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            answer *= map.get(key) + 1;
        }
        answer -= 1;
        return answer;
    }
}
