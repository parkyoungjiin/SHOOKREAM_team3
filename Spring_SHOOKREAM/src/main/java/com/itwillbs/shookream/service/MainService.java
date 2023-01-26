package com.itwillbs.shookream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.MainMapper;
import com.itwillbs.shookream.vo.ProductBean;

@Service
public class MainService {
	@Autowired
	private MainMapper mapper;

	// 베스트 상품 목록
	public List<ProductBean> getProductBestList(int startRow, int listLimit) {
		return mapper.getProductBestList(startRow, listLimit);
	}

	// 최근 등록 상품 목록
	public List<ProductBean> getProductNewList(int startRow, int listLimit) {
		return  mapper.getProductNewList(startRow, listLimit);
	}
	// 상품목록 개수(listcount 계산)
	public int getProductListCount() {
		return mapper.getProductListCount();
	}
	// 할인 상품 목록
	public List<ProductBean> getProductSaleList(int startRow, int listLimit) {
		return mapper.getProductSaleList(startRow, listLimit);
	}
	//브랜드 카테고리별 목록
	public List<ProductBean> getProductCGList(String cg, int startRow, int listLimit) {
		return mapper.getProductCGList(cg, startRow, listLimit);
	}
	//브랜드 카테고리 목록 정렬 시 상품목록 개수(브랜드 별 개수)
	public int getProductCgListCount(String cg) {
		return mapper.getProductCGListCount(cg);
	}
	//키워드 별 목록 (검색창)
	public List<ProductBean> getProductSearchList(String keyword, int startRow, int listLimit) {
		return mapper.getProductSearchList(keyword, startRow, listLimit);
	}


	
	
}
