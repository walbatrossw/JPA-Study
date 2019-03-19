package com.doubles.jpa05.oneway;

import javax.persistence.*;

// 다대일 단방향 매핑
//@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    // 연관 관계 매핑 : 다대일
    @ManyToOne
    @JoinColumn(name = "TEAM_ID") // 외래키 매핑할 때 사용
    private Team team;

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

    public void setTeam(Team team) {
        this.team = team;
    }
}
