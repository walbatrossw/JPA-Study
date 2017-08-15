package com.doubls.jpastudy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // 클래스를 테이블과 매핑한다고 JPA에게 알려준다.
@Table(name = "MEMBER") // 엔티티 클래스에 매핑할 테이블 정보를 알려준다.
public class Member {

    @Id // 엔티티 클래스의 필드를 테이블의 기본키에 매핑 , 식별자 필드라고도 한다.
    @Column(name = "ID") // 필드를 컬럼에 매핑
    private String id;

    @Column(name = "NAME") // 필드를 컬럼에 매핑
    private String username;

    // 매핑 정보가 없는 필드 : 어노테이션 생략하면 필드명을 사용해서 컬럼명으로 매핑해준다.
    // 데이터베이스가 대소문자를 구분하는 경우, 명시적으로 매핑을 해줘야한다.
    private Integer age;

    // GETTER, SETTER
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
