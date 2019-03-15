package com.doubles.jpa04;

import javax.persistence.*;
import java.util.Date;

// 기본키 IDENTITY 전략
//@Entity
@Table(name = "MEMBER_IDENTITY")
public class MemberIdentity {


    @Id // 기본키 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
