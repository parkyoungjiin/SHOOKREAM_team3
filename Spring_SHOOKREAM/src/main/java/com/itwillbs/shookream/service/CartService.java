package com.itwillbs.shookream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.CartMapper;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.cartVo;

@Service
public class CartService {
	@Autowired
	private CartMapper mapper;
	//카트 이동
	public List<cartVo> getCartlist(int member_idx, int startRow, int listLimit) {
		return mapper.getCartlist(member_idx, startRow, listLimit);
	}
	//카트 금액 합계
	public int CartTotalPrice(int member_idx) {
		return 0;
	}
	// 페이징 처리
	// 한 페이지에서 표시할 페이지 목록(번호) 갯수 계산
	// 1. BoardListService - selectBoardListCount() 메서드를 호출하여 전체 게시물 수 조회(페이지 목록 계산에 사용)
	// => 파라미터 : 검색어   리턴타입 : int(listCount)
	public int getCartListCount(int member_idx) {
		return 0;
	}
	//장바구니 담기
	public int getInsertCart(int product_idx, int member_idx, int cart_count, ProductVo product) {
		return mapper.getInsertCart(product_idx, member_idx, cart_count, product);
	}
	//product_idx 에 맞는 상품을 조회하여 productVo에 저장
	public ProductVo getProduct(int product_idx) {
		return mapper.getProduct(product_idx);
	}
}
