package com.itwillbs.shookream.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

import com.itwillbs.shookream.service.AdminService;
import com.itwillbs.shookream.service.BoardService;
import com.itwillbs.shookream.vo.BoardVo;
import com.itwillbs.shookream.vo.MemberVo;
import com.itwillbs.shookream.vo.PageInfo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.imageVo;


@Controller
public class AdminController {
	@Autowired
	private AdminService service;
	@Autowired
	private BoardService service1;
	//관리자 페이지 이동
	@GetMapping(value = "admin.ad")
	public String adminMain() {
		return "admin/admin";
	}
	
	// 상품 및 주문 관리 페이지로 포워딩
	@GetMapping(value="AdminProduct.ad")
		public String adminProd() {
			return "admin/admin_product";
	}
	
	//게시판 관리(공지사항) 페이지로 포워딩 
	@GetMapping(value ="AdminBoard.ad")
		public String adminboard(@ModelAttribute BoardVo board,@RequestParam(defaultValue = "1") int pageNum, String keyword, Model model) {
		int listLimit = 10;
		int startRow = (pageNum - 1) * listLimit;
		String notice_type = "Notice";
		
		if(keyword ==null) {
			keyword="";
		}
		List<BoardVo> boardList= service1.getBoardList(keyword, startRow, listLimit, notice_type);
		int listCount = service1.getBoardListCount(keyword,notice_type);
		System.out.println(boardList);
		
		int pageListLimit =3;
		int maxPage = listCount / listLimit 
				+ (listCount % listLimit == 0 ? 0 : 1); 


		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;

		int endPage = startPage + pageListLimit - 1;

		if(endPage > maxPage) {
			endPage = maxPage;
		}
		PageInfo page = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		model.addAttribute("boardList", boardList);
		model.addAttribute("page", page);
		return "admin/admin_notice_manage";
	}
	// 게시판관리(자주묻는질문) 페이지로 포워딩 
	@GetMapping(value ="AdminFAQ.ad")
	public String adminfaq(@ModelAttribute BoardVo board,@RequestParam(defaultValue = "1") int pageNum, String keyword, Model model) {
		int listLimit =10;
		pageNum =1;
		int startRow = (pageNum -1) * listLimit;
		
		if(keyword ==null) {
			keyword ="";
		}
		String type ="FAQ";
		List<BoardVo> boardList = service1.getBoardList(keyword, startRow, listLimit, type);
		int listCount = service1.getBoardListCount(keyword, type);

		int pageListLimit =3;
		int maxPage = listCount / listLimit 
				+ (listCount % listLimit == 0 ? 0 : 1); 


		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;

		int endPage = startPage + pageListLimit - 1;

		if(endPage > maxPage) {
			endPage = maxPage;
		}
		PageInfo page = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		model.addAttribute("boardList", boardList);
		model.addAttribute("page", page);
	return "admin/admin_FAQ_manage";
}
	
	//================== 현진 =============================
	//============================ 상품 등록 ============================
		// Product insert Form으로 포워딩
		@GetMapping(value = "/ProductInsertForm.po") 
		public String insert() {
			return "admin/admin_product_insert";
		}
		
