<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>購物車</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
<link rel="stylesheet" href="/css/shoppingCar.css">
<link rel="stylesheet" href="/css/all.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
	<%@ include file="MenuBar2.html"%>
	<div class="title">Joyful_Garden 購物車	</div>
	<div class="clear"><button type="button" class="clearCar" onclick="clearCar()">一鍵清空</button></div>
	<div class="showCar">
		<c:forEach items="${car}" var="c" varStatus="loop">
			<div class="carItem">
			<div class="image"><div><i class="fa-regular fa-circle-xmark fa-2xl" style="color:  #9ca8b8;padding-top:30px;padding-right:50px;cursor: pointer;" onclick="deleteCar(${c.value.product.productID})"></i></div><div><img src="${c.value.product.image}"></div></div>
			<div class='item'>
			<div class="lable">
				<div>產品: </div>
				<div>單價: </div>
				<div>購買數量: </div>
				<div style='height:95px'></div>
				<div>金額: </div>
			</div>
			<div class="value">
			<div><span id="productName_${loop.index}"></span>${c.value.product.productName}</div>
			<div >$<span id="price_${loop.index}">${c.value.product.price}</span></div>
			<div><input type="text" style="width:50px" id="quantity_${loop.index}" pattern="\d+" style="width: 30px;" maxlength="3" value="${c.value.quantity}" onchange="countAmount(${c.value.product.productID},${loop.index})" ></input>/<span id="stock_${loop.index}">${c.value.product.stock}</span></div>
			<div style='height:80px'></div>
			<div><span id="amount_${loop.index}" style='font-size: 35px;color: red'>${c.value.quantity * c.value.product.price}</span></div>
			</div>
			</div>
			</div>
			<c:set var="totalAmount" value="${totalAmount + (c.value.quantity * c.value.product.price)}" />
		</c:forEach>
	</div>
	
	<div class="totalAmount">小計: <i style="color:red">$ </i><span id="totalAmount" style="color:red">${totalAmount}</span></div>
	
	<div class="showCar" style="background: #fffaf4;">
		<div class="check">
		<div class="form-check form-check-inline" style="margin-right:100px;margin-left:50px;">
 		 	<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked>
  		 	<label class="form-check-label" for="inlineRadio1">到店自取</label>
		</div>
		<div class="form-check form-check-inline">
 			 <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
 			 <label class="form-check-label" for="inlineRadio2">送貨服務$500(購物金額滿三千享免運)</label>
		</div>
		</div>
		
		<form class="row g-3 needs-validation" style="text-align: left;margin:50px" novalidate>
		<div class="col-md-6">
   			 <label for="validationCustom03" class="form-label">送貨地址:</label>
    		 <input type="text" class="form-control" id="validationCustom03" required>
  		</div>
  		<div class="col-md-6">
   			 <label for="validationCustom03" class="form-label">收貨人:</label>
    		 <input type="text" class="form-control" id="validationCustom03" required>
  		</div>
  		<div class="col-md-6">
   			 <label for="validationCustom03" class="form-label">聯絡電話:</label>
    		 <input type="text" class="form-control" id="validationCustom03" required>
  		</div>
 		<div class="col-md-3">
   			 <label for="validationCustom04" class="form-label">送貨日期</label>
    		 <input type="date" class="form-control" id="validationCustom04" required>
		</div>
		<div class="col-md-6">
   			 <label for="validationCustom03" class="form-label">備註:</label>
    		 <textarea class="form-control" id="validationCustom03" required></textarea>
  		</div>

		</form>
		
	</div>
	<div class="fee">服務收費: <i style="color:red">$ </i><span id="deliveryFee" style="color:red">0</span></div>
	
	<div class="showCar" style="background: #fffaf4;">
		<div class="check">
		<div class="form-check form-check-inline" style="margin-right:100px;margin-left:50px;">
 		 	<input class="form-check-input" type="radio" name="paymentOption" id="Radio1" value="cash" checked>
  		 	<label class="form-check-label" for="inlineRadio1">現金</label>
		</div>
		<div class="form-check form-check-inline">
 			 <input class="form-check-input" type="radio" name="paymentOption" id="Radio2" value="greenPay">
 			 <label class="form-check-label" for="inlineRadio2">LINE Pay</label>
		</div>
		</div>
	</div>
	<div class="total">總計: <i style="color:red">$ </i><span id="sum" style="color:red">${totalAmount}</span></div>
	<div class="checkout"><button class="checkoutB" type="button" onclick="checkOut()">結帳</button></div>

	<script src="https://www.unpkg.com/axios@1.6.7/dist/axios.min.js"></script>
	<script>
	
	document.addEventListener("DOMContentLoaded", function() {
	    var radio1 = document.getElementById("inlineRadio1");
	    var radio2 = document.getElementById("inlineRadio2");
	    var form = document.querySelector(".needs-validation");

	    form.style.display = "none";

	    radio1.addEventListener("change", function() {
	        form.style.display = "none";
	    });

	    radio2.addEventListener("change", function() {
	        form.style.display = "block";
	    });
	});
	
	function countAmount(productID, index) {
		 var quantityInput = document.getElementById('quantity_' + index);
		 var quantity = quantityInput.value;
		 var price = document.getElementById('price_'+index).innerText;
		 var amount = document.getElementById('amount_'+index).innerText;
		 var stock = document.getElementById('stock_'+index).innerText;
		 var deliveryFeeText = document.getElementById('deliveryFee');
		 var deliveryFee = parseInt(deliveryFeeText.innerText, 10);
		 var quantityINT = parseInt(quantity, 10);
		 var stockINT = parseInt(stock, 10);

		    if (!/^\d+$/.test(quantity)) {
		    	Swal.fire("請輸入正整數！");
		        quantityInput.value = amount/price; 
		        return; 
		    }
		    
		    if (quantityINT>stockINT) {
		    	Swal.fire("庫存不足！");
		        quantityInput.value = amount/price; 
		        return; 
		    }
	    
	    var totalAmount = document.getElementById('totalAmount').innerText;
	    axios.put('/ShoppingCar/update/'+productID+'/'+quantity)
	        .then((response)=>{
	        	totalAmount = totalAmount - amount + (quantity * price);
	        	amount = quantity*price;
	        	document.getElementById('totalAmount').innerText = totalAmount;
	        	document.getElementById('amount_'+index).innerText = amount;	        	
	    		var selectedPaymentOption = document.querySelector('input[name="inlineRadioOptions"]:checked').value;
	    		var sumStr = document.getElementById('sum')
	    
	    		if(selectedPaymentOption == 'option1'){
	    			sumStr.innerText = totalAmount;
	   			 }
	    		if(selectedPaymentOption == 'option2'){
	    			if(totalAmount >= '3000'){
	    				deliveryFeeText.innerText ='0';
	    				sumStr.innerText = totalAmount;
	    			}else{
	    				deliveryFeeText.innerText ='500';
	    				sumStr.innerText = totalAmount+500;
	    			}
	    		}
	        });
	}
	
	function deleteCar(productID) {
	    axios.get('/product/'+productID)
	    .then(response =>{
	        Swal.fire({
	        title: "是否刪除"+response.data.products.productName+"?",
	        showCancelButton: true,
	        confirmButtonText: "Comfire",
	        }).then((result) => {
	             if (result.isConfirmed) {
	                axios.delete('/ShoppingCar/delete/'+productID)
	                .then(response =>{
	                    Swal.fire({
	                        position: "center",
	                        icon: "success",
	                        title: response.data,
	                        showConfirmButton: false,
	                        timer: 1500
	                        }).then(()=>{
	                    		window.location.href = '/ShoppingCar/view';
	                        })
	                });
	                }
	            });
	        });
	}
	

	function clearCar() {
	    Swal.fire({
	        title: "是否清空購物車",
	        showCancelButton: true,
	        confirmButtonText: "Confirm",
	    }).then((result) => {
	        if (result.isConfirmed) {
	            axios.get('/ShoppingCar/clear')
	            .then(response =>{
	                Swal.fire({
	                    position: "center",
	                    icon: "success",
	                    title: response.data,
	                    showConfirmButton: true,
	                    timer: 1500
	                }).then(() => {
	                        window.location.href = '/';
	                });
	            });
	        }
	    });
	}
	
	document.getElementById('inlineRadio2').addEventListener('change', delivery);
	document.getElementById('inlineRadio1').addEventListener('change', bySelf);
	
	function delivery() {
		  var totalAmount = document.getElementById('totalAmount').innerText;
		  var deliveryFee = document.getElementById('deliveryFee');
		  var sumStr = document.getElementById('sum')
		  var sum = parseInt(totalAmount, 10);
		  sumf = sum + 500;
		  
		  if (totalAmount >= 3000) {
		    deliveryFee.innerText = '0';
		  } else {
		    deliveryFee.innerText = '500';
		    sumStr.innerText = sumf;
		  }
		};
	
	function bySelf() {
		  var totalAmount = document.getElementById('totalAmount').innerText;
		  var deliveryFee = document.getElementById('deliveryFee');
		  var sumStr = document.getElementById('sum')
		  var sum = parseInt(sumStr.innerText, 10);
		  if(totalAmount <= 3000){
		  sumf = sum - 500;
		    deliveryFee.innerText = '0';
		    sumStr.innerText = sumf;
		  }
		};
	
	
	function checkOut(){

		var selectedPaymentOption = document.querySelector('input[name="paymentOption"]:checked').value;
		
		if(selectedPaymentOption == 'cash'){
				axios.post('/cashCheckout')
				.then(()=>{
					axios.get('/ShoppingCar/clear')
					.then((response)=>{
						 Swal.fire("已完成訂單")
						 .then(()=>{
							 window.location.href = '/shop';
						 })
					});
				})
			}
			
		if(selectedPaymentOption == 'greenPay'){
			
			const deliveryFee = document.querySelector('#deliveryFee').textContent;
		
			const totalAmount = document.querySelector('#totalAmount').textContent;
		
			const total = parseInt(document.querySelector('#sum').textContent);
		
			var formData = new FormData();
			
			if(deliveryFee == '500'){
				formData.append("totalAmount",total);
				formData.append("total",total);
				formData.append("deliveryFee",'500');
			}else{
				formData.append("totalAmount",totalAmount);
				formData.append("total",total);
				formData.append("deliveryFee",'0');
			}

			
				axios.post('/checkout',formData)
				.then((response)=>{
					window.location.href = response.data;
				}).then(()=>{
					axios.get('/ShoppingCar/clear')
				})

			}
		}


	</script>
</body>
</html>