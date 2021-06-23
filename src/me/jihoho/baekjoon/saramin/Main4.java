package me.jihoho.baekjoon.saramin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-06-19 Time: 오후 3:24
 */
public class Main4 {

    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String text = scanner.nextLine().trim();
        int len = text.length();
        int i = 0;
        int startRef = -1;
        int endRef = -1;
        int refNum = 1;
        HashMap<String, Integer> map = new HashMap<>();
        while (i < len) {
            if (text.charAt(i) == '[') {
                startRef = i;
                endRef = findEndRef(text, i + 1);
                String refs = text.substring(startRef + 2, endRef - 2);
                StringTokenizer st = new StringTokenizer(refs, ", ");
                List<Integer> refNumbers = new ArrayList<>();
                while (st.hasMoreTokens()) {
                    String ref = st.nextToken();
                    if (!map.containsKey(ref)) {
                        text += "\n[" + refNum + "]" + " " + ref;
                        map.put(ref, refNum++);
                    }
                    refNumbers.add(map.get(ref));
                }
                Collections.sort(refNumbers);
                String replaceRefs = "";
                for (int j = 0; j < refNumbers.size(); j++) {
                    int curr = refNumbers.get(j);
                    if (j == 0) {
                        replaceRefs += "[ " + curr;
                    } else {
                        replaceRefs += ", " + curr;
                    }
                }
                replaceRefs+=" ]";
                String targetRefs = text.substring(startRef, endRef);
                text = text.replace(targetRefs, replaceRefs);
                len -= text.substring(startRef, endRef).length() - replaceRefs.length();
            }
            i++;
        }
        System.out.println(text);
    }

    private static int findEndRef(String text, int i) {
        while (i < text.length()) {
            if (text.charAt(i) == ']') {
                return i + 1;
            }
            i++;
        }
        return -1;
    }
}
