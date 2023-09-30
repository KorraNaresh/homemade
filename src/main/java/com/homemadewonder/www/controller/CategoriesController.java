package com.homemadewonder.www.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homemadewonder.www.entity.Categories;
import com.homemadewonder.www.excpetion.CategoryNotFoundException;
import com.homemadewonder.www.service.CategoriesService;

@RestController
@RequestMapping("/api/Categories")
public class CategoriesController {
	
	Logger logger =LoggerFactory.getLogger(CategoriesController.class);
	
	@Autowired
	private CategoriesService categoriesService;
	
	@PostMapping("/save")
	public ResponseEntity<Categories> saveCategory(@RequestParam("categoryNames") String categoryNames,
	                                               @RequestParam(value="categoryOffer",required=false) String categoryOffer,
	                                               @RequestParam("categoryImage") MultipartFile categoryImageFile) {
	    try {
	        byte[] categoryImage = categoryImageFile.getBytes();

	        Categories categories = new Categories();
	        categories.setCategoryNames(categoryNames);
	        categories.setCategoryOffer(categoryOffer);
	        categories.setCategoryImage(categoryImage);

	        Categories savedCategory = categoriesService.saveCatagories(categories);
	        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
	    } catch (IOException e) {
	        // Handle the exception appropriately (e.g., return an error response)
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> categories = categoriesService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId) {
        try {
            categoriesService.deleteCategory(categoryId);
            return new ResponseEntity<>("Category with ID " + categoryId + " deleted successfully.", HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete category.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategory/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable long categoryId) {
        try {
            Categories category = categoriesService.getCategoryById(categoryId);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<Categories> updateCategory(@PathVariable long categoryId,
                                                     @RequestParam String categoryNames,
                                                     @RequestParam String categoryOffer,
                                                     @RequestParam MultipartFile categoryImage) throws IOException {
        Categories updatedCategory = categoriesService.updateCategory(categoryId, categoryNames, categoryOffer, categoryImage);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    
    @GetMapping("/countall")
    public long countAllCategories() {
        return categoriesService.countall();
    }

}
