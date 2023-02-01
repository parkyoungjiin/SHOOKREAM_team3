package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.shookream.vo.ProductBean;
import com.itwillbs.shookream.vo.WishBean;
import com.itwillbs.shookream.vo.imageBean;

@Mapper
public interface WishMapper {
	boolean InsertLike(
			@Param("member_idx") int member_idx
			,@Param("product_idx") int product_idx);

	boolean DeleteWish(
			@Param("member_idx")int member_idx, 
			@Param("product_idx")int product_idx);

	List<ProductBean> getWishList(
			@Param("member_idx")int member_idx,
			@Param("startRow")int startRow, 
			@Param("listLimit")int listLimit
			);

	int getWishListCount(int member_idx);
	
	int updateWishCount(int product_idx);

	int DecWishCount(int product_idx);
	
}
