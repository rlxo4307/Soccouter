<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.dtoTeam" %>
<%@ page import="jdbc.DAO" %>

<% 
	ArrayList<dto.dtoTeam> team = (ArrayList)request.getAttribute("team");
	String league = request.getParameter("league");
	String lslid = request.getParameter("lslid");
	int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
	int sid_daum = Integer.parseInt(request.getParameter("sid_daum"));
	
	String tko = null;
	String timg = null;
	String no_img_url = "https://t1.daumcdn.net/media/img-section/sports13/player/noimage/square_m.png";
	int tid_daum = 0;
	int tid_sofa = 0;
	
	if(team.size() != 0) {
		tko = team.get(0).getTko();
		timg = team.get(0).getTimg();
	}
	
	DAO dao = new DAO();
%>


<!DOCTYPE html>
<html>
<head>
<style>
	
	#start { margin-left:10%; margin-right:10%; height:1400px; border: 1px solid #FFD700; }
	
	#team { margin:10px 20px 10px 10px; width:100%; height:100%; float:left; }
	.team_choice { margin:0px 2% 30px 2%; width:20%; height:230px; float:left; 
												border-radius:30px; border:1px solid #808080; }
	.team_logo { text-align:center; width:100%; heigth:210px; margin-top:10px;
				float:left; }
	.logo_img { text-align:center; width:150px; height:150px; }
	.team_name { text-align:center; font-size:25px; color:#EEEEEE; }
	
</style>
<meta charset="UTF-8">
<title> 리그별 팀 선택 </title>
<link rel="stylesheet" href="football.css">
</head>
<body>
<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡ 타이틀 START ㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
<jsp:include page="pageTitleGray.jsp" />
<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 타이틀 END ㅡㅡㅡㅡㅡㅡㅡㅡ-->

<div id="start">	
	<!--ㅡㅡㅡㅡㅡㅡ 리그 선택 & SRART ㅡㅡㅡㅡㅡㅡ-->
	<div id="league">
		<div class="league_choice"><a href="Team.do?league=epl&lslid=<%=lslid %>&sid_sofa=41886&sid_daum=<%=sid_daum %>" class="league_text">프리미어리그</a></div>
		<div class="league_choice"><a href="Team.do?league=primera&lslid=<%=lslid %>&sid_sofa=42409&sid_daum=<%=sid_daum %>" class="league_text">라리가</a></div>
		<div class="league_choice"><a href="Team.do?league=bundesliga&lslid=<%=lslid %>&sid_sofa=42268&sid_daum=<%=sid_daum %>" class="league_text">분데스리가</a></div>
		<div class="league_choice"><a href="Team.do?league=seriea&lslid=<%=lslid %>&sid_sofa=42415&sid_daum=<%=sid_daum %>" class="league_text">세리에A</a></div>
		<div class="league_choice"><a href="Team.do?league=ligue1&lslid=<%=lslid %>&sid_sofa=42273&sid_daum=<%=sid_daum %>" class="league_text">리그1</a></div>
	</div>
	<!--ㅡㅡㅡㅡㅡㅡ 리그 선택 & END ㅡㅡㅡㅡㅡㅡ-->
	<div id="team">
		<% for(int i=0; i<team.size(); i++) { %>
		<div class="team_choice">
		<% if(team.size() != 0) {
				tko = team.get(i).getTko();
				timg = team.get(i).getTimg();
				tid_daum = dao.get_tid_daum(tko);
				tid_sofa = dao.get_tid_sofa(tko); %>
			<div class="team_logo">
				<a href="Squad.do?league=<%=league %>&team=<%=tko %>&tid_daum=<%=tid_daum %>&tid_sofa=<%=tid_sofa %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>"><img src="<%=timg %>" class="logo_img"></a>
			</div>
			<div class="team_name">
				<%=tko %>
			</div>
		</div>
		<%} }%>	
	</div>
</div>
</body>
</html>