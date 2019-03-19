package com.doubles.jpa06.n_to_n.twoway;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

// 다대다 양방향
@Entity
public class Product {

    @Id
    private String id;

    private String name;

    @ManyToMany(mappedBy = "products") // 역방향 추가
    private List<Member> members = new ArrayList<Member>();

    public Product() {
    }

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
