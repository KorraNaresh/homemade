package com.homemadewonder.www.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homemadewonder.www.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

	@Query("SELECT COUNT(s) FROM Subcategory s WHERE s.parentCategory.categoryId = :categoryId")
	int countSubcategoriesByCategoryId(long categoryId);
	
	
	
	
	
	 @Query("SELECT s FROM Subcategory s WHERE s.parentCategory.categoryId = :categoryId") 
     List<Subcategory> findByParentCategoryId(@Param("categoryId") long categoryId); 

	
}
