package com.springboot.ceshi.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_info")
public class Order_info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private int orderId;
    @Column(name = "order_name")
    private String order_name;
    @Column(name = "create_date")
    private Date create_date;

    public Order_info() {
    }

    public Order_info(String order_name, Date create_date) {
        this.order_name = order_name;
        this.create_date = create_date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
