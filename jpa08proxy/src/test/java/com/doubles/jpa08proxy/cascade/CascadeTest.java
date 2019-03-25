package com.doubles.jpa08proxy.cascade;

import com.doubles.jpa08proxy.JPAHibernateTest;
import org.junit.Test;

public class CascadeTest extends JPAHibernateTest {

    // 영속성 전이 저장 테스트
    @Test
    public void saveCascade() {

        transaction.begin();
        Parent parent = new Parent("parent01");
        Child child1 = new Child("child01");
        Child child2 = new Child("child02");
        child1.setParent(parent);
        child2.setParent(parent);

        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        manager.persist(parent);
        transaction.commit();
    }

    @Test
    public void removeCascade() {
        transaction.begin();
        Parent parent = manager.find(Parent.class, 1L);
        manager.remove(parent);
        transaction.commit();
    }

}
