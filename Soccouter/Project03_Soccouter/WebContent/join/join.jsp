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
<title>Insert title here</title>
</head>
<body>

	<div align="center">
		<h2>회원가입</h2>
		<hr>
		<form action="joinOk.jsp" method="post">
			<table>
				<tr>
					<td>아이디</td>
					<td><input type="text" name="id"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="pw"></td>
				</tr>
				<tr>
					<select name="type">
				        <option value="admin">관리자</option>
				        <option value="none" selected>일반 사용자</option>
					</select>
				</tr>
			</table>         
		<br><hr>
			<input type="hidden" name="league" value="<%=league %>">
			<input type="hidden" name="team" value="<%=team %>">
			<input type="hidden" name="tid_daum" value="<%=tid_daum %>">
			<input type="hidden" name="tid_sofa" value="<%=tid_sofa %>">
			<input type="hidden" name="sid_daum" value="<%=sid_daum %>">
			<input type="hidden" name="sid_sofa" value="<%=sid_sofa %>">
			<input type="submit" value="회원가입">
			<input type="reset" value="다시입력">
		</form>
	</div>
		

</body>
</html>