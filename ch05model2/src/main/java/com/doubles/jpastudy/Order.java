package com.doubles.jpastudy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memebrId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderdate;     // 주문날짜

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemebrId() {
        return memebrId;
    }

    public void setMemebrId(Long memebrId) {
        this.memebrId = memebrId;
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