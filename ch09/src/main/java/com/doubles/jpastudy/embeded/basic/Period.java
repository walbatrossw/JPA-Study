package com.doubles.jpastudy.embeded.basic;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Embeddable
public class Period {

    @Temporal(TemporalType.DATE) java.util.Date startDate;
    @Temporal(TemporalType.DATE) java.util.Date endDate;

    public boolean isWork(Date date) {
        // 값타입을 위한 메서드 정의 가능...
        return false;
    }
}
