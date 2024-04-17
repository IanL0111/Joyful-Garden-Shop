<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品管理</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
<link rel="stylesheet" href="/css/management.css">
<link rel="stylesheet" href="/css/MpList.css">
<link rel="stylesheet" href="/css/all.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://www.unpkg.com/axios@1.6.7/dist/axios.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>


</head>
<body>
	
	<div class="title">商品管理
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
	<div class="mBox">
		<div class="add">
			<div><button class="addButton" id="addButton">新增商品</button></div>
			
			<form method="post" action="shop" enctype="multipart/form-data">
			<div class="addBox">
			<div class="addBoxTop" id="addBoxTop">
				<div class="addBoxLeft">
					<div class="item"><label>商品名稱: </label>
					<input type="text"  name="productName"></div>
					<div class="item"><label >價格: </label>
					<input type="text" name="price" /></div>
					<div class="item"><label >庫存: </label>
					<input type="text" name="stock" /></div>
					<div class="item"><label >商品類別: </label>
					<select name="category">
						<c:forEach items="${categories}" var="c" >
							<option value="${c.categoryID}">${c.categoryID}. ${c.categoryName}</option>
						</c:forEach>
					</select></div>
				</div>
				
					<div class="inputImage"><label>商品圖片: </label><input type="file" id="inputImage" name="image" /></div>
					<div class=""><img src="/image/no_image.png" class="preImage" id="preImage"></div>
					
			</div>
			<div class="addBoxDown"><input type="submit" value="確認新增" class="submitAdd" id="submitAdd"></div>
		</form>
		</div>
		</div>
		
		<div class="Allp" id="Allp">
		<c:forEach items="${products}" var="p">
			<div class="pList">

				<div class="pListItem"><label>商品名稱: </label> ${p.productName}</div>
				<div class="pListItem"><label>價錢: </label> ${p.price}</div>
				<div class="pListItem"><label>庫存: </label> ${p.stock}</div>
				<div class="pListItem"><label>商品類別: </label> ${p.category.categoryName}</div>
				
				<div ><img src="${p.image}" class="Image"></div>
			
				<div class="submitBox">
				
				<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="callUpdate(${p.productID})">
  					更新資料
				</button>
				<div><button type="button" class="btn btn-secondary" onclick="deleteProduct(${p.productID})">刪除</button></div>
				</div>

			</div>
		</c:forEach> 
		</div>
		
	
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel">更新產品</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body" id="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="upload()">確認更新</button>
      </div>
    </div>
  </div>
</div>


	<%@ include file="MenuBar2.html" %>
	<script src="/js/management.js"></script>



</body>
</html>