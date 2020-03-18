package com.springboot.ceshi.control;

import com.springboot.ceshi.model.Product;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.ProductService;
import com.springboot.ceshi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductContrl {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/productInsert",method = RequestMethod.POST)
    public void userregister(@RequestBody Product product) {
        productService.saveProduct(product);
        return;
    }
    @RequestMapping(value = "/insertProduct",method = RequestMethod.GET)
    public void userregister() {
        Product product=new Product(1,"水果","苹果");
        Product product1=new Product(2,"水果","香蕉");
        productService.saveProduct(product);
        return;
    }
    @RequestMapping(value = "/findByProductid",method = RequestMethod.GET)
    public Product findByUserid(@RequestParam int  id){
        Product product =  productService.findById(id);
        return product;
    }
    @RequestMapping(value = "/findAllProduct",method = RequestMethod.GET)
    public List<Product> findAllProduct(){
        List<Product> allUser = productService.findAllProduct();
        return allUser;
    }
}
