package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.cartVo;

@Mapper
public interface CartMapper {
	//장바구니 이동 (장바구니 목록 담기)
	List<cartVo> getCartlist(@Param("member_idx") int member_idx,
			@Param("startRow") int startRow,
			@Param("listLimit") int listLimit);
	//장바구니 담기
	int getInsertCart(@Param("product_idx") int product_idx, 
			@Param("member_idx") int member_idx,
			@Param("cart_count") int cart_count,
			@Param("product") ProductVo product);
	//product_idx 에 맞는 상품을 조회하여 productVo에 저장
	ProductVo getProduct(int product_idx);

}
