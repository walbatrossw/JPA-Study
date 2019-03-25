package com.doubles.jpa08proxy.orphan;

import com.doubles.jpa08proxy.JPAHibernateTest;
import org.junit.Test;

import java.util.List;

public class OrphanTest extends JPAHibernateTest {

    @Test
    public void testRemove() {
        Parent parent = manager.find(Parent.class, 1L);
        transaction.begin();
        parent.getChildren().clear();
        transaction.commit();

    }
}
