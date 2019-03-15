package com.doubles.jpa04;

import javax.persistence.*;

//@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",  // 식별자 생성기 이름
        sequenceName = "MEMBER_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,  // DDL 생성시에 사용, 시퀀스 DDL을 생성할 때 처음 시작하는 수
        allocationSize = 1) // 시퀀스 한번 호출에 증가하는 수
public class MemberSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "NAME")
    private String username;

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
}
