package com.doubles.jpa06.one_to_n.twoway;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 일대다 양방향
//@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long ld;

    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<Member> members = new ArrayList<Member>();

    public Long getLd() {
        return ld;
    }

    public void setLd(Long ld) {
        this.ld = ld;
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
