package com.itwillbs.shookream.mapper;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.shookream.vo.MemberBean;

public interface MemberMapper {

	public MemberBean selectMemberInfo(String id);


}













