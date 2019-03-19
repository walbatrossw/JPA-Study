package com.doubles.jpa06.n_to_one.oneway;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 다대일 단방향
//@Entity
public class Team {

    @Id // 식별자
    @GeneratedValue // 기본키 자동 생성
    @Column(name = "TEAM_ID") // 컬럼명 매핑
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
