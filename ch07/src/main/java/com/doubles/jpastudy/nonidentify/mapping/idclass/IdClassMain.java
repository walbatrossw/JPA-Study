package com.doubles.jpastudy.nonidentify.mapping.idclass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class IdClassMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch07");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // 비지니스 로직
//            Parent parent = new Parent();
//            parent.setId1("myId1");
//            parent.setId2("myId2");
//            parent.setName("parentName");
//            em.persist(parent);
//            ParentId parentId = new ParentId("myId1", "myId2");
//            Parent parent2 = em.find(Parent.class, parentId);
//            System.out.println("parent2 = "+parent2);
            ParentId id1 = new ParentId();
            id1.setId1("myId1");
            id1.setId2("myId2");

            ParentId id2 = new ParentId();
            id2.setId1("myId1");
            id2.setId2("myId2");

            System.out.println("result ====> " + id1.equals(id2));

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
