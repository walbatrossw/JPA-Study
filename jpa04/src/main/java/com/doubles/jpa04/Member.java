package com.doubles.jpa04;

import javax.persistence.*;
import java.util.Date;

//@Entity // 클래스와 테이블 매핑
@Table(name = "MEMBER", // 매핑할 테이블 정보 명시
        uniqueConstraints = {@UniqueConstraint( // 유니크 제약 조건 추가
                name = "NAME_AGE_UNIQUE",
                columnNames = {"NAME", "AGE"})})
public class Member {

    @Id // 기본키 매핑
    @Column(name = "ID") // 필드를 컬럼에 매핑
    private String id;

    @Column(name = "NAME", nullable = false, length = 10) // 필수입력(null 허용X), 길이는 10자
    private String username;

    private Integer age; // 매핑 정보가 없을 경우 필드명이 컬럼명으로 매핑

    // 회원 타입 구분
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 날짜 타입 매핑
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    // 날짜 타입 매핑
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob // 길이 제한 없음
    private String description;

    // Getter, Setter
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

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
