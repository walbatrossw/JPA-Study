package com.doubles.jpastudy.orphan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OrphanMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch08");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // 비지니스 로직
            orphanRemoveTest(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void orphanRemoveTest(EntityManager em) {
        // 부모 엔티티 저장
        Parent parent = new Parent();
        em.persist(parent);

        // 자식 엔티티 1 저장
        Child child1 = new Child();
        child1.setParent(parent);
        parent.getChildren().add(child1);
        em.persist(child1);

        // 자식 엔티티 2 저장
        Child child2 = new Child();
        child2.setParent(parent);
        parent.getChildren().add(child2);
        em.persist(child2);

        Parent findParent = em.find(Parent.class, 1L);
        findParent.getChildren().remove(child2);
    }


}
