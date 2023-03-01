package com.itwillbs.shookream.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.shookream.service.WishService;
import com.itwillbs.shookream.vo.PageInfo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.WishVo;
import com.itwillbs.shookream.vo.imageVo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class WishController {
	
	@Autowired 
	private WishService service;
	
	
	@RequestMapping(value = "/LikeInsertPro.ca", method= {RequestMethod.GET, RequestMethod.POST})
	public void likeInsert(@RequestParam(defaultValue = "1")int member_idx,
			@RequestParam(defaultValue = "1")int product_idx,
			HttpServletResponse response
			) {
		
		boolean isSuccess = service.InsertLike(member_idx, product_idx);
		
		try {
			if(isSuccess) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();

				out.println("<script>");
				out.println("alert('찜한 상품에 추가되었습니다')");
				out.println("</script>");
				
				System.out.println("결과 : " + isSuccess);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('찜하기를 실패했습니다')");
				out.println("</script>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/LikeDeletePro.ca", method= {RequestMethod.GET, RequestMethod.POST})
	public String likedelete(@RequestParam(defaultValue = "1")int member_idx,
			@RequestParam(defaultValue = "1")int product_idx,
			HttpServletResponse response
			) {
		
		boolean isSuccess = service.DeleteWish(member_idx, product_idx);
		try {
			if(isSuccess) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();

				out.println("<script>");
				out.println("alert('찜한 상품에서 삭제되었습니다')");
				out.println("</script>");
				
				System.out.println("idDeleteSuccess? : " + isSuccess);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('찜 삭제 실패')");
				out.println("</script>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/LikeList.ca?member_idx"+member_idx;
	}
	
	@GetMapping(value = "LikeList.ca")
	public String likeList(@RequestParam(defaultValue = "1")int member_idx,
						@RequestParam(defaultValue = "1")int pageNum,
						Model model) {
		
		// 페이징 처리를 위한 변수 선언
		int listLimit = 10; // 한 페이지에서 표시할 게시물 목록을 10개로 제한
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산

		List<ProductVo> wishlist = service.getWishList(member_idx,startRow, listLimit);
		
		int listCount = service.getWishListCount(member_idx);
		int pageListLimit = 10; // 한 페이지에서 표시할 페이지 목록을 3개로 제한
		
		int maxPage = listCount / listLimit 
						+ (listCount % listLimit == 0 ? 0 : 1);
		
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		
		int endPage = startPage + pageListLimit - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		model.addAttribute("wishlist",wishlist);
		model.addAttribute("pageInfo",pageInfo);
		
		return "product/Product_wishlist";
	}
	
	
	
}
