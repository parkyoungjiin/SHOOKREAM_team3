package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.OrderVo;
import com.itwillbs.shookream.vo.ProductVo;
import com.itwillbs.shookream.vo.WishVo;
import com.itwillbs.shookream.vo.imageVo;


public interface ProductMapper {
	
	// 1. 상품 등록
	// => 파라미터 : ProductVO 객체    리턴타입 : int
	public int insertProduct(ProductVo product);

	// 2. 상품 이미지 등록
	// => 파라미터 : ImageVO 객체    리턴타입 : int
	public int insertImage(@Param("image") imageVo image, 
					@Param("product_idx")int product_idx);

	// 3. 상품 수정
	public int modifyProduct(
			int product_idx, 
			@Param("product") ProductVo product,
			@Param("image") imageVo image);

	// 4. 상품 삭제
	public int removeProduct(int product_idx);
	
	// 5. 상품 목록 조회
	public List<ProductVo> selectProductList();
	
	// 6. 이미지 조회
	public imageVo selectImgList(int product_idx);

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