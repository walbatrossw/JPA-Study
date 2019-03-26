package com.doubles.jpa09;

import com.doubles.jpa09.value_type_collection.Address;
import com.doubles.jpa09.value_type_collection.Member;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CollectionTypeTest extends JPAHibernateTest {

    // 값 타입 컬렉션 등록 테스트
    @Test
    public void testSave() {
        transaction.begin();
        Member member = new Member();

        member.setHomeAddress(new Address("SEOUL", "GANGNAM-GU", "06325"));

        member.getFavoriteFoods().add("pizza");
        member.getFavoriteFoods().add("pasta");
        member.getFavoriteFoods().add("hamburger");

        member.getAddressHistory().add(new Address("SEOUL", "SEOCHO-GU", "06801"));
        member.getAddressHistory().add(new Address("SEOUL", "YONGSAN-GU", "04333"));

        manager.persist(member);
        transaction.commit();

        Member findMember = manager.find(Member.class, member.getId());

        assertEquals(member, findMember);
    }

    // 값 타입 조회 테스트
    @Test
    public void testFind() {
        Member member = manager.find(Member.class, 12L);
        Address homeAddress = member.getHomeAddress();

        System.out.println(homeAddress.toString());

        Set<String> favoriteFoods = member.getFavoriteFoods();

        for (String favoriteFood : favoriteFoods) {
            System.out.println("favoriteFood = " + favoriteFood);
        }

        List<Address> addressHistory = member.getAddressHistory();

        Address address = addressHistory.get(0);
        System.out.println(address);
    }

    // 수정 테스트
    @Test
    public void testUpdate() {
        transaction.begin();

        // 임베디드 값 타입 수정
        Member member = manager.find(Member.class, 13L);
        member.setHomeAddress(new Address("NEWCITY", "NEWTOWN", "123456"));

        // 기본값 타입 수정
        Set<String> favoriteFoods = member.getFavoriteFoods();
        favoriteFoods.remove("pizza");
        favoriteFoods.add("ramen");

        // 임베디드 값 타입 컬렉션 수정
        List<Address> addressHistory = member.getAddressHistory();
        addressHistory.remove(new Address("SEOUL", "SEOCHO-GU", "06801"));
        addressHistory.add(new Address("SEOUL", "GANSEO-GU", "654321"));

        transaction.commit();
    }
}
