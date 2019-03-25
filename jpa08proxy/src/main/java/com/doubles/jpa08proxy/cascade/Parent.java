package com.doubles.jpa08proxy.cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 영속성 전이 적용
    // 옵션 종류 : ALL - 모두 적용, PERSIST - 영속, MERGE - 병합, REMOVE - 삭제
    // cascade = {CascadeType.PERSIST, CascadeType.REMOVE} 속성을 같이 사용할 수 있음
    //@OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children = new ArrayList<Child>();

    public Parent() {
    }

    public Parent(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
