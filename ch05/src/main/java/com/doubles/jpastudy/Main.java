package com.doubles.jpastudy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        /* == 순수한 객체 연관관계 == */
        // 회원 인스턴스 생성
//        Member member1 = new Member("member01", "회원01");
//        Member member2 = new Member("member02", "회원02");
//
//        // 팀 인스턴스 생성
//        Team team1 = new Team("team01", "팀01");
//
//        member1.setTeam(team1);
//        member2.setTeam(team1);
//
//        // 참조를 사용해서 연관관계를 탐색 : 그래프 탐색
//        Team findMember1Team = member1.getTeam();
//        System.out.println(findMember1Team);
//
//        Team findMember2Team = member2.getTeam();
//        System.out.println(findMember2Team);
        /* == 순수한 객체 연관관계 == */

        /* == 연관관계 사용 == */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch05");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // 비지니스 로직
            testSave(em); // 저장
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    // 연관관계 사용 - 저장
    private static void testSave(EntityManager em) {
        // 팀01 저장
        Team team1 = new Team("team01", "팀01");
        System.out.println("team1 = " + team1);
        em.persist(team1);


        // 회원01 저장
        Member member1 = new Member("member01", "회원01");
        member1.setTeam(team1); // 연관관계 설정 member01 ---> team01
        System.out.println("member1 = " + member1);
        em.persist(member1);

        // 회원02 저장
        Member member2 = new Member("member02", "회원02");
        member2.setTeam(team1); // 연관관계 설정 member02 ---> team02
        System.out.println("member2 = " + member2);
        em.persist(member2);
    }

}
