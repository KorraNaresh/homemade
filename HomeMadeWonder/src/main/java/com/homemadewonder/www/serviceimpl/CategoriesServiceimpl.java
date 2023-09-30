package com.homemadewonder.www.serviceimpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.entity.Categories;
import com.homemadewonder.www.excpetion.CategoryNotFoundException;
import com.homemadewonder.www.repository.CategoriesRepository;
import com.homemadewonder.www.service.CategoriesService;

@Service
public class CategoriesServiceimpl implements CategoriesService{
	
	@Autowired
	private CategoriesRepository categoriesRepository;

	@Override
	public Categories saveCatagories(Categories categories) {

		return categoriesRepository.save(categories);
	}

	@Override
	public List<Categories> getAllCategories() {

		return categoriesRepository.findAll();
	}

	@Override
	public void deleteCategory(long categoryId) throws CategoryNotFoundException {
	
		categoriesRepository.deleteById(categoryId);
	}

	@Override
	public Categories getCategoryById(long categoryId) {
	    return categoriesRepository.findById(categoryId)
	            .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + categoryId + " not found"));
	}


	@Override
	public Categories updateCategory(long categoryId, String categoryNames, String categoryOffer, MultipartFile categoryImage) throws IOException {
	    Categories category = categoriesRepository.findById(categoryId)
	            .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + categoryId + " not found"));
	    
	    category.setCategoryNames(categoryNames);
	    category.setCategoryOffer(categoryOffer);
	    category.setCategoryImage(categoryImage.getBytes());
	    
	    return categoriesRepository.save(category);
	}

	@Override
	public long countall() {

		return categoriesRepository.count();
	}

	
	

}
