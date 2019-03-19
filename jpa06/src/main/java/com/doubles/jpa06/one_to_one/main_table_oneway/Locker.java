package com.doubles.jpa06.one_to_one.main_table_oneway;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 일대일 단방향 주 테이블에 외래키
//@Entity
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
