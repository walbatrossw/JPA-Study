package com.doubles.jpastudy.general;

public class GeneralMain {
    public static void main(String[] args) {

        Team team1 = new Team("team1", "팀1");
        Member member1 = new Member("member1", "홍길동");
        Member member2 = new Member("member2", "김철수");

        member1.setTeam(team1);
        member2.setTeam(team1);

        Team findMember1Team = member1.getTeam();
        Team findMember2Team = member2.getTeam();

        System.out.println("findMember1Team = " + findMember1Team);
        System.out.println("findMember2Team = " + findMember2Team);
    }
}
