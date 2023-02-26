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
	
	//================== 현진 =============================
		// 상품 등록
		// Product insert Form으로 포워딩
		@GetMapping(value = "/ProductInsertForm.po") 
		public String insert() {
			return "admin/admin_product_insert";
		}
		
		// "/ProductInsertPro.po" 비즈니스 로직 - 파일 업로드 기능 추가
		@PostMapping(value = "/ProductInsertPro.po")
		public String insertPro(@ModelAttribute ProductBean product,
								@ModelAttribute imageBean image,
								Model model,
								HttpSession session) {

			String uploadDir = "/resources/upload";
			String saveDir = session.getServletContext().getRealPath(uploadDir);
			System.out.println("실제 업로드 경로 : " + saveDir);
			System.out.println(image);
			// -------------- java.nio 패키지(Files, Path, Paths) 객체 활용 ---------------------------
			Path path = Paths.get(saveDir);
			
			try {
				Files.createDirectories(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//----------------------------------------
			
			MultipartFile[] mFiles = image.getFiles();
			
			String originalFileNames = "";
			String realFileNames = "";
			
			// 복수개의 파일에 접근하기 위한 반복문
			for(MultipartFile mFile : mFiles) {
				String originalFileName = mFile.getOriginalFilename();
				if(!originalFileName.equals("")) {
				// 파일명 중복 방지 대책
					String uuid = UUID.randomUUID().toString();
					System.out.println("업로드 될 파일명 : " + uuid + "_" + originalFileName);
					
					// 파일명을 결합하여 보관할 변수에 하나의 파일 문자열 결합
					originalFileNames += originalFileName + "/";
					realFileNames += uuid + "_" + originalFileName + "/";
				} else {
					// 파일이 존재하지 않을 경우 널스트링으로 대체(뒤에 슬래시 포함)
					originalFileNames += "/";
					realFileNames += "/";
				}
			}
			// productVO 객체에 원본 파일명과 업로드 될 파일명 저장
			image.setImage_main_file(originalFileNames);
			image.setImage_real_file1(realFileNames);
			System.out.println("원본 파일명 : " +image.getImage_main_file());
			System.out.println("업로드 될 파일명 : " + image.getImage_real_file1());
			
			// ProductService 객체의 insertProduct() 메서드 호출
			int insertCount = service.insertProduct(product);
			
			// 등록 성공/실패에 따른 포워딩 작업 수행
			if(insertCount > 0) { // 성공시
				//이미지 저장
				int insertImage = service.insertImg(image, product.getProduct_idx());
//				System.out.println(image);
				if(insertImage > 0) { // 성공
					try {
						
						for(int i = 0; i < image.getFiles().length; i++) {
							MultipartFile mFile = image.getFiles()[i];
							System.out.println("MultipartFile : " + mFile.getOriginalFilename());
							
							// 가져온 파일이 있을 경우에만 파일 이동 작업 수행
							if(!mFile.getOriginalFilename().equals("")) {
								mFile.transferTo(
									new File(saveDir, image.getImage_real_file1().split("/")[i])
								);
							}
						}
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				// 메인페이지로 리다이렉트
				model.addAttribute("msg", "상품 등록이 완료되었습니다.");
				return "admin/admin_product_insert";
			} else { // 실패
				// 등록 폼으로 다시 이동
				model.addAttribute("msg", "상품 등록에 실패했습니다.");
				return "admin/admin_product_insert";
			}
			
		}		
		
		// 상품 수정
		@GetMapping(value="/ProductModifyForm.po")
		public String modify(@RequestParam int product_idx
							, Model model) {
			
			// product idx 조회하기
			ProductBean product = service.getProduct(product_idx);
			// idx 일치하는 정보 가져오기
			model.addAttribute("product", product);
			
			
			imageBean image =service.getImage(product_idx);
			model.addAttribute("image", image);
			
			return "admin/admin_Product_ModifyForm";
		}
		
		// "/ProductModifyPro.po" 비즈니스 로직
		@PostMapping(value = "/ProductModifyPro.po")
		public String ModifyPro(@RequestParam int product_idx, @ModelAttribute ProductBean product, 
							Model model, @ModelAttribute imageBean image) {
			System.out.println(product_idx);
			System.out.println(product);
			System.out.println(image);
			
			int updateCount = service.updateProduct(product_idx, product, image);
			
			// 등록 성공/실패에 따른 포워딩 작업 수행
			if(updateCount > 0) { // 성공
				// 메인페이지로 리다이렉트
				return "redirect:/";
			} else { // 실패
				return "admin/admin_Product_ModifyForm";
			}
		}
		
		// 상품 삭제
		@PostMapping(value = "/ProductDeletePro.po")
		public String deleteProduct(@RequestParam(defaultValue = "1") int product_idx, 
									Model model, HttpSession session) {
			
			int deleteCount = service.deleteProduct(product_idx);
			
			if(deleteCount > 0) {
				model.addAttribute("msg", "상품이 삭제되었습니다.");
				return "redirect:/";
			} else {
				model.addAttribute("msg", "상품 삭제 실패!");
				return "fail_back";
			}
			
			
			
		}
		
		
		// 상품 목록 조회
		@GetMapping(value="/ProductListForm.po")
		public String getProductList(Model model) {
//			System.out.println("product : " + product);
//			System.out.println("image : " + image);
//			
			List<ProductBean> productList = service.getProductList();
			model.addAttribute("productList", productList);
//			System.out.println("productList" + productList);
			
//			imageBean imgList = service.getImgList(product.getProduct_idx());
//			model.addAttribute("image", image);
//			model.addAttribute("imgList", imgList);
//			System.out.println("imgList" + imgList);
			
			return "admin/admin_Product_List";
		}
		
		
		@PostMapping(value = "/ProductList.po")
		public String productList(@ModelAttribute ProductBean product, HttpSession session, Model model) {
			String id = (String)session.getAttribute("sId");
			
			if(id == null || id.equals("") || !id.equals("admin")) { 
				model.addAttribute("msg", "잘못된 접근입니다!");
				return "redirect:/ProductList.po";
			} else {//admin일 경우
				// Service 객체의 getProductList() 메서드를 호출하여 전체 회원 목록 조회
				// => 파라미터 : 없음   리턴타입 : List<ProductVO>(productList)
				List<ProductBean> productList = service.getProductList();
				
				// Model 객체에 "productList" 속성명으로 조회된 회원 목록(List 객체) 저장
				model.addAttribute("productList", productList);
				
				// 관리자 메인페이지(임시로 member_list.jsp) 로 포워딩
				return "member/member_list";
			}
		}


			//================== 끝 =============================
	
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
