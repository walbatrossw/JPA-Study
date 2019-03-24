package com.doubles.jpa08proxy.proxy;

import com.doubles.jpa08proxy.JPAHibernateTest;
import org.junit.Test;

import javax.persistence.EntityTransaction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ProxyTest extends JPAHibernateTest {

    // 팀, 회원 저장 테스트
    @Test
    public void testPersistTeamAndUser() {

        transaction.begin();

        // 팀 생성, 저장
        Team team = new Team("t01", "team01");
        manager.persist(team);

        // 회원 생성, 저장
        Member member1 = new Member("m01", "kim");
        member1.setTeam(team);
        manager.persist(member1);

        // 회원 생성, 저장
        Member member2 = new Member("m02", "park");
        member2.setTeam(team);
        manager.persist(member2);

        transaction.commit();

        // 회원 조회
        Member findMember1 = manager.find(Member.class, member1.getId());
        Member findMember2 = manager.find(Member.class, member2.getId());

        // 테스트
        assertEquals(findMember1, member1);
        assertEquals(findMember2, member2);

    }

    // 회원, 팀 정보 조회 테스트
    @Test
    public void testGetUserAndTeam() {
        Member member= manager.find(Member.class, "m01");
        Team team = member.getTeam();

        assertThat(member.getUsername(), is("kim"));
        assertThat(team.getName(), is("team01"));
    }

    // 회원 조회 테스트
    @Test
    public void testGetUser() {
        Member member= manager.find(Member.class, "m01");
        assertThat(member.getUsername(), is("kim"));
    }

    // 프록시 초기화 테스트
    @Test
    public void testProxyInit() {
        Member member = manager.getReference(Member.class, "m01");  // 식별자 보관
        assertThat(member.getUsername(), is("kim"));
    }

    // 프록시 확인 테스트
    @Test
    public void testIsLoadedProxy() {
        Member member = manager.getReference(Member.class, "m01");
        boolean isLoad = factory.getPersistenceUnitUtil().isLoaded(member);
        assertFalse(isLoad);

        member.getUsername(); // 강제 초기화

        boolean isLoad2 = factory.getPersistenceUnitUtil().isLoaded(member);
        assertTrue(isLoad2);
    }


}
