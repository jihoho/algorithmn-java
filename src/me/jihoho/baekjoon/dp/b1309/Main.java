package me.jihoho.baekjoon.dp.b1309;

import java.util.Scanner;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-03-17 Time: 오전 7:48
 */
public class Main {
    public static void main(String[] args) {
        int[][] cache;
        Scanner sc= new Scanner(System.in);
        int n=sc.nextInt();
        cache =new int[n+1][3];
        cache[1][0]=cache[1][1]=cache[1][2]=1;

        for(int i=2;i<cache.length;i++){
            cache[i][0]=(cache[i-1][0]+cache[i-1][1]+cache[i-1][2])%9901;
            cache[i][1]=(cache[i-1][0]+cache[i-1][2])%9901;
            cache[i][2]=(cache[i-1][0]+cache[i-1][2])%9901;
        }

        System.out.println((cache[n][0]+cache[n][1]+cache[n][2])%9901);

    }



}
