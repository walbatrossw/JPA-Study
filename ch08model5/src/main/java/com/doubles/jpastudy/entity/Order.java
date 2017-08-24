package com.doubles.jpastudy.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    // 주문회원
    // N:1 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩 설정
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 주문한 상품들
    // 1:N 관계 매핑
    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL) // 영속성 전이 설정
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    // 주문날짜
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderdate;

    // 주문상태
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 배송정보
    // 1:1 관계 매핑
    // 연관관계 주인
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 지연로딩 설정, 영속성 전이 설정
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;


    // 연관관계 메서드
    public void setMember(Member member) {
        // 기존관계 제거
        if (this.member != null) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // GETTER, SETTER
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
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

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}