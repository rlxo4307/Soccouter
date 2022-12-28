<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jdbc.DAO" %>
<%@ page import="dto.dtoMem" %>

<%
	request.setCharacterEncoding("UTF-8");

	String league = request.getParameter("league");
	String team = request.getParameter("team");
	String tid_daum = request.getParameter("tid_daum");
	String tid_sofa = request.getParameter("tid_sofa");
	String sid_sofa = request.getParameter("sid_sofa");
	String sid_daum = request.getParameter("sid_daum");
	
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String type = request.getParameter("type");
	
	DAO dao = new DAO();
	
	dtoMem mem = new dtoMem(id, pw, type);
	
	int result = dao.Join(mem);
		
	request.setAttribute("league", league);
	request.setAttribute("team", team);
	request.setAttribute("tid_daum", tid_daum);
	request.setAttribute("tid_sofa", tid_sofa);
	request.setAttribute("sid_sofa", sid_sofa);
	request.setAttribute("sid_daum", sid_daum);
		
	if(result == 1){
		response.sendRedirect("join_success.jsp");
	} else{
		response.sendRedirect("join_fail.jsp");
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
</body>
</html>