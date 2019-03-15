package com.doubles.jpa04;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Jpa04Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa04");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            logic(entityManager);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
        entityManagerFactory.close();
    }

    private static void logic(EntityManager entityManager) {

//        // 기본키 직접 할당 전략
//        for (int i = 0; i < 3; i++) {
//            MemberDirect member = new MemberDirect();
//            member.setUsername("doubles" + i);
//            member.setId("id00" + i);
//            entityManager.persist(member);
//            System.out.println("DIRECT member id = " + member.getId());
//        }


        // 기본키 IDENTITY 전략
//        for (int i = 0; i < 3; i++) {
//            MemberIdentity member2 = new MemberIdentity();
//            member2.setUsername("doubles" + i);
//            entityManager.persist(member2);
//            System.out.println("IDENTITY member id = " + member2.getId());
//        }


        // 기본키 SEQUENCE 전략
//        for (int i = 0; i < 3; i++) {
//            MemberSequence member3 = new MemberSequence();
//            member3.setUsername("doubles" + i);
//            entityManager.persist(member3);
//            System.out.println("SEQUENCE member id = " + member3.getId());
//        }

        // 기본키 TABLE 전략
//        for (int i = 0; i < 3; i++) {
//            MemberTable member4 = new MemberTable();
//            member4.setUsername("doubles" + i);
//            entityManager.persist(member4);
//            System.out.println("TABLE member id = " + member4.getId());
//        }

        // 기본키 AUTO 전략
        for (int i = 0; i < 3; i++) {
            MemberAuto member5 = new MemberAuto();
            member5.setUsername("doubles" + i);
            entityManager.persist(member5);
            System.out.println("AUTO member id = " + member5.getId());
        }

    }

}
