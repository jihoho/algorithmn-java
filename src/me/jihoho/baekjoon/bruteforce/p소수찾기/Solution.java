package me.jihoho.baekjoon.bruteforce.p소수찾기;

import java.util.HashSet;
import java.util.Set;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        String str = "011";
        System.out.println(solution.solution(str));

    }

    public int solution(String numbers) {
        int answer = 0;
        Set<Integer> decimal = new HashSet<>();
        for (int i = 1; i <= numbers.length(); i++) {
            permutation(numbers, 0, numbers.length(), i, decimal);
        }
        answer = decimal.size();
        return answer;
    }

    private void permutation(String numbers, int depth, int n, int r, Set<Integer> decimal) {
        if (depth == r) {
            if (checkDecimal(numbers, r)) {
                decimal.add(Integer.valueOf(numbers.substring(0, r)));
            }
        }
        for (int i = depth; i < n; i++) {
            numbers = swap(numbers, depth, i);
            permutation(numbers, depth + 1, n, r, decimal);
            numbers = swap(numbers, depth, i);
        }
    }

    private boolean checkDecimal(String numbers, int r) {
        int number = Integer.valueOf(numbers.substring(0, r));
        if (number == 1 || number == 0) {
            return false;
        }
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    private String swap(String numbers, int depth, int i) {
        char[] ch = numbers.toCharArray();
        char tmp = ch[depth];
        ch[depth] = ch[i];
        ch[i] = tmp;
        return String.valueOf(ch);
    }


}
