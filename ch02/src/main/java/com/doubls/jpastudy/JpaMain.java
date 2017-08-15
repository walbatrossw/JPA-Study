package com.doubls.jpastudy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        // 1. 엔티티 매니저 설정
        // 엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch02");
        // 엔티티 매너저 생성
        EntityManager em = emf.createEntityManager();
        // 트랜잭션 기능 획득
        EntityTransaction tx = em.getTransaction();

        // 2. 트랜잭션 관리
        try {
            tx.begin();     // 트랜잭션 시작
            logic(em);      // 비지니스 로직 실행
            tx.commit();    // 트랜잭션 commit
        } catch (Exception e) {
            tx.rollback();  // 트랜잭션 rollback
        } finally {
            em.close();     // 엔티티 매니저 종료
        }
        emf.close();        // 엔티티 매니저 팩토리 종료
    }

    // 3. 비지니스 로직
    public static void logic(EntityManager em) {
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("doubles");
        member.setAge(30);

        // 등록
        em.persist(member);

        // 수정
        member.setAge(31);

        // 한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        // 목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("member.size=" + members.size());

        // 삭제
        em.remove(member);
    }
}
