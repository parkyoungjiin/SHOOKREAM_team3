package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.OrderBean;
import com.itwillbs.shookream.vo.ProductBean;
import com.itwillbs.shookream.vo.WishBean;
import com.itwillbs.shookream.vo.imageBean;


public interface ProductMapper {

	public int selectMemberIdx(String sId);

	public WishBean selectWish(@Param("product_idx") int product_idx, 
			@Param("member_idx") int member_idx);

	public ProductBean selectProduct(int product_idx);

	public imageBean selectImage(int product_idx);

	public List<String> selectCategoryList(String product_name);

	public List<String> selectColorList(String product_name);

	public List<imageBean> selectImageList(String product_name);

	public boolean insertOrder(OrderBean order);

}
