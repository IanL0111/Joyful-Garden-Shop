package com.joyfulgarden.controller.shop;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyfulgarden.model.shop.CarItem;
import com.joyfulgarden.model.shop.Products;
import com.joyfulgarden.service.shop.ProductsService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ShoppingCar")
public class ShoppingCarController {
	
	@Autowired
	private ProductsService ps;
	
	@GetMapping("/view")
	public String showCar(HttpSession session,Model model) {
		Map<Integer,CarItem> car = getCar(session);
		 model.addAttribute("car", car);
		 return "shop/ShoppingCar";
	}
	
	@GetMapping("/getCar")
	@ResponseBody
	public Map<Integer, CarItem> showCar(HttpSession session) {
		Map<Integer,CarItem> car = getCar(session);
		 return car;
	}
	
	@GetMapping("/clear")
	@ResponseBody
    public String clearCar(HttpSession session) {
        session.removeAttribute("jgCar");
        return "已清空";
    }
	
	@PostMapping("/add/{productID}/{quantity}")
	@ResponseBody
	public String addCar(@PathVariable("productID")Integer productID,@PathVariable("quantity")Integer quantity,HttpSession session) {
		Map<Integer,CarItem> car = getCar(session);
		String pName = ps.findById(productID).getProductName();
		 if (car.containsKey(productID)) {
			 quantity += car.get(productID).getQuantity();
			 car.get(productID).setQuantity(quantity);
			 }else {
	        	Products product = ps.findById(productID);
	            CarItem newItem = new CarItem(product, quantity);
	            car.put(productID, newItem);
	        }
		 
		session.setAttribute("jgCar", car);
		 
		return pName +"已加入購買車";
	}
	
	@PutMapping("/update/{productID}/{quantity}")
	@ResponseBody
	public String updateCar(@PathVariable("productID")Integer productID,@PathVariable("quantity")Integer quantity,HttpSession session) {
		Map<Integer,CarItem> car = getCar(session);
			 car.get(productID).setQuantity(quantity);
			 session.setAttribute("jgCar", car);
			 return "已更新購買車";
	}
	
	
	@DeleteMapping("/delete/{productID}")
	@ResponseBody
	public String deleteCar(@PathVariable("productID")Integer productID,HttpSession session) {
		Map<Integer,CarItem> car = getCar(session);
		car.remove(productID);
		return "已刪除成功";
	}
	
	
	public Map<Integer,CarItem> getCar(HttpSession session){
		Map<Integer,CarItem> car = (Map<Integer, CarItem>) session.getAttribute("jgCar");
		if(car == null) {
			car = new HashMap<>();
			session.setAttribute("jgCar", car);
		}
		return car;
	}
}
