package com.doubles.jpa03;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ExamMergeMain {

    // 엔티티 매니저 팩토리 생성
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa03");

    public static void main(String[] args) {

        // 회원 엔티티 생성 --> 준영속 상태
        Member member = createMember("id001", "doubles", 20);

        // 준영속 상태 --> 회원 정보 변경 : 변경이 이루어지지 않음
        member.setUsername("더블에스");

        // 회원 엔티티 병합
        mergeMember(member);
    }

    // 회원 엔티티 생성 --> 영속성 컨텍스트 종료
    private static Member createMember(String id, String username, int age) {

        // 엔티티 매니저 생성
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        // 트랜잭션 획득
        EntityTransaction transaction1 = entityManager1.getTransaction();

        transaction1.begin();   // 트랜잭션 시작

        // 회원 객체 생성
        Member member = new Member();
        member.setId(id);
        member.setUsername(username);
        member.setAge(age);

        // 회원 엔티티 등록
        entityManager1.persist(member);

        transaction1.commit();  // 트랜잭션 커밋

        // 영속성 컨텍스트 종료 ---> 회원 엔티티 준영속 상태로 변경됨
        entityManager1.close();

        return member;
    }

    // 병합
    private static void mergeMember(Member member) {

        // 엔티티 매니저 생성
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        // 트랜잭션 획득
        EntityTransaction transaction2 = entityManager2.getTransaction();

        transaction2.begin(); // 트랜잭션 시작

        // 회원 엔티티 병합 ---> 회원 엔티티 변경 감지 DB 반영
        Member mergeMember = entityManager2.merge(member);

        transaction2.commit(); // 트랜잭션 커밋

        System.out.println("member = " + member.getUsername()); // 준영속 상태

        System.out.println("mergeMember = " + mergeMember.getUsername()); // 영속 상태

        System.out.println("entityManager2 contains member = " + entityManager2.contains(member));

        System.out.println("entityManager2 contains mergeMember = " + entityManager2.contains(mergeMember));

        entityManager2.close();
    }

}