		// "/ProductInsertPro.po" 비즈니스 로직 - 파일 업로드 기능 추가
		@PostMapping(value = "/ProductInsertPro.po")
		public String insertPro(@ModelAttribute ProductVo product,
								@ModelAttribute imageVo image,
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
			 
			String originalFileNames = ""; // 복수개의 파일을 하나의 이름으로 묶어서 다룰 경우에 사용할 변수 선언
			String realFileNames = ""; // 1개의 파일명을 저장할 변수 선언
			
			// 파일 이동 처리에 사용할 파일명 저장 List 객체 생성
			List<String> realFileNameList = new ArrayList<String>();
			
			// 복수개의 파일에 접근하기 위한 반복문
			for(MultipartFile mFile : mFiles) {
				String originalFileName = mFile.getOriginalFilename();
				
				// 1개의 파일명을 저장할 변수 선언
				String realFileName = "";
				
				// 가져온 파일이 있을 경우에만 중복 방지 대책 수행하기
				if(!originalFileName.equals("")) {
				// 파일명 중복 방지 대책
					String uuid = UUID.randomUUID().toString();
					System.out.println("업로드 될 파일명 : " + uuid + "_" + originalFileName);
					
					// 파일명을 결합하여 보관할 변수에 하나의 파일 문자열 결합
					originalFileNames += originalFileName + "/";
					realFileNames += uuid + "_" + originalFileName + "/";
				} else {
					
					
					// 업로드될 파일명에 1개 파일명을 결합
					realFileNames += realFileName;
					// 각 파일명을 List 객체에도 추가
					// => MultipartFile 객체를 통해 실제 폴더로 이동 시킬 때 사용
					realFileNameList.add(realFileName);
					
					// 파일이 존재하지 않을 경우 널스트링으로 대체(뒤에 슬래시 포함)
//							originalFileNames += "/";
//							realFileNames += "/";

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
//						System.out.println(image);
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
				return "redirect:/ProductList.po";
			} else { // 실패
				// 등록 폼으로 다시 이동
				model.addAttribute("msg", "상품 등록에 실패했습니다.");
				return "fail_back";
			}
			
		}		
				
	//============================= 상품 등록 끝 ============================
	
	//============================= 상품 수정 ================================
		@GetMapping(value="/ProductModifyForm.po")
		public String modify(@RequestParam int product_idx
							, Model model) {
			
			// product idx 조회하기
			ProductVo product = service.getProduct(product_idx);
			// idx 일치하는 정보 가져오기
			model.addAttribute("product", product);
			
			
			imageVo image =service.getImage(product_idx);
			model.addAttribute("image", image);
			
			return "admin/admin_Product_ModifyForm";
		}
		
//		// "/ProductModifyPro.po" 비즈니스 로직1
//		@PostMapping(value = "/ProductModifyPro.po")
//		public String ModifyPro(@RequestParam(defaultValue = "1") int product_idx,
////								@RequestParam(defaultValue = "") String product_name,
////								@RequestParam(defaultValue = "") String product_brand,
////								@RequestParam(defaultValue = "") String product_size,
//								@ModelAttribute ProductVo product,
//								@ModelAttribute imageVo image,
//								Model model,
//								HttpSession session) {
//			System.out.println(product_idx);
//			System.out.println(product);
//			System.out.println(image);
//			
//			int updateCount = service.updateProduct(product_idx, product, image);
////			int updateCount = service.updateProduct(product_idx,product_name, product_brand,product_size, product, image);
//			
//			// 등록 성공/실패에 따른 포워딩 작업 수행
//			if(updateCount > 0) { // 성공
//				// 메인페이지로 리다이렉트
//				return "redirect:/";
//			} else { // 실패
//				return "admin/admin_Product_ModifyForm";
//			}
//		}
		
		// "/ProductModifyPro.po" 비즈니스 로직2(new!)
		@PostMapping(value="/ProductModifyPro.po")
		public String ModifyPro(
				@ModelAttribute ProductVo product
				, @ModelAttribute imageVo image
				, @RequestParam(value = "file", required = false) MultipartFile file
				, HttpSession session
				, Model model) {
			
			System.out.println("*** image.getImage_main_file() : " + image.getImage_main_file());
			// ------------------- 이미지 수정 -----------------------------
			// 경로 설정
			String uploadDir = "/resources/upload";
			String saveDir = session.getServletContext().getRealPath(uploadDir);
			System.out.println("실제 업로드 경로 : " + saveDir);
			
			// -------------- java.nio 패키지(Files, Path, Paths) 객체 활용 ---------------------------
			Path path = Paths.get(saveDir);
			try {
				Files.createDirectories(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//----------------------------------------
			
			// 기존 파일명 설정
//			String image_main_file = image.getImage_main_file();
			
			MultipartFile[] mFiles = image.getFiles();
			 
			String originalFileNames = ""; // 복수개의 파일을 하나의 이름으로 묶어서 다룰 경우에 사용할 변수 선언
			String realFileNames = ""; // 1개의 파일명을 저장할 변수 선언
			
			// 파일 이동 처리에 사용할 파일명 저장 List 객체 생성
			List<String> realFileNameList = new ArrayList<String>();
			
			// 복수개의 파일에 접근하기 위한 반복문
			for(MultipartFile mFile : mFiles) {
				String originalFileName = mFile.getOriginalFilename();
				
				// 1개의 파일명을 저장할 변수 선언
				String realFileName = "";
				
				// 가져온 파일이 있을 경우에만 중복 방지 대책 수행하기
				if(!originalFileName.equals("")) {
				// 파일명 중복 방지 대책
					String uuid = UUID.randomUUID().toString();
					System.out.println("업로드 될 파일명 : " + uuid + "_" + originalFileName);
					
					// 파일명을 결합하여 보관할 변수에 하나의 파일 문자열 결합
					originalFileNames += originalFileName + "/";
					realFileNames += uuid + "_" + originalFileName + "/";
					
				} else {
					// 업로드될 파일명에 1개 파일명을 결합
					realFileNames += realFileName;
					// 각 파일명을 List 객체에도 추가
					// => MultipartFile 객체를 통해 실제 폴더로 이동 시킬 때 사용
					realFileNameList.add(realFileName);
					
				}
			}
			// productVO 객체에 원본 파일명과 업로드 될 파일명 저장
			image.setImage_main_file(originalFileNames);
			image.setImage_real_file1(realFileNames);
			System.out.println("원본 파일명 : " +image.getImage_main_file());
			System.out.println("업로드 될 파일명 : " + image.getImage_real_file1());
			// ------------------ 이미지 수정 코드 끝 ------------------------
			
			int updateCount = service.updateProduct(0, product);
			
			// 등록 성공/실패에 따른 포워딩 작업 수행
			if(updateCount > 0) { // 성공시
				
				// 파일을 새로 등록한 경우
				if(file != null) { //file 객체가 null이 아닌 경우에만 처리
					if(!file.isEmpty()) { // 파일을 선택한 경우
				//이미지 저장
				int updateImage = service.updateImage(updateCount, product, image);
//						System.out.println(image);
				if(updateImage > 0) { // 성공
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
					} else { // 파일을 선택하지 않은 경우
						String image_main_file = product.getImage_main_file(); // 기존 파일명으로 유지
					}
					
				} else { // file 객체가 null인 경우
					String image_main_file = product.getImage_main_file(); // 기존 파일명으로 유지
				}
				
				// 메인페이지로 리다이렉트
				model.addAttribute("msg", "상품 등록이 완료되었습니다.");
				return "redirect:/ProductList.po";
			} else { // 실패
				// 등록 폼으로 다시 이동
				model.addAttribute("msg", "상품 등록에 실패했습니다.");
				return "fail_back";
			}
			
		}
		
	//============================= 상품 수정 끝 ================================
			
	//============================= 상품 삭제 ============================= 
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
	//============================= 상품 삭제 끝 =============================
			

			
//			 상품 목록 조회
			@GetMapping(value="/ProductListForm.po")
			public String getProductList(Model model) {
//				System.out.println("product : " + product);
//				System.out.println("image : " + image);
//				
				List<ProductVo> productList = service.getProductList();
				model.addAttribute("productList", productList);
				System.out.println("productList" + productList);
				
//				imageVo imgList = service.getImgList(product.getProduct_idx());
//				model.addAttribute("image", image);
//				model.addAttribute("imgList", imgList);
//				System.out.println("imgList" + imgList);
				
				return "admin/admin_Product_List";
			}
			
      //============================= 상품 목록 ================================
			@GetMapping(value = "/ProductList.po")
			public String productList(@ModelAttribute ProductVo product,
//									  @ModelAttribute imageVo img,
									  @RequestParam(defaultValue = "0") int product_idx,
									  HttpSession session, Model model) {

				String id = (String)session.getAttribute("sId");
				
				if(id == null || id.equals("") || !id.equals("admin")) { 
					model.addAttribute("msg", "잘못된 접근입니다!");
					return "redirect:/Admin.ad";
				} else {//admin일 경우
					// Service 객체의 getProductList() 메서드를 호출하여 전체 회원 목록 조회
					// => 파라미터 : 없음   리턴타입 : List<ProductVO>(productList)
					List<ProductVo> productList = service.getProductList();
					
					// *** image_main_file에 있는 세 파일 중 맨 앞 파일만 가져와서 list에 보여주기 (split) ***
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
					
					for(ProductVo product1 : productList) {
					        // 일치하는 상품을 찾았을 때 해당 상품에 대한 이미지를 조회
					        List<imageVo> imageList = service.getImgList(product1.getProduct_idx());
					        String fileNames = imageList.get(0).getImage_main_file();
					        String[] splitFileNames = fileNames.split("/");
					        String firstFileName = splitFileNames[0]; // 맨 앞 파일명
					        System.out.println("fileNames :: " + fileNames);
					        
					        product1.setImage_main_file(firstFileName);
					    }
					
					// Model 객체에 "productList" 속성명으로 조회된 회원 목록(List 객체) 저장
					model.addAttribute("productList", productList);

					// admin_Product_List.jsp로 포워딩
					return "admin/admin_Product_List";
				}
			}

		//============================= 상품 목록 ================================


//================== 끝 =============================
			
	//---------관리자 회원 & 쿠폰관리 메인--------
	@GetMapping("AdminMemberCoupon.ad")	
	public String adMember() {
		return "admin/admin_coupon_and_member";
	}
	
	//--------- 회원목록------------------
	@GetMapping("MemberList.me")
	public String adMemberList(Model model) {
		List<MemberVo> member = service.getMemberInfo();
		model.addAttribute("member",member);
		return "admin/admin_Member_List";
	}
	
	//----------- 쿠폰 등록 ----------------------
	@GetMapping("CouponInsertForm.po") 
	public String insertCouponForm() {
		return "admin/admin_coupon_insert";
	}
	
	// ------------ 쿠폰 목록 --------------------
	@GetMapping("CouponList.po")
	public String couponList() {
		return "admin/admin_coupon_list";
	}
	
	//------------쿠폰 수정------------------------
}//AdminController

		
	


	
	

