package com.internousdev.radish.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.radish.dao.CartInfoDAO;
import com.internousdev.radish.dao.PurchaseHistoryInfoDAO;
import com.internousdev.radish.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class SettlementCompleteAction extends ActionSupport implements SessionAware {
	
	private String id;
	private Map<String,Object>session;
	
	public String execute(){
		String tempLogined = String.valueOf(session.get("logined"));
		int logined = "null".equals(tempLogined)? 0: Integer.parseInt(tempLogined);
		if(logined!=1){
			return "loginError";
		}
		
		String result = ERROR;
		
		String userId = session.get("userId").toString();
		
		CartInfoDAO dao = new CartInfoDAO();
		List<CartInfoDTO> cartInfoDTOList = dao.getCartInfoDTOList(userId);
		
		PurchaseHistoryInfoDAO phdao = new PurchaseHistoryInfoDAO();
		/*商品購入履歴テーブルに情報を登録*/
		int count = 0;
		for(CartInfoDTO dto: cartInfoDTOList){
			count +=phdao.regist(userId,dto.getProductId(),dto.getProductCount(),Integer.parseInt(id),dto.getPrice());
		}
		
		/*登録に成功した場合カートテーブルから情報を削除*/
		if(count >0){
			count = dao.deleteAll(String.valueOf(session.get("userId")));
			if(count > 0){
				result = SUCCESS;
			}
		}
		return result;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public Map<String, Object> getSession(){
		return session;
	}
	
	public void setSession(Map<String,Object>session){
		this.session = session;
	}

}
