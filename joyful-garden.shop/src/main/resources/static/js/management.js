var addButton = document.querySelector("#addButton");
var addBox = document.querySelector(".addBox")
addBox.style.display='none';

addButton.addEventListener('click',()=>{
	addBox.style.display = (addBox.style.display === 'none') ? 'flex':'none';
});


var preImage = document.getElementById("preImage");
var inputImage = document.getElementById("inputImage");

    inputImage.addEventListener("change", function () {
      const file = this.files[0];
      if (file == null) {
        preImage.src = "../image/no_image.png";
        return;
      }
      const tempURL = URL.createObjectURL(file);
      preImage.src = tempURL;
      
    });
    preImage.addEventListener("click", function () {
     inputImage.click();
    });


    


document.addEventListener("DOMContentLoaded", function() {
    var cBox = document.querySelectorAll(".cBox");
    var sum = cBox.length;

    for (let i = 1; i <= sum; i++) {
        var elementId = 'category' + i;
        var element = document.getElementById(elementId);

        if (element) {
            (function(el, index) {
                el.addEventListener('change', function() {
                    // 使用傳遞進來的 `el`，而不是外部的 `element`
                    var selectedOption = el.options[el.selectedIndex];
                    if (selectedOption) {
                        var optionText = selectedOption.textContent;
                        var target = 'choice' + index;
                        var choice = document.getElementById(target);

                        if (choice) {
                            choice.value = optionText;
                            console.log(optionText);
                        } else {
                            console.error("無法找到目標元素：" + target);
                        }
                    }
                });
            })(element, i);
        }
    }
});

//在這裡，el 和 element 其實是同一個元素的兩個不同名字的引用。當我們在迴圈內使用 (function(el, index) {...})(element, i); 時，element 會被傳遞給立即函數中的 el 參數。這樣，el 和 element 其實是指向同一個 DOM 元素的引用。
//
//這種技巧是一種閉包（closure）的應用。JavaScript 中的閉包允許我們在函數內部形成一個封閉的作用域，可以保留外部變數的值。在這種情況下，我們確保每次迴圈運行時，element 的值都被傳遞到立即函數中的 el 參數，而這樣就避免了閉包中的問題。
//
//簡而言之，el 和 element 是指向同一個元素的兩個引用，只是名稱不同。在這個例子中，它們都用於指向迴圈中的不同 category1、category2 等元素。



function deleteProduct(productID) {
    axios.get('/product/'+productID)
    .then(response =>{
        Swal.fire({
        title: "是否刪除"+response.data.products.productName+"?",
        showCancelButton: true,
        confirmButtonText: "Comfire",
        }).then((result) => {
             if (result.isConfirmed) {
                axios.delete('/shop/'+productID)
                .then(response =>{
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: response.data,
                        showConfirmButton: false,
                        timer: 1500
                        }).then(()=>{
							window.location.href = '/Management';
						})
                });
                }
            });
        });
}

