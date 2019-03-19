package com.doubles.jpa06.n_to_one.twoway;

import javax.persistence.*;

// 다대일 양방향
//@Entity
public class Member {

    @Id // 식별자
    @GeneratedValue // 기본키 자동 생성
    @Column(name = "MEMBER_ID") // 컬럼명과 매핑
    private Long id;

    private String username;

    @ManyToOne  // 다대일
    @JoinColumn(name = "TEAM_ID") // 외래키 이름 매핑
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

    public void setTeam(Team team) {
        this.team = team;
        // 무한 루프에 빠지지 않도록 체크
        if (!team.getMembers().contains(this)) {
            team.getMembers().add(this);
        }
    }
}