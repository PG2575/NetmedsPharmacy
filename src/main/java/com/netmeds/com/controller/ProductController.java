package com.netmeds.com.controller;

import com.netmeds.com.dto.Productdto;
import com.netmeds.com.entity.Products;
import com.netmeds.com.repo.ProductCrudRepo;
import com.netmeds.com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/netmedsApp")
public class ProductController {





 @Autowired
 private ProductService productService;

 @Autowired
 private ProductCrudRepo productCrudRepo;










    @PostMapping("/addProducts")
    public Products  addProduct(@RequestBody Productdto productdto){
        Products p = productService.addProducts(productdto);
        return p;


    }

    @GetMapping("/getProducts")
    public List<Products> getProducts(@RequestBody Productdto productdto){
        return productCrudRepo.findAll();

    }

    @GetMapping(value = "/getpagination")
    Page<Products> productsPageable(Pageable pageable) {
        return productCrudRepo.findAll(pageable);


    }



}
