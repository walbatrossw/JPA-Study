package com.doubles.jpa08proxy.eager_loading;

import com.doubles.jpa08proxy.JPAHibernateTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EagerLoadingTest extends JPAHibernateTest {

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

    // 즉시 로딩 테스트
    @Test
    public void TestEagerLoading() {
        // 회원 조회
        Member member = manager.find(Member.class, "m01");
        // 팀 조회 : 그래프 탐색
        Team team = member.getTeam();

        assertEquals(team.getId(), "t01");
    }

}
