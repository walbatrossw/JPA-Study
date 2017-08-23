package com.doubles.jpastudy.transitive.cascade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CascadeMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch08");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // 비지니스 로직
            saveWithCascade(em);
            removeWithCascade(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void saveWithCascade(EntityManager em) {

        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();
        child1.setParent(parent);   // 연관관계 추가
        child2.setParent(parent);   // 연관관계 추가
        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        // 부모, 연관된 엔티티(자식엔티티) 저장
        em.persist(parent);
    }

    private static void removeWithCascade(EntityManager em) {
        Parent findParent = em.find(Parent.class, 1L);
        em.remove(findParent);
    }
}
