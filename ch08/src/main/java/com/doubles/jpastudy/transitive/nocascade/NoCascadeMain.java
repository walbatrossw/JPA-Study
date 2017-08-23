package com.doubles.jpastudy.transitive.nocascade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class NoCascadeMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch08");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // 비지니스 로직
            saveNoCascade(em);
            removeNoCascade(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void saveNoCascade(EntityManager em) {
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

        // JPA에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속 상태이어야 함
        // 부모 엔티티를 영속상태로 만들고 나머지 두 자식 엔티티도 영속상태로 만듬
        // 영속성 전이를 사용하면 부모만 영속상태로 만들면 연관된 자식 엔티티도 함께 영속상태로 만들 수 있음


    }

    private static void removeNoCascade(EntityManager em) {
        // 엔티티 제거
        Parent findParent = em.find(Parent.class, 1L);
        Child findChild1 = em.find(Child.class, 1L);
        Child findChild2 = em.find(Child.class, 2L);

        em.remove(findChild1);
        em.remove(findChild2);
        em.remove(findParent);
    }
}
