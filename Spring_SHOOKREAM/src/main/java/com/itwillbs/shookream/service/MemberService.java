package com.itwillbs.shookream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.MemberMapper;
import com.itwillbs.shookream.vo.AuthVo;
import com.itwillbs.shookream.vo.MemberVo;
import com.itwillbs.shookream.vo.WishVo;

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

	// 회원가입
	public boolean joinMember(MemberVo member) {
		return mapper.insertMember(member);
	}

	// 회원가입 : id 중복체크
	public int idCheck(String id) {
		return mapper.selectAllId(id);
	}

	// 회원가입 : 이메일 인증1 - 이메일 전
	public boolean isAuthUser(AuthVo auth) {
		return mapper.selectAuth(auth);
	}

	// 이메일 인증1 - 가입한 회원
	public String isMember(AuthVo auth) {
		return mapper.updateAuth(auth);
	}

	//이메일 인증1 - 새로운 회원
	public String isNewMem(AuthVo auth) {
		return mapper.insertAuth(auth);
	}

	// 회원 정보 수정 
	public int modifyMember(MemberVo member, String newpass1, String id) {
		return mapper.updateMember(member,newpass1, id);
	}

	// 회원 탈퇴
	public int removeMember(MemberVo member) {
		return mapper.deleteMember(member);
	}
	
	// 아이디 찾기
	public String findId(MemberVo vo) {
		return mapper.findId(vo);
	}

	// 아이디 유무 확인
	public boolean isLoginUser(MemberVo vo) {
		return mapper.isLoginUser(vo);
	}


	public boolean updatePass(MemberVo vo, String imsiPw) {
		return mapper.updatePass(vo,imsiPw);
	}


	// wish 조회
	public List<WishVo> getWish(int member_idx) {
		return mapper.selectWish(member_idx);
	}

}















