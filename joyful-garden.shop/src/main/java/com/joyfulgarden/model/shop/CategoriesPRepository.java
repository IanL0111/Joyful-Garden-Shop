package com.joyfulgarden.model.shop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriesPRepository extends JpaRepository<CategoriesP, Integer> {
	
	List<CategoriesP> findByParentCategoryID(CategoriesP parentCategoryID);
	
	 @Query(value = "SELECT TOP 5 * FROM productcategories", nativeQuery = true)
	 List <CategoriesP> findAllParent();
	 
}
