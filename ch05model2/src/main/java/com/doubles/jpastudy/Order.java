package com.doubles.jpastudy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    // 주문회원
    @ManyToOne // N:1 관계 매핑
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 주문한 상품들
    @OneToMany(mappedBy = "order")  // 1:N 관계 매핑
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    // 주문날짜
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderdate;

    // 주문상태
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(Member member, Date orderdate, OrderStatus status) {
        this.member = member;
        this.orderdate = orderdate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    // 연관 관계 메서드
    public void setMember(Member memeber) {
        // 기존관계 제거
        if (this.member != null) {
            this.member.getOrders().remove(this);
        }
        this.member = memeber;
        memeber.getOrders().add(this);
    }

    // 연관 관계 메서드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


}