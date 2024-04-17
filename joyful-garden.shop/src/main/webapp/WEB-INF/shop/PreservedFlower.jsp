<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品列表</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
<link rel="stylesheet" href="/css/preservedFlower.css">
<link rel="stylesheet" href="/css/all.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	
	<div class="title">手作商店 <span style="display:none" id="parentID">${parentID}</span>
		<div style="font-size: 20px;margin-top: 50px;margin-right:80px"><label>商品類別: </label>
			<select name="parents" onchange="fetchAndRenderProductsbyParents(this.value)" id="select">
				<option value="0" id="pID">-全部-</option>
				<c:forEach items="${parents}" var="p" >
					<option value="${p.categoryID}">${p.categoryID}. ${p.categoryName}</option>
				</c:forEach>
			</select>
			<select name="category" onchange="fetchAndRenderProducts(this.value)" id="select2">
				<option value="0" id="categoryID">-全部-</option>
			</select>
	
  			<input type="text" placeholder="輸入產品名稱作搜尋" onchange="searchProducts(this.value)" id="search">

		</div>
		</div>

	<div class="listBox" id="productListContainer">
	
		

	</div>
	
	<div class="carList" id="carList">
		<div class="carListItem" id="carListItem">
			<c:forEach items="${car}" var="c">
				<div><i class="fa-regular fa-circle-xmark" onclick="deleteCar(${c.value.product.productID})"></i>${c.value.product.productName} * ${c.value.quantity}</div>
				<c:set var="totalAmount" value="${totalAmount + (c.value.quantity * c.value.product.price)}" />
			</c:forEach>
		</div>
		<div style="text-align: center;margin-top:10px">
			<a href='/ShoppingCar/view'>
				<button type="button" class="carbutton"><i class="fa-solid fa-cart-plus" style="cursor: pointer;"></i>前往結帳</button>
			</a>
				<span style="color:orange">小計: $ </span><span id="totalAmount" style="color:orange">${totalAmount}</span>
		</div>
	</div>
	
<%@ include file="MenuBar2.html" %>
<%@ include file="Footer.html" %>



