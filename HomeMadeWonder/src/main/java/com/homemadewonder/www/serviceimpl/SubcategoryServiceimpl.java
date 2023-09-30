package com.homemadewonder.www.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemadewonder.www.entity.Categories;
import com.homemadewonder.www.entity.Subcategory;
import com.homemadewonder.www.excpetion.SubCategoryNotfound;
import com.homemadewonder.www.repository.SubcategoryRepository;
import com.homemadewonder.www.service.SubcategeoryService;

@Service
public class SubcategoryServiceimpl implements SubcategeoryService {

	@Autowired
	private SubcategoryRepository subcategoryRepository;

	@Override
	public List<Subcategory> getAllSubcategories() {
		return subcategoryRepository.findAll();
	}

	@Override
	public Subcategory getSubcategoryById(long subcategoryId) {
		return subcategoryRepository.findById(subcategoryId)
				.orElseThrow(() -> new SubCategoryNotfound("Subcategory not found with id: " + subcategoryId));
	}

	@Override
	public Subcategory createSubcategory(Subcategory subcategory, Categories categoryId) {
		subcategory.setParentCategory(categoryId);
		return subcategoryRepository.save(subcategory);
	}

	@Override
	public Subcategory updateSubcategory(long subcategoryId, Subcategory updatedSubcategory) {
		Subcategory subcategory = getSubcategoryById(subcategoryId);
		// Update subcategory properties as needed
		subcategory.setSubCategoryNames(updatedSubcategory.getSubCategoryNames());
		subcategory.setParentCategory(updatedSubcategory.getParentCategory());
		return subcategoryRepository.save(subcategory);
	}

	@Override
	public void deleteSubcategory(long subcategoryId) {
		subcategoryRepository.deleteById(subcategoryId);
	}

	@Override
	public int countSubcategoriesByCategoryId(long categoryId) {
		return subcategoryRepository.countSubcategoriesByCategoryId(categoryId);
	}

	@Override
	public long countall() {
	
		return subcategoryRepository.count();
	}
	
	
	
	
	@Override 
	 public List<Subcategory> getSubcategoriesByCategoryId(long categoryId) { 
	     return subcategoryRepository.findByParentCategoryId(categoryId); 
	 }
	
	
	
	
	

}
