package me.jihoho.baekjoon.dfs.b14502;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-03-17 Time: 오후 8:06
 */
public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int k=Integer.parseInt(br.readLine());
        int[] ab= new int[2];
        int[] nextAB=new int[2];
        ab[0]=1;
        ab[1]=0;

        for(int i=0;i<k-1;i++){
            nextAB[1]=ab[0];
            nextAB[1]+=ab[1];
            nextAB[0]=ab[1];

            ab[0]=nextAB[0];
            ab[1]=nextAB[1];
        }

        System.out.println(ab[0]+" "+ab[1]);
    }
}
