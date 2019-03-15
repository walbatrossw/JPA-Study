package com.doubles.jpa04;

import javax.persistence.*;

// 기본키 AUTO 전략
@Entity
@Table(name = "MEMBER_AUTO")
public class MemberAuto {


    @Id // 기본키 매핑
    @GeneratedValue(strategy = GenerationType.AUTO)
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
