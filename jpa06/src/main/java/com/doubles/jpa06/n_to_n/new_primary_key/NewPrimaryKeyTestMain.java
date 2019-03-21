package com.doubles.jpa06.n_to_n.new_primary_key;

import javax.persistence.*;

public class NewPrimaryKeyTestMain {
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
        Member member1 = new Member("member2", "회원2");
        manager.persist(member1);

        // 상품 생성, 저장
        Product productA = new Product("productB", "상품1");
        manager.persist(productA);

        // 주문 생성, 연관관계 설정, 저장
        Orders order = new Orders();
        order.setMember(member1);
        order.setProduct(productA);
        order.setOrderAmount(2);
        manager.persist(order);
    }

    private static void findTest(EntityManager manager) {

        Long orderId = 3L;  // 주문 식별자
        Orders order = manager.find(Orders.class, orderId); // 주문 식별자로 주문 조회

        Member member = order.getMember();  // 주문 엔티티로 회원 엔티티 그래프 탐색
        Product product = order.getProduct();   // 주문 엔티티로 상품 엔티티 그래프 탐색

        // 출력
        System.out.println("member = " + member.getUsername());
        System.out.println("product = " + product.getName());
        System.out.println("order amount = " + order.getOrderAmount());

    }
}
