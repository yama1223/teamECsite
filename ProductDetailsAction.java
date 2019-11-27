package com.internousdev.radish.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.radish.dao.ProductInfoDAO;
import com.internousdev.radish.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetailsAction extends ActionSupport implements SessionAware {
	private int productId;
	private List<Integer> productCountList;
	private List<ProductInfoDTO> relatedProductList;
	private ProductInfoDTO productInfoDTO = new ProductInfoDTO();
	private Map<String, Object>session;
	
	public String execute(){
		
		ProductInfoDAO dao = new ProductInfoDAO();
		productInfoDTO = dao.getProductIdProductInfo(productId);
		
		if(productInfoDTO.getProductId() == 0){
			productInfoDTO = null;
		}else{
			//購入個数のリスト ProductDetails.jspで利用
			productCountList = new ArrayList<Integer>();
			for(int i=1; i<=5; i++){
				productCountList.add(i);
			}
			//関連商品の設定
			relatedProductList = dao.getCategoryIdProductInfo(productInfoDTO.getCategoryId(),productInfoDTO.getProductId(),0,3);
		}
		return SUCCESS;
	}
	
	public int getProductId(){
		return productId;
	}
	
	public void setProductId(int productId){
		this.productId = productId;
	}
	
	public List<Integer> getProductCountList(){
		return productCountList;
	}
	
	public void setProductCountList(List<Integer> productCountList){
		this.productCountList = productCountList;
	}
	
	public List<ProductInfoDTO> getRelatedProductList(){
		return relatedProductList;
	}
	
	public void setRelatedProductList(List<ProductInfoDTO>relatedProductList){
		this.relatedProductList = relatedProductList;
	}
	
	public ProductInfoDTO getProductInfoDTO(){
		return productInfoDTO;
	}
	
	public void setProductInfoDTO(ProductInfoDTO productInfoDTO){
		this.productInfoDTO = productInfoDTO;
	}
	
	public Map<String,Object> getSession(){
		return session;
	}
	
	public void setSession(Map<String,Object>session){
		this.session = session;
	}

}
