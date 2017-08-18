package com.doubles.jpastudy;

public class Main {
    public static void main(String[] args) {

        // 회원 인스턴스 생성
        Member member1 = new Member("member01", "회원01");
        Member member2 = new Member("member02", "회원02");

        // 팀 인스턴스 생성
        Team team1 = new Team("team01", "팀01");

        member1.setTeam(team1);
        member2.setTeam(team1);

        // 참조를 사용해서 연관관계를 탐색 : 그래프 탐색
        Team findMember1Team = member1.getTeam();
        System.out.println(findMember1Team);

        Team findMember2Team = member2.getTeam();
        System.out.println(findMember2Team);

    }
}
