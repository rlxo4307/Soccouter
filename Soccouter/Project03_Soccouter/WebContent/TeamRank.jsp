<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.dtoTeamRank" %>
<%@ page import="jdbc.DAO" %>
<% 
	ArrayList<dtoTeamRank> teamRank = (ArrayList)request.getAttribute("teamRank");
	String league = request.getParameter("league");
	String sslug = request.getParameter("sslug");
	int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
	int sid_daum = Integer.parseInt(request.getParameter("sid_daum"));
	
	System.out.println("league : " + league);
	System.out.println("sslug : " + sslug);
	
	int tid_daum = 0;
	int tid_sofa = 0;
	
	String tko = null;
	String timg = null;
	String no_img_url = "https://t1.daumcdn.net/media/img-section/sports13/player/noimage/square_m.png";
	int ranking = 0;
	int game = 0;
	int win = 0;
	int draw = 0;
	int loss = 0;
	int gf = 0;
	int ga = 0;
	int gd = 0;
	int pts = 0;
	
	if(teamRank.size() != 0) {
		tko = teamRank.get(0).getTko();
		timg = teamRank.get(0).getTimg();
	}
	
	DAO dao = new DAO();
%>
<!DOCTYPE html>
<html>
<head>
<style>
	
	#start { margin-left:10%; margin-right:10%; height:105vh; border: 1px solid #FFD700; }
	
	a { text-decoration:none }
	
	#choice_margin { margin-left:5%; margin-right:5%; }
	.choice { display:block; width:60vw; height:12vh; }
	.choice_box { width:7vw; height:2vw; border:1px solid white; float:left; 
					text-align:center; padding:6px 0;}
	.choice_text { font-size:1.2vw; color:white; }
	.choice_text_white { color:white; }
	
	#rankIntro { margin-left:3%; color:#FFD700; font-size:2.3vw; float:left; }
	.rankSeason { font-size:2.9vw; color:white; }
	
	#rankSeasonChoice { margin: 2vh 3% 0px 0px; float:right; }
	
	hr { background-color:#888888; }
	
	#rankTable { margin:10px 3% 1% 3%; float:left;
			font-size:15px; color:white; border:1px solid #FFD700; }
	.tableTh{ height: 30px; color:yellow; background-color:#888888;}
	.tableTr{ height: 25px; color:white; }
	.teamRank { width:60px; text-align:center; }
	.teamRankUEFAchamps { width:60px; text-align:center; background-color:skyblue; color:#505050; }
	.teamRankUEFAeuropa { width:60px; text-align:center; background-color:#DDAA00; color:#505050; }
	.teamRankRelegation { width:60px; text-align:center; background-color:#00C7C7; color:#303030; } 
	.teamRankDemoted { width:60px; text-align:center; background-color:#EE3030; color:white; }
	.teamIN { padding-left:10px; width:540px; }
	.teamImg { width:40px; text-align:center; }
	.teamImgSize { width:30px; height: 30px; text-align:center; }
	.teamName { width:500px; } 
	.teamGame { width:100px; text-align:center; }
	.teamWin { width:100px; text-align:center; }
	.teamDraw { width:100px; text-align:center; }
	.teamLoss { width:100px; text-align:center; }
	.teamGf { width:100px; text-align:center; }
	.teamGa { width:100px; text-align:center; }
	.teamGd { width:100px; text-align:center; }
	.teamPts { width:100px; text-align:center; }
	.textDecoNone {color:white;}
	
	#rule { width:100%; margin:0px 3% 1% 3%; display:inline-block; 
			color:#D0D0D0; font-size:1vw;}
	.ruleUEFAChampsBox { width:0.8vw; height:0.8vh; background-color:skyblue; 
						display:inline-block; }
	.ruleUEFAEuropaBox { width:0.8vw; height:0.8vh; background-color:#DDAA00; 
						display:inline-block; }
	.ruleRelegation { width:0.8vw; height:0.8vh; background-color:#00C7C7; 
						display:inline-block; }
	.ruleDemotedBox { width:0.8vw; height:0.8vh; background-color:#EE3030; 
						display:inline-block; }
	
</style>
<meta charset="UTF-8">
<title>	리그 순위 </title>
<link rel="stylesheet" href="football.css">
</head>
<body>
<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡ 타이틀 START ㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
<jsp:include page="pageTitleGray.jsp" />
<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 타이틀 END ㅡㅡㅡㅡㅡㅡㅡㅡ-->

<!--ㅡㅡㅡㅡㅡㅡ 테이블 SRART ㅡㅡㅡㅡㅡㅡ-->
<div id="start">
	<!-- <div id ="TeamOrPlayer"></div> -->
	<!--ㅡㅡㅡㅡㅡㅡ 리그 선택 & SRART ㅡㅡㅡㅡㅡㅡ-->
	<div id="league">
		<div class="league_choice"><a href="TeamRank.do?league=epl&sid_daum=20222023&sslug=premier-league&sid_sofa=41886" class="league_text">프리미어리그</a></div>
		<div class="league_choice"><a href="TeamRank.do?league=primera&sid_daum=20222023&sslug=laliga&sid_sofa=42409" class="league_text">라리가</a></div>
		<div class="league_choice"><a href="TeamRank.do?league=bundesliga&sid_daum=20222023&sslug=bundesliga&sid_sofa=42268" class="league_text">분데스리가</a></div>
		<div class="league_choice"><a href="TeamRank.do?league=seriea&sid_daum=20222023&sslug=serie-a&sid_sofa=42415" class="league_text">세리에A</a></div>
		<div class="league_choice"><a href="TeamRank.do?league=ligue1&sid_daum=20222023&sslug=ligue-1&sid_sofa=42273" class="league_text">리그1</a></div>
	</div>
	<!--ㅡㅡㅡㅡㅡㅡ 리그 선택 & END ㅡㅡㅡㅡㅡㅡ-->
	
	<!--ㅡㅡㅡㅡㅡ 팀/선수 선택 START ㅡㅡㅡㅡㅡ-->
	<div id="choice_margin">
		<div class="choice">
			<div class="choice_box"><span class="choice_text"><a href="TeamRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>" class="choice_text_white">팀</a></span></div>
			<div class="choice_box"><span class="choice_text"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=goals" class="choice_text_white">선수</a></span></div>
		</div>
	</div>
	<!--ㅡㅡㅡㅡㅡㅡ 팀/선수 선택 END ㅡㅡㅡㅡㅡㅡ-->
	
	<!--ㅡㅡㅡㅡㅡ 테이블 소개 START  ㅡㅡㅡㅡㅡ-->
	<div id="rankIntro">
		종합 순위 &nbsp;&nbsp;<span class="rankSeason"><%=sid_daum %> 시즌</span>
	</div>
	<!--ㅡㅡㅡㅡㅡㅡ 테이블 소개 END  ㅡㅡㅡㅡㅡㅡ-->
	
	<!--ㅡㅡㅡㅡㅡㅡ 시즌 선택 START ㅡㅡㅡㅡㅡㅡ-->
	<div id="rankSeasonChoice">
		<form action="TeamRank.do" method=get>
			<select name="sid_daum" style="width:100px; height:27px;" >
				<option value="20222023">2022-2023</option>
				<option value="20212022">2021-2022</option>
				<option value="20202021">2020-2021</option>
				<option value="20192020">2019-2020</option>
				<option value="20182019">2018-2019</option>
				<option value="20172018">2017-2018</option>
				<option value="20162017">2016-2017</option>
				<option value="20152016">2015-2016</option>
				<option value="20142015">2014-2015</option>
				<option value="20132014">2013-2014</option>
				<option value="20122013">2012-2013</option>
				<option value="20112012">2011-2012</option>
				<option value="20102011">2010-2011</option>
			</select>
			<input type="hidden" name="league" value="<%=league %>">
			<input type="hidden" name="sslug" value="<%=sslug %>">
			<input type="hidden" name="sid_sofa" value="<%=sid_sofa %>">
			<input type="submit" style="height:30px; background-color:skyblue" value="시즌 검색">
		</form>
	</div>
	<!--ㅡㅡㅡㅡㅡㅡㅡ 시즌 선택 END ㅡㅡㅡㅡㅡㅡㅡ-->
	
	<!--ㅡㅡㅡㅡㅡㅡ 종합 순위 START ㅡㅡㅡㅡㅡㅡ-->
	<div>
		<table id="rankTable">
			<tr class="tableTh">
				<td class="teamRank">순위</td>
				<td colspan="2" class="teamIN">팀</td>
				<td class="teamGame">경기</td>
				<td class="teamWin">승</td>
				<td class="teamDraw">무</td>
				<td class="teamLoss">패</td>
				<td class="teamGf">득점</td>
				<td class="teamGa">실점</td>
				<td class="teamGd">득실차</td>
				<td class="teamPts">승점</td>
			</tr>
			<%
			for(int i=0; i<teamRank.size(); i++) {
				tko = teamRank.get(i).getTko();
				timg = teamRank.get(i).getTimg();
				ranking = teamRank.get(i).getRanking();
				game = teamRank.get(i).getGame();
				win = teamRank.get(i).getWin();
				draw = teamRank.get(i).getDraw();
				loss = teamRank.get(i).getLoss();
				gf = teamRank.get(i).getGf();
				ga = teamRank.get(i).getGa();
				gd = teamRank.get(i).getDraw();//gd=득실차 = gf-ga
				pts = teamRank.get(i).getPts();	
				tid_sofa = teamRank.get(i).getTid_sofa();
				tid_daum = teamRank.get(i).getTid_daum();
			%>
			
			<tr class="tableTr">
				<%if(ranking<=4) { %>
				<td class="teamRankUEFAchamps"><%=ranking %></td>	
				<%} else if (ranking<=6) { %>
				<td class="teamRankUEFAeuropa"><%=ranking %></td>
				<%} else if (ranking==16 && league.equals("bundesliga")) { %>
				<td class="teamRankRelegation"><%=ranking %></td>
				<%} else if (ranking==17 && league.equals("bundesliga")) { %>
				<td class="teamRankDemoted"><%=ranking %></td>
				<%} else if (ranking==18 && league.equals("ligue1")) { %>
				<td class="teamRankRelegation"><%=ranking %></td>
				<%} else if (ranking>=18) { %>
				<td class="teamRankDemoted"><%=ranking %></td>
				<%} else { %>
				<td class="teamRank"><%=ranking %></td>
				<%} %>
				<td class="teamImg"><a href="Squad.do?league=<%=league %>&team=<%=tko %>&tid_daum=<%=tid_daum %>&tid_sofa=<%=tid_sofa %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>" class="textDecoNone">
									<img src="<%=timg %>" class="teamImgSize"></img></a></td>
				<td class="teamName"><a href="Squad.do?league=<%=league %>&team=<%=tko %>&tid_daum=<%=tid_daum %>&tid_sofa=<%=tid_sofa %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>" class="textDecoNone">
									<%=tko %></a></td>
				<td class="teamGame"><%=game %></td>
				<td class="teamWin"><%=win %></td>
				<td class="teamDraw"><%=draw %></td>
				<td class="teamLoss"><%=loss %></td>
				<td class="teamGf"><%=gf %></td>
				<td class="teamGa"><%=ga %></td>
				<td class="teamGd"><%=gf-ga %></td>
				<td class="teamPts"><%=pts %></td>
			</tr>	
			<%	} %>
		</table>
		<!--ㅡㅡㅡㅡㅡㅡㅡ 규칙 STRAT ㅡㅡㅡㅡㅡㅡㅡ-->
		<div id="rule">
			<div class="ruleUEFAChampsBox"></div> UEFA 챔스 출전 자격을 얻는다.
			<div class="ruleUEFAEuropaBox"></div>유로파리그 출전 자격을 얻는다.
			<div class="ruleRelegation"></div>승강 플레이오프를 치른다.
			<div class="ruleDemotedBox"></div>2부 리그로 강등된다.
		</div>
		<!--ㅡㅡㅡㅡㅡㅡㅡ 규칙 STRAT ㅡㅡㅡㅡㅡㅡㅡ-->
	</div>
	<!--ㅡㅡㅡㅡㅡㅡ 종합 순위 END ㅡㅡㅡㅡㅡㅡ-->
</div>
<!--ㅡㅡㅡㅡㅡㅡㅡ 테이블 END ㅡㅡㅡㅡㅡㅡㅡ-->
</body>
</html>