async function callUpdate(productID) {
        try {
            const url = '/product/' + productID;
            const response = await axios.get(url);
            const products = response.data.products;
            const categories = response.data.categories;
            renderProduct(products,categories);
        } catch (error) {
            console.error('獲取產品時出錯：', error);
        }
    }

	function renderProduct(products,categories) {
	    const productListContainer = document.getElementById('modal-body');
	    productListContainer.innerHTML = '';
	        const productDiv = document.createElement('div');
	        productDiv.classList.add('AddModel');
	        productDiv.innerHTML = '<div><label>商品圖片: </label><input type="file" class="inputI"><div><img src="' + products.image + '"'+'class="preI"></div></div><div><input type="text" id="productID" style="display: none" value="'+products.productID+'"></input><div>產品名稱: <input type="text" id="productName" value="' + products.productName + '"></div><div>產品價錢: <input type="text" id="price" value="' + products.price + '"></div><div>產品數量: <input type="text" id="stock" value="' + products.stock + '"></div><div><label>商品類別: </label><input type="text" value="'+products.category.categoryID+'.'+products.category.categoryName+'" id="choice" readonly></div><lable>類別選擇: </lable><select id="category">';
            productListContainer.appendChild(productDiv);

            const categorySelect = document.getElementById('category');

            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.text = '請選擇類別';
            categorySelect.appendChild(defaultOption);

            categories.forEach(c => {
            const option = document.createElement('option');
            option.value = c.categoryID;
            option.text = c.categoryID + '.' + c.categoryName;
            categorySelect.appendChild(option);
             });


	    var choiceInput = document.getElementById("choice");

	    categorySelect.addEventListener('change', function() {
	        var selectedOption = categorySelect.options[categorySelect.selectedIndex];
	        if (selectedOption) {
	            var optionText = selectedOption.textContent;
	            choiceInput.value = optionText;
	        } else {
	            console.error("無法找到選擇的類別");
	        }
	    });

        var preI = document.querySelector(".preI");
        var inputI = document.querySelector(".inputI");

        inputI.addEventListener("change", function () {
        const file = this.files[0];
        if (file == null) {
        preI.src = "../image/no_image.png";
        return;
        }
        const tempURL = URL.createObjectURL(file);
        preI.src = tempURL;
         });
        preI.addEventListener("click", function () {
         inputI.click();
        });

    }



        function upload() {

			var formData = new FormData();
            var productID = document.getElementById('productID').value;
			var productName = document.getElementById('productName').value;
			var price = document.getElementById('price').value;
			var stock = document.getElementById('stock').value;
			var category = document.getElementById('choice').value;

            var dotIndex = category.indexOf('.');
            var categoryID = category.substring(0,dotIndex);
			
			
			formData.append('productID', productID);
			formData.append('productName', productName);
			formData.append('price', price);
			formData.append('stock', stock);
			formData.append('category', categoryID);

			var fileInput = document.querySelector(".inputI");
			var file = fileInput.files[0];
			if(file !== null && file !== undefined){

				formData.append('image', file);

		        axios.post('/update/' + productID, formData)
    			.then(response =>{
                    Swal.fire({
                        position: "center",
                        icon: "success",
                        title: response.data,
                        showConfirmButton: false,
                        timer: 1500
                        }).then(()=>{
							window.location.href = '/Management';
						})
                	})
    			}else{
                    var imageElement = document.querySelector(".preI");
			        var image = imageElement.getAttribute('src');
                    
					formData.append('image', image);
					   axios.put('/update/'+productID,formData)
			        	.then((response) => {
			        		Swal.fire({
		                    position:"center",
		                    icon:"success",
		                    title: response.data,
		                    showConfirmButton: false,
		                    timer: 2500
		                    });
			        		window.location.href = '/Management';
			        	})
			        	.catch((error) => {
			        	    console.log('There was a problem with your Axios request:', error);
			        	  });
					}
			
			
	    }
	  
	    
	async function fetchAndRenderProductsbyParents(Category) {
		var pID = document.getElementById('pID').value;
		var search = document.getElementById('search');
		var select = document.getElementById('select2');
		$(window).on('scroll', handleScroll);
		const productListContainer = document.getElementById('Allp');
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
					url = '/PreservedFlower/select/'+selectedParent;

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
	    const productListContainer = document.getElementById('Allp');
	    productListContainer.innerHTML = '';
	    products.forEach(product => {
	        const pListDiv = document.createElement('div');
	        pListDiv.classList.add('pList');
	        pListDiv.innerHTML = '<div class="pListItem"><label>商品名稱: </label>'+product.productName+'</div><div class="pListItem"><label>價錢: </label>'+product.price+'</div><div class="pListItem"><label>庫存: </label>'+product.stock+'</div><div class="pListItem"><label>商品類別: </label>'+product.category.categoryName+'</div><div ><img src="'+product.image+'" class="Image"></div><div class="submitBox"><button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="callUpdate('+product.productID+')">更新資料</button><div><button type="button" class="btn btn-secondary" onclick="deleteProduct('+product.productID+')">刪除</button></div></div>';
	        productListContainer.appendChild(pListDiv);
	    });
	}
	
	    function renderAllProducts(products) {
	    const productListContainer = document.getElementById('Allp');
	    products.forEach(product => {
	        const pListDiv = document.createElement('div');
	        pListDiv.classList.add('pList');
	        pListDiv.style.animation = 'fade-in 2s ease'; 
	        pListDiv.innerHTML = '<div class="pListItem"><label>商品名稱: </label>'+product.productName+'</div><div class="pListItem"><label>價錢: </label>'+product.price+'</div><div class="pListItem"><label>庫存: </label>'+product.stock+'</div><div class="pListItem"><label>商品類別: </label>'+product.category.categoryName+'</div><div ><img src="'+product.image+'" class="Image"></div><div class="submitBox"><button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="callUpdate('+product.productID+')">更新資料</button><div><button type="button" class="btn btn-secondary" onclick="deleteProduct('+product.productID+')">刪除</button></div></div>';
	        productListContainer.appendChild(pListDiv);
	    });
	}
	
	

	var page = 0;
	var size = 8;
	var loading = false;
	var parentID = 0;
	
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
					console.log("Response.data: ",Response.data)
					renderAllProducts(Response.data.content);
					loading = false;
				})
			}else{
				url = '/PreservedFlower/select/'+parentID+'?page=' + page + '&size=' + size;
				axios.get(url)
				.then((Response)=>{
					console.log("Response.data: ",Response.data)
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
