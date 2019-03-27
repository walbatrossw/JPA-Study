package com.doubles.jpa05.oneway;

import com.doubles.jpa05.JPAHibernateTest;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasProperty;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.*;

// 다대일 단방향 연관관계 테스트
public class OneWayTest extends JPAHibernateTest {

    // 저장 테스트
    @Test
    public void testSave() {

        // GIVEN
        transaction.begin(); // 트랜잭션 시작

        // 팀 객체 생성
        Team team1 = new Team("t01", "team01");
        // 팀 저장
        manager.persist(team1);

        // 회원 객체 생성 1
        Member member1 = new Member("m01", "kim");
        member1.setTeam(team1);     // 팀 연관 관계 설정
        manager.persist(member1);   // 회원 저장

        // 회원 객체 생성 2
        Member member2 = new Member("m02", "lee");
        member2.setTeam(team1);     // 팀 연관 관계 설정
        manager.persist(member2);   // 회원 저장

        transaction.commit(); // 트랜잭션 커밋

        // WHEN
        Team findTeam = manager.find(Team.class, team1.getId());
        Member findMember1 = manager.find(Member.class, member1.getId());
        Member findMember2 = manager.find(Member.class, member2.getId());

        // THEN
        assertEquals(team1, findTeam);
        assertEquals(member1, findMember1);
        assertEquals(member2, findMember2);

    }

    // 객체 그래프 탐색 조회 테스트
    @Test
    public void testFind() {
        // GIVEN

        // WHEN
        Member findMember = manager.find(Member.class, "m01");
        Team team = findMember.getTeam(); // 객체 그래프 탐색

        // THEN
        assertEquals("t01", team.getId());
        assertEquals("team01", team.getName());
    }

    // JPQL 쿼리 조회 테스트
    @Test
    public void testFindJPQL() {
        // GIVEN
        String jpql = "SELECT m FROM Member m JOIN m.team t WHERE t.name=:teamName"; // JPQL 쿼리

        // WHEN
        List<Member> results = manager.createQuery(jpql, Member.class) // 조회
                .setParameter("teamName", "team01")
                .getResultList();

        // THEN
        assertThat(results.get(0), Matchers.<Member>hasProperty("username", is("kim")));
        assertThat(results.get(1), Matchers.<Member>hasProperty("username", is("lee")));
    }

    // 수정 테스트
    @Test
    public void testUpdateRelation() {

        // GIVEN
        transaction.begin();
        Team newTeam = new Team("t02", "team02"); // 팀 객체 새로 생성
        manager.persist(newTeam);   // 새로 생성한 팀 저장

        // WHEN
        Member member1 = manager.find(Member.class, "m01"); // 회원1 조회
        member1.setTeam(newTeam);   // 새로운 팀 연관관계 설정
        transaction.commit();       // 수정 처리
        Member findMember = manager.find(Member.class, "m01"); // 회원1 다시 조회

        // THEN
        assertEquals(findMember.getTeam().getName(), "team02");

    }

    // 연관관계 제거 테스트
    @Test
    public void testDeleteRelation() {

        // GIVEN
        transaction.begin();
        Member member1 = manager.find(Member.class, "m01"); // 조회

        // WHEN
        member1.setTeam(null); // 연관 관계 제거
        transaction.commit();

        Member findMember = manager.find(Member.class, "m01"); // 회원1 다시 조회

        // THEN
        assertNull(findMember.getTeam());
    }

    // 연관된 엔티티 제거 테스트
    @Test
    public void testRemoveEntity() {

        // GIVEN
        transaction.begin();
        Team team1 = manager.find(Team.class, "t01"); // 팀1 조회
        Member member2 = manager.find(Member.class, "m02"); // 회원1 조회

        // WHEN
        member2.setTeam(null);  // 연관관계 제거
        manager.remove(team1); // 팀1 삭제
        transaction.commit();

        Team findTeam = manager.find(Team.class, "t01"); // 팀1 조회

        // THEN
        assertNull(findTeam);
    }

}
