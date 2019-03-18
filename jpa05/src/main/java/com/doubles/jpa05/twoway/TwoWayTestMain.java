package com.doubles.jpa05.twoway;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class TwoWayTestMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa05");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // 비지니스 로직
            test(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void test(EntityManager em) {

        // 1. 엔티티 등록

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

        System.out.println("member1 = " + member1.getUsername());
        System.out.println("member2 = " + member2.getUsername());
        System.out.println("team1 = " + team1.getName());

        Team findTeam = em.find(Team.class, "team1");
        List<Member> members = findTeam.getMembers();

        for (Member member : members) {
            System.out.println("member.username = " + member.getUsername());
        }
    }
}
