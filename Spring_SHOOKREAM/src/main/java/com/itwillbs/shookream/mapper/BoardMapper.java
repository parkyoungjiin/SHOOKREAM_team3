package com.itwillbs.shookream.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.BoardVo;
import com.itwillbs.shookream.vo.ProductVo;

@Mapper
public interface BoardMapper {

		
		int insertBoard(BoardVo board);

		List<BoardVo> selectBoardList(@Param("keyword") String keyword,@Param("startRow") int startRow, @Param("listLimit")int listLimit, @Param("notice_type") String type);

		int selectBoardListCount(@Param("keyword")String keyword,@Param("notice_type") String notice_type);

		BoardVo selectBoard(int notice_idx);

		BoardVo selectModifyBoard(int notice_idx);

		int updateReadcount(int notice_idx);

		int updateBoard(BoardVo board);

		boolean deleteBoard(int notice_idx);

}
