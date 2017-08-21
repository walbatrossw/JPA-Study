package com.doubles.jpastudy.manytoone.bothside;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id                         // 기본키
    @GeneratedValue             // 기본키 자동생성
    @Column(name = "TEAM_ID")   // 칼럼명 TEAM_ID
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")   // 일대다 관계 매핑
    private List<Member> members = new ArrayList<Member>();

    // 연관관계 편의메서드
    public void addMember(Member member) {
        this.members.add(member);
        if (member.getTeam() != this) { // 무한루프에 빠지 않도록 체크
            member.setTeam(this);
        }
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
