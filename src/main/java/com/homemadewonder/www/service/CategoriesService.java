package com.homemadewonder.www.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.entity.Categories;
import com.homemadewonder.www.excpetion.CategoryNotFoundException;

public interface CategoriesService {

	Categories saveCatagories(Categories categories);

	List<Categories> getAllCategories();

	void deleteCategory(long categoryId) throws CategoryNotFoundException;

	Categories getCategoryById(long categoryId);

	Categories updateCategory(long categoryId, String categoryNames, String categoryOffer, MultipartFile categoryImage)throws IOException;

	
	long countall();
	
}
