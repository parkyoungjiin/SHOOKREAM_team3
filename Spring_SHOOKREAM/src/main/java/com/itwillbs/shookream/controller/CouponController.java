package com.itwillbs.shookream.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.shookream.service.CouponService;
import com.itwillbs.shookream.service.ProductService;
import com.itwillbs.shookream.vo.CouponVo;

@Controller
public class CouponController {
	
	@Autowired 
	private CouponService service;
	
	@Autowired 
	private ProductService service_product;
	
	
	// 배너 쿠폰 목록
	@RequestMapping(value = "/CouponMainList.po", method= {RequestMethod.GET, RequestMethod.POST})
	public String likeInsert(
			@RequestParam(defaultValue = "1")String coupon_content
			, Model model , HttpSession session
			) {
		
		String sId = (String)session.getAttribute("sId");
		
		int member_idx = service_product.getMemberIdx(sId);
		System.out.println("member_idx = " + member_idx);
		
		
		// 배너 쿠폰 목록
		List<CouponVo> couponList = service.getMainCouponList(coupon_content);
		
		model.addAttribute("couponList", couponList);
		
		// 회원별 쿠폰 보유 검사
		CouponVo member_coupon = service.getMemberCoupon(coupon_content, member_idx);
		
		if(member_coupon != null) {
			model.addAttribute("member_coupon", member_coupon);
		} else {
			model.addAttribute("member_coupon", null);
		}
		
		
		return "main_coupon";
		
	}
	
	@RequestMapping(value = "/CouponDownPro.po", method= {RequestMethod.GET, RequestMethod.POST})
	public void CouponDown(HttpSession session, HttpServletResponse response,
			@RequestParam(defaultValue = "1")String coupon_content) {
		
		String sId = (String)session.getAttribute("sId");
		
		int member_idx = service_product.getMemberIdx(sId);
//		System.out.println("member_idx = " + member_idx);
		
		CouponVo coupon = service.getCouponInfo(coupon_content);
		
		int insertCount = service.downCoupon(member_idx, coupon);
		
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(insertCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
