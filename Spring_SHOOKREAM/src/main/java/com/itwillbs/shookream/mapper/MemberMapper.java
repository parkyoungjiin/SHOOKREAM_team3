package com.itwillbs.shookream.mapper;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.AuthVo;
import com.itwillbs.shookream.vo.MemberVo;

public interface MemberMapper {

	// 로그인을 위한 패스워드 조회
	public MemberVo getSelectPass(String id);
	
	// 회원 상세 정보 조회 작업 - getMemberInfo()
	public MemberVo selectMemberInfo(String id);
	
	// 회원가입
	public boolean insertMember(MemberVo member);
	
	// 회원가입 : id 중복체크
	public int selectAllId(String id);

	// 회원가입 : 이메일 인증1 - 인증코드 전송
	public boolean selectAuth(AuthVo auth);

	// 이메일 인증1 - 가입한 회원
	public String updateAuth(AuthVo auth);

	// 이메일 인증1 - 새로운 회원
	public String insertAuth(AuthVo auth);

	// 회원정보 수정
	public int updateMember(
			@Param("member") MemberVo member, 
			@Param("newpass1") String newpass1, 
			@Param("id") String id);

	// 회원 탈퇴
	public int deleteMember(MemberVo member);
	
	

}













