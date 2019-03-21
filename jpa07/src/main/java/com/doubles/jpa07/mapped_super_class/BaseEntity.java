package com.doubles.jpa07.mapped_super_class;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

// 부모 클래스는 테이블과 매핑 X
// 자식 클래스에게 매핑 정보만 제공하고 싶을 경우 사용
//@MappedSuperclass
public abstract class BaseEntity {

    // 식별자
    @Id
    @GeneratedValue
    private Long id;

    // 이름
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
