package com.doubles.jpa04;

import javax.persistence.*;
import java.util.Date;

// TABLE 전략
//@Entity
@TableGenerator(name = "MEMBER_SEQ_GENERATOR",  // 식별자 생성기 이름
        table = "MY_SEQUENCES", // 키 생성 테이블 명
        pkColumnValue = "MEMBER_SEQ", // 키로 사용할 값 이름
        allocationSize = 1) // 시퀀스 한번 호출에 증가하는 수
public class MemberTable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
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
