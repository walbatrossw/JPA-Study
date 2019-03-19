package com.doubles.jpa05.oneway;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

// 다대일 단방향 연관관계 테스트
public class OneWayTestMain {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa05");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            //testSave(manager);
            //testFind(manager);
            //queryLogicJoin(manager);
            //updateRelation(manager);
            //deleteRelation(manager);
            removeEntity(manager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            manager.close();
        }
        factory.close();
    }

    // 저장 테스트
    private static void testSave(EntityManager manager) {

        // 팀 객체 생성
        Team team1 = new Team("team01", "팀01");
        // 팀 저장
        manager.persist(team1);

        // 회원 객체 생성 1
        Member member1 = new Member("member01", "회원01");
        member1.setTeam(team1);     // 팀 연관 관계 설정
        manager.persist(member1);   // 회원 저장

        // 회원 객체 생성 2
        Member member2 = new Member("member02", "회원02");
        member2.setTeam(team1);     // 팀 연관 관계 설정
        manager.persist(member2);   // 회원 저장
    }

    // 조회 테스트
    private static void testFind(EntityManager manager) {
        // 객체 그래프 탐색
        Member findMember = manager.find(Member.class, "member01");
        Team team = findMember.getTeam();
        System.out.println("team name = " + team.getName());
    }

    // JPQL 쿼리 조회 테스트
    private static void queryLogicJoin(EntityManager manager) {

        // JPQL 쿼리
        String jpql = "SELECT m FROM Member m JOIN m.team t WHERE t.name=:teamName";

        // 조회
        List<Member> results = manager.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀01")
                .getResultList();

        // 출력
        for (Member member : results) {
            System.out.println("member.username = " + member.getUsername());
        }
    }

    // 수정 테스트
    private static void updateRelation(EntityManager manager) {

        // 팀 객체 새로 생성
        Team newTeam = new Team("team03", "팀03");
        manager.persist(newTeam);   // 새로 생성한 팀 저장

        // 회원1 조회
        Member member1 = manager.find(Member.class, "member01");
        member1.setTeam(newTeam);   // 새로운 팀 연관관계 설정

        System.out.println(member1.getTeam());  // 출력
    }

    // 연관관계 제거 테스트
    private static void deleteRelation(EntityManager manager) {
        Member member1 = manager.find(Member.class, "member01"); // 조회
        member1.setTeam(null); // 연관 관계 제거

        System.out.println(member1.getTeam());
    }

    // 연관된 엔티티 제거 테스트
    private static void removeEntity(EntityManager manager) {
        // 팀1 조회
        Team team1 = manager.find(Team.class, "team01");

        // 회원1 조회
        Member member2 = manager.find(Member.class, "member02");
        member2.setTeam(null);  // 연관관계 제거

        // 팀1 삭제
        manager.remove(team1);
    }
}
