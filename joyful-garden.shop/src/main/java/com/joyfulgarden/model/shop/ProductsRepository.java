package com.joyfulgarden.model.shop;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProductsRepository extends JpaRepository<Products, Integer> {
	
//	@Query(value = "select * from Users", nativeQuery = true)
//	public List<Users> findAll();
	
//	@Query(value = "select * from Users where userName = ?1", nativeQuery = true)
//	public Users findByName(String userName);
	@Query("SELECT p FROM Products p")
	Page<Products> findAllByOrderByProductIDDesc(Pageable pageable);
	 
	 List<Products> findByCategory(CategoriesP category);
	 
	 @Query(value = "SELECT * FROM products p WHERE p.CATEGORYID IN (SELECT c.CATEGORYID FROM productcategories c WHERE c.parentCategoryID = ?1)", nativeQuery = true)
	 Page<Products> findByParentCategoryID(Integer parentCategoryID,Pageable pageable);
	 
	 List<Products> findByProductNameContaining(String productName);
	 
	 @Query(value = "select * from products where categoryID = ?1", nativeQuery = true)
	 Page<Products> findAllJelleryByCategory(Integer category,Pageable pageable);
	 
}
