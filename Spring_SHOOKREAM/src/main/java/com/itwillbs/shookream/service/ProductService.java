package com.itwillbs.shookream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.ProductMapper;
import com.itwillbs.shookream.vo.OrderBean;
import com.itwillbs.shookream.vo.ProductBean;
import com.itwillbs.shookream.vo.WishBean;
import com.itwillbs.shookream.vo.imageBean;

@Service
public class ProductService {
	
	@Autowired
	private ProductMapper mapper;
	
	// 상품 등록 insertProduct() 메서드
	// => 파라미터 : BoardVO 객체    리턴타입 : int(insertCount)
	public int insertProduct(ProductBean product) {
		return mapper.insertProduct(product);
	}
	
	// 상품 이미지 등록 insertProduct() 메서드
	// => 파라미터 : BoardVO 객체    리턴타입 : int(insertCount)
	public int insertImg(imageBean image, int product_idx) {
		return mapper.insertImage(image, product_idx);
	}
	
	// 상품 수정 updateProduct() 메서드
	public int updateProduct(int product_idx, ProductBean product, imageBean image) {
		return mapper.modifyProduct(product_idx, product, image);
	}

	// 상품 삭제 deleteProduct() 메서드
	public int deleteProduct(int product_idx) {
		return mapper.removeProduct(product_idx);
	}
	// 상품 목록 조회 - getProductList()
	public List<ProductBean> getProductList() {
		return mapper.selectProductList();
	}
	// 이미지 목록 조회 
	public imageBean getImgList(int product_idx) {
		return mapper.selectImgList(product_idx);
	}

	public int getMemberIdx(String sId) {
		return mapper.selectMemberIdx(sId);
	}

	public WishBean getWishInfo(int product_idx, int member_idx) {
		return mapper.selectWish(product_idx, member_idx);
		
	}

	public ProductBean getProduct(int product_idx) {
		return mapper.selectProduct(product_idx);
	}

	public imageBean getImage(int product_idx) {
		return mapper.selectImage(product_idx);
	}

	public List<String> getCategoryList(String product_name) {
		return mapper.selectCategoryList(product_name);
	}

	public List<String> getColorList(String product_name) {
		return mapper.selectColorList(product_name);
	}

	public List<imageBean> getImageList(String product_name) {
		return mapper.selectImageList(product_name);
	}

	public boolean InsertOrder(OrderBean order) {
		return mapper.insertOrder(order);
	}

}
