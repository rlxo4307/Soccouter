<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
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
<title>Soccouter</title>
</head>
<body>
	<!-- <script>
		$(function(){
			$('.btnlogin').click(function(){
				if($("[name=id]").value == ""){
					alert("아이디를 입력하세요")
					$(this).focus();
				}else if($('[name=pw]').value == ""){
					alert("비밀번호를 입력하세요")
					$(this).focus();
				}else{
					$('form').submit();
				}
			})
		})
	</script> -->

	<h2 align="center">로그인</h2>
	<!-- <div id="mq1">
		<img src="../picture/login.jpg" width=550>
	</div><br>
	 -->
	<form action="check.jsp" method="post" align="center">
		<div class="input-box">
			<input id="id" type="text" name="id" style="width:190px;" placeholder="아이디">
		</div>
		
		<div class="input-box">
			<input id="pw" type="password" name="pw" style="width:190px;" placeholder="비밀번호">
		</div>
		<input type="hidden" name="league" value="<%=league %>">
		<input type="hidden" name="team" value="<%=team %>">
		<input type="hidden" name="tid_daum" value="<%=tid_daum %>">
		<input type="hidden" name="tid_sofa" value="<%=tid_sofa %>">
		<input type="hidden" name="sid_daum" value="<%=sid_daum %>">
		<input type="hidden" name="sid_sofa" value="<%=sid_sofa %>">
		<input type="submit" class="btnlogin" value="로그인" style="width:200px; margin-top:3px;">
	</form>
	
	<form action="../join/join.jsp" method="post" align="center">
		<input type="hidden" name="league" value="<%=league %>">
		<input type="hidden" name="team" value="<%=team %>">
		<input type="hidden" name="tid_daum" value="<%=tid_daum %>">
		<input type="hidden" name="tid_sofa" value="<%=tid_sofa %>">
		<input type="hidden" name="sid_daum" value="<%=sid_daum %>">
		<input type="hidden" name="sid_sofa" value="<%=sid_sofa %>">
		<input type="submit" value="회원가입" style="width:200px; margin-top:3px;">
	</form>
</body>
</html>