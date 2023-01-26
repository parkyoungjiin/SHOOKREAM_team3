package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.ProductBean;

@Mapper
public interface MainMapper {
	
	// 베스트 상품 가져오기
	public List<ProductBean> getProductBestList(@Param("startRow") int startRow,@Param("listLimit") int listLimit);
	
	// 최근 등록 상품 가져오기
	public List<ProductBean> getProductNewList(@Param("startRow") int startRow,@Param("listLimit") int listLimit);
	// 상품목록 개수(listcount 계산)
	public int getProductListCount();
	// 할인 상품 목록
	public List<ProductBean> getProductSaleList(@Param("startRow") int startRow,@Param("listLimit") int listLimit);
	//브랜드 카테고리 별 목록
	public List<ProductBean> getProductCGList(@Param("cg") String cg,@Param("startRow") int startRow,@Param("listLimit") int listLimit);
	//브랜드 카테고리 목록 정렬 시 상품목록 개수(브랜드 별 개수)
	public int getProductCGListCount(@Param("cg") String cg);
	//키워드 별 목록 (검색창)
	public List<ProductBean> getProductSearchList(@Param("keyword") String keyword,@Param("startRow") int startRow,@Param("listLimit") int listLimit);
	
	

}
