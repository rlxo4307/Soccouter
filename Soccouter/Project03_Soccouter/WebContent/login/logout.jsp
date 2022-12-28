<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="member.memberDAO.MemberDAO"%>
<%@page import="member.memberVO.MemberVO"%>
<%@page import="classifyroom.classifyroomDAO.ClassifyroomDAO"%>
<%@page import="classifyroom.classifyroomVO.ClassifyroomVO"%>
<%@page import="chat.chatDAO.ChatDAO"%>
<%@page import="chat.chatVO.ChatVO"%>    
    
<% 
	MemberVO vo = (MemberVO)session.getAttribute("login");
	String lestmember = vo.getMem_id();
	
	ClassifyroomVO co = new ClassifyroomVO();
	ClassifyroomDAO cao = ClassifyroomDAO.getInstance();
	//현재 사용자 claasifyroom에서 제거
	cao.outRoom(lestmember);

	
	//DAO함수의 out_room, delete_room 기능을 활용하기 위해 DAO를 선언
	ChatDAO dao2 = ChatDAO.getInstance();
	
	String id = lestmember; //사실 lestmember를 사용해도 된다.
	//원치 않은 프로그램 종료를 한 사용자가 들어있는 채팅방을 구한다;
	String checkRoom = dao2.get_name(id);
	//사용자를 방에서 삭제한다
	dao2.out_room(checkRoom, id);
	//방이 비어있다면 방을 삭제한다
	dao2.delete_room();
	

	session.invalidate(); 
	
	out.println("<script type = 'text/javascript'>");
	out.println("alert('로그아웃 되었습니다');");
	out.println("location.href='../index.jsp'");
	out.println("</script> ");
%>