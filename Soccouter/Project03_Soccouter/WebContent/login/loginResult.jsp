<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");

	String league = request.getParameter("league");
	String team = request.getParameter("team");
	String tid_daum = request.getParameter("tid_daum");
	String tid_sofa = request.getParameter("tid_sofa");
	String sid_sofa = request.getParameter("sid_sofa");
	String sid_daum = request.getParameter("sid_daum");
	
	//out.println("loginResult.jsp tid_sofa : " + tid_sofa);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div align="center">
		<h3>${msg }</h3><br>
		<c:if test="${boo }">
			<a href="../Cheer.do?league=<%=league %>&team=<%=team %>&tid_daum=<%=tid_daum %>&tid_sofa=<%=tid_sofa %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>"><h3>응원댓글 페이지로 이동</h3></a>
		</c:if>
		<c:if test="${!boo }">
			<a href="login.jsp?league=<%=league %>&team=<%=team %>&tid_daum=<%=tid_daum %>&tid_sofa=<%=tid_sofa %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>"><h3>로그인 페이지로 이동</h3></a>
		</c:if>
	</div>

</body>
</html>