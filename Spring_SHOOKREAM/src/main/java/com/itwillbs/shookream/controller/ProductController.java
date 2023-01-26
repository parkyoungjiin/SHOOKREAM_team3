package com.itwillbs.shookream.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.shookream.service.MemberService;
import com.itwillbs.shookream.service.ProductService;
import com.itwillbs.shookream.vo.MemberBean;
import com.itwillbs.shookream.vo.OrderBean;
import com.itwillbs.shookream.vo.ProductBean;
import com.itwillbs.shookream.vo.WishBean;
import com.itwillbs.shookream.vo.imageBean;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;
	
	@Autowired
	private MemberService service_member;
	
	@GetMapping(value = "/ProductInfoForm.po")
	public String productInfo(
			@ModelAttribute ProductBean product, 
			@ModelAttribute WishBean wish, 
			@ModelAttribute imageBean image,
			@RequestParam(defaultValue = "0") int product_idx,
//			@RequestParam int member_idx,
			Model model, HttpSession session) {
		
		// 임시 값 지정 (추후 수정 필요)
//		int product_idx = product.getProduct_idx();
//		int product_idx = 2;
		
		// ================= wish 정보 조회 ====================
		// 임시 값 지정 (추후 수정 필요)
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
		
		model.addAttribute("product", product);
		model.addAttribute("image", image);
		// ======================================================
		
		
		// ================= 상품별 카테고리 조회 =====================
		//상품별 카테고리 가져오기
		List<String> categorylist =  service.getCategoryList(product.getProduct_name());
		System.out.println("카테고리 : " + categorylist);
		List<String> colorlist = service.getColorList(product.getProduct_name());
		
		//이미지 리스트 출력
		List<imageBean> imagelist = service.getImageList(product.getProduct_name());
		System.out.println(imagelist);
		
		model.addAttribute("categorylist", categorylist);
		model.addAttribute("colorlist", colorlist);
		model.addAttribute("imagelist", imagelist);
		// ======================================================
		
		
		return "product/Product_info";
	}
	
	// 주문 상세 페이지
	@GetMapping(value = "/OrderDetailForm.po")
	public String orderDetail(
			@ModelAttribute ProductBean product, 
			@ModelAttribute WishBean wish, 
			@ModelAttribute imageBean image, 
			@ModelAttribute MemberBean member, 
//			@RequestParam(defaultValue = "0") int product_idx,
			Model model, HttpSession session) {
		
		// 임시 값 지정 (추후 수정 필요)
		int product_idx = 2;
		int member_idx = 1;
		String sId = "admin";
		
//		String sId = (String)session.getAttribute("sId");		
//		// 로그인 후 session 에 member_idx 저장할 시 불필요한 과정
//		int member_idx = service.getMemberIdx(sId);
		
		product = service.getProduct(product_idx);
		image = service.getImage(product_idx);
		member = service_member.getMemberInfo(sId);
		
		model.addAttribute("product", product);
		model.addAttribute("member", member);
		model.addAttribute("image", image);
		
		return "product/order_form";
	}
	
	// 주매 구문
	@PostMapping(value = "/ProductOrderPro.po")
	public String orderPro(@ModelAttribute OrderBean order, Model model, HttpSession session) {
		
		// 임시 값 지정 (추후 수정 필요)
		int member_idx = 1;
		order.setOrder_member_idx(member_idx);
		
		// 임시 값 지정 (추후 수정 필요)
		if(model.getAttribute("coupon_idx") != "") {
			order.setOrder_coupon_idx(1);
		}
		
		boolean result = service.InsertOrder(order);
		
		
		
		return "redirect:/ProductOrderList.po";
	}
	
}
