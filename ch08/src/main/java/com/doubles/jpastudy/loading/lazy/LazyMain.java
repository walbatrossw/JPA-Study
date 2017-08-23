package com.doubles.jpastudy.loading.lazy;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class LazyMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch08");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            testLazy(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void testLazy(EntityManager em) {
        Member findMember1 = em.find(Member.class, "member1");
        Team findMember1Team = findMember1.getTeam();
        String teamName = findMember1Team.getName();
        System.out.println("team1 name = "+ teamName);
    }
}
