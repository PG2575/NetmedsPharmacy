package com.netmeds.com.repo;

import com.netmeds.com.entity.Products_category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryrepo extends JpaRepository<Products_category,Integer> {
}
