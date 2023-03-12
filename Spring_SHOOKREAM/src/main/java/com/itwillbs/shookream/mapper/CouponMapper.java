package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.CouponVo;

@Mapper
public interface CouponMapper {

	// 메인 쿠폰 목록
	CouponVo selectMainCoupon(@Param("coupon_content")String coupon_content,@Param("member_idx") int member_idx);

	List<CouponVo> selectMainCouponList(String coupon_content);

	CouponVo selectCoupon(String coupon_content);

	int insertCoupon(@Param("member_idx")int member_idx, @Param("coupon")CouponVo coupon);

}
