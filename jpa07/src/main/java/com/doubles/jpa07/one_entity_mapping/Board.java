package com.doubles.jpa07.one_entity_mapping;

import javax.persistence.*;

// 엔티티 하나에 여러테이블 매핑
@Entity
@Table(name = "BOARD")  // BOARD 테이블 매핑
@SecondaryTable(name = "BOARD_DETAIL", // BOARD_DETAIL 테이블 추가 매핑
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOARD_DETAIL_ID")) // 매핑할 다른 테이블의 기본키 컬럼 속성
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    private String title;

    @Column(table = "BOARD_DETAIL") // BOARD_DETAIL 테이블의 칼럼 매핑
    private String content;

}
