package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.BoardVo;
import com.itwillbs.shookream.vo.ProductVo;

@Mapper
public interface BoardMapper {

		//게시판 등록작업
		int insertBoard(BoardVo board);
		//공지 목록
		List<BoardVo> selectBoardList(@Param("keyword") String keyword,@Param("startRow") int startRow, @Param("listLimit")int listLimit, @Param("notice_type") String type);
		//공지 목록 번호 
		int selectBoardListCount(@Param("keyword")String keyword,@Param("notice_type") String notice_type);
		//공지 상세정보 및 수정 
		BoardVo selectBoard(int notice_idx);
		
		//공지 상세정보 & 수정 & 자주묻는질문 상세 
		int updateReadcount(int notice_idx);
		//공지 수정 pro
		int updateBoard(BoardVo board);
		//공지 삭제 
		boolean deleteBoard(int notice_idx);
		
		// 공지 카테고리별 모아보기
		List<BoardVo> selectBoardJson(String notice_category);
		
//		BoardVo selectModifyBoard(int notice_idx);

}
