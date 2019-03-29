package com.doubles.jpa06.one_to_one.main_table_twoway;

import javax.persistence.*;

// 일대일 양방향 주 테이블에 외래키
//@Entity
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    // 일대일 매핑
    @OneToOne(mappedBy = "locker") // 연관관계 주인 지정
    private Member member;

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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
