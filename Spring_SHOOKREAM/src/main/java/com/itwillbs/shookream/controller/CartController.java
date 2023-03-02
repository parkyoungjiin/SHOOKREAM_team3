package com.itwillbs.shookream.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.taglibs.standard.tag.el.fmt.RequestEncodingTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.HttpResource;

import com.itwillbs.shookream.service.BoardService;
import com.itwillbs.shookream.service.CartService;
import com.itwillbs.shookream.vo.BoardVo;
import com.itwillbs.shookream.vo.PageInfo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.cartVo;


@Controller
public class CartController {
	@Autowired
	private CartService service;
	
	//장바구니 이동
	@GetMapping(value = "CartList.ca")
	public String cartList(
			@RequestParam() int pageNum,
			HttpServletResponse response,
			HttpServletRequest request,
			HttpSession session) {
		//세션 아이디와 Member_idx 변수 선언
		String sId = (String)session.getAttribute("sId");
		int member_idx = (int)session.getAttribute("member_idx");
		// 페이징 처리를 위한 변수 선언
		int listLimit = 10; // 한 페이지에서 표시할 게시물 목록을 10개로 제한
		pageNum = 1; // 현재 페이지 번호 설정(pageNum 파라미터 사용)

		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
		//	System.out.println("startRow = " + startRow);
		// ---------------------------------------------------------
		// 파라미터로 전달받은 검색어(keyword) 가져와서 변수에 저장

			
			List<cartVo> cartlist = service.getCartlist(member_idx,startRow,listLimit);
			
			// 합계 가격
			int total = service.CartTotalPrice(member_idx);
			
			// 페이징 처리
			// 한 페이지에서 표시할 페이지 목록(번호) 갯수 계산
			// 1. BoardListService - selectBoardListCount() 메서드를 호출하여 전체 게시물 수 조회(페이지 목록 계산에 사용)
			// => 파라미터 : 검색어   리턴타입 : int(listCount)
			int listCount = service.getCartListCount(member_idx);
			//	System.out.println("총 게시물 수 : " + listCount);
			
			// 2. 한 페이지에서 표시할 페이지 목록 갯수 설정
			int pageListLimit = 10; // 한 페이지에서 표시할 페이지 목록을 3개로 제한
			
			// 3. 전체 페이지 목록 수 계산
			int maxPage = listCount / listLimit 
							+ (listCount % listLimit == 0 ? 0 : 1);
			
			// 4. 시작 페이지 번호 계산
			int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
			
			// 5. 끝 페이지 번호 계산
			int endPage = startPage + pageListLimit - 1;
			
			// 6. 만약, 끝 페이지 번호(endPage)가 전체(최대) 페이지 번호(maxPage) 보다
			//    클 경우, 끝 페이지 번호를 최대 페이지 번호로 교체
			if(endPage > maxPage) {
				endPage = maxPage;
			}
				
				// PageInfo 객체 생성 후 페이징 처리 정보 저장
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("cartlist", cartlist);
		request.setAttribute("total", total);
		
		return "product/Product_cart";
	}
	
	
	//	상세페이지에서 장바구니 추가
	@ResponseBody
	@PostMapping(value = "CartInsertPro.ca")
	public String CartInsert(
			@RequestParam("product_idx") int product_idx,
			@RequestParam("cart_count") int cart_count,
			Model model,
			HttpSession session){
		//세션 아이디와 Member_idx 변수 선언
		String sId = (String)session.getAttribute("sId");
		int member_idx = (int)session.getAttribute("member_idx");
		//product_idx 에 맞는 상품을 조회하여 productVo에 저장
		ProductVo product = service.getProduct(product_idx);
		System.out.println("확인용 " + product);
		//장바구니 담기
		int insertCount = service.getInsertCart(product_idx, member_idx, cart_count, product);
		if(insertCount > 0) {
			model.addAttribute("msg", "장바구니 담기에 성공했습니다.");
			return "reload_cart";
		}else {
			model.addAttribute("msg", "장바구니 담기에 실패했습니다.");
			return "reload_cart";
		}
	}//CartInsert 끝 

}//CartController 끝
		
	
	

	
	

