package com.doubles.jpa06.n_to_n.oneway;

import com.doubles.jpa06.JPAHibernateTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class NToNTest extends JPAHibernateTest {

    @Test
    public void testSave() {
        // GIVEN
        transaction.begin();
        Product productA = new Product("productA", "상품A"); // 상품 생성, 저장
        manager.persist(productA);

        Member member1 = new Member("m01", "kim");  // 회원 생성, 저장
        member1.getProducts().add(productA); // 연관관계 설정
        manager.persist(member1);
        transaction.commit();

        // WHEN
        Member findMember = manager.find(Member.class, member1.getId());
        List<Product> products = findMember.getProducts(); // 상품 그래프 탐색

        // THEN
        assertEquals(member1, findMember);
        assertEquals(productA.getId(), products.get(0).getId());
    }

    @Test
    public void testFind() {
        // GIVEN

        // WHEN
        Member member = manager.find(Member.class, "m01");
        List<Product> products = member.getProducts();

        // THEN
        assertEquals("m01", member.getId());
        assertEquals("productA", products.get(0).getId());
    }

}
