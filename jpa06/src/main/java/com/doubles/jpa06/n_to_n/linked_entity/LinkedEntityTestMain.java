package com.doubles.jpa06.n_to_n.linked_entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class LinkedEntityTestMain {
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
        // 회원 생성, 저장
        Member member1 = new Member("member1", "회원1");
        manager.persist(member1);

        // 상품 생성, 저장
        Product productA = new Product("productA", "상품1");
        manager.persist(productA);

        // 회원상품 생성, 연관관계 설정, 저장
        MemberProduct memberProduct = new MemberProduct();
        memberProduct.setMember(member1);
        memberProduct.setProduct(productA);
        memberProduct.setOrderAmount(2);
        manager.persist(memberProduct);
    }

    private static void findTest(EntityManager manager) {

        // 회원상품 엔티티를 조회하기 위한 식별자 객체 생성
        MemberProductId memberProductId = new MemberProductId();
        memberProductId.setMember("member1");   // 회원 식별자 설정
        memberProductId.setProduct("productA"); // 상품 식별자 설정

        // 식별자 객체를 통해 회원상품 엔티티 조회
        MemberProduct memberProduct = manager.find(MemberProduct.class, memberProductId);

        Member member = memberProduct.getMember(); // 회원상품 엔티티를 통해 회원 엔티티 그래프 탐색
        Product product = memberProduct.getProduct(); // 회원상품 엔티티를 통해 상품 엔티티 그래프 탐색

        // 출력
        System.out.println("member = " + member.getUsername());
        System.out.println("product = " + product.getName());
        System.out.println("order amount = " + memberProduct.getOrderAmount());
    }
}
