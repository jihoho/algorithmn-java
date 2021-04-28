package me.jihoho.baekjoon.recursion.b5568;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-04-27 Time: 오전 10:45
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] card = new int[n];
        for (int i = 0; i < n; i++) {
            card[i] = sc.nextInt();
        }

        Set<String> set = new HashSet<>();
        permutation(set, card,  n, k, 0);
        System.out.println(set.size());

    /*    for (String s : set) {
            System.out.println(s);
        }*/
    }

    private static void permutation(Set<String> set, int[] card, int n, int r,
            int depth) {
        if (depth == r) {
            addSet(set,card,r);
            return;
        }
        for (int i = 0; i < n; i++) {
            swap(card, depth, i);
            permutation(set, card, n, r, depth + 1);
            swap(card, depth, i);
        }
    }

    private static void addSet(Set<String> set, int[] card, int r) {
        String str = "";
        for (int i = 0; i < r; i++) {
            str+=String.valueOf(card[i]);
        }
        set.add(str);
    }


    private static void swap(int[] arr, int depth, int i) {
        int tmp = arr[depth];
        arr[depth] = arr[i];
        arr[i] = tmp;
    }
}
