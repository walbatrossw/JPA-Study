package com.doubles.jpastudy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch04");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    public static void logic(EntityManager em) {
        // H2 DB : 기본키 Auto 전략
        MemberAuto member = new MemberAuto();
        member.setUsername("더블에스");
        em.persist(member);
        System.out.println("AUTO member.id = " + member.getId());

        // H2 DB : 기본키 Table 전략
//        MemberForTable member = new MemberForTable();
//        member.setUsername("더블에스");
//        em.persist(member);
//        System.out.println("TABLE member.id = " + member.getId());

        // H2 DB : 기본키 SEQUENCE 전략
//        MemberForSequence member = new MemberForSequence();
//        member.setUsername("더블에스");
//        em.persist(member);
//        System.out.println("SEQUENCE member.id = " + member.getId());

        // Mysql : 기본키 IDENTITY 전략
//        MemberForIdentity member = new MemberForIdentity();
//        member.setUsername("더블에스");
//        em.persist(member);
//        System.out.println("IDENTITY member.id = " + member.getId());

        // H2 DB : 기본키 직접할당 전략
//        String id = "id001";
//        Member member = new Member();
//        member.setId(id);
//        member.setUsername("더블에스");
//        member.setAge(30);
//
//        // 등록
//        em.persist(member);
//        System.out.println("member.id=" + member.getId());
//        // 수정
//        member.setAge(31);
//
//        // 한건 조회
//        Member findMember = em.find(Member.class, id);
//        System.out.println("findMember = " + findMember.getUsername() + "age = " + findMember.getAge());
//
//        // 목록 조회
//        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
//        System.out.println("members.size = " + members.size());
//
//        // 삭제
//        em.remove(member);
    }
}
