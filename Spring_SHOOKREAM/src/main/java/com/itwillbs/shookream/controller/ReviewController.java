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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itwillbs.shookream.service.MainService;
import com.itwillbs.shookream.service.ReviewService;
import com.itwillbs.shookream.vo.ProductVo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ReviewController {
	
	@Autowired 
	private ReviewService service;
	
	@GetMapping(value = "/MemberMyPage.me")
	public String mypage() {
		return "member/my_page";
	}
	
	
	
}
