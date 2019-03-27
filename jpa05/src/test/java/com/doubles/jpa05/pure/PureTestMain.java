package com.doubles.jpa05.pure;

// 순수한 객체 연관 관계 테스트
public class PureTestMain {
    public static void main(String[] args) {

        // 팀, 회원 객체 생성
        Team team1 = new Team("team01", "팀01");
        Member member1 = new Member("member01", "회원01");
        Member member2 = new Member("member02", "회원02");

        // 회원 객체에 팀 연관관계 설정
        member1.setTeam(team1);
        member2.setTeam(team1);

        // 회원으로부터 팀 객체 그래프 탐색
        Team findTeam = member1.getTeam();

        // 출력
        System.out.println(findTeam.getName());
    }
}
