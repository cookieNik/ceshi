package com.springboot.ceshi.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by youfan on 2018/6/6 0006.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;//主键
    private String name;//姓名
    private int age ;//年龄
    private String address;//地址
    @Column(name = "create_date")
    private Date create_date;
    private BigDecimal decr;
    private float flor;
    private double dour;

    public User() {
    }

    public User(String name, int age, String address, Date create_date, BigDecimal decr, float flor, double dour) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.create_date = create_date;
        this.decr = decr;
        this.flor = flor;
        this.dour = dour;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public BigDecimal getDecr() {
        return decr;
    }

    public void setDecr(BigDecimal decr) {
        this.decr = decr;
    }

    public float getFlor() {
        return flor;
    }

    public void setFlor(float flor) {
        this.flor = flor;
    }

    public double getDour() {
        return dour;
    }

    public void setDour(double dour) {
        this.dour = dour;
    }
}
