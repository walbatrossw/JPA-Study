package com.doubles.jpa05.twoway;

import com.doubles.jpa05.JPAHibernateTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

// 다대일 양방향 매핑 테스트
public class TwoWayTest extends JPAHibernateTest {

    // 양방향 그래프 탐색 테스트
    @Test
    public void testBiDirection() {
        // GIVEN
        Team team = manager.find(Team.class, "t01"); // 팀 조회
        // WHEN
        List<Member> members = team.getMembers(); // 회원 객체 그래프 탐색
        // THEN
        assertThat(members.get(0), Matchers.<Member>hasProperty("username", is("lee")));
        assertThat(members.get(1), Matchers.<Member>hasProperty("username", is("kim")));
    }

    // 양방향 연관관계 저장 테스트
    @Test
    public void testSave() {

        // GIVEN
        transaction.begin();
        Team team1 = new Team("t01", "team01"); // 팀01 저장
        manager.persist(team1);

        Member member1 = new Member("m01", "kim"); // 회원01 저장
        member1.setTeam(team1); // 연관관계 지정
        manager.persist(member1);

        Member member2 = new Member("m02", "lee"); // 회원02 저장
        member2.setTeam(team1); // 연관관계 지정
        manager.persist(member2);
        transaction.commit();

        // WHEN
        Team findTeam = manager.find(Team.class, team1.getId());
        Member findMember1 = manager.find(Member.class, member1.getId());
        Member findMember2 = manager.find(Member.class, member2.getId());

        // THEN
        assertEquals(team1, findTeam);
        assertEquals(member1, findMember1);
        assertEquals(member2, findMember2);
    }

    // 양방향 연관관계 저장 테스트2 : 주의 사항 - 주인이 아닌 곳에만 연관관계 설정
    @Test
    public void testSaveNonOwner() {

        // GIVEN
        transaction.begin();
        Member member1 = new Member("m01", "kim"); // 회원01 저장
        manager.persist(member1);
        Member member2 = new Member("m02", "lee"); // 회원02 저장
        manager.persist(member2);
        Team team1 = new Team("t01", "team01"); // 팀01 저장
        team1.getMembers().add(member1); // 연관관계에서 주인이 아닌 곳에만 연관관계를 설정
        team1.getMembers().add(member2); // 연관관계에서 주인이 아닌 곳에만 연관관계를 설정
        manager.persist(team1);
        transaction.commit();

        // WHEN
        Member findMember1 = manager.find(Member.class, member1.getId());
        Member findMember2 = manager.find(Member.class, member2.getId());

        // THEN
        assertNull(findMember1.getTeam());
        assertNull(findMember2.getTeam());
    }


    // 순수한 객체 연관관계 설정 : 단방향만 설정
    @Test
    public void testPureBiDirection() {

        // GIVEN
        transaction.begin();
        // 팀, 회원 객체 생성
        Team team1 = new Team("t01", "team01");
        Member member1 = new Member("m01", "kim");
        Member member2 = new Member("m02", "lee");
        // 연관 관계 설정 : 한쪽만 설정
        member1.setTeam(team1); // member1 --> team1
        member2.setTeam(team1); // member2 --> team1

        // WHEN
        List<Member> members = team1.getMembers();

        // THEN
        assertThat(members.size(), is(2));
    }

    // 순수한 객체 연관관계 설정 2 : 양방향 설정
    @Test
    public void testPureBiDirection2() {

        // GIVEN
        // 팀, 회원 객체 생성
        Team team1 = new Team("t01", "team01");
        Member member1 = new Member("m01", "kim");
        Member member2 = new Member("m02", "lee");
        // 연관 관계 설정 : 양쪽 다 설정
        member1.setTeam(team1); //member1 --> team1
        team1.getMembers().add(member1); //team1 --> member1
        member2.setTeam(team1); // member2 --> team1
        team1.getMembers().add(member2); //team1 --> member2

        // WHEN
        List<Member> members = team1.getMembers();

        // THEN
        assertThat(members.size(), is(2));
    }

    // JPA 양방향 설정
    @Test
    public void testORMBiDirection() {

        // GIVEN
        transaction.begin();
        // 팀 생성 및 저장
        Team team1 = new Team("t01", "team01");
        manager.persist(team1);
        // 회원 객체 생성 및 저장
        Member member1 = new Member("m01", "kim");
        Member member2 = new Member("m02", "lee");
        // 연관 관계 설정 : 양쪽 다 설정
        member1.setTeam(team1); //member1 --> team1
        team1.getMembers().add(member1); //team1 --> member1
        member2.setTeam(team1); // member2 --> team1
        team1.getMembers().add(member2); //team1 --> member2

        manager.persist(member1);
        manager.persist(member2);
        transaction.commit();

        // WHEN
        Team team = manager.find(Team.class, "t01");
        List<Member> members = team.getMembers();

        // THEN
        assertThat(members.size(), is(2));
    }

    // JPA 양방향 설정 : 리팩토링
    @Test
    public void testORMBiDirectionRefactoring() {

        // GIVEN
        transaction.begin();

        // 팀 생성 및 저장
        Team team1 = new Team("t01", "team01");
        manager.persist(team1);

        // 회원 객체 생성 및 저장
        Member member1 = new Member("m01", "kim");
        Member member2 = new Member("m02", "lee");

        // 연관 관계 설정 : 양쪽 다 설정
        member1.setTeam(team1); //member1 --> team1, team1 --> member1
        manager.persist(member1);

        member2.setTeam(team1); // member2 --> team1, team1 --> member2
        manager.persist(member2);

        transaction.commit();

        // WHEN
        Team team = manager.find(Team.class, "t01");
        List<Member> members = team.getMembers();

        // THEN
        assertThat(members.size(), is(2));
    }

    // JPA 양방향 설정 : 리팩토링
    public void testAfterRefactoring(EntityManager manager) {

        // 팀 생성 및 저장
        Team team1 = new Team("team01", "팀01");
        manager.persist(team1);

        // 회원 객체 생성 및 저장
        Member member1 = new Member("member01", "회원01");
        Member member2 = new Member("member02", "회원02");

        // 연관 관계 설정 : 양쪽 다 설정
        member1.setTeam(team1); //member1 --> team1, team1 --> member1
        manager.persist(member1);

        member2.setTeam(team1); // member2 --> team1, team1 --> member2
        manager.persist(member2);

        Team team2 = new Team("team02", "팀02");
        manager.persist(team2);

        member2.setTeam(team2); // member2 --> team1, team1 --> member2
        manager.persist(member2);

        Boolean isContain = team1.getMembers().contains(member2);
        System.out.println(isContain);

    }
}
