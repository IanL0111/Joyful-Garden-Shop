package com.joyfulgarden.controller.shop;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyfulgarden.model.shop.CarItem;
import com.joyfulgarden.model.shop.CheckoutPaymentRequestForm;
import com.joyfulgarden.model.shop.PaymentResponse;
import com.joyfulgarden.model.shop.ProductForm;
import com.joyfulgarden.model.shop.ProductPackageForm;
import com.joyfulgarden.model.shop.Products;
import com.joyfulgarden.model.shop.RedirectUrls;
import com.joyfulgarden.service.shop.ProductsService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ConsumerCheck {
		
		@Autowired
		private ProductsService ps;
	
	
	public static String encrypt(final String keys, final String data) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] keyBytes = keys.getBytes();

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");

        Mac mac = Mac.getInstance("HmacSHA256");

        mac.init(secretKeySpec);

        byte[] encryptedBytes = mac.doFinal(data.getBytes());

        String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);

        return encryptedBase64; // 回傳加密後的結果
    }
	
	
	public Map<Integer,CarItem> getCar(HttpSession session){
		Map<Integer,CarItem> car = (Map<Integer, CarItem>) session.getAttribute("jgCar");
		if(car == null) {
			car = new HashMap<>();
			session.setAttribute("jgCar", car);
		}
		return car;
	}
	
	@PostMapping("/cashCheckout")
	@ResponseBody
	 public String cashCheckout(HttpSession session) {
		Map<Integer,CarItem> car = getCar(session);
		  car.forEach((key,value)->{
			  Products p = ps.findById(key);
		        int newStock = p.getStock() - value.getQuantity();
		        p.setStock(newStock);
		        ps.update(p);
		  });
		return "success";
	}
	
	
	@PostMapping("/checkout")
	@ResponseBody
	 public String checkout(HttpServletResponse response , HttpSession session,@RequestParam("totalAmount")String totalAmount,@RequestParam("total")String total,@RequestParam("deliveryFee")String deliveryFee){
		
		Map<Integer,CarItem> car = getCar(session);
		
		RestTemplate restTemplate = new RestTemplate();
		
		CheckoutPaymentRequestForm form = new CheckoutPaymentRequestForm();
		
		form.setAmount(new BigDecimal(total));
        form.setCurrency("TWD");
        form.setOrderId("merchant_order_id");

        ProductPackageForm productPackageForm = new ProductPackageForm();
        productPackageForm.setId("package_id");
        productPackageForm.setName("Joyful_Garden Shop");
        productPackageForm.setAmount(new BigDecimal(totalAmount));
        
        List<ProductForm> products = new ArrayList<>();
        
        car.forEach((key,value)->{
        
        ProductForm productForm = new ProductForm();
        
        productForm.setId(key.toString());
        productForm.setName(value.getProduct().getProductName());
		productForm.setImageUrl("null");

        productForm.setQuantity(new BigDecimal(value.getQuantity()));
        productForm.setPrice(new BigDecimal(value.getProduct().getPrice()));
        products.add(productForm);
        
        Products p = ps.findById(key);
        int newStock = p.getStock() - value.getQuantity();
        p.setStock(newStock);
        });
        
        if("500".equals(deliveryFee)) {
        	ProductForm productForm = new ProductForm("DeliveryFee",new BigDecimal(1),new BigDecimal("500"));
        	products.add(productForm);
        }
        
        productPackageForm.setProducts(products);
        
        List<ProductPackageForm> packages = new ArrayList<>();
        packages.add(productPackageForm);
        form.setPackages(packages);
        

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setAppPackageName("");
        redirectUrls.setConfirmUrl("http://localhost:8081/");
        form.setRedirectUrls(redirectUrls);
        
        ObjectMapper mapper = new ObjectMapper();
        String ChannelSecret = "d9d3b1f5902c7076dd76140a07cbd8db";
        String requestUri = "/v3/payments/request";
        String nonce = UUID.randomUUID().toString();
        
        String requestBody;
        try {
            requestBody = mapper.writeValueAsString(form);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            requestBody = "";
        }
        
			String signature = null;
			try {
				signature = encrypt(ChannelSecret, ChannelSecret + requestUri + requestBody + nonce);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("X-LINE-ChannelId", "2004207015");
	        headers.set("X-LINE-Authorization-Nonce", nonce);
	        headers.set("X-LINE-Authorization", signature);
	        
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
	        
	        ResponseEntity<PaymentResponse> responseEntity = restTemplate.exchange(
	                "https://sandbox-api-pay.line.me/v3/payments/request",
	                HttpMethod.POST,
	                requestEntity,
	                PaymentResponse.class);
	        PaymentResponse paymentResponse = responseEntity.getBody();
	        String webPaymentUrl = paymentResponse.getInfo().getPaymentUrl().getWeb();
	        
        return webPaymentUrl;
	}
		 
}
//test_202403202749
//dqePP96K)V
// Channel ID 2004207015
// Channel Secret Key d9d3b1f5902c7076dd76140a07cbd8db
