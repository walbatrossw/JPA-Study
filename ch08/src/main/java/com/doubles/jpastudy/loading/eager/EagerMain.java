package com.doubles.jpastudy.loading.eager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EagerMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch08");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            testEager(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void testEager(EntityManager em) {
        Member findMember1 = em.find(Member.class, "member1"); // 회원을 조회하는 순간 팀도 함께 조회
        Team findMember1Team = findMember1.getTeam();
        // 회원과 팀 테이블을 조회하기 때문에 2번 조회할 것 같지만
        // 즉시 로딩을 최적화 하기 위해 조인쿼리를 사용함
        // 실행시 콘솔화면에 보여지는 쿼리
//        select
//        member0_.member_id as member_i1_0_0_,
//                member0_.team_id as team_id3_0_0_,
//        member0_.username as username2_0_0_,
//                team1_.team_id as team_id1_1_1_,
//        team1_.name as name2_1_1_
//                from
//        member member0_
//        left outer join
//        team team1_
//        on member0_.team_id = team1_.team_id
//        where
//        member0_.member_id =?
    }
}
