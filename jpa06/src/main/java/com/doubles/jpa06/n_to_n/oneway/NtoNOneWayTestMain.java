package com.doubles.jpa06.n_to_n.oneway;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class NtoNOneWayTestMain {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa06");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            //saveTest(manager);
            findTest(manager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
        }
        factory.close();



    }

    private static void saveTest(EntityManager manager) {

        // 상품 객체 생성, 저장
        Product productA = new Product("productA", "상품A");
        manager.persist(productA);

        // 회원 객체 생성, 연관 관계 설정, 저장
        Member member1 = new Member("member01", "회원01");
        member1.getProducts().add(productA);
        manager.persist(member1);

    }

    private static void findTest(EntityManager manager) {

        Member member = manager.find(Member.class, "member01"); // 회원01 조회
        List<Product> products = member.getProducts(); // 객체 그래프 탐색
        // 상품 출력
        for (Product product : products) {
            System.out.println("product.name = " + product.getName());
        }
    }
}
