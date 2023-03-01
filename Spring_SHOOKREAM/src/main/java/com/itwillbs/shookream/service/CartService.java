package com.itwillbs.shookream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.CartMapper;

@Service
public class CartService {
	@Autowired
	private CartMapper mapper;
}
