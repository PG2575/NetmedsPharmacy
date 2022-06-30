package com.netmeds.com.repo;



import com.netmeds.com.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ProductCrudRepo extends JpaRepository<Products, Integer> {
}
