package com.springboot.ceshi.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 测试商品
 */
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productid;
    private int type;
    private String title;
    private String name;


    public Product() {
    }

    public Product(int type, String title, String name) {
        this.type = type;
        this.title = title;
        this.name = name;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
