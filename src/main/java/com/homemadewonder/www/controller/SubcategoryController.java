package com.homemadewonder.www.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homemadewonder.www.entity.Categories;
import com.homemadewonder.www.entity.Subcategory;
import com.homemadewonder.www.service.SubcategeoryService;

@RestController
@RequestMapping("/api/subcategroy")
public class SubcategoryController {

	@Autowired
	private SubcategeoryService subcategoryService;

	@GetMapping("/getallsubcategories")
	public List<Subcategory> getAllSubcategories() {
		return subcategoryService.getAllSubcategories();
	}

	@GetMapping("/{subcategoryId}")
	public ResponseEntity<Subcategory> getSubcategoryById(@PathVariable long subcategoryId) {
		Subcategory subcategory = subcategoryService.getSubcategoryById(subcategoryId);
		return ResponseEntity.ok(subcategory);
	}

	@PostMapping("/save/{categoryId}")
	public ResponseEntity<Subcategory> createSubcategory(@RequestParam("subCategoryNames") String subCategoryNames,
			@PathVariable Categories categoryId) {
		Subcategory subcategory = new Subcategory();
		subcategory.setSubCategoryNames(subCategoryNames);

		Subcategory createdSubcategory = subcategoryService.createSubcategory(subcategory, categoryId);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSubcategory);
	}

	@PutMapping("/{subcategoryId}")
	public ResponseEntity<Subcategory> updateSubcategory(@PathVariable long subcategoryId,
			@RequestBody Subcategory subcategory) {
		Subcategory updatedSubcategory = subcategoryService.updateSubcategory(subcategoryId, subcategory);
		return ResponseEntity.ok(updatedSubcategory);
	}

	@DeleteMapping("/{subcategoryId}")
	public ResponseEntity<Void> deleteSubcategory(@PathVariable long subcategoryId) {
		subcategoryService.deleteSubcategory(subcategoryId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/count/{categoryId}")
	public ResponseEntity<Integer> countSubcategoriesByCategoryId(@PathVariable long categoryId) {
		int subcategoryCount = subcategoryService.countSubcategoriesByCategoryId(categoryId);
		return ResponseEntity.ok(subcategoryCount);
	}

	@GetMapping("/countall")
	public long countAllSubcategories() {
		return subcategoryService.countall();
	}

	@GetMapping("/getByCategory/{categoryId}")
	public ResponseEntity<List<Subcategory>> getSubcategoriesByCategory(@PathVariable long categoryId) {
		List<Subcategory> subcategories = subcategoryService.getSubcategoriesByCategoryId(categoryId);
		if (subcategories.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(subcategories);
	}

}
