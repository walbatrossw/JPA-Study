package com.doubles.jpa04;

import javax.persistence.*;
import java.util.Date;

// 기본키 직접 할당 전략
//@Entity
@Table(name = "MEMBER_DIRECT")
public class MemberDirect {

    @Id // 기본키 매핑
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
