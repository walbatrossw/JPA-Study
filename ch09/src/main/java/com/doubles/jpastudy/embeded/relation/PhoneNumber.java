package com.doubles.jpastudy.embeded.relation;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class PhoneNumber {

    String areaCode;
    String localNumber;
    @ManyToOne PhoneServiceProvider provider;   // 엔티티 참조

    // ...
}
