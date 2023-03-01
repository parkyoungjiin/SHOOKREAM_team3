package com.itwillbs.shookream.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.taglibs.standard.tag.el.fmt.RequestEncodingTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.shookream.service.BoardService;
import com.itwillbs.shookream.vo.BoardVo;
import com.itwillbs.shookream.vo.PageInfo;


@Controller
public class BoardController {

	
	@Autowired
	private BoardService service;
	
	@GetMapping("/BoardWriteForm.bo")
	public String write() {
		return "admin/board_write_form";
	}
	//---------------------게시판 등록 작업-------------------------
	@PostMapping("/BoardWritePro.bo")
	public String writePro(@ModelAttribute BoardVo board, Model model, HttpSession session ,@RequestParam String notice_type) {
		int isWriteSuccess = service.insertCount(board);
		System.out.println(board);
		
		if(isWriteSuccess < 0) {
			model.addAttribute("msg", "글쓰기 실패");
			return "admin/admin_notice_manage";
		}else if(notice_type.equals("Notice")){
		
		}
		return "redirect:/BoardList.bo?notice_idx="+board.getNotice_idx();
	}// 게시판 등록 작업 끝
	//-------------공지 목록 -----------
	@GetMapping("/BoardList.bo")
	public String list(@ModelAttribute BoardVo board,@RequestParam(defaultValue = "1") int pageNum, String keyword, Model model ) {
		
		int listLimit = 10;
		int startRow = (pageNum - 1) * listLimit;
		String notice_type = "Notice";
		
		if(keyword ==null) {
			keyword="";
		}
		List<BoardVo> boardList= service.getBoardList(keyword, startRow, listLimit, notice_type);
		int listCount = service.getBoardListCount(keyword,notice_type);
		System.out.println(boardList);
		
		int pageListLimit =3;
		int maxPage = listCount / listLimit 
				+ (listCount % listLimit == 0 ? 0 : 1); 


		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;

		int endPage = startPage + pageListLimit - 1;

		if(endPage > maxPage) {
			endPage = maxPage;
		}
		PageInfo page = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		model.addAttribute("boardList", boardList);
		model.addAttribute("page", page);
		return"board/board_list";
	}
	
	//공지 목록 끝
	//--------------공지 상세정보 --------------
	@GetMapping("/BoardInfo.bo")
	public String info(@ModelAttribute BoardVo board, @RequestParam(defaultValue="1") int notice_idx, boolean isUpdateReadCount, Model model) {
		
		 board = service.getBoard(notice_idx,true);
		if(board != null && isUpdateReadCount) {
			int updateCount = service.updateReadcount(notice_idx);
			if(updateCount>0) {
				board.setNotice_readcount(board.getNotice_readcount() +1);
			}
		}
		model.addAttribute("board", board);
		return "board/board_detail";
	}// 공지 상세정보 끝
	
	//--------공지 정보 수정----------
	@GetMapping("/BoardModifyForm.bo")
	public String modifyform(@ModelAttribute BoardVo board, @RequestParam(defaultValue = "1") int notice_idx,@RequestParam(defaultValue = "1") int board_num,Model model) {
		board= service.getBoard(notice_idx,false);
//		System.out.println(board);
		
		model.addAttribute("board",board);
		return"admin/board_modify_form";
	}// 공지 정보수정폼
	
