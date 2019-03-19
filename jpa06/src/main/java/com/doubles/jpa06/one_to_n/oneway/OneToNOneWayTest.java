package com.doubles.jpa06.one_to_n.oneway;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OneToNOneWayTest {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa06");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            testSave(manager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
        }
        factory.close();
    }


    private static void testSave(EntityManager manager) {
        Member member1 = new Member("회원01");
        Member member2 = new Member("회원02");

        Team team1 = new Team("팀01");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        manager.persist(member1); // INSERT member1
        manager.persist(member2); // INSERT member2
        manager.persist(team1);   // INSERT team1, UPDATE member1.fk UPDATE member2.fk

    }
}
