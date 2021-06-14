package me.jihoho.baekjoon.greedy.b1339;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-14 Time: 오후 5:08
 */
public class Main {

    static int n;
    static String[] word;
    static HashMap<Character, Word> map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        word = new String[n];
        for (int i = 0; i < n; i++) {
            word[i] = br.readLine();
        }
        map = new HashMap<>();
        System.out.println(solution());
    }

    static class Word {

        char ch;
        int weight;
        int value;

        public Word(char ch, int weight) {
            this.ch = ch;
            this.weight = weight;
        }
    }

    private static int solution() {
        for (int i = 0; i < word.length; i++) {
            String str = word[i];
            for (int j = 0; j < str.length(); j++) {
                char ch = str.charAt(j);
                int weight = (int) Math.pow(10, str.length() - j);
                if (map.containsKey(ch)) {
                    Word word = map.get(ch);
                    word.weight += weight;
                    map.put(ch, word);
                } else {
                    map.put(ch, new Word(ch, weight));
                }
            }
        }

        return calcByWeight();
    }


    private static int calcByWeight() {
        int value = 9;
        HashMap<Character, Boolean> visited = new HashMap<>();

        while (value >= 0) {
            int maxWeight = -1;
            char maxChar = 0;
            for (char key : map.keySet()) {
                Word word = map.get(key);
                if (maxWeight < word.weight && !visited.containsKey(word.ch)) {
                    maxWeight = word.weight;
                    maxChar = word.ch;
                }
                map.put(key, word);
            }
            if (maxChar != 0) {
                visited.put(maxChar, true);
                Word word = map.get(maxChar);
                word.value = value;
                map.put(maxChar, word);
            }
            value--;
        }
        int sum = 0;
        for (int i = 0; i < word.length; i++) {
            String str = word[i];
            for (int j = 0; j < str.length(); j++) {
                char ch = str.charAt(j);

                sum += map.get(ch).value * Math.pow(10, str.length() - j - 1);
            }
        }
        return sum;
    }
}
