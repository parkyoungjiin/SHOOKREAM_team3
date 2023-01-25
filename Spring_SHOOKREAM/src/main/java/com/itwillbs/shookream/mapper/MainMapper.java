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
	
	

}
