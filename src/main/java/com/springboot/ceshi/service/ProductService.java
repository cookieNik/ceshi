package com.springboot.ceshi.service;

import com.springboot.ceshi.dao.ProductDao;
import com.springboot.ceshi.model.Product;
import com.springboot.ceshi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductDao productDao;
    public void inserProductInfo(Product product){
        productDao.inserProductInfo(product);
    }
    public Product findProductByid(int id){
        Product product=productDao.findProductByid(id);
        return product;
    }
    public List<Product> findAllProduct(){
        return productDao.findAllProduct();
    }

}
