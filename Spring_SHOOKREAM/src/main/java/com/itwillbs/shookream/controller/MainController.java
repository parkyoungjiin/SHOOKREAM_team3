package com.itwillbs.shookream.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itwillbs.shookream.service.MainService;
import com.itwillbs.shookream.vo.ProductBean;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	@Autowired 
	private MainService service;
	
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
	}
	
}
