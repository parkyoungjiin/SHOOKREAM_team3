package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.BoardBean;
import com.itwillbs.shookream.vo.ProductBean;

@Mapper
public interface BoardMapper {

		
		int insertBoard(BoardBean board);

		List<BoardBean> selectBoardList(@Param("keyword") String keyword,@Param("startRow") int startRow, @Param("listLimit")int listLimit, @Param("notice_type") String type);

		int selectBoardListCount(@Param("keyword")String keyword,@Param("notice_type") String notice_type);

		BoardBean selectBoard(int notice_idx);

		BoardBean selectModifyBoard(int notice_idx);

		int updateReadcount(int notice_idx);

		int updateBoard(BoardBean board);

		boolean deleteBoard(int notice_idx);

}
