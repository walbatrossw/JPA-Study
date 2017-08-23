package com.doubles.jpastudy.proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ProxyMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch08");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // 회원, 팀 저장
//            Member member1 = new Member("member1","홍길동");
//            Team team1 = new Team("team1","팀1");
//            em.persist(team1);
//            member1.setTeam(team1);
//            em.persist(member1);
//
//            String memberId = member1.getId();
//            printUserAndTeam(em, memberId);
//            printUser(em, memberId);
            proxy1(em);
            proxy2(em);
            isLoadedProxy(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    // 회원과 팀 정보를 출력하는 비지니스 로직
    public static void printUserAndTeam(EntityManager em, String memberId) {
        Member findMember = em.find(Member.class, memberId);
        Team findTeam = findMember.getTeam();
        System.out.println("회원이름 = " + findMember.getUsername());
        System.out.println("팀이름 = " + findTeam.getName());
    }

    // 회원정보만 출력하는 비지니스 로직
    public static void printUser(EntityManager em, String memberId) {
        Member onlyFindMember = em.find(Member.class, memberId);
        System.out.println("onlyFindMember = " + onlyFindMember);
        System.out.println("회원이름 = " + onlyFindMember.getUsername());
    }

    // 프록시
    public static void proxy1(EntityManager em) {
        Member member = em.getReference(Member.class, "member1");
        System.out.println(member);
        System.out.println("proxy membername = " + member.getUsername());
    }

    public static void proxy2(EntityManager em) {
        Member member = em.getReference(Member.class, "member1");
        System.out.println("proxy memberId = " + member.getId());
    }

    // 프록시 확인
    public static void isLoadedProxy(EntityManager em) {
        Member member = em.getReference(Member.class, "member1");
        boolean isLoad = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member);
        System.out.println("proxy isLoad = " + isLoad);
        System.out.println("proxy member = " + member.getClass().getName());
    }

}
