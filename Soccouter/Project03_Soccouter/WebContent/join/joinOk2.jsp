<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	String url = "jdbc:oracle:thin:@shared00.iptime.org:32779:XEPDB1";
	String uid = "FOOTBALL";
	String upw = "football";
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	String sql = "INSERT INTO mem values(?, ?, ?)";
	
	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		conn = DriverManager.getConnection(url, uid, upw);
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		pstmt.setString(3, type);

		int result = pstmt.executeUpdate();
		
		request.setAttribute("league", league);
		request.setAttribute("team", team);
		request.setAttribute("tid_daum", tid_daum);
		request.setAttribute("tid_sofa", tid_sofa);
		request.setAttribute("sid_sofa", sid_sofa);
		request.setAttribute("sid_daum", sid_daum);
		
		if(result == 1){
			response.sendRedirect("join_succes.jsp");
		} else{
			response.sendRedirect("join_fail.jsp");
		}
		
	} catch(Exception e){
		e.printStackTrace();
	} finally{
		try{
			if(conn != null) conn.close();
			if(pstmt != null) pstmt.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	회원가입에 성공하였습니다.
</body>
</html>