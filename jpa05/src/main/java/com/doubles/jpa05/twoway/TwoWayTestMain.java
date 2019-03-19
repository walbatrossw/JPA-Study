package com.doubles.jpa05.twoway;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

// 다대일 양방향 매핑 테스트
public class TwoWayTestMain {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa05");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            //biDirectionTest(manager);
            //testSave(manager);
            //testSaveNonOwner(manager);
            //testPureBiDirection();
            //testPureBiDirection2();
            //testORMBiDirection(manager);
            //testAfterRefactoring(manager);
            //testORMBiDirectionRefactoring(manager);
            testAfterRefactoring(manager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
        }
        factory.close();
    }

    // 양방향 그래프 탐색 테스트
    private static void biDirectionTest(EntityManager manager) {
        Team team = manager.find(Team.class, "team02");
        List<Member> members = team.getMembers();

        for (Member member : members) {
            System.out.println("member.userName = " + member.getUsername());
        }
    }

    // 양방향 연관관계 저장 테스트
    private static void testSave(EntityManager manager) {
        // 팀01 저장
        Team team1 = new Team("team01", "팀01");
        manager.persist(team1);

        // 회원01 저장
        Member member1 = new Member("member01", "회원01");
        member1.setTeam(team1);
        manager.persist(member1);

        // 회원02 저장
        Member member2 = new Member("member02", "회원02");
        member2.setTeam(team1);
        manager.persist(member2);

    }

    // 양방향 연관관계 저장 테스트2 : 주의 사항 - 주인이 아닌 곳에만 연관관계 설정
    private static void testSaveNonOwner(EntityManager manager) {
        // 회원01 저장
        Member member1 = new Member("member01", "회원01");
        manager.persist(member1);
        // 회원02 저장
        Member member2 = new Member("member02", "회원02");
        manager.persist(member2);

        // 팀01 저장
        Team team1 = new Team("team01", "팀01");
        // 연관관계에서 주인이 아닌 곳에만 연관관계를 설정
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);
        manager.persist(team1);
    }

    // 순수한 객체 연관관계 설정 : 단방향만 설정
    private static void testPureBiDirection() {

        // 팀, 회원 객체 생성
        Team team1 = new Team("team01", "팀01");
        Member member1 = new Member("member01", "회원01");
        Member member2 = new Member("member02", "회원02");

        // 연관 관계 설정 : 한쪽만 설정
        member1.setTeam(team1); // member1 --> team1
        member2.setTeam(team1); // member2 --> team1

        List<Member> members = team1.getMembers();
        System.out.println("members.size = " + members.size()); // size는 0이다.
    }

    // 순수한 객체 연관관계 설정 2 : 양방향 설정
    private static void testPureBiDirection2() {

        // 팀, 회원 객체 생성
        Team team1 = new Team("team01", "팀01");
        Member member1 = new Member("member01", "회원01");
        Member member2 = new Member("member02", "회원02");

        // 연관 관계 설정 : 양쪽 다 설정
        member1.setTeam(team1); //member1 --> team1
        team1.getMembers().add(member1); //team1 --> member1

        member2.setTeam(team1); // member2 --> team1
        team1.getMembers().add(member2); //team1 --> member2

        List<Member> members = team1.getMembers();
        System.out.println("members.size = " + members.size()); // size는 2이다.
    }

    // JPA 양방향 설정
    private static void testORMBiDirection(EntityManager manager) {

        // 팀 생성 및 저장
        Team team1 = new Team("team01", "팀01");
        manager.persist(team1);

        // 회원 객체 생성 및 저장
        Member member1 = new Member("member01", "회원01");
        Member member2 = new Member("member02", "회원02");

        // 연관 관계 설정 : 양쪽 다 설정
        member1.setTeam(team1); //member1 --> team1
        team1.getMembers().add(member1); //team1 --> member1
        manager.persist(member1);

        member2.setTeam(team1); // member2 --> team1
        team1.getMembers().add(member2); //team1 --> member2
        manager.persist(member2);

    }

    // JPA 양방향 설정 : 리팩토링
    private static void testORMBiDirectionRefactoring(EntityManager manager) {

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

    }

    // JPA 양방향 설정 : 리팩토링
    private static void testAfterRefactoring(EntityManager manager) {

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
