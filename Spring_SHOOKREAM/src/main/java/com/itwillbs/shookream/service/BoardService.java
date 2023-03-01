package com.itwillbs.shookream.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.shookream.mapper.BoardMapper;
import com.itwillbs.shookream.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardMapper mapper;


	public int insertCount(BoardVo board) {
		return mapper.insertBoard(board);
	}


	public List<BoardVo> getBoardList(String keyword, int startRow, int listLimit , String type) {
		return mapper.selectBoardList(keyword, startRow, listLimit, type);
	}


	public int getBoardListCount(String keyword, String notice_type) {
		
		return mapper.selectBoardListCount(keyword,notice_type);
	}


	public BoardVo getBoard(int notice_idx, boolean isUpdateReadCount) {
		int updateCount = mapper.updateReadcount(notice_idx);
		
		if(updateCount > 0) { 
			updateCount += 1;
		}
		return mapper.selectBoard(notice_idx);
	}


	public int updateReadcount(int notice_idx) {
		return mapper.updateReadcount(notice_idx);
	}


	public int modifyBoard(BoardVo board) {
		
		return mapper.updateBoard(board);
	}


	public boolean removeBoard(int notice_idx) {
		
		return mapper.deleteBoard(notice_idx);
	}




}
		
		
	