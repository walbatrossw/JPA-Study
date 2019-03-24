package com.doubles.jpa07.super_key_relation.identifying.one_to_one;

import javax.persistence.*;

// 식별관계 : 일대일 매핑
//@Entity
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    @OneToOne(mappedBy = "board")
    private BoardDetail boardDetail;

}
