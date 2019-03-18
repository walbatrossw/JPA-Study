package com.doubles.jpa05.pure;

public class PureTestMain {
    public static void main(String[] args) {
        Team team1 = new Team();
        team1.setId("team1");
        team1.setName("팀01");

        // 회원1 객체 생성
        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("회원01");

        // 회원2 객체 생성
        Member member2 = new Member();
        member2.setId("member2");
        member2.setUsername("회원02");

        // 회원 객체 연관 관계 추가
        member1.setTeam(team1);
        member2.setTeam(team1);

        // 회원 객체에서 팀 탐색 : 객체 그래프 탐색
        Team findTeam = member1.getTeam();
        System.out.println("find team : " + findTeam.getName());

    }
}
