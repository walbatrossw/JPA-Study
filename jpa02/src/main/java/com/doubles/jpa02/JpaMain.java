package com.doubles.jpa02;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        // 엔티티 매나저 팩토리 생성
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa02");

        // 엔티티 매나저 생성
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 트랜잭션 획득
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();  // 트랜잭션 시작
            logic(entityManager);       // 비지니스 로직 수행
            entityTransaction.commit(); // 트랜잭션 커밋
        } catch (Exception e) {
            entityTransaction.rollback();   // 트랜잭션 롤백
        } finally {
            entityManager.close();      // 엔티티 매니저 종료
        }
        entityManagerFactory.close();   // 엔티티 매니저 팩토리 종료
    }

    // 비지니스 로직
    private static void logic(EntityManager entityManager) {

        // 회원 객체 생성
        String id = "id01";
        Member member = new Member();
        member.setId(id);
        member.setUsername("doubles");
        member.setAge(20);

        // 등록
        entityManager.persist(member);

        // 수정
        member.setAge(25);

        // 조회 : 하나의 회원
        Member findMember = entityManager.find(Member.class, id);
        System.out.println("findMember : " + findMember.getUsername() + ", " + findMember.getAge());

        // 조회 : 목록
        List<Member> members = entityManager.createQuery("SELECT m FROM Member m", Member.class).getResultList();
        System.out.println("members.size = " + members.size());

        // 삭제
        entityManager.remove(member);
    }
}
