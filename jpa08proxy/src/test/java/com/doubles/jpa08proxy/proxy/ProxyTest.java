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

        // 검증 테스트
        assertEquals(findMember1, member1);
        assertEquals(findMember2, member2);

    }

    // 회원, 팀 정보 조회 테스트
    @Test
    public void testGetUserAndTeam() {
        // 회원 조회
        Member member= manager.find(Member.class, "m01");

        // 팀 조회 : 그래프 탐색
        Team team = member.getTeam();

        // 검증 테스트
        assertThat(member.getUsername(), is("kim"));
        assertThat(team.getName(), is("team01"));
    }

    // 회원 조회 테스트
    @Test
    public void testGetUser() {
        // 회원 조회
        Member member= manager.find(Member.class, "m01");

        // 검증 테스트
        assertThat(member.getUsername(), is("kim"));
    }

    // 프록시 초기화 테스트
    @Test
    public void testProxyInit() {
        // 식별자 보관, 프록시 객체만 보관
        Member member = manager.getReference(Member.class, "m01");
        String userName = member.getUsername(); // 초기화 시도

        // 검증 테스트
        assertThat(userName, is("kim"));
    }

    // 프록시 확인 테스트
    @Test
    public void testIsLoadedProxy() {
        Member member = manager.getReference(Member.class, "m01");
        boolean isLoad = factory.getPersistenceUnitUtil().isLoaded(member); // 초기화 여부 확인
        assertFalse(isLoad); // 초기화 되지 않은 경우 false

        member.getUsername(); // 강제 초기화

        boolean isLoad2 = factory.getPersistenceUnitUtil().isLoaded(member);
        assertTrue(isLoad2); // 초기화 된 경우 true
    }


}
