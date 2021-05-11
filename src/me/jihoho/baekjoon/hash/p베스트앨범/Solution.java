package me.jihoho.baekjoon.hash.p베스트앨범;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Created by IntelliJ IDEA
 * User: hojun
 * Date: 2021-05-11 Time: 오후 7:06
 */
public class Solution {

    public static void main(String[] args) {
        String[] genres = {"A"};
        int[] plays = {5};
        int[] answer = solution(genres, plays);
        for (int tmp : answer) {
            System.out.print(tmp + ",");
        }
    }

    public static int[] solution(String[] genres, int[] plays) {
        HashMap<String, Genre> map = new HashMap<>();
        for (int i = 0; i < genres.length; i++) {
            if (map.containsKey(genres[i])) {
                Genre genre = map.get(genres[i]);
                genre.totalPlays += plays[i];
                genre.musicList.add(new Music(i, plays[i]));
            } else {
                Genre genre = new Genre();
                genre.totalPlays = plays[i];
                genre.musicList.add(new Music(i, plays[i]));
                map.put(genres[i], genre);
            }
        }
        List<Entry<String, Genre>> genreList = new ArrayList<>(map.entrySet());

        Collections.sort(genreList, new Comparator<Entry<String, Genre>>() {
            @Override
            public int compare(Entry<String, Genre> o1, Entry<String, Genre> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        List<Integer> answerList = new ArrayList<>();
        for (Entry<String, Genre> entry : genreList) {
            System.out.print("장르 : " + entry.getKey());
            List<Music> sortedMusics = entry.getValue().musicList;
            Collections.sort(sortedMusics);
            for (int i = 0; i < 2; i++) {
                if (i < sortedMusics.size()) {
                    answerList.add(sortedMusics.get(i).id);
                    System.out.print("id: " + sortedMusics.get(i).id + " ");
                }
            }
            System.out.println();
        }
        int[] answer = Arrays.stream(answerList.toArray(new Integer[answerList.size()]))
                .mapToInt(Integer::intValue).toArray();
        return answer;
    }

    static class Genre implements Comparable<Genre> {

        int totalPlays;
        List<Music> musicList = new ArrayList<>();

        @Override
        public int compareTo(Genre o) {
            return o.totalPlays - totalPlays;
        }
    }

    static class Music implements Comparable<Music> {

        int id;
        int plays;

        public Music(int id, int plays) {
            this.id = id;
            this.plays = plays;
        }

        @Override
        public int compareTo(Music o) {
            return o.plays - plays;
        }
    }
}
