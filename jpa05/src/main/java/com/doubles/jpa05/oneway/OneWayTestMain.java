package com.doubles.jpa05.oneway;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class OneWayTestMain {
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
        System.out.println("member1 = " + member1);
        System.out.println("member2 = " + member2);
        System.out.println("team1 = " + team1);


        // 2. 엔티티 조회

        // 객체 그래프 탐색
        Member findMember = em.find(Member.class, "member1");
        Team getTeam = findMember.getTeam();
        System.out.println("팀 이름 = " + getTeam.getName());

        // 객체지향 쿼리(JPQL) 사용
        String jpql = "select m from Member m join m.team t where t.name=:teamName";
        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();
        for (Member member : resultList) {
            System.out.println("[Query] member.username = " + member.getUsername());
        }

        // 3. 엔티티 수정
        Team newTeam = new Team("team2", "팀2");
        em.persist(newTeam);

        Member findMember2 = em.find(Member.class, "member2");
        findMember2.setTeam(newTeam);
        System.out.println("member1 team = " + findMember2.getTeam());


        // 4. 연관 관계 제거
        Member member01 = em.find(Member.class, "member1");
        member01.setTeam(null);
        System.out.println("relation removed member = " + member01);

        // 5. 연관된 엔티티 제거
        em.remove(team1);

    }
}
