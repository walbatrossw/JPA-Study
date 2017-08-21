package com.doubles.jpastudy.oneside;

import javax.persistence.*;

// 단방향 매핑
//@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    // 연관관계 매핑
    @ManyToOne  // 다대일 관계
    @JoinColumn(name = "TEAM_ID")   // 조인칼럼 설정, 외래키 설정
    private Team team;  // 필드를 사용하여 객체의 연관관계를 설정, 테이블의 연관관계는 MEMBER.TEAM_ID로 연관관계 설정


    public Member() {
    }

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }

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

    public Team getTeam() {
        return team;
    }

    // 연관관계 설정
    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", team=" + team +
                '}';
    }
}
