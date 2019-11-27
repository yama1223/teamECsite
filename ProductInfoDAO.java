package com.internousdev.radish.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.radish.dto.ProductInfoDTO;
import com.internousdev.radish.util.DBConnector;

public class ProductInfoDAO {

	/*全ての商品情報を取得
	@return 商品のList*/
		public List<ProductInfoDTO>getAllProductInfo(){
			DBConnector db = new DBConnector();
			Connection con = db.getConnection();
			List<ProductInfoDTO> productInfoDTOList= new ArrayList<ProductInfoDTO>();

			String sql = "SELECT * FROM product_info";

			try{
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

			while(rs.next()){
				ProductInfoDTO dto= new ProductInfoDTO();
				dto.setId(rs.getInt("id"));
				dto.setProductId(rs.getInt("product_id"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setProductDescription(rs.getString("product_description"));
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setPrice(rs.getInt("price"));
				dto.setImageFilePath(rs.getString("image_file_path"));
				dto.setImageFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(dto);
			}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try{
					con.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			return productInfoDTOList;
		}

	/*商品IDを利用して商品の詳細情報を取得
	@param productId 商品ID
	@return 商品情報*/
	public ProductInfoDTO getProductIdProductInfo(int productId) {
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		ProductInfoDTO dto= new ProductInfoDTO();
		String sql = "SELECT id,product_id,product_name,product_name_kana,product_description,category_id,price,image_file_path,image_file_name,release_date,release_company FROM product_info WHERE product_id =?";

		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();

		while(rs.next()){
			dto.setId(rs.getInt("id"));
			dto.setProductId(rs.getInt("product_id"));
			dto.setProductName(rs.getString("product_name"));
			dto.setProductNameKana(rs.getString("product_name_kana"));
			dto.setProductDescription(rs.getString("product_description"));
			dto.setCategoryId(rs.getInt("category_id"));
			dto.setPrice(rs.getInt("price"));
			dto.setImageFilePath(rs.getString("image_file_path"));
			dto.setImageFileName(rs.getString("image_file_name"));
			dto.setReleaseDate(rs.getDate("release_date"));
			dto.setReleaseCompany(rs.getString("release_company"));
		}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return dto;
	}

	/*カテゴリーIDを利用して関連商品の情報を取得
	@param categoryId カテゴリーID
	@param productId 商品ID
	@param limitOffset = データベースから情報取得を開始する位置
	@param limitRowCount = 表示する件数
	@return 関連商品情報*/
	public List<ProductInfoDTO>getCategoryIdProductInfo(int categoryId,int productId,int limitOffset,int limitRowCount){
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<ProductInfoDTO>productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql ="SELECT id,product_id,product_name,product_name_kana,product_description,category_id,price,image_file_path,image_file_name,release_date,release_company FROM product_info WHERE category_id=? AND product_id not in(?) ORDER BY rand() limit ?,?";

		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ps.setInt(2, productId);
			ps.setInt(3, limitOffset);
			ps.setInt(4, limitRowCount);
			ResultSet rs = ps.executeQuery();

		while(rs.next()){
			ProductInfoDTO dto= new ProductInfoDTO();
			dto.setId(rs.getInt("id"));
			dto.setProductId(rs.getInt("product_id"));
			dto.setProductName(rs.getString("product_name"));
			dto.setProductNameKana(rs.getString("product_name_kana"));
			dto.setProductDescription(rs.getString("product_description"));
			dto.setCategoryId(rs.getInt("category_id"));
			dto.setPrice(rs.getInt("price"));
			dto.setImageFilePath(rs.getString("image_file_path"));
			dto.setImageFileName(rs.getString("image_file_name"));
			dto.setReleaseDate(rs.getDate("release_date"));
			dto.setReleaseCompany(rs.getString("release_company"));
			productInfoDTOList.add(dto);
		}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	/*キーワードから情報を取得
	@param keywordList キーワードの配列
	@return 商品情報のList*/
	public List<ProductInfoDTO> getKeywordProductInfo(String[]keywordsList){
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();
		String sql ="select id,product_id,product_name,product_name_kana,product_description,category_id,price,image_file_path,image_file_name,release_date,release_company FROM product_info";
		boolean initializeFlag = true;

		/*検索ワードが空欄だった場合WHEREを利用しないようにするため*/
		if(!keywordsList[0].equals("")){
			for(String keyword : keywordsList){
				if(initializeFlag){
					sql +=" WHERE (product_name like '%" + keyword + "%' or product_name_kana like '%" + keyword + "%')";
					/*二つ目のワードを利用する場合にWHEREが不要なため、if文を利用し条件を分岐させる*/
					initializeFlag = false;
				}else{
					sql +=" OR (product_name like '%" + keyword + "%' OR product_name_kana like '%" + keyword + "%')";
				}
			}
		}
		sql += " ORDER BY product_id ASC";
		
		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				ProductInfoDTO dto = new ProductInfoDTO();
				dto.setId(rs.getInt("id"));
				dto.setProductId(rs.getInt("product_id"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setProductDescription(rs.getString("product_description"));
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setPrice(rs.getInt("price"));
				dto.setImageFilePath(rs.getString("image_file_path"));
				dto.setImageFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	/*キーワードとカテゴリーIDから情報を取得
	@param categoryId カテゴリーID
	@param keywordsList キーワードの配列
	@return 商品のList*/
	public List<ProductInfoDTO> getCategoryIdKeywordProductInfo(String categoryId,String[]keywordsList ){
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List <ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();
		String sql = "select id,product_id,product_name,product_name_kana,product_description,category_id,price,image_file_path,image_file_name,release_date,release_company FROM product_info where category_id=" + categoryId ;
		boolean initializeFlag = true;

		/*検索ワードが空欄だった場合WHEREを利用しないようにするため*/
		if(!keywordsList[0].equals("")){
			for(String keyword : keywordsList){
				if(initializeFlag){
					sql += " AND ((product_name like '%" + keyword + "%' or product_name_kana like '%" + keyword + "%')";
					/*二つ目のワードを利用する場合にWHEREが不要なため、if文を利用し条件を分岐させる*/
					initializeFlag = false;
				}else{
					sql += " OR (product_name like '%" + keyword + "%' or product_name_kana like '%" + keyword + "%')";
				}
			}
			sql+=")";
		}
		sql += " ORDER BY product_id ASC";
		
		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				ProductInfoDTO dto = new ProductInfoDTO();
				dto.setId(rs.getInt("id"));
				dto.setProductId(rs.getInt("product_id"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setProductDescription(rs.getString("product_description"));
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setPrice(rs.getInt("price"));
				dto.setImageFilePath(rs.getString("image_file_path"));
				dto.setImageFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(dto);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}
}