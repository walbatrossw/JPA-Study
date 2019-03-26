package com.doubles.jpa09;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAHibernateTest {

    protected static EntityManagerFactory factory;
    protected static EntityManager manager;
    protected static EntityTransaction transaction;

    @BeforeClass
    public static void init() throws Exception {
        factory = Persistence.createEntityManagerFactory("jpa09valuetype");
        manager = factory.createEntityManager();
        transaction = manager.getTransaction();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        manager.clear();
        manager.close();
        factory.close();
    }


}
