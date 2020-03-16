package com.springboot.ceshi.dao;


import com.springboot.ceshi.mapper.ProductMapper;
import com.springboot.ceshi.mapper.UserMappper;
import com.springboot.ceshi.model.Product;
import com.springboot.ceshi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by youfan on 2018/6/6 0006.
 */
@Component
public class ProductDao {

    @Autowired
    ProductMapper productMapper;

    public void inserProductInfo(Product product){
        productMapper.inserProductInfo(product);
    }

    public Product findProductByid(int id){
        Product productByid = productMapper.findProductByid(id);
        return productByid;
    }
    public List<Product> findAllProduct(){
        List<Product> lsit=productMapper.findAllProduct();
        return lsit;
    }





}
