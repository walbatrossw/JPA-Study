package com.doubles.jpa07.super_key_relation.identifying.one_to_one;

import javax.persistence.*;

// 식별관계 : 일대일 매핑
//@Entity
public class BoardDetail {

    @Id
    private Long boardId;

    @MapsId // BoardDetail.boardId 매핑
    @OneToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private String content;

}
