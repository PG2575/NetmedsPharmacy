package com.netmeds.com.service;

import com.netmeds.com.dto.Productdto;
import com.netmeds.com.entity.Products;
import com.netmeds.com.entity.Products_category;
import com.netmeds.com.repo.ProductCategoryrepo;
import com.netmeds.com.repo.ProductCrudRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductCrudRepo productCrudRepo;

    @Autowired
    private ProductCategoryrepo productCategoryrepo;


@Transactional
public Products addProducts (@NotNull Productdto productdto) {

    Products p = new Products();
    p.setName(productdto.getName());
    p.setPrice(productdto.getPrice());
    p.setExpiry(productdto.getExpiry());
    p.setApplication(productdto.getApplication());


    Products sp = productCrudRepo.save(p);


List <Products_category> al = new ArrayList();

    for(int i= 0;i<productdto.getCategoryIds().size();i++) {







        Products_category pc = new Products_category();

        pc.setCategoryId(productdto.getCategoryIds().get(i));
        pc.setProductsId(sp.getProductsId());

        al.add(pc);


    }

    productCategoryrepo.saveAll(al);



    return sp;



    }

}
