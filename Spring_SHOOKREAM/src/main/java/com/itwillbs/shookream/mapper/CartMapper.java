package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.OrderdeliveryVo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.cartVo;
import com.itwillbs.shookream.vo.cartVoArr;

@Mapper
public interface CartMapper {
	//장바구니 이동 (장바구니 목록 담기)
	List<cartVo> getCartlist(
			@Param("member_idx") int member_idx,
			@Param("startRow") int startRow,
			@Param("listLimit") int listLimit);
	//장바구니 금액(상품금액, 총 결제금액)
	//장바구니 담기
	int getInsertCart(
			@Param("product_idx") int product_idx, 
			@Param("member_idx") int member_idx,
			@Param("cart_count") int cart_count,
			@Param("product") ProductVo product);
	//product_idx 에 맞는 상품을 조회하여 productVo에 저장
	ProductVo getProduct(int product_idx);
	//장바구니 삭제
	int getCartDelete(
			@Param("cart_idx") int cart_idx,
			@Param("member_idx") int member_idx);
	//장바구니 등록 전 확인
	cartVo getCartSelect(
			@Param("product_idx") int product_idx, 
			@Param("member_idx") int member_idx);
	//이미 담긴 상품이 있는 경우 CART_count 만큼의 수량을 증가
	int getUpdateCart(
			@Param("product_idx") int product_idx, 
			@Param("member_idx") int member_idx,
			@Param("cart_count") int cart_count);
	//장바구니 -> 구매페이지
	cartVo getCartOrderList(
			@Param("cart_idx") String cart_idx,
			@Param("member_idx") int member_idx);
	int getAmountAdjust(
			@Param("cart_idx") int cart_idx,
			@Param("type") String type,
			@Param("member_idx") int member_idx);
	//다중 주문 처리 작업
	int insertCartOrder(cartVo vo2);
	//상품 수량 빼기 작업
	void updatePorduct_Amount(cartVo vo2);
	//이미 주문한 상품인지 확인 작업
	int getCartOrderCount(cartVo vo2);
	// 주문 수량 더하기 작업
	void updateOrder_Amount(cartVo vo2);
	int insertCartOrderDetail(@Param("order") cartVo vo2,@Param("delivery") OrderdeliveryVo delivery);
}
