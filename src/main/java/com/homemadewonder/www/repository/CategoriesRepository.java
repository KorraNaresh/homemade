package com.homemadewonder.www.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homemadewonder.www.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

}
