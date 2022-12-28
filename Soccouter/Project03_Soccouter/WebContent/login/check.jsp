<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="dto.dtoMem"%>
<%@page import="jdbc.DAO"%>
<%

	request.setCharacterEncoding("UTF-8");

	String league = request.getParameter("league");
	String team = request.getParameter("team");
	String tid_daum = request.getParameter("tid_daum");
	String tid_sofa = request.getParameter("tid_sofa");
	String sid_sofa = request.getParameter("sid_sofa");
	String sid_daum = request.getParameter("sid_daum");

	out.println("check.jsp tid_sofa : " + tid_sofa);
	
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	out.println("넘겨받은 아이디 : "+id);
	out.println("넘겨받은 비번 : "+pw);
	
	
	
	dtoMem mem = new dtoMem();	
	
	DAO dao = new DAO();
	boolean boo = dao.checkLogin(id, pw);
	
	String msg = null;
	
	if(boo){
		msg = id + "님이 로그인 하셨습니다.";
		session.setAttribute("login", mem);
	}else if(!boo){
		msg = "아이디 혹은 비밀번호가 올바르지 않습니다.";
	}
	
	request.setAttribute("boo", boo);
	request.setAttribute("msg", msg);
	
	request.setAttribute("league", league);
	request.setAttribute("team", team);
	request.setAttribute("tid_daum", tid_daum);
	request.setAttribute("tid_sofa", tid_sofa);
	request.setAttribute("sid_sofa", sid_sofa);
	request.setAttribute("sid_daum", sid_daum);
	
	pageContext.forward("loginResult.jsp");
%>