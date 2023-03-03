package com.itwillbs.shookream.mapper;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.MemberVo;

public interface MemberMapper {

	// 로그인을 위한 패스워드 조회
	public MemberVo getSelectPass(String id);
	// 회원 상세 정보 조회 작업 - getMemberInfo()
	public MemberVo selectMemberInfo(String id);
	
	// 회원가입 비즈니스
	public boolean insertMember(MemberVo member);


}













