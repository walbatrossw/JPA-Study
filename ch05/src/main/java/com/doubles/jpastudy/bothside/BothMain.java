package com.doubles.jpastudy.bothside;

import javax.persistence.*;

public class BothMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch05");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // 비지니스 로직
            bothTest(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void bothTest(EntityManager em) {

        /* ====== 저장 ====== */

        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "홍길동");
        member1.setTeam(team1); // 회원 ---> 팀참조
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "김철수");
        member2.setTeam(team1); // 회원 ---> 팀참조
        em.persist(member2);

        System.out.println("member1 = " + member1);
        System.out.println("member2 = " + member2);
        System.out.println("team1 = " + team1);

        /* ====== 저장 ====== */

        /* ====== 주인이 아닌 곳에 연관관계를 설정했을 경우?? ====== */

        // 회원3 저장
        Member member3 = new Member("member3", "김영희");
        em.persist(member3);

        // 회원4 저장
        Member member4 = new Member("member4", "이지훈");
        em.persist(member4);

        // 팀2 저장
        Team team2 = new Team("team2", "팀2");
        team2.getMembers().add(member3);
        team2.getMembers().add(member4);
        em.persist(team2);

        System.out.println("member3 = " + member3);
        System.out.println("member4 = " + member4);
        System.out.println("team2 = " + team2);

        /* ====== 주인이 아닌 곳에 연관관계를 설정했을 경우?? ====== */
        // 연관관계의 주인만이 외래키 값을 저장할 수 있다. TEAM_ID 값이 null 이 된다.

        /* ====== 양방향 관계 설정 ====== */

        Team team3 = new Team("team3" , "팀3");
        em.persist(team3);

        Member member5 = new Member("member5", "박찬호");
        member5.setTeam(team3); // 양방향 설정
        // team3.getMembers().add(member5); --> Member.java로 이동
        em.persist(member5);

        Member member6 = new Member("member6", "이승엽");
        member5.setTeam(team3); // 양방향 설정
        // team3.getMembers().add(member5); --> Member.java로 이동
        em.persist(member6);

        /* ====== 양방향 관계 설정 ====== */
    }
}
