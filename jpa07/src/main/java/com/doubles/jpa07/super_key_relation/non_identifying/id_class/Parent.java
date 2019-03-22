package com.doubles.jpa07.super_key_relation.non_identifying.id_class;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

// 복합키 비식별 관계 매핑
//@Entity
@IdClass(ParentId.class) // 관계형 데이터베이스에 가까운 방법, 식별자 클래스 지정
public class Parent {

    // 복합키 1
    @Id
    @Column(name = "PARENT_ID1")
    private String id1; // ParentId.id1과 연결

    // 복합키 2
    @Id
    @Column(name = "PARENT_ID2")
    private String id2; // ParentId.id2와 연결

    private String name;

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
