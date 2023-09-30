package com.homemadewonder.www.service;

import java.util.List;

import com.homemadewonder.www.entity.Categories;
import com.homemadewonder.www.entity.Subcategory;

public interface SubcategeoryService {

	List<Subcategory> getAllSubcategories();

    Subcategory getSubcategoryById(long subcategoryId);

    Subcategory createSubcategory(Subcategory subcategory,Categories categoryId);

    Subcategory updateSubcategory(long subcategoryId, Subcategory subcategory);

    void deleteSubcategory(long subcategoryId);
    
    int countSubcategoriesByCategoryId(long categoryId);
    
    
    long countall();
    
    
    
    
    
    
    
    
    List<Subcategory> getSubcategoriesByCategoryId(long categoryId);
    
    
}
