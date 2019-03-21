package com.doubles.jpa06.n_to_n.new_primary_key;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

// 다대다 : 새로운 기본키 사용
// 회원 엔티티
@Entity
public class Member {

    // 회원 식별자
    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    // 회원이름
    private String username;

    // 일대다 매핑
    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<Orders>();

    public Member() {
    }

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }

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

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
