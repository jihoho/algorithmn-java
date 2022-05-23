package me.jihoho.programmers.p2019_KAKAO_BLIND_RECRUITMENT.오픈채팅방;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    public static void main(String[] args) {
        String[] record = {"Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234",
                "Enter uid1234 Prodo", "Change uid4567 Ryan"};
        String[] answer = solution(record);
        for (String ans : answer) {
            System.out.println(ans);
        }
    }

    public static String[] solution(String[] record) {
        Map<String, Member> memberMap = new HashMap<>();
        EntryRecord entryRecord = new EntryRecord();
        for (String r : record) {
            String[] tokens = r.split(" ");
            String action = tokens[0];
            if (action.equals("Change")) {
                Member member = memberMap.get(tokens[1]);
                member.name = tokens[2];
            } else {
                Member member;
                if (action.equals("Enter")) {
                    member = memberMap.getOrDefault(tokens[1], new Member(tokens[1], tokens[2]));
                    member.name = tokens[2];
                } else {
                    member = memberMap.get(tokens[1]);
                }

                memberMap.put(tokens[1], member);
                entryRecord.addRecord(new Record(member, action));
            }

        }
        return entryRecord.getRecordList();
    }
}

class EntryRecord {

    List<Record> list = new ArrayList<>();

    void addRecord(Record record) {
        list.add(record);
    }

    String[] getRecordList() {
        return list.stream().map(r -> r.toString()).toArray(String[]::new);
    }

}

class Record {

    Member member;
    ACTION action;

    public Record(Member member,
            String actionString) {
        this.member = member;
        if (actionString.equals("Enter")) {
            this.action = ACTION.ENTER;
        } else {
            this.action = ACTION.LEAVE;
        }
    }

    @Override
    public String toString() {
        String actionString;
        if (action == ACTION.ENTER) {
            actionString = "들어왔습니다.";
        } else {
            actionString = "나갔습니다.";
        }
        return member.name + "님이 " + actionString;
    }
}

enum ACTION {
    ENTER, LEAVE, CHANGE
}

class Member {

    String uid;
    String name;

    public Member(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }
}
