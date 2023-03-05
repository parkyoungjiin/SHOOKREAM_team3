package com.itwillbs.shookream.controller;

import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.shookream.service.MemberService;
import com.itwillbs.shookream.vo.AuthVo;
import com.itwillbs.shookream.vo.MemberVo;

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
	
	// <<=======================================회원가입 관련 코드=======================================>>
	//--------------- 회원가입 폼 -------------------
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
	
	// ------------------- 회원가입 id 중복체크 ------------------------------
	@GetMapping("dbCheckId.me")
	public String chkForm(@RequestParam("member_id") String id, Model model) {
		int result = service.idCheck(id);

		model.addAttribute("result",result);			
		return "member/member_checkId";
	}
	
	// -------------------- 이메일 인증1 : 메일 전송 ------------------------------------
	@GetMapping("CheckEmailAddress.me")
	public String authEmail(
			@RequestParam("member_id") String id,
			@RequestParam("authCode") String authCode,
			@RequestParam("email1") String email1, @RequestParam("email2") String email2,
			AuthVo auth) {
		String email = email1 + "@" + email2;
		StringBuilder authCd = new  StringBuilder();
		String[] ch = {"0","1","2","3","4","5","6","7","8","9"};
		
		for(int i=0; i < 6; i++) {
			int num = (int)(Math.random()*ch.length);
			authCd.append(ch[num]);
		}

		auth.setAuth_id(id);
		auth.setAuth_authCode(authCd.toString());
		boolean isRightAuth = service.isAuthUser(auth);
		
		if(isRightAuth) {
			String userExist = service.isMember(auth);
		} else {
			String newMember = service.isNewMem(auth);
		}
		
		String mailServer = "smtp.gmail.com";
		Properties properties = new Properties();
		properties.put("mail.smtp.host", mailServer);
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.port", "587"); 
		properties.put("mail.smtp.starttls.enable", "true");  
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//		Authenticator authenticator = new GoogleMailAuthenticator();
		
		/*
		
			String content = "회원가입창으로 돌아가 인증번호를 입력해 주세요.";
			content += "<hr>";
			content += "<b>"+authCd.toString() +"</b>";
		
			
			String mailServer = "smtp.gmail.com"; // 메일 서버 지정하기
			Properties properties = new Properties();
			properties.put("mail.smtp.host", mailServer);
			properties.put("mail.smtp.auth", true);
			properties.put("mail.smtp.port", "587"); 
			properties.put("mail.smtp.starttls.enable", "true");  
			properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
			Authenticator authenticator = new GoogleMailAuthenticator(); // 메일서버에서 인증받은 계정 + 비번
			Session mailSession = Session.getDefaultInstance(properties, authenticator); // 메일서버, 계정, 비번이 유효한지 검증
			try {
				InternetAddress address = new InternetAddress(email); 		// 받는사람 이메일 주소
				Message msg = new MimeMessage(mailSession);					// 메일 관련 정보 작성
				msg.setRecipient(Message.RecipientType.TO, address);		// 받는 사람
				msg.setFrom(new InternetAddress("hz0123hz@gmail.com"));		// 보내는 사람
				msg.setSubject("[SHOOKREAM] 이메일 인증코드입니다.");		// 메일 제목
				msg.setContent(content, "text/html; charset=UTF-8");		// 데이터 처리
				msg.setSentDate(new Date());								// 보낸 날짜
				Transport.send(msg);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
	
		return forward;
		 
		 */
		
		return "forward:/";
	} // authEmail 
	
	// -------------------- 이메일 인증2 : 전송코드 일치여부 확인 ----------------------------------
	@GetMapping("CompareEmailAddress.me")
	public String authCodeChk() {
		return "forward:/";
	} // authCodeChk 끝
	//<<===================================== 회원가입 끝=================================================>>
	
	// ------------------- 회원 정보수정 비밀번확인 폼 -----------------------------
	@GetMapping("MemberModifyCheck.me")
	public String modifyCheck() {
		return "member/member_password_modify_form";
	} // modifyCheck
	
	//------------------- 회원 정보수정 비밀번호 확인 비즈니스 로직 ---------------------------------
	@PostMapping("MemberPassCheck.me")
	public String passCheck(@RequestParam("id") String id, @RequestParam("pass") String pass, Model model) {

		//암호화 되어있지 않아서 우선 주석처리 
//		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
			
		//비밀번호 일치 여부 확인을 위해 비밀번호 가져오기
		MemberVo member = service.getSelectPass(id); // DB저장된 비밀번호 (PASSWD)
			//로그인 작업(비밀번호 일치여부 판별)
		if(member.getMember_pass() == null || !member.getMember_pass().equals(pass)) { // 실패
			model.addAttribute("msg", "비밀번호를 확인해주세요");
			return "fail_back";
		}else { // 성공
			//성공 시 세션아이디, member_idx 저장
			return "redirect:/MemberModifyForm.me?id=" + id;
		}	
	}
	
	// ------------------- 회원 정보수정 폼 ----------------------------
	@GetMapping("MemberModifyForm.me")
	public String modifyForm(@RequestParam("id") String id, Model model) {
		
		MemberVo member = service.getMemberInfo(id);
		System.out.println("회원정보수정 memberInfo : " + member);
		model.addAttribute("member",member);
		return "member/member_modify_form";
	} // modifyForm
	
	// ------------------ 회원 정보수정 비즈니스 로직-------------------------
	@PostMapping("MemberModifyPro.me")
	public String modifyPro(@ModelAttribute MemberVo member, @RequestParam("id") String id, @RequestParam String newpass1, Model model) {
//		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
//		if(newpass1 != null && !newpass1.equals("")) {
//			newpass1 = passwdEncoder.encode(newpass1);
//		}
		int updateCount = service.modifyMember(member, newpass1, id);
		
		if(updateCount > 0) { // 성공
			return "redirect:/MemberMyPage.me";
		} else { 
			model.addAttribute("msg", "회원정보 수정에 실패하였습니다");
			return "fail_back";
		}
	} // modifyPro
	
	// ------------------- 회원 탈퇴 확인 폼 ---------------------------
	@GetMapping("MemberDeleteForm.me")
	public String deleteForm() {
		return "member/member_delete_form";
	} // deleteForm 
	
	// 회원 탈퇴 비밀번호 확인
	@PostMapping("MemberDeleteFormCheck.me")
	public String deleteFormChk(@RequestParam("id") String id, 
			@RequestParam("pass") String pass,
			HttpSession session, Model model) {
		
		//암호화 되어있지 않아서 우선 주석처리 
//		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
			
		//비밀번호 일치 여부 확인을 위해 비밀번호 가져오기
		MemberVo member = service.getSelectPass(id); // DB저장된 비밀번호 (PASSWD)
			//로그인 작업(비밀번호 일치여부 판별)
		if(member.getMember_pass() == null || !member.getMember_pass().equals(pass)) { // 실패
			model.addAttribute("msg", "비밀번호를 확인해주세요");
			return "fail_back";
		}else { 
			return "redirect:/MemberDeletePro.me";
		}
		
//		String sId = (String)session.getAttribute("sId");
//		// 세션아이디 권한 판별
//		if(member.getMember_id() != null && !member.getMember_id().equals("") && !member.getMember_id().equals(sId)) {
//			model.addAttribute("msg","권한이 없습니다");
//			return "fail_back";
//		} else if (member.getMember_id().equals("")) {
//			model.addAttribute("msg","잘못된 접근입니다!");
//			return "fail_back";
//		} else {
//			return "redirect:/MemberDeletePro.me";
//		}
	}
	
	//-------------------- 회원 탈퇴 비즈니스 로직----------------------------
	@PostMapping("MemberDeletePro.me")
	public String deletePro(HttpSession session, @ModelAttribute MemberVo member, Model model) {
//		String sId = (String)session.getAttribute("sId");
		int deleteCount = service.removeMember(member);
		if(deleteCount > 0) {			
			session.invalidate();
			return "redirect:/";
		} else {
			model.addAttribute("msg","탈퇴에 실패하였습니다.");
			return "fail_back";
		}
	} // deletePro
	
}//Controller 끝