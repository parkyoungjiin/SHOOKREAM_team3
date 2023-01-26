package com.itwillbs.shookream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.MemberMapper;
import com.itwillbs.shookream.vo.MemberBean;

@Service
public class MemberService {
	@Autowired
	private MemberMapper mapper;
	

	// 회원 상세 정보 조회 작업 - getMemberInfo()
	// => 파라미터 : 아이디  리턴타입 : MemberVO
	public MemberBean getMemberInfo(String id) {
		return mapper.selectMemberInfo(id);
	}

}















