package com.itwillbs.shookream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.ProductMapper;
import com.itwillbs.shookream.vo.OrderVo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.WishVo;
import com.itwillbs.shookream.vo.imageVo;

@Service
public class ProductService {
	
	@Autowired
	private ProductMapper mapper;
	
	// 상품 등록 insertProduct() 메서드
	// => 파라미터 : BoardVO 객체    리턴타입 : int(insertCount)
	public int insertProduct(ProductVo product) {
		return mapper.insertProduct(product);
	}
	
	// 상품 이미지 등록 insertProduct() 메서드
	// => 파라미터 : BoardVO 객체    리턴타입 : int(insertCount)
	public int insertImg(imageVo image, int product_idx) {
		return mapper.insertImage(image, product_idx);
	}
	
	// 상품 수정 updateProduct() 메서드
	public int updateProduct(int product_idx, ProductVo product, imageVo image) {
		return mapper.modifyProduct(product_idx, product, image);
	}

	// 상품 삭제 deleteProduct() 메서드
	public int deleteProduct(int product_idx) {
		return mapper.removeProduct(product_idx);
	}
	// 상품 목록 조회 - getProductList()
	public List<ProductVo> getProductList() {
		return mapper.selectProductList();
	}
	// 이미지 목록 조회 
	public imageVo getImgList(int product_idx) {
		return mapper.selectImgList(product_idx);
	}

	public int getMemberIdx(String sId) {
		return mapper.selectMemberIdx(sId);
	}

	public WishVo getWishInfo(int product_idx, int member_idx) {
		return mapper.selectWish(product_idx, member_idx);
		
	}

	public ProductVo getProduct(int product_idx) {
		return mapper.selectProduct(product_idx);
	}

	public imageVo getImage(int product_idx) {
		return mapper.selectImage(product_idx);
	}

	public List<String> getCategoryList(String product_name) {
		return mapper.selectCategoryList(product_name);
	}

	public List<String> getColorList(String product_name) {
		return mapper.selectColorList(product_name);
	}

	public List<imageVo> getImageList(String product_name) {
		return mapper.selectImageList(product_name);
	}

	public boolean InsertOrder(OrderVo order) {
		return mapper.insertOrder(order);
	}

}
