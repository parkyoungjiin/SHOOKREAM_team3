package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.OrderVo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.WishVo;
import com.itwillbs.shookream.vo.imageVo;


public interface ProductMapper {
	
	public int selectMemberIdx(String sId);

	public WishVo selectWish(@Param("product_idx") int product_idx, 
			@Param("member_idx") int member_idx);

	public ProductVo selectProduct(int product_idx);

	public imageVo selectImage(int product_idx);

	public List<String> selectCategoryList(String product_name);

	public List<String> selectColorList(String product_name);

	public List<imageVo> selectImageList(String product_name);

	public boolean insertOrder(OrderVo order);

}
