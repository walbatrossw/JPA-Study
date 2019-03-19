package com.doubles.jpa06.n_to_one.twoway;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 다대일 양방향
//@Entity
public class Team {

    @Id // 식별자
    @GeneratedValue // 기본키 자동 생성
    @Column(name = "TEAM_ID") // 컬럼명 매핑
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();

    // 양방향 연관관계 설정
    public void addMember(Member member) {

        this.members.add(member);

        // 무한 루프에 빠지 않도록 체크
        if (member.getTeam() != this) {
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
