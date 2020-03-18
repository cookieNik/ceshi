package com.springboot.ceshi.service;

import com.springboot.ceshi.reporisty.ProductRepository;
import com.springboot.ceshi.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService{
    @Autowired
    private ProductRepository productRepository;
    public Product findById(int id){
        return productRepository.findOne(id);
    }
    public List<Product> findAllProduct(){
        return productRepository.findAllProduct();
    }
    public void deleteProduct(int id){
        productRepository.delete(id);
    }
    public void saveProduct(Product product){
        productRepository.save(product);
    }

}
