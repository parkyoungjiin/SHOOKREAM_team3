package com.itwillbs.shookream.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.taglibs.standard.tag.el.fmt.RequestEncodingTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.shookream.service.BoardService;
import com.itwillbs.shookream.service.MemberService;
import com.itwillbs.shookream.vo.MemberVo;
import com.itwillbs.shookream.vo.PageInfo;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	//---------로그인 폼-----------
	@GetMapping(value = "LoginMember.me")
	public String LoginForm() {
		return "member/MemberLoginForm";
	}// LoginForm 끝 
	
	//-------------로그인 작업-----------------
	@PostMapping(value = "LoginMemberPro.me")
	public String LoginPro(
			@RequestParam("id") String id,
			@RequestParam("pass") String pass,
			Model model, 
			HttpSession session
			) {
		//암호화 되어있지 않아서 우선 주석처리 
//			BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
		
		//비밀번호 일치 여부 확인을 위해 비밀번호 가져오기
		MemberVo member = service.getSelectPass(id); // DB저장된 비밀번호 (PASSWD)
		//로그인 작업(비밀번호 일치여부 판별)
		if(member.getMember_pass() == null || !member.getMember_pass().equals(pass)) { // 실패
			// Model 객체에 "msg" 속성명으로 "로그인 실패!" 메세지 저장 후
			// fail_back.jsp 페이지로 포워딩
				model.addAttribute("msg", "로그인 실패(아이디 또는 비밀번호를 확인해주세요.)");
				return "fail_back";
		}else { // 성공
			//성공 시 세션아이디, member_idx 저장
			session.setAttribute("sId", id);
			session.setAttribute("member_idx", member.getMember_idx());
				return "redirect:/";
		}
	}//LoginPro 끝 
	
	//-------------로그아웃 작업------------
	@GetMapping(value = "MemberLogout.me")
	public String logout(HttpSession session) {
		// 세션 초기화
		session.invalidate();
		return "redirect:/";
	}//logout 끝
	
	//--------- 회원가입 폼 -------------------
	@GetMapping("MemberJoinForm.me")
	public String joinForm() {
		return "member/member_join_form";
	} // joinForm 끝
	
	// --------------- 회원가입 비즈니스 로직 ---------------------
	@PostMapping("MemberJoinPro.me") 
	public String joinPro(MemberVo member, Model model) {
		
		// 비밀번호 암호화 작업
		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
		String securePasswd = passwdEncoder.encode(member.getMember_pass());
		member.setMember_pass(securePasswd);
		
		// 이메일 결합
		String [] member_emailArr = member.getMember_email().split(",");
		for(int i=0; i< member_emailArr.length; i++) {
			String member_email = member_emailArr[i].join("@", member_emailArr);
//			System.out.println(EMP_EMAIL);
			member.setMember_email(member_email);
		}
		
		boolean joinMember = service.joinMember(member);
		
		if(joinMember) {
			return "member/member_join_result";
		} else {
			model.addAttribute("msg","회원가입에 실패하였습니다!");
			return "fail_back";
		}
	} // joinPro 끝
	
	// ------------------- 회원정보수정 확인창 -----------------------------
	@PostMapping("MemberModifyCheck.me")
	public String modifyCheck(HttpSession session) {
		return "member/member_password_modify_form";
	}
	
	// ------------------- 회원 정보수정 폼 ----------------------------
	@PostMapping("MemberModifyForm.me")
	public String modifyForm(HttpSession session) {
		return "member/member_modify_form.jsp";
	}
	
	// --------------------- 회원 정보 수정 비즈니스 로직-----------------------------
	@PostMapping("MemberModifyPro.me")
	public String modifyPro() {
		return null;
	}
	
	
}//Controller 끝