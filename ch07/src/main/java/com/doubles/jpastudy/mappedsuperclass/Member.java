package com.doubles.jpastudy.mappedsuperclass;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID")) // 부모로부터 상속받은 id속성의 칼럼명을 재정의, 둘이상일 경우 @AttributeOverrides를 사용
public class Member extends BaseEntity {

    // 아이디, 이름 (공톡속성) 상속

    // 고유 속성만 추가
    private String email;

}
