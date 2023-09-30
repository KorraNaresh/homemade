package com.homemadewonder.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homemadewonder.www.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByOrderByProductCostDesc();

    List<Product> findByOrderByDiscountPercentDesc();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.subcategory.id = :subcategoryId")
    int countProductsBySubcategoryId(long subcategoryId);
    
    
    
    
    List<Product> findBySubcategorySubId(long subcategoryId);
    
    
    
    
    
    

}