	@PostMapping("/BoardModifyPro.bo")
	public String modifyPro(@ModelAttribute BoardVo board, Model model, int pageNum) {
		System.out.println(board);
		int updatecount = service.modifyBoard(board);
		
		if(updatecount >0 ) {
			if(board.getNotice_type().equals("Notice")) {
//				return "redirect:/AdminNoticeManage.ad?notice_idx=" +board.getNotice_idx()+"&pageNum="+pageNum;
				return "redirect:/BoardList.bo?notice_idx="+board.getNotice_idx()+"&pageNum="+pageNum;
			} else {
				return "redirect:/FAQList.bo?notice_idx="+board.getNotice_idx()+"&pageNum="+pageNum;
				
			}
		}else {
			model.addAttribute("msg","삭제실패!");
			return "fail_back";
		}
		
		
	}
	
	
	@GetMapping("/BoardDeletePro.bo")
	public String deletePro(@ModelAttribute BoardVo board, Model model,
						@RequestParam(defaultValue="1") String notice_type, 
						@RequestParam(defaultValue="1") int notice_idx, 
						@RequestParam(defaultValue="1") int pageNum) {
		boolean isDeleteSuccess = service.removeBoard(notice_idx);
		if(!isDeleteSuccess) {
			model.addAttribute("msg","삭제에 실패했습니다" );
		}else {
			if(notice_type.equals("Notice")) {
				return "redirect:/board/board_list";
	}
		}
		
			return "admin/admin_FAQ_manage";
	}
	
	@GetMapping("/FAQList.bo")
	public String FAQList(@ModelAttribute BoardVo board , @RequestParam(defaultValue = "1") int pageNum , String keyword, Model model) {
		int listLimit =10;
		pageNum =1;
		int startRow = (pageNum -1) * listLimit;
		
		if(keyword ==null) {
			keyword ="";
		}
		String type ="FAQ";
		List<BoardVo> boardList = service.getBoardList(keyword, startRow, listLimit, type);
		int listCount = service.getBoardListCount(keyword, type);

		int pageListLimit =3;
		int maxPage = listCount / listLimit 
				+ (listCount % listLimit == 0 ? 0 : 1); 


		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;

		int endPage = startPage + pageListLimit - 1;

		if(endPage > maxPage) {
			endPage = maxPage;
		}
		PageInfo page = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		model.addAttribute("boardList", boardList);
		model.addAttribute("page", page);
		return "board/FAQ_list";
	}
	
	@GetMapping("/FAQInfo.bo")
	public String FAQInfo(@ModelAttribute BoardVo board, Model model, @RequestParam(defaultValue = "1") int notice_idx , boolean isUpdateReadCount) {
		board = service.getBoard(notice_idx, true);
		if(board != null && isUpdateReadCount) {
			int updateCount = service.updateReadcount(notice_idx);
			if(updateCount>0) {
				board.setNotice_readcount(board.getNotice_readcount() +1);
			}
		}
		model.addAttribute("board",board);
		return "board/FAQ_detail";
		
	}
	
	@GetMapping("/FAQModifyForm.bo")
	public String FAQmodify(@ModelAttribute BoardVo board, @RequestParam(defaultValue = "1") int notice_idx,@RequestParam(defaultValue = "1") int board_num,Model model) {
		board= service.getBoard(notice_idx,false);
//		System.out.println(board);
		
		model.addAttribute("board",board);
		return"admin/board_modify_form";
	}
	
	@PostMapping("/FAQModifyPro.bo")
	public String FAQmodifyPro(@ModelAttribute BoardVo board, Model model, int pageNum) {
		System.out.println(board);
		int updatecount = service.modifyBoard(board);
		
		if(updatecount >0 ) {
			if(board.getNotice_type().equals("Notice")) {
				return "redirect:/FAQ_list";
			} else {
			
				
			}
		}else {
			model.addAttribute("msg","삭제실패!");
			return "fail_back";
		}
		return "redirect:/FAQ_list";
	}
	
	@GetMapping("/FAQDeletePro.bo")
	public String FAQDeletePro(@ModelAttribute BoardVo board, Model model,
						@RequestParam(defaultValue="1") String notice_type, 
						@RequestParam(defaultValue="1") int notice_idx, 
						@RequestParam(defaultValue="1") int pageNum) {
		boolean isDeleteSuccess = service.removeBoard(notice_idx);
		if(!isDeleteSuccess) {
			model.addAttribute("msg","삭제에 실패했습니다" );
		}else {
			if(notice_type.equals("Notice")) {
				return "board/FAQ_list";
	}
		}
		
			return "board/FAQ_list";
	}
}
		
	
	

	
	

