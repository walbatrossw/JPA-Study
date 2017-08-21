package com.doubles.jpastudy.manytoone.bothside;

import javax.persistence.*;

@Entity
public class Member {

    @Id                             // 기본키
    @GeneratedValue                 // 키 자동증가
    @Column(name = "MEMBER_ID")     // 칼럼명 MEMBER_ID
    private Long id;

    private String username;

    @ManyToOne                      // 다대일관계 매핑
    @JoinColumn(name = "TEAM_ID")   // 칼럼명 TEAM_ID
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    // 연관관계 편의메서드
    public void setTeam(Team team) {
        this.team = team;
        //  무한루프에 빠지지 않도록 체크
        if (!team.getMembers().contains(this)) {
            team.getMembers().add(this);
        }
    }
}
