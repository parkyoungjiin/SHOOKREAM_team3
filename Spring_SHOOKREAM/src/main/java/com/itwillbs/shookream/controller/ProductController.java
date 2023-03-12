package com.itwillbs.shookream.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.shookream.service.MemberService;
import com.itwillbs.shookream.service.ProductService;
import com.itwillbs.shookream.vo.CouponVo;
import com.itwillbs.shookream.vo.MemberVo;
import com.itwillbs.shookream.vo.OrderVo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.ReviewVo;
import com.itwillbs.shookream.vo.WishVo;
import com.itwillbs.shookream.vo.imageVo;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;
	
	@Autowired
	private MemberService service_member;
	
	
	// 상품 상세 정보
	@GetMapping(value = "/ProductInfoForm.po")
	public String productInfo(
			@ModelAttribute ProductVo product, 
			@ModelAttribute WishVo wish, 
			@ModelAttribute imageVo image,
			@RequestParam(defaultValue = "0") int product_idx,
//			@RequestParam int member_idx,
			Model model, HttpSession session) {
		
		// 임시 값 지정 (추후 수정 필요)
//		int product_idx = product.getProduct_idx();
//		int product_idx = 2;
		
		// ================= wish 정보 조회 ====================
		String sId = (String)session.getAttribute("sId");
//		String sId = "admin";
		
		if(sId != null) {
		// 로그인 후 session 에 member_idx 저장할 시 불필요한 과정
		int member_idx = service.getMemberIdx(sId);
		System.out.println("member_idx = " + member_idx);
		
		// ================= 멤버 Wish 조회 =====================
		wish = service.getWishInfo(product_idx, member_idx);
		
		if(wish != null) {
			model.addAttribute("wish", wish);
		} else {
			model.addAttribute("wish", null);
		}
		System.out.println("wish 조회 : " + wish);
		}
		// =====================================================
		
		
		// ================= 상품 정보 조회 =====================
		product = service.getProduct(product_idx);
		System.out.println("product 조회 : " + product);
		
		image = service.getImage(product_idx);
		System.out.println("image 조회 : " + image);
		
		session.setAttribute("product", product);
		session.setAttribute("image", image);
		
		model.addAttribute("product", product);
		model.addAttribute("image", image);
		
		// ======================================================
		
		
		// ================= 상품별 카테고리 조회 =====================
		//상품별 카테고리 가져오기
		List<String> categorylist =  service.getCategoryList(product.getProduct_name());
		System.out.println("카테고리 : " + categorylist);
		List<String> colorlist = service.getColorList(product.getProduct_name());
		
		//이미지 리스트 출력
		List<imageVo> imagelist = service.getImageList(product.getProduct_name());
		System.out.println(imagelist);
		
		
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("colorlist", colorlist);
		model.addAttribute("imagelist", imagelist);
		// ======================================================
		
		// ================= 리뷰 조회 =====================
		List<ReviewVo> reviewList = service.getReviewList(product.getProduct_idx());
		
		model.addAttribute("reviewList", reviewList);
		// ======================================================
		
		
		return "product/Product_info";
	} // 상세 정보 끝
	
	
	
	
	// 주문 상세 페이지
	@GetMapping(value = "/OrderDetailForm.po")
	public String orderDetail(
			@ModelAttribute ProductVo product, 
			@ModelAttribute WishVo wish, 
			@ModelAttribute imageVo image, 
			@ModelAttribute MemberVo member, 
			@RequestParam(defaultValue = "0") int product_idx,
			Model model, HttpSession session) {
		
		// 임시 값 지정 (추후 수정 필요)
//		int product_idx = 2;
//		int member_idx = 1;
//		String sId = "admin";
		
		String sId = (String)session.getAttribute("sId");		
//		// 로그인 후 session 에 member_idx 저장할 시 불필요한 과정
//		int member_idx = service.getMemberIdx(sId);
		
		product = service.getProduct(product_idx);
		image = service.getImage(product_idx);
		member = service_member.getMemberInfo(sId);
		
		model.addAttribute("product", product);
		model.addAttribute("member", member);
		model.addAttribute("image", image);
		
		return "product/order_form";
	} // 주문 상세 페이지 끝
	
	
	
	
	// 주매 구문
	@GetMapping(value = "/ProductOrderPro.po")
	public String orderPro(@ModelAttribute OrderVo order, Model model, 
			@RequestParam(defaultValue = "0") int coupon_idx,
			@RequestParam(defaultValue = "0") int product_idx,
			@RequestParam(defaultValue = "0") int product_price,
			HttpSession session) {
		
		String sId = (String)session.getAttribute("sId");		
		int member_idx = service.getMemberIdx(sId);
		order.setOrder_member_idx(member_idx);
		order.setOrder_product_idx(product_idx);
		order.setOrder_product_price(product_price);
		
		// 쿠폰 임시 값 지정 (추후 수정 필요)
		if(model.getAttribute("coupon_idx") != "") {
			order.setOrder_coupon_idx(coupon_idx);
		}
		
		System.out.println("구매 order : " + order);
		
		int insertOrder = service.InsertOrder(order);
		
		if(insertOrder > 0) {
			int insertOrder2 = service.InsertOrderDetail(order);
			 
			if(insertOrder2 > 0) {
				service.updatePro(order);
				service.updateMem(order);
			}
			
			return "redirect:/ProductOrderList.po?member_idx"+member_idx;
			
		} else {
			
			model.addAttribute("msg", "일시적 오류로 구매에 실패했습니다.");
			return "fail_back";
		}
		
	} // 주문 구매 끝
	
	
	
	
	// 회원별 쿠폰 리스트
	@GetMapping(value = "/CouponListForm.po")
	public String CouponList(Model model, HttpSession session) {
		
		String sId = (String)session.getAttribute("sId");		
		int member_idx = service.getMemberIdx(sId);
		
		List<CouponVo> couponList = service.getCouponList(member_idx);
		
		model.addAttribute("couponList", couponList);
		
		return "product/Product_couponlist";
	}// 회원별 쿠폰 리스트 끝
	
	
	
	
	// 회원 주문 목록
	@GetMapping(value = "/ProductOrderList.po")
	public String OrderList(Model model, HttpSession session) {
		
		String sId = (String)session.getAttribute("sId");		
		int member_idx = service.getMemberIdx(sId);
		
		List<OrderVo> orderList = service.getOrderList(member_idx);
		
		model.addAttribute("orderList", orderList);
		
		return "product/Product_orderlist";
	}// 회원 주문 목록 끝
			
}
