package com.itwillbs.shookream.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.taglibs.standard.tag.el.fmt.RequestEncodingTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
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
			HttpSession session,
			Model model) {
		//세션이 없을 경우 로그인 페이지로 이동
		if(session.getAttribute("sId") == null || session.getAttribute("member_idx") == null ) {
			model.addAttribute("msg", "로그인이 필요한 페이지입니다.");
			model.addAttribute("url", "LoginMember.me");
			return "reload_cart";
		}
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
			int cart_total_price =0;
			int cart_order_total_price =0;
			System.out.println(cartlist.size());
			//반복문을 통해 cart_price합계 계산
			for(int i=0; i<cartlist.size(); i++) {
				cart_total_price += cartlist.get(i).getCart_price();
				cart_order_total_price += cartlist.get(i).getCart_order_price();
			}
			//장바구니 금액(상품금액, 총 결제금액)
//			int cartTotalPrice = service.getCartTotalPrice(member_idx);
//			System.out.println(cartTotalPrice);
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
		request.setAttribute("cart_total_price", cart_total_price);
		request.setAttribute("cart_order_total_price", cart_order_total_price);
		
		return "product/Product_cart";
	}
	
	
	//	상세페이지에서 장바구니 추가
	@ResponseBody
	@PostMapping(value = "CartInsertPro.ca")
	public void CartInsert(
			@RequestParam("product_idx") int product_idx,
			@RequestParam("cart_count") int cart_count,
			Model model,
			HttpSession session,
			HttpServletResponse response
			){
		
		//세션 아이디와 Member_idx 변수 선언
		String sId = (String)session.getAttribute("sId");
		int member_idx = (int)session.getAttribute("member_idx");
		//product_idx 에 맞는 상품을 조회하여 productVo에 저장
		ProductVo product = service.getProduct(product_idx);
		System.out.println("확인용 " + product);
		//동일한 상품일 경우 수량을 증가
		cartVo cart = service.getCartSelect(product_idx, member_idx);
		//null 이 아닐 경우 -> 이미 담긴 상품이 있는 경우 수량만 추가
		if(cart != null) {
			int updateCount = service.getUpdateCart(product_idx, member_idx, cart_count);
			try {
				if(updateCount > 0) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("이미 담긴상품");
					
				} else {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("장바구니 추가에 실패되었습니다.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//null일 경우 ->담긴 상품이 없기에 새로운 cart를 추가
		}else {
			//장바구니 담기
			int insertCount = service.getInsertCart(product_idx, member_idx, cart_count, product);
			try {
				if(insertCount > 0) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					
					out.print("새상품");
					
				} else {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					
					out.println("<script>");
					out.println("alert('장바구니 등록에 실패되었습니다.')");
					out.println("</script>");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
			
	}//CartInsert 끝 
	
	
	//------장바구니 삭제-------
	@GetMapping(value = "CartDeletePro.ca")
	public String cartDelete(
			@RequestParam("cart_idx") int cart_idx,
			HttpSession session,
			Model model) {
		int member_idx = (int)session.getAttribute("member_idx");
		
		int deleteCount = service.getCartDelete(cart_idx, member_idx);
		if(deleteCount > 0) {
			//작업 성공 후 reload_cart.jsp로 이동하여 msg, url 값에 맞게 alert창 출력 후 url에 저장된 주소로 location.href을 통해 이동
			model.addAttribute("msg", "장바구니에서 삭제되었습니다.");
			model.addAttribute("url", "CartList.ca?pageNum=1");
			return "reload_cart";
		}else {
			//작업 실패 시 reload_cart.jsp로 이동하여 msg, url 값에 맞게 alert창 출력 후 url에 저장된 주소로 location.href을 통해 이동
			model.addAttribute("msg", "장바구니 삭제 실패.");
			model.addAttribute("url", "CartList.ca?pageNum=1");
			return "reload_cart";
			
		}
	}//cartDelete 
	
	//------장바구니 -> 구매페이지-------
	@GetMapping(value = "CartOrderDetail.ca")
	public String cartOrderForm(
			@RequestParam("cart_idx") String cart_idx,
			HttpSession session,
			Model model,
			HttpServletRequest request
			) {
		if(session.getAttribute("sId") == null || session.getAttribute("member_idx") == null ) {
			model.addAttribute("msg", "로그인이 필요한 페이지입니다.");
			model.addAttribute("url", "LoginMember.me");
			return "reload_cart";
		}
		int member_idx = (int)session.getAttribute("member_idx");
		//세션이 없을 경우 로그인 페이지로 이동
		List<cartVo> cartOrderList = new ArrayList<cartVo>();
		System.out.println("cart_idx :" + cart_idx);
		String[] cart_idxArr = cart_idx.split(",");
		System.out.println(cart_idxArr);
		for(int i=0; i<cart_idxArr.length; i++) {
			cart_idx = cart_idxArr[i];
			System.out.println(cart_idx);
			cartOrderList.add(service.getCartOrderlist(cart_idx, member_idx));
		}
		int cart_total_price =0;
		int cart_order_total_price =0;
		System.out.println(cartOrderList.size());
		//반복문을 통해 cart_price합계 계산
		for(int i=0; i<cartOrderList.size(); i++) {
			cart_total_price += cartOrderList.get(i).getCart_price();
			cart_order_total_price += cartOrderList.get(i).getCart_order_price();
		}
		System.out.println(cartOrderList);
		model.addAttribute("cartOrderList", cartOrderList);
		model.addAttribute("cart_total_price", cart_total_price);
		model.addAttribute("cart_order_total_price", cart_order_total_price);
		
		return "product/order_form_cart";
	}
}//CartController 끝
		
	
	

	
	

