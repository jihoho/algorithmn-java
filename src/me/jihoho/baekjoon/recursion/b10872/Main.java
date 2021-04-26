package me.jihoho.baekjoon.recursion.b10872;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-04-26 Time: 오전 10:32
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());
        System.out.println(pactorial(n));
    }

    public static int pactorial(int n){
        if(n==0){
            return 1;
        }
        return pactorial(n-1)*n;
    }
}
