package com.doubles.jpastudy.nonidentify.mapping.embeddedid;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class EmbeddedMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch07");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // 비지니스 로직
            Parent parent = new Parent();
            ParentId parentId = new ParentId("myId1", "myId2");
            parent.setId(parentId);
            parent.setName("parentName");
            em.persist(parent);

            System.out.println("parent="+parent);

            Parent findParent = em.find(Parent.class, parentId);

            System.out.println("findParent="+findParent);

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
