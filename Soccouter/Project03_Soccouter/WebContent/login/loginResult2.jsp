<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>

    
<% 
	String id = request.getParameter("id");
	String result = request.getParameter("result");
	boolean boo = false;
	if(result=="성공"){
		boo = true;
	} else if(result=="실패"){
		boo = false;
	}
%>
안녕하세요 사용자님

사용자님이 입력하신 아이디는 <%=id%> 입니다.
<% if(boo==true) {%>
입력하신 아이디는 사용하실 수 있으며 회원가입이 완료되었습니다.
<% } else if(boo==false) {%>
입력하신 아이디는 중복된 아이디 이므로 사용하실 수 없습니다.
다시 가입해주세요.
<% } %>