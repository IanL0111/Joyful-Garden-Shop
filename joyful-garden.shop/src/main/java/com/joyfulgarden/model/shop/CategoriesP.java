package com.joyfulgarden.model.shop;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="productcategories")
@Component
public class CategoriesP {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CATEGORYID")
	private Integer categoryID;
	@Column(name = "CATEGORYNAME")
	private String categoryName;
	@ManyToOne
    @JoinColumn(name = "PARENTCATEGORYID")
    private CategoriesP parentCategoryID;
	 
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Products> products;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "parentCategoryID", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CategoriesP> children;
	
	
	
	public Integer getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public CategoriesP getParentCategoryID() {
        return parentCategoryID;
    }

    public void setParentCategory(CategoriesP parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }
	
	
}
