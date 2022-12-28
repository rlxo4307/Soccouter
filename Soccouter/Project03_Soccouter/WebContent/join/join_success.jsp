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

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 성공</title>
</head>
<body>

	<h2>회원가입에 성공하셨습니다</h2>
	<a href="../login/login.jsp?league=<%=league %>&team=<%=team %>&tid_daum=<%=tid_daum %>&tid_sofa=<%=tid_sofa %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>">로그인 페이지로 이동</a><br>

</body>
</html>