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

	// 베스트 상품 가져오기
	public List<ProductBean> getProductBestList(int startRow, int listLimit) {
		return mapper.getProductBestList(startRow, listLimit);
	}

	// 최근 등록 상품 가져오기
	public List<ProductBean> getProductNewList(int startRow, int listLimit) {
		return  mapper.getProductNewList(startRow, listLimit);
	}
	
	
}
