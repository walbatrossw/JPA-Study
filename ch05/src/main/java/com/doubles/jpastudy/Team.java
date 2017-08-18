package com.doubles.jpastudy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;

    /* == 양방향 연관관계 매핑 == */
    @OneToMany(mappedBy = "team")   // 1:N 관계 매핑, mappedBy속성 : 반대쪽 매핑의 필드 이름값
    private List<Member> members = new ArrayList<Member>();

    // 기본 생성자
    public Team() {
    }

    // 생성자
    public Team(String id, String name) {
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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

}
