package com.springboot.ceshi.mapper;

import com.springboot.ceshi.model.Product;
import com.springboot.ceshi.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {
    public void inserProductInfo(Product product);

    public Product findProductByid(int id);
    public List<Product> findAllProduct();
}
