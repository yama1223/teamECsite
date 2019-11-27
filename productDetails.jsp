<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/productDetails.css">
<link rel="stylesheet" href="./css/radish.css">
<title>商品詳細画面</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div id="contents">
<h1>商品詳細画面</h1>

<s:if test="productInfoDTO!=null">
<s:form action="AddCartAction">
<div id="main">

	<div id="left">
		<img src='<s:property value="productInfoDTO.imageFilePath"/>/<s:property value="productInfoDTO.imageFileName"/>' class="detailsPic"/><br>
	</div>
	
	<div id="right">
		<table class="productDetails">
			<tr>
				<th scope="row"><s:label value="商品名"/></th>
				<td><s:property value="productInfoDTO.productName"/>
			</tr>
			<tr>
				<th scope="row"><s:label value="商品名ふりがな"/></th>
				<td><s:property value="productInfoDTO.productNameKana"/>
			</tr>
			<tr>
				<th scope="row"><s:label value="値段"/></th>
				<td><s:property value="productInfoDTO.price"/>円</td>
			</tr>
			<tr>
				<th scope="row"><s:label value="購入個数"/></th>
				<td><s:select name="productCount" list="%{productCountList}"/>個</td>
			</tr>
			<tr>
				<th scope="row"><s:label value="発売会社名"/></th>
				<td><s:property value="productInfoDTO.releaseCompany"/>
			</tr>
			<tr>
				<th scope="row"><s:label value="発売年月日"/></th>
				<td><s:property value="productInfoDTO.releaseDate"/>
			</tr>
			<tr>
				<th scope="row"><s:label value="商品詳細情報"/></th>
				<td><s:property value="productInfoDTO.productDescription"/>
			</tr>
		</table>
			<s:hidden name="productId" value="%{productInfoDTO.productId}"/>
			<div class="button">
			<s:submit value="カートに追加" class="submitBtn"/>
			</div>
	</div>
	
</div>
			
</s:form>
		
		<s:if test="relatedProductList!=null && relatedProductList.size()>0">
			<div id="under">
				<h2>【関連商品】</h2>
				<div class="related">
				<s:iterator value="relatedProductList">
					<div class="relatedBox">
					<a href='<s:url action="ProductDetailsAction">
					<s:param name="productId" value="%{productId}"/>
					</s:url>'><img src='<s:property value="imageFilePath"/>/<s:property value="imageFileName"/>' class="relatedPic"/>
					<s:property value="productName"/><br>
					</a></div>
				</s:iterator>
				</div>
			</div>
		</s:if>
		
</s:if>

<s:else>
<div class="info">
	<p>商品の詳細情報がありません。</p>
</div>
</s:else>
</div>
</body>
</html>