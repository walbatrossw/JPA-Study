package com.doubles.jpastudy.inheritance.mapping.join;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

//@Entity
@DiscriminatorValue("B") // 엔티티를 저장할 때 구분 칼럼에 입력할 값을 지정
@PrimaryKeyJoinColumn(name = "BOOK_ID") // ID를 재정의, 자식테이블의 기본키 컬럼명을 변경하고 싶을 경우
public class Book {

    private String author;  // 작가
    private String isbn;    // ISBN
}
