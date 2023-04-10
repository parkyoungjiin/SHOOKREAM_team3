<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 전달받은 오류메시지(msg) 출력 후 이전페이지로 돌아가기 --%>
<script>
	var msg ='${msg}';
	
	if(msg == "이미 신청하셨습니다!"){
		alert(msg);
		history.back();
	}
</script> 