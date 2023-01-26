package com.itwillbs.shookream.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.shookream.service.MainService;
import com.itwillbs.shookream.vo.PageInfo;
import com.itwillbs.shookream.vo.ProductBean;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	@Autowired 
	private MainService service;
	//-----------------------메인 페이지--------------------------------
	@RequestMapping(value = "/main.ma", method = RequestMethod.GET)
	public String main(Model model, HttpServletRequest request, ProductBean product) {
		int listLimit = 16; // 한 페이지에서 표시할 게시물 목록을 10개로 제한
		int pageNum = 1; // 현재 페이지 번호 설정(pageNum 파라미터 사용)
		
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}

		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
		
		// 베스트 상품 가져오기
		List<ProductBean> productBestList = service.getProductBestList(startRow, listLimit);
		model.addAttribute("productBestList", productBestList);
		
		// 최근 등록 상품 가져오기
		List<ProductBean> productNewList = service.getProductNewList(startRow, listLimit);
		model.addAttribute("productNewList", productNewList);
		
		return "main";
	}//main 끝
	
	//---------------------Best 페이지--------------------------
	@GetMapping(value = "Best.ma")
	public String best(Model model, HttpServletRequest request, ProductBean product) {
		// 페이징 처리를 위한 변수 선언
		int listLimit = 16; // 한 페이지에서 표시할 게시물 목록을 10개로 제한
		int pageNum = 1; // 현재 페이지 번호 설정(pageNum 파라미터 사용)

		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}

		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
		
		List<ProductBean> productBestList = service.getProductBestList(startRow, listLimit);
		model.addAttribute("productBestList", productBestList);
		
		// ------------------페이징 처리----------------------
		// => 파라미터 : x   리턴타입 : int(listCount)
		//1. 목록 개수
		int listCount = service.getProductListCount();

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
		model.addAttribute("pageInfo", pageInfo);
		

		return "main_best";
	}//best 끝
	
//------------------------------New(최근 상품) 페이지--------------------------------------
	@GetMapping(value = "New.ma")
	public String New(Model model, HttpServletRequest request, ProductBean product) {
		// 페이징 처리를 위한 변수 선언
				int listLimit = 16; // 한 페이지에서 표시할 게시물 목록을 10개로 제한
				int pageNum = 1; // 현재 페이지 번호 설정(pageNum 파라미터 사용)
			
				if(request.getParameter("pageNum") != null) {
					pageNum = Integer.parseInt(request.getParameter("pageNum"));
				}
			
				int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
				
				List<ProductBean> productNewList = service.getProductNewList(startRow, listLimit);
				model.addAttribute("productNewList", productNewList);
				
				// ------------------페이징 처리----------------------
				// => 파라미터 : x   리턴타입 : int(listCount)
				//1. 목록 개수
				int listCount = service.getProductListCount();
			
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
				model.addAttribute("pageInfo", pageInfo);
				
			
				return "main_new";
	}//New 끝
	
//------------------------------Sale 페이지--------------------------------------
	@GetMapping(value = "Sale.ma")
	public String sale(Model model, HttpServletRequest request, ProductBean product) {
		// 페이징 처리를 위한 변수 선언
		int listLimit = 16; // 한 페이지에서 표시할 게시물 목록을 10개로 제한
		int pageNum = 1; // 현재 페이지 번호 설정(pageNum 파라미터 사용)
	
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
	
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
		
		List<ProductBean> productSaleList = service.getProductSaleList(startRow, listLimit);
		model.addAttribute("productSaleList", productSaleList);
		
		// ------------------페이징 처리----------------------
		// => 파라미터 : x   리턴타입 : int(listCount)
		//1. 목록 개수
		int listCount = service.getProductListCount();
	
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
		model.addAttribute("pageInfo", pageInfo);
		
	
		return "main_sale";
	}//sale 끝
	//--------------브랜드별 포워딩----------------
	@GetMapping(value = "BrandCG.ma")
	public String brand(Model model, HttpServletRequest request) {
		String cg = request.getParameter("cg");
		// 페이징 처리를 위한 변수 선언
			int listLimit = 16; // 한 페이지에서 표시할 게시물 목록을 10개로 제한
			int pageNum = 1; // 현재 페이지 번호 설정(pageNum 파라미터 사용)

			if (request.getParameter("pageNum") != null) {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			}

			int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산

			if (cg != null) { // 브랜드별 카테고리 이동

				List<ProductBean> productList = service.getProductCGList(cg, startRow, listLimit);
				model.addAttribute("productList", productList);
				
				int listCount = service.getProductCgListCount(cg);
				
				int pageListLimit = 10; // 한 페이지에서 표시할 페이지 목록을 3개로 제한
				
				int maxPage = listCount / listLimit 
								+ (listCount % listLimit == 0 ? 0 : 1);
				
				int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
				
				int endPage = startPage + pageListLimit - 1;
				
				if(endPage > maxPage) {
					endPage = maxPage;
				}
					
				PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
				model.addAttribute("pageInfo", pageInfo); 
			}		
		return "main_cg";
	}//BRANDCG 끝
			
	//----------------------------------검색창(Keyword)----------------------------------------
	@GetMapping(value = "keyword.ma")
	public String search(@RequestParam("keyword") String keyword, Model model, HttpServletRequest request, ProductBean product) {
		
		
		
		// 페이징 처리를 위한 변수 선언
		int listLimit = 16; // 한 페이지에서 표시할 게시물 목록을 10개로 제한
		int pageNum = 1; // 현재 페이지 번호 설정(pageNum 파라미터 사용)
	
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
	
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
		
		List<ProductBean> productList = service.getProductSearchList(keyword, startRow, listLimit);
		model.addAttribute("productList", productList);
		
		// ------------------페이징 처리----------------------
		// => 파라미터 : x   리턴타입 : int(listCount)
		//1. 목록 개수
		int listCount = service.getProductListCount();
	
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
		model.addAttribute("pageInfo", pageInfo);
		
	
		return "main_keyword";
	}//sale 끝
	
}//MainController
