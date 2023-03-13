package com.itwillbs.shookream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.AdminMapper;
import com.itwillbs.shookream.vo.CouponVo;
import com.itwillbs.shookream.vo.MemberVo;
import com.itwillbs.shookream.vo.OrderVo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.imageVo;

@Service
public class AdminService {
	@Autowired
	private AdminMapper mapper;
	
	// 상품 등록 insertProduct() 메서드
		// => 파라미터 : BoardVO 객체    리턴타입 : int(insertCount)
		public int insertProduct(ProductVo product) {
			return mapper.insertProduct(product);
		}
		
		// 상품 이미지 등록 insertProduct() 메서드
		// => 파라미터 : BoardVO 객체    리턴타입 : int(insertCount)
		public int insertImg(imageVo image, int product_idx) {
			return mapper.insertImage(image, product_idx);
		}
		
		// 상품 수정 updateProduct() 메서드
		public int updateProduct(int product_idx, ProductVo product) {
			return mapper.modifyProduct(product_idx, product);
		}
		
		// 상품 수정 - 이미지 수정 updateImage() 메서드
		public int updateImage(int product_idx, ProductVo product, imageVo image) {
			return mapper.modifyImage(product_idx, product, image);
		}

		// 상품 삭제 deleteProduct() 메서드
		public int deleteProduct(int product_idx) {
			return mapper.removeProduct(product_idx);
		}
		// 상품 목록 조회 - getProductList()
		public List<ProductVo> getProductList() {
			return mapper.selectProductList();
		}
		// 이미지 목록 조회 
		public List<imageVo> getImgList(int product_idx) {
			return mapper.selectImgList(product_idx);
		}
//		public List<imageVo> getImgList() {
//			return mapper.selectImgList();
//		}
		
		public ProductVo getProduct(int product_idx) {
			return mapper.selectProduct(product_idx);
		}

		public imageVo getImage(int product_idx) {
			return mapper.selectImage(product_idx);
		}
		
		//회원목록
		public List<MemberVo> getMemberInfo() {
			return mapper.selectMember();
		}

		//주문목록
		public List<OrderVo> getOrderList() {
			return mapper.selectOrderList();
		}
		
		//주문목록 - 삭제
		public int deleteOrder(int order_idx) {
			return mapper.delectOrder(order_idx);
		}

		//쿠폰목록
		public List<CouponVo> getCouponList() {
			return mapper.selectCouponList();
		}

		//쿠폰수정폼
		public CouponVo getCouponInfo(int coupon_idx) {
			return mapper.selectCoupon(coupon_idx);
		}

		//쿠폰수정
		public int updateCoupon(int coupon_idx, CouponVo coupon) {
			return mapper.updateCoupon(coupon_idx, coupon);
		}

		//쿠폰삭제
		public int deleteCoupon(int coupon_idx) {
			return mapper.deletCoupon(coupon_idx);
		}

		//쿠폰등록
		public int insertCoupon(CouponVo coupon) {
			return mapper.insertCoupon(coupon);
		}
	
}
