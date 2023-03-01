package com.itwillbs.shookream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.MemberMapper;
import com.itwillbs.shookream.vo.MemberVo;

@Service
public class MemberService {
	@Autowired
	private MemberMapper mapper;
	// 로그인을 위한 패스워드 조회
	public MemberVo getSelectPass(String id) {
		return mapper.getSelectPass(id);
	}
	// 로그아웃
	

	// 회원 상세 정보 조회 작업 - getMemberInfo()
	// => 파라미터 : 아이디  리턴타입 : MemberVO
	public MemberVo getMemberInfo(String id) {
		return mapper.selectMemberInfo(id);
	}

}















