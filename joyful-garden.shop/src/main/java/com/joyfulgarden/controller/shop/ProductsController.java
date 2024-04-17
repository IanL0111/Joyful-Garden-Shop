package com.joyfulgarden.controller.shop;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.joyfulgarden.model.shop.CarItem;
import com.joyfulgarden.model.shop.CategoriesP;
import com.joyfulgarden.model.shop.Products;
import com.joyfulgarden.service.shop.ProductsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductsController {

	@Autowired
	private ProductsService ps;
	
	@GetMapping("/")
	public String index() {
		return "shop/Joyful_Garden_Shop";   
	}
	
	@GetMapping("/Management")
	public String management(Model model) {
	    List<CategoriesP> p = ps.findAllParent();
	    model.addAttribute("parents", p);
	    List<CategoriesP> categories = ps.findAllCategories();
	    model.addAttribute("categories", categories);
		return "shop/Management";
	}
	
	@PostMapping("/shop")
	public String processInsert(@RequestParam("productName") String pName, 
								@RequestParam("price") Integer price , @RequestParam("stock") Integer stock , 
								@RequestParam("category") CategoriesP c, @RequestPart("image") MultipartFile image)throws IOException  {
		byte[] imgContent = image.getBytes();
		String mimeType = image.getContentType();
		String Guess64 = Base64.getEncoder().encodeToString(imgContent);
		String base64String ="data:%s;base64,".formatted(mimeType) + Guess64;
		Products newp = new Products(pName,price,stock,c,base64String);
		ps.insert(newp);

		return "shop/Management";
	}
	
	@GetMapping("/PreservedFlower/{parentCategoryID}")
	public String processFindAll(@PathVariable("parentCategoryID") CategoriesP parentCategoryID ,@PathVariable("parentCategoryID") Integer p2 ,Model model,HttpSession session){
	     List<CategoriesP> p = ps.findAllParent();
		 model.addAttribute("parents", p); 
		 model.addAttribute("parentID",p2);
		 Map<Integer,CarItem> car = getCar(session);
		 model.addAttribute("car", car);
	     return "shop/PreservedFlower";
	}
	
	
	@GetMapping("/PreservedFlower/select/{parentCategoryID}")
	@ResponseBody
	public Page<Products> processFindAll(@PathVariable("parentCategoryID") Integer parentCategoryID,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "8") int size){
		Pageable pageable = PageRequest.of(page, size, Sort.by("productID").ascending());
		Page<Products> products = ps.findbyParentCategoryID(parentCategoryID,pageable);
	     return products;
	}
	
	@GetMapping("/Management/SelectAll")
	@ResponseBody
	public List<Products> processFindAllSelect(@RequestParam(defaultValue = "0") int page, 
	                                           @RequestParam(defaultValue = "8") int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("productID").descending());
	    Page<Products> productsPage = ps.findAllProducts(pageable);
	    List<Products> productsList = productsPage.getContent();
	    return productsList;
	}
	
	@GetMapping("/PreservedFlower/jellery/{CategoryID}")
	@ResponseBody
	public Page<Products> processFindAllbyjelly(@PathVariable("CategoryID") Integer CategoryID,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "8") int size){
		Pageable pageable = PageRequest.of(page, size, Sort.by("productID").ascending());
		Page<Products> products = ps.findAlljellery(CategoryID,pageable);
	     return products;
	}
	
	@GetMapping("/PreservedFlower/getSelect/{parentCategoryID}")
	@ResponseBody
	public List<CategoriesP> processFindSelectbyParentID(@PathVariable("parentCategoryID") CategoriesP parentCategoryID){
		List<CategoriesP> categories = ps.findCategoriesByParentCategoryID(parentCategoryID);
	     return categories;
	}
	
	@GetMapping("/Management/select/{Category}")
	@ResponseBody
	public List<Products> processFindSelect(@PathVariable("Category") CategoriesP Category){
		List<Products> products = ps.findAllByCategoryID(Category);
	     return products;
	}
	
	@GetMapping("/PreservedFlower/Search/{productName}")
	@ResponseBody
	public List<Products> processFindByProductNameContaining(@PathVariable("productName")String productName){
		List<Products> products = ps.findByProductNameContaining(productName);
	     return products;
	}
	
	@GetMapping("/PreservedFlower/Category/{Category}")
	@ResponseBody
	public List<Products> processFindAllByCategoryID(@PathVariable("Category") CategoriesP Category){
		List<Products> products = ps.findAllByCategoryID(Category);
	     return products;
	}
	
	
	
	@GetMapping("/jellery/{CategoryID}")
	public String processFindAll(@PathVariable("CategoryID") CategoriesP CategoryID ,@PathVariable("CategoryID") Integer cID,Model model){
//		List<Products> products = ps.findAllByCategoryID(CategoryID);
//	     model.addAttribute("products", products);
	     List<CategoriesP> p = ps.findAllParent();
		 model.addAttribute("parents", p); 
		 model.addAttribute("parentID",cID);
	     return "shop/PreservedFlower";
	}
	
	
	@PostMapping("/update/{productID}")
	@ResponseBody
	public String processUpdate(@PathVariable("productID") Integer pID,
								@RequestParam("productName") String pName, 
								@RequestParam("price") Integer price, 
								@RequestParam("stock") Integer stock, 
								@RequestParam("category") CategoriesP c,
								@RequestParam("image") MultipartFile image,
								HttpServletRequest request)throws IOException {
		
		Products p = new Products();
		byte[] imgContent = image.getBytes();
		String mimeType = image.getContentType();
		String Guess64 = Base64.getEncoder().encodeToString(imgContent);
		String base64String ="data:%s;base64,".formatted(mimeType) + Guess64;
		p.setImage(base64String);
		p.setProductID(pID);
		p.setProductName(pName);
		p.setPrice(price);
		p.setStock(stock);
		p.setCategory(c);
		
		ps.update(p);
		
		return "Update Success";
	}
	
	@PutMapping("/update/{productID}")
	@ResponseBody
	public String processUpdatePut(@PathVariable("productID") Integer pID,
								@RequestParam("productName") String pName, 
								@RequestParam("price") Integer price, 
								@RequestParam("stock") Integer stock, 
								@RequestParam("category") CategoriesP c,
								@RequestParam("image")	String image)throws IOException {
		
		Products p = new Products();
		p.setImage(image);
		p.setProductID(pID);
		p.setProductName(pName);
		p.setPrice(price);
		p.setStock(stock);
		p.setCategory(c);
		
		ps.update(p);
		
		return "Update Success";
	}
	
	
	
	
	
	@DeleteMapping("/shop/{productID}")
	@ResponseBody
	public String processDeleteById(@PathVariable("productID") Integer pID) {

	        ps.deleteById(pID);
	        return "Delete Success";

	}
	
	@GetMapping("/product/{productID}")
	@ResponseBody
	public Map<String, Object>  processById(@PathVariable("productID") Integer pID,Model model) {
		Map<String, Object> response = new HashMap<>();
	    
	    Products products = ps.findById(pID); 
	    List<CategoriesP> categories = ps.findAllCategories();
	    
	    response.put("products", products);
	    response.put("categories", categories);
	    
	    return response;
	}
	
	
	@GetMapping("/shop/{category}")
	@ResponseBody
	public List<Products> processFindAllByCategoryId(@PathVariable("category") CategoriesP category,Model model) {
		
//		model.addAttribute("category", ps.findAllByCategoryID(category)); 
		List<Products> p = ps.findAllByCategoryID(category);
		return p;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer,CarItem> getCar(HttpSession session){
		Map<Integer,CarItem> car = (Map<Integer, CarItem>) session.getAttribute("jgCar");
		if(car == null) {
			car = new HashMap<>();
			session.setAttribute("jgCar", car);
		}
		return car;
	}
	
	
	
}
