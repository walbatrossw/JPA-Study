package com.doubles.jpa06.one_to_n.oneway;

import com.doubles.jpa06.JPAHibernateTest;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OneToNTest extends JPAHibernateTest {

    @Test
    public void testSave() {
        // GIVEN
        transaction.begin();

        Member member1 = new Member("kim");
        Member member2 = new Member("lee");

        Team team = new Team("team01");
        team.getMembers().add(member1);
        team.getMembers().add(member2);

        manager.persist(member1); // INSERT member1
        manager.persist(member2); // INSERT member2
        manager.persist(team);  // INSERT team, UPDATE member1.fk, UPDATE member2.fk

        transaction.commit();

        // WHEN
        Team findTeam = manager.find(Team.class, team.getId());
        List<Member> members = findTeam.getMembers(); // 회원 객체 그래프 탐색

        // THEN
        assertThat(members.get(0), Matchers.<Member>hasProperty("username", is("kim")));
        assertThat(members.get(1), Matchers.<Member>hasProperty("username", is("lee")));

    }



}
