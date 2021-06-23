package me.jihoho.baekjoon.saramin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-19 Time: 오후 1:31
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String n = br.readLine();

        if (isHappyNumber(n)) {
            System.out.printf("%s is Happy Number.\n", n);
        } else {
            System.out.printf("%s is Unhappy Number.\n", n);
        }
    }

    private static boolean isHappyNumber(String n) {
        // int disit = (int) Math.pow(10, 9);
        String curr = n;
        HashSet<String> set=new HashSet<>();
        set.add(n);
        while (true) {
            int nextNumber = 0;
            for (int i = 0; i<curr.length() ; i++) {
                int number = curr.charAt(i) - 48;
                nextNumber += (int) Math.pow(number, 2);
            }
            curr = String.valueOf(nextNumber);
            if(set.contains(curr)){
                return false;
            }
            set.add(curr);
            if (nextNumber == 1) {
                return true;
            }
        }
    }
}
