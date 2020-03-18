package com.springboot.ceshi.reporisty;

import com.springboot.ceshi.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {
    @Query(value = "select * from product",nativeQuery=true)
    List<Product> findAllProduct();
}
