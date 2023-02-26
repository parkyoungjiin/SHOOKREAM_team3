package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.OrderBean;
import com.itwillbs.shookream.vo.ProductBean;
import com.itwillbs.shookream.vo.WishBean;
import com.itwillbs.shookream.vo.imageBean;


public interface ProductMapper {
	
	// 1. 상품 등록
	// => 파라미터 : ProductVO 객체    리턴타입 : int
	public int insertProduct(ProductBean product);

	// 2. 상품 이미지 등록
	// => 파라미터 : ImageVO 객체    리턴타입 : int
	public int insertImage(@Param("image") imageBean image, 
					@Param("product_idx")int product_idx);

	// 3. 상품 수정
	public int modifyProduct(
			int product_idx, 
			@Param("product") ProductBean product,
			@Param("image") imageBean image);

	// 4. 상품 삭제
	public int removeProduct(int product_idx);
	
	// 5. 상품 목록 조회
	public List<ProductBean> selectProductList();
	
	// 6. 이미지 조회
	public imageBean selectImgList(int product_idx);

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
