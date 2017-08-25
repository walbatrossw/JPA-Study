package com.doubles.jpastudy.embeded.basic;

import javax.persistence.*;

// 일반적인 회원엔티티
@Entity
public class GeneralMember {

    @Id
    @GeneratedValue
    private Long id;    // 아이디

    private String username;    // 회원이름

    // 근무 기간
    @Temporal(TemporalType.DATE) java.util.Date startDate;
    @Temporal(TemporalType.DATE) java.util.Date endDate;

    // 집주소
    private String city;
    private String street;
    private String zipcode;

}
