package com.itwillbs.shookream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.CouponMapper;
import com.itwillbs.shookream.vo.CouponVo;

@Service
public class CouponService {

	@Autowired 
	private CouponMapper mapper;
	
	// 메인 쿠폰 목록
	public CouponVo getMemberCoupon(String coupon_content, int member_idx) {
		return mapper.selectMainCoupon(coupon_content, member_idx);
	}

	public List<CouponVo> getMainCouponList(String coupon_content) {
		return mapper.selectMainCouponList(coupon_content);
	}

	// 쿠폰 다운 (조회)
	public CouponVo getCouponInfo(String coupon_content) {
		return mapper.selectCoupon(coupon_content);
	}

	// 쿠폰 다운 
	public int downCoupon(int member_idx, CouponVo coupon) {
		return mapper.insertCoupon(member_idx, coupon);
	}
}
