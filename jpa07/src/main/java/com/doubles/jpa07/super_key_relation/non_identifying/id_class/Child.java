package com.doubles.jpa07.super_key_relation.non_identifying.id_class;

import javax.persistence.*;

@Entity
public class Child {

    @Id
    private String id;

    // 부모 테이블의 기본키 칼럼이 복합키인 경우
    // 자식 테이블의 외래키도 복합키이기 때문에 아래와 같이 @JoinColumn을 사용
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID1", referencedColumnName = "PARENT_ID1"), // referencedColumnName이 같은 경우 생략 가능
            @JoinColumn(name = "PARENT_ID2", referencedColumnName = "PARENT_ID2")
    })
    private Parent parent;

}
