package com.doubles.jpa08proxy.no_cascade;

import com.doubles.jpa08proxy.JPAHibernateTest;
import org.junit.Test;

public class NoCascadeTest extends JPAHibernateTest {

    @Test
    public void saveNoCascade() {
        transaction.begin();
        Parent parent = new Parent("parent01");
        manager.persist(parent);

        Child child1 = new Child("child01");
        child1.setParent(parent);
        parent.getChildren().add(child1);
        manager.persist(child1);

        Child child2 = new Child("child02");
        child2.setParent(parent);
        parent.getChildren().add(child2);
        manager.persist(child2);
        transaction.commit();
    }

}