</body>
<script src="https://www.unpkg.com/axios@1.6.7/dist/axios.min.js"></script>
<script>
	
	document.addEventListener('DOMContentLoaded', function() {
		showAndHide();
	});
	
	function showAndHide(){
		var carList = document.getElementById('carList');
    	var carListItem = document.getElementById('carListItem');

    	if (carListItem.children.length === 0) {
        	carList.style.top = '-100px'; 
  	  	}else{
  	  		carList.style.top = '0px';
  	  	}
	}

	
	async function fetchAndRenderProductsbyParents(Category) {
		var pID = document.getElementById('pID').value;
		var search = document.getElementById('search');
		var select2 = document.getElementById('select2');
		$(window).on('scroll', handleScroll);
		const productListContainer = document.getElementById('productListContainer');
	    productListContainer.innerHTML = '';
	    page = 0;
		size = 9;		
				if (pID == Category) {
						parentID = 0;
						loadPage();
	       			}else if(Category !== '4'){
	       				parentID = Category;
	       				loadPage();
					}else{
						parentID = 4;
						loadPage();
					}
				search.value = '';
				url = '/PreservedFlower/getSelect/'+Category;
				const response = await axios.get(url);
	            const categories = response.data;
	            renderCategories(categories)
		} 
		
	    
	    var selectedParent;
	    
	    document.getElementById("select").addEventListener("change", function() {
	  		selectedParent = this.value;
		});

		async function fetchAndRenderProducts(Category) {
		var categoryID = document.getElementById('categoryID').value;
		var search = document.getElementById('search');
		$(window).off('scroll', handleScroll);
	        try {
				if (categoryID == Category) {
					$(window).on('scroll', handleScroll);
					loadPage();
					return ;
	       			}else{
						 url = '/Management/select/'+Category;
					}
	            const response = await axios.get(url);
	            const products = response.data;
	            renderProducts(products);
	            search.value = '';
	        } catch (error) {
	            console.error('獲取產品時出錯：', error);
	        }
	    }

		async function searchProducts(productName) {
			var select = document.getElementById('select');
			var select2 = document.getElementById('select2');
			$(window).off('scroll', handleScroll);
	        try {
	            const url = '/PreservedFlower/Search/' + productName;
	            const response = await axios.get(url);
	            const products = response.data;
	            renderProducts(products);
	            select.selectedIndex = 0;
	            select2.selectedIndex = 0;
	        } catch (error) {
	            console.error('獲取產品時出錯：', error);
	        }
	    }
		
		 function renderCategories(categories) {
			  const selectElement = document.getElementById('select2');
			  selectElement.innerHTML = '';
			  const allOption = document.createElement('option');
			  allOption.value = '0';
			  allOption.textContent = '-全部-';
			  allOption.id = 'categoryID'
			  selectElement.appendChild(allOption);
			  categories.forEach(category => {
		    	const optionElement = document.createElement('option');
		    	optionElement.value = category.categoryID;
		    	optionElement.textContent = category.categoryID + '. ' + category.categoryName;
		    	optionElement.id = 'categoryID'
		        selectElement.appendChild(optionElement);
				});
			}

	function renderProducts(products) {
	    const productListContainer = document.getElementById('productListContainer');
	    productListContainer.innerHTML = '';
	    products.forEach(product => {
	        const productDiv = document.createElement('div');
	        productDiv.classList.add('productList');
	        productDiv.innerHTML = `
	            <div>
	                <img src=`+product.image+`>
	            </div>
	            <div>`+product.productName+`</div>
	            <div>$`+product.price+`</div>
	        `;
	        
	        // 根據庫存動態生成加入購物車按鈕
	        if (product.stock > 0) {
	            const addButton = document.createElement('button');
	            addButton.type = 'button';
	            addButton.classList.add('button');
	            addButton.innerHTML = `
	                <i class="fa-solid fa-cart-plus" style="color: white;"></i> 加入購物車
	            `;
	            addButton.addEventListener('click', () => {
	                addCar(product.productID, 1);
	            });
	            productDiv.appendChild(addButton);
	        } else {
	        	const soldoutButton = document.createElement('button');
	        	soldoutButton.type = 'button';
	        	soldoutButton.classList.add('soldout');
	        	soldoutButton.innerHTML = `
	        	    <i class="fa-solid fa-cart-plus" style="color: white;"></i> 加入購物車
	        	`;
	        	soldoutButton.addEventListener('click', () => {
	        	    soldout();
	        	});
	        	productDiv.appendChild(soldoutButton);
	        }
	        
	        productListContainer.appendChild(productDiv);
	    });
	}
	
	function renderAllProducts(products) {
	    const productListContainer = document.getElementById('productListContainer');
	    products.forEach(product => {
	        const productDiv = document.createElement('div');
	        productDiv.classList.add('productList');
	        productDiv.innerHTML = `
	            <div>
	                <img src=`+product.image+`>
	            </div>
	            <div>`+product.productName+`</div>
	            <div>$`+product.price+`</div>
	        `;
	        
	        // 根據庫存動態生成加入購物車按鈕
	        if (product.stock > 0) {
	            const addButton = document.createElement('button');
	            addButton.type = 'button';
	            addButton.classList.add('button');
	            addButton.innerHTML = `
	                <i class="fa-solid fa-cart-plus" style="color: white;"></i> 加入購物車
	            `;
	            addButton.addEventListener('click', () => {
	                addCar(product.productID, 1);
	            });
	            productDiv.appendChild(addButton);
	        } else {
	        	const soldoutButton = document.createElement('button');
	        	soldoutButton.type = 'button';
	        	soldoutButton.classList.add('soldout');
	        	soldoutButton.innerHTML = `
	        	    <i class="fa-solid fa-cart-plus" style="color: white;"></i> 加入購物車
	        	`;
	        	    soldoutButton.addEventListener('click', () => {
	        	    soldout();
	        	});
	        	productDiv.appendChild(soldoutButton);
	        }
	        
	        productListContainer.appendChild(productDiv);
	    });
	}
	
	function renderCar(){
		axios.get('/ShoppingCar/getCar')
		.then((response)=>{
			var carListItem = document.getElementById('carListItem');
			carListItem.innerHTML = '';
			var amount = 0;
			for (var key in response.data) {
                if (response.data.hasOwnProperty(key)) {
                	const carDiv = document.createElement('div');
                    carDiv.innerHTML = '<i class="fa-regular fa-circle-xmark" onclick="deleteCar('+response.data[key].product.productID+')"></i>'+response.data[key].product.productName + ' * ' + response.data[key].quantity;
                    carListItem.appendChild(carDiv);
                    amount += response.data[key].product.price*response.data[key].quantity
                }
            }
			document.getElementById('totalAmount').textContent = amount;
		}).then(()=>{
			showAndHide();
		})
		
	}
	
	function addCar(productID,quantity){
		axios.post('/ShoppingCar/add/'+productID+'/'+quantity)
		.then((response) => {
		    			 renderCar();
		        	})
		        	
		}
	
	function deleteCar(productID){
		axios.delete('/ShoppingCar/delete/'+productID)
		.then(()=>{
			renderCar();
		})
	}
	
	function soldout(){
		Swal.fire({
            title: "產品已售罄",
            icon: "warning"
        });
	}
	
	
	var page = 0;
	var size = 8;
	var loading = false;
	var parentID = document.getElementById('parentID').textContent;
	
	function loadPage() {
		if (!loading) { 
			     loading = true; 
			if(parentID == '0'){
				url = '/Management/SelectAll?page=' + page + '&size=' + size;
				axios.get(url)
				.then((Response)=>{
					renderAllProducts(Response.data);
					loading = false;
				})
			}else if(parentID == '4'){
				url = '/PreservedFlower/jellery/'+parentID+'?page=' + page + '&size=' + size;
				axios.get(url)
				.then((Response)=>{
					renderAllProducts(Response.data.content);
					loading = false;
				})
			}else{
				url = '/PreservedFlower/select/'+parentID+'?page=' + page + '&size=' + size;
				axios.get(url)
				.then((Response)=>{
					renderAllProducts(Response.data.content);
					loading = false;
				})
			}
				 page++;
		}
	}
		

		function handleScroll() {
    		var scrollTop = $(window).scrollTop();
   			var windowHeight = $(window).height();
    		var documentHeight = $(document).height();
    
    		var distanceToBottom = windowHeight / 3;

    		if (scrollTop + windowHeight >= documentHeight - distanceToBottom) {
        		loadPage();
    			}
			}
		

		$(document).ready(function() {
		$(window).on('scroll', handleScroll);
	    loadPage();
		});

	

</script>
</html>