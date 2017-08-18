package com.doubles.jpastudy;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String name;

    /* 객체관계 매핑 추가사항 */
    // 연관관계 매핑
    @ManyToOne  // N:1 매핑관계
    @JoinColumn(name = "TEAM_ID")   // 외래키 매핑, name속성(매핑할 외래키 이름지정)
    private Team team; // 팀의 참조를 보관

    // 기본생성자
    public Member() {
    }

    // 생성자
    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", team=" + team +
                '}';
    }
}
