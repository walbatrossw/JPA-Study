package com.doubles.jpastudy;

import javax.persistence.*;
import java.util.List;

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
            testSave(em);       // 저장
            queryLogicJoin(em); // 조회
            updateRelation(em); // 수정
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
        em.persist(team1);
        System.out.println("team1 = " + team1);


        // 회원01 저장
        Member member1 = new Member("member01", "회원01");
        member1.setTeam(team1); // 연관관계 설정 member01 ---> team01
        em.persist(member1);
        System.out.println("member1 = " + member1);

        // 회원02 저장
        Member member2 = new Member("member02", "회원02");
        member2.setTeam(team1); // 연관관계 설정 member02 ---> team02
        em.persist(member2);
        System.out.println("member2 = " + member2);

        // 객체 그래프 탐색
        Member member = em.find(Member.class, "member01");
        Team team = member.getTeam();
        System.out.println("팀 이름 = " + team.getName());
    }

    // 연관관계 사용 - 조회(객체지향 쿼리 사용)
    private static void queryLogicJoin(EntityManager em) {
        String jpql = "select m from Member m join m.team t where t.name=:teamName";
        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀01")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("[QUERY] member.username = " + member.getUsername());
        }
    }

    // 연관관계 사용 - 수정
    private static void updateRelation(EntityManager em) {
        // 새로운 팀 인스턴스 생성
        Team team2 = new Team("team02", "팀02");
        em.persist(team2);
        System.out.println("team02 = " + team2);

        // 회원1에 새로운 팀2 설정
        Member member1 = em.find(Member.class, "member01");
        member1.setTeam(team2);
        System.out.println("updated member01 = " + member1);
    }
}
