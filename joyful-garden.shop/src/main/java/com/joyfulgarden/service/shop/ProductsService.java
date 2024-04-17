package com.joyfulgarden.service.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyfulgarden.model.shop.CategoriesP;
import com.joyfulgarden.model.shop.CategoriesPRepository;
import com.joyfulgarden.model.shop.Products;
import com.joyfulgarden.model.shop.ProductsRepository;


@Service
@Transactional
public class ProductsService {
	
	@Autowired
	private ProductsRepository pr;
	@Autowired
	private CategoriesPRepository cr;
	
	public Products insert(Products p) {
		return pr.save(p);
	}
	
	public Products update(Products p) {
		return pr.save(p);
	}
	
	public void deleteById(Integer pID) {
		pr.deleteById(pID);
	}
	
	public Products findById(Integer pID) {
		Optional<Products> op = pr.findById(pID);
		if(op.isPresent())
			return op.get();
		else
			return null;
	}
	
	public List<Products> findAllProducts(){
		return pr.findAll();
	}
	
	public List<CategoriesP> findAllCategories(){
		return cr.findAll();
	}
	
	public Page<Products> findAllProducts(Pageable pageable){
		return pr.findAllByOrderByProductIDDesc(pageable);
	}
	
	public List<Products> findAllByCategoryID(CategoriesP category){
		return pr.findByCategory(category);
	}
	
	public Page<Products> findAlljellery(Integer category,Pageable pageable){
		return pr.findAllJelleryByCategory(category, pageable);
	}
	
	public List<CategoriesP> findCategoriesByParentCategoryID(CategoriesP parentCategoryID){
		return cr.findByParentCategoryID(parentCategoryID);
	}
	
	public Page<Products> findbyParentCategoryID(Integer parentCategoryID,Pageable pageable){
		return pr.findByParentCategoryID(parentCategoryID,pageable);
	}
	
	public List<Products> findByProductNameContaining(String productName){
		return pr.findByProductNameContaining(productName);
	}
	
	public List<CategoriesP> findAllParent(){
		return cr.findAllParent();
	}
	
}
