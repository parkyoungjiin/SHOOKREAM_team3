package com.itwillbs.shookream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.ProductMapper;
import com.itwillbs.shookream.vo.OrderVo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.WishVo;
import com.itwillbs.shookream.vo.imageVo;

@Service
public class ProductService {
	@Autowired
	private ProductMapper mapper;
	
	public int getMemberIdx(String sId) {
		return mapper.selectMemberIdx(sId);
	}

	public WishVo getWishInfo(int product_idx, int member_idx) {
		return mapper.selectWish(product_idx, member_idx);
		
	}

	public ProductVo getProduct(int product_idx) {
		return mapper.selectProduct(product_idx);
	}

	public imageVo getImage(int product_idx) {
		return mapper.selectImage(product_idx);
	}

	public List<String> getCategoryList(String product_name) {
		return mapper.selectCategoryList(product_name);
	}

	public List<String> getColorList(String product_name) {
		return mapper.selectColorList(product_name);
	}

	public List<imageVo> getImageList(String product_name) {
		return mapper.selectImageList(product_name);
	}

	public boolean InsertOrder(OrderVo order) {
		return mapper.insertOrder(order);
	}

}
