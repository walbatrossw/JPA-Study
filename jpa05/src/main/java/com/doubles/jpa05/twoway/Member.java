package com.doubles.jpa05.twoway;

import javax.persistence.*;

// 다대일 양방향 매핑
@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    // 다대일 매핑
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")   // 외래키 설정
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

    // 연관관계 편의 메서드
    public void setTeam(Team team) {

        // 기존의 관계 제거
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }

        // 양방향 연관관계 설정
        this.team = team;
        team.getMembers().add(this);
    }
}
