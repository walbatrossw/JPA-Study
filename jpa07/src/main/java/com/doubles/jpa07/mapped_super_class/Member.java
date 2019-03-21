package com.doubles.jpa07.mapped_super_class;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

//@Entity
@AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID"))   // 상속받은 칼럼 이름 재정의 - 하나만
public class Member extends BaseEntity {

    // 식별자, 이름 상속

    // 이메일
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
