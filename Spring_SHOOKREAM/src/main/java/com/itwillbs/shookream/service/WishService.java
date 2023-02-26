package com.itwillbs.shookream.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itwillbs.shookream.mapper.WishMapper;
import com.itwillbs.shookream.vo.ProductBean;
import com.itwillbs.shookream.vo.WishBean;
import com.itwillbs.shookream.vo.imageBean;

@Service
public class WishService {
	@Autowired
	private WishMapper mapper;


	public boolean InsertLike(int member_idx, int product_idx) {
		updateWishCount(product_idx);
		return mapper.InsertLike(member_idx,product_idx);
	}


	public boolean DeleteWish(int member_idx, int product_idx) {
		DecWishCount(product_idx);
		return mapper.DeleteWish(member_idx,product_idx);
	}

	public List<ProductBean> getWishList(int startRow, int listLimit, int member_idx) {
		return mapper.getWishList(startRow,listLimit,member_idx);
	}
	public int getWishListCount(int member_idx) {
		return mapper.getWishListCount(member_idx);
	}
	
	public int updateWishCount(int product_idx) {
		return mapper.updateWishCount(product_idx);
	}


	public int DecWishCount(int product_idx) {
		return mapper.DecWishCount(product_idx);
	}
	
	
}
