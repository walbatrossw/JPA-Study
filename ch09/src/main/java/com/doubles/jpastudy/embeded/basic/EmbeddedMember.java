package com.doubles.jpastudy.embeded.basic;

import javax.persistence.*;

@Entity
public class EmbeddedMember {

    @Id
    @GeneratedValue
    private Long id;                // 아이디
    private String username;        // 회원이름
    // 값 타입을 사용하는 곳에 표시
    @Embedded Period workPeriod;    // 근무 기간
    @Embedded Address homeAdress;   // 집주소

}
