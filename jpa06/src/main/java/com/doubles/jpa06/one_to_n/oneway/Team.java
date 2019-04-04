package com.doubles.jpa06.one_to_n.oneway;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 일대다 단방향
//@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany  // 일대다 매핑
    // 일대다에서 명시하지 않을 경우 조인테이블을 생성
    @JoinColumn(name = "TEAM_ID")   // MEMBER 테이블의 TEAM_ID 외래키를 관리
    private List<Member> members = new ArrayList<Member>();

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

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

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
