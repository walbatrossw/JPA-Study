package com.doubles.jpa06.n_to_n.twoway;

import com.doubles.jpa06.JPAHibernateTest;

import javax.persistence.EntityManager;
import java.util.List;

public class NtoNTwoWayTest extends JPAHibernateTest {

    // 상품, 회원 저장 테스트
    public void saveTest() {

        // 상품 객체 생성, 저장
        Product productA = new Product("productA", "상품A");
        manager.persist(productA);

        // 회원 객체 생성, 연관 관계 설정, 저장
        Member member1 = new Member("member01", "회원01");
        member1.addProduct(productA);
        manager.persist(member1);

    }

    // 상품에서 회원 역방향 탐색 조회
    public void findInverse(EntityManager manager) {
        // 상품 조회
        Product product = manager.find(Product.class, "productA");
        // 상품에서 회원조회 : 객체 그래프 탐색
        List<Member> members = product.getMembers();
        // 출력
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
        }
    }

}
