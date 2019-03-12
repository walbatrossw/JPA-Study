package com.doubles.jpa02;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // 클래스와 테이블 매핑
@Table(name = "MEMBER") // 매핑할 테이블 정보 명시
public class Member {

    @Id // 기본키 매핑
    @Column(name = "ID") // 필드를 컬럼에 매핑
    private String id;

    @Column(name = "NAME")
    private String username;

    private Integer age; // 매핑 정보가 없을 경우 필드명이 컬럼명으로 매핑

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
