package com.doubles.jpa06;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAHibernateTest {

    protected static EntityManagerFactory factory;  // 엔티티 매니저 팩토리
    protected static EntityManager manager;         // 엔티티 매니저
    protected static EntityTransaction transaction; // 엔티티 트랜잭션


    @BeforeClass // 대상 클래스에서 테스트가 실행되기 전 딱 한번만 수행되는 메서드
    public static void init() throws Exception {
        factory = Persistence.createEntityManagerFactory("jpa06"); // 팩토리 생성
        manager = factory.createEntityManager(); // 매니저 생성
        transaction = manager.getTransaction(); // 트랜잭션 획득
    }

    @AfterClass // 대상 클래스에서 테스트가 실행된 후 딱 한번만 수행되는 메서드
    public static void tearDown() throws Exception {
        manager.clear(); // 매니저 초기화
        manager.close(); // 매니저 닫음
        factory.close(); // 팩토리 닫음
    }

}
