package me.jihoho.baekjoon.dp.b1003;

import java.io.*;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-03-17 Time:오후3:27
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Node[] cache=new Node[41];
        //  객체 배열 초기화
        for(int i=0;i<cache.length;i++){
            cache[i]=new Node();
        }
        int t;
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));

        t=Integer.parseInt(br.readLine());
        int[] arr=new int[t];
        for(int i=0;i<t;i++){
            arr[i]=Integer.parseInt(br.readLine());
        }

        for(int i=0;i<arr.length;i++){
            Node result=recursive(arr[i],cache);
            bw.write(result.zeroCnt+" "+ result.oneCnt+"\n");
        }

        br.close();
        bw.close();
    }

    public static Node recursive(int n,Node[] cache){
        if(cache[n].zeroCnt!=0||cache[n].oneCnt!=0){
            return cache[n];
        }
        if(n==0){
            cache[n].zeroCnt=1;
            return cache[n];
        }else if (n==1){
            cache[n].oneCnt=1;
            return cache[n];
        }else{
            cache[n].zeroCnt=recursive(n-1,cache).zeroCnt+recursive(n-2,cache).zeroCnt;
            cache[n].oneCnt=recursive(n-1,cache).oneCnt+recursive(n-2,cache).oneCnt;
            return cache[n];
        }
    }
}

class Node{
    int zeroCnt=0;
    int oneCnt=0;
}