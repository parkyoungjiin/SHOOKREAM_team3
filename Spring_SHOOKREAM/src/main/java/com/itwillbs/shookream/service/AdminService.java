package com.itwillbs.shookream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.AdminMapper;

@Service
public class AdminService {
	@Autowired
	private AdminMapper maper;
}
