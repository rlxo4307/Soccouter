<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.dtoPlayerRank" %>
<%@ page import="dto.dtoPlayerRank_Assists" %>
<%@ page import="dto.dtoPlayerRank_Total_Shots" %>
<%@ page import="dto.dtoPlayerRank_Attack_Points" %>
<%@ page import="dto.dtoPlayerRank_Shots_On_Target" %>
<%@ page import="dto.dtoPlayerRank_Penalties_Taken" %>
<%@ page import="dto.dtoPlayerRank_Fouls" %>
<%@ page import="dto.dtoPlayerRank_Yellow_Cards" %>
<%@ page import="dto.dtoPlayerRank_Red_Cards" %>
<%@ page import="dto.dtoPlayerRank_Offsides" %>
<%@ page import="jdbc.DAO" %>
<%
	ArrayList<dtoPlayerRank> PlayerRank = (ArrayList)request.getAttribute("PlayerRank");
//	ArrayList<dtoPlayerRank> PlayerRankTest = (ArrayList)request.getAttribute("PlayerRankTest");
	ArrayList<dtoPlayerRank_Assists> PlayerRank_Assists = (ArrayList)request.getAttribute("PlayerRank_Assists");
	ArrayList<dtoPlayerRank_Total_Shots> PlayerRank_Total_Shots = (ArrayList)request.getAttribute("PlayerRank_Total_Shots");
	ArrayList<dto.dtoPlayerRank_Attack_Points> PlayerRank_Attack_Points = (ArrayList)request.getAttribute("PlayerRank_Attack_Points");
	ArrayList<dtoPlayerRank_Shots_On_Target> PlayerRank_Shots_On_Target = (ArrayList)request.getAttribute("PlayerRank_Shots_On_Target");
	ArrayList<dtoPlayerRank_Penalties_Taken> PlayerRank_Penalties_Taken = (ArrayList)request.getAttribute("PlayerRank_Penalties_Taken");
	ArrayList<dtoPlayerRank_Fouls> PlayerRank_Fouls = (ArrayList)request.getAttribute("PlayerRank_Fouls");
	ArrayList<dtoPlayerRank_Yellow_Cards> PlayerRank_Yellow_Cards = (ArrayList)request.getAttribute("PlayerRank_Yellow_Cards");
	ArrayList<dtoPlayerRank_Red_Cards> PlayerRank_Red_Cards = (ArrayList)request.getAttribute("PlayerRank_Red_Cards");
	ArrayList<dtoPlayerRank_Offsides> PlayerRank_Offsides = (ArrayList)request.getAttribute("PlayerRank_Offsides");
	
	String league = request.getParameter("league");
	String sslug = request.getParameter("sslug");
	String sid_daum = request.getParameter("sid_daum");
	int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
	String category = request.getParameter("category");
	
	System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
	System.out.println("PlayerRank.jsp | league : " + league);
	System.out.println("PlayerRank.jsp | sslug : " + sslug);
	System.out.println("PlayerRank.jsp | category : " + category);
	System.out.println("PlayerRank.jsp | sid_sofa : " + sid_sofa);
	
	int tid_daum = 0;
	int tid_sofa = 0;
	int pid_sofa = 0;
	
	int rank = 0;
	String pko = null;
	String pimg = null;
	String tko = null;
	String timg = null;
	String no_img_url = "https://t1.daumcdn.net/media/img-section/sports13/player/noimage/square_m.png";
	int appearances = 0;
	int goals = 0;
	int assists = 0;
	int attack_points = 0;
	int total_shots = 0;
	int shots_on_target = 0;
	int penalties_taken = 0;
	int fouls = 0;
	int yellow_cards = 0;
	int red_cards = 0;
	int offsides = 0;
	
	if(PlayerRank.size() != 0) {
		pko = PlayerRank.get(0).getPko();
		pimg = PlayerRank.get(0).getPimg();
		tko = PlayerRank.get(0).getTko();
		timg = PlayerRank.get(0).getTimg();
	}
	
	DAO dao = new DAO();
%>
<!DOCTYPE html>
<html>
<head>
<style>
	
	#start { margin-left:15%; margin-right:15%; height:140vh; border: 1px solid #FFD700; }
	
	#div_margin { margin-left:5%; margin-right:5%; }
	
	a { text-decoration:none } 
	
	#choice { display:block; width:60vw; height:13vh; }
	.choice_box { width:7vw; height:2vw; border:1px solid white; float:left; 
					text-align:center; padding:6px 0;}
	.choice_text { font-size:1.2vw; color:white; }
	
	#rank_top4 { width:15.3vw; height:32.2vh; float:left; border:1px solid #6060FF; margin-bottom:3vh; }
	.top4_title { font-size:21px; text-align:center; background-color:#777777; color:white; }
	.top_player { height:12vh; }
	.top_player_img { width:100px; height:100px; display:block;
						padding:1vh; margin:auto; border-radius:50%; }
	.top_player_info { width:15.3vw; height:2.5vh; }
	.top_player_ranking { width:1vw; font-size:13px; font-style:bold; color:white; float:left; }
	/* .top_playerName_team { font-size:0.8vw; overflow:hidden; color:white; } */
	.top_playerName_team { width:10.3vw; color:white; float:left; }
	.top_player_name { width:11.3vw; height:1.2vh; font-size:11px; display:block; color:white; text-decoration-line:none; }
	.top_player_team { width:11.3vw; height:1.2vh; font-size:12px; display:block; color:white; margin-top:0.2vh; }
	.top_player_record { width:1.2vw; font-size:1vw; font-style:bold; color:#FFD700; float:left; margin-left:1vw; }
	.top_player_unit { width:1vw; font-size:1.1vw; font-style:bold; color:#FFD700; float:left; }
	.space { height:1vh; }
	
	.least_player_info { height:2.9vh; padding-top:0.6vh; border-top:1px solid #6060FF; }
	.least_player_ranking { width:1vw; font-size:13px; font-style:bold; color:white; float:left; }
	.least_playerName_team { width:10.3vw; color:white; float:left; }
	.least_player_name { width:11.3vw; height:1.2vh; font-size:11px; display:block; color:white; text-decoration-line:none;;
						margin-bottom:0.2vh; }
	.least_player_team { width:11.3vw; height:1.2vh; font-size:12px; display:block; color:white; }
	.least_player_record { width:1.2vw; font-size:1vw; font-style:bold; color:white; float:left; margin-left:1vw; }
	.least_player_unit { width:1vw; font-size:1.1vw; font-style:bold; color:white; float:left; }
	
	#category_intro { font-size:21px; color:white; }
	
	#player_category { width:62vw; height:4vh; font-size:0.8vw; text-align:center;
					background-color:#777777; color:white; 
					border-top:1px solid #404040; border-bottom:0.5px solid #404040;
					border-left:0.5px solid #404040; border-right:0.5px solid #404040; }
	.player_name { width:14vw; text-align:left; float:left; margin-top:1vh; }
	.player_team { width:9vw; text-align:left; float:left; margin-top:1vh; }
	.player_2 { width:2vw; float:left; text-align:center; margin-top:1vh;}
	.player_3 { width:2.7vw; float:left; text-align:center; margin-top:1vh; }
	.player_5 { width:4.2vw; float:left; text-align:center; margin-top:1vh; }
	.player_6 { width:4.8vw; float:left; text-align:center; margin-top:1vh; }
	.player_category_deco { font-size:0.8vw; color:white; text-align:center; }
	
	.player_category_choice_3 { width:2.7vw; height:2vh; font-size:0.8vw; color:black; background-color:#5050EE; 
								float:left; text-align:center; padding-top:1vh; padding-bottom:1vh; }
	.player_category_choice_5 { width:4.2vw; height:2vh; font-size:0.8vw; color:black; background-color:#5050EE; 
								float:left; text-align:center; padding-top:1vh; padding-bottom:1vh; }
	.player_category_choice_6 { width:4.8vw; height:2vh; font-size:0.8vw; color:black; background-color:#5050EE; 
								float:left; text-align:center; padding-top:1vh; padding-bottom:1vh; }
	.player_category_choice_deco { font-size:0.8vw; color:#FFD700; text-align:center; }
	
	
	#player_record { width:62vw; height:4vh; font-size:0.8vw; text-align:center; 
				border-left:0.5px solid #909090; border-right:0.5px solid #909090;
				border-bottom:0.5px solid #909090; color:white; }
	.record_player_img { width:3vw; height:3vh; float:left; }
	.record_player_img_size { width:35px; height:35px; border-radius:50%; }
	.record_player_name { width:11vw; text-align:left; color:white; float:left; margin-top:1vh; }
	.record_player_name_a { font-size:0.8vw; color:white; }
	
	.record_player_3_choice { width:2.7vw; height:3vh; float:left; text-align:center; padding-top:1vh;
							border-left:1px solid #5050EE; border-right:1px solid #5050EE; }
	.record_player_3_choice_last { width:2.7vw; height:3vh; float:left; text-align:center; padding-top:1vh;
								border-left:1px solid #5050EE; border-right:1px solid #5050EE; border-bottom:1px solid #5050EE; }
								
	.record_player_5_choice { width:4.2vw; height:3vh; float:left; text-align:center; padding-top:1vh;
							border-left:1px solid #5050EE; border-right:1px solid #5050EE; }
	.record_player_5_choice_last { width:4.2vw; height:3vh; float:left; text-align:center; padding-top:1vh;
								border-left:1px solid #5050EE; border-right:1px solid #5050EE;	border-bottom:1px solid #5050EE; }
								
	.record_player_6_choice { width:4.8vw; height:3vh; float:left; text-align:center; padding-top:1vh;
								border-left:1px solid #5050EE; border-right:1px solid #5050EE; }
	.record_player_6_choice_last { width:4.8vw; height:3vh; float:left; text-align:center; padding-top:1vh;
								border-left:1px solid #5050EE; border-right:1px solid #5050EE; border-bottom:1px solid #5050EE; }

</style>
<meta charset="UTF-8">
<title>선수 및 팀 카테고리별 순위</title>
<link rel="stylesheet" href="football.css">
</head>
<body>
<jsp:include page="pageTitleGray.jsp" />
<div id="start">

	<!--ㅡㅡㅡㅡㅡㅡ 리그 선택 & SRART ㅡㅡㅡㅡㅡㅡ-->
	<div id="league">
		<div class="league_choice"><a href="PlayerRank.do?league=epl&sslug=premier-league&sid_daum=<%=sid_daum %>&sid_sofa=41886&category=goals" class="league_text">프리미어리그</a></div>
		<div class="league_choice"><a href="PlayerRank.do?league=primera&sslug=laliga&sid_daum=<%=sid_daum %>&sid_sofa=42409&category=goals" class="league_text">라리가</a></div>
		<div class="league_choice"><a href="PlayerRank.do?league=bundesliga&sslug=bundesliga&sid_daum=<%=sid_daum %>&sid_sofa=42268&category=goals" class="league_text">분데스리가</a></div>
		<div class="league_choice"><a href="PlayerRank.do?league=seriea&sslug=serie-a&sid_daum=<%=sid_daum %>&sid_sofa=42415&category=goals" class="league_text">세리에A</a></div>
		<div class="league_choice"><a href="PlayerRank.do?league=ligue1&sslug=ligue-1&sid_daum=<%=sid_daum %>&sid_sofa=42273&category=goals" class="league_text">리그1</a></div>
	</div>
	<!--ㅡㅡㅡㅡㅡㅡ 리그 선택 & END ㅡㅡㅡㅡㅡㅡ-->
	<div id="div_margin">
		<!--ㅡㅡㅡㅡㅡㅡ 팀, 선수 선택 START ㅡㅡㅡㅡㅡㅡ-->
		<div id="choice">
			<div class="choice_box"><a href="TeamRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>" class="choice_text">팀</a></div>
			<div class="choice_box"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=goals" class="choice_text">선수</a></div>
		</div>
		<!--ㅡㅡㅡㅡㅡㅡㅡ 팀, 선수 선택 END ㅡㅡㅡㅡㅡㅡㅡ-->
		
		<!--ㅡㅡㅡㅡㅡㅡ 선수 기록별 랭킹 SRART ㅡㅡㅡㅡㅡㅡ-->
		<div id="rank_top4">
		<div class="top4_title">득점</div>
		<% 
		for(int i = 0; i<4; i++) {
			tid_sofa = PlayerRank.get(i).getTid_sofa();
			pid_sofa = PlayerRank.get(i).getPid_sofa();
			rank = PlayerRank.get(i).getRank();
			pko = PlayerRank.get(i).getPko();
			pimg = PlayerRank.get(i).getPimg();
			tko = PlayerRank.get(i).getTko();
			timg = PlayerRank.get(i).getTimg();
			goals = PlayerRank.get(i).getGoals();
			if(i==0) {%>
			<div class="top_player">
				<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="top_player_img"></a>
			</div>
			<div class="top_player_info">
				<span class="top_player_ranking"><%=rank %></span>
				<div class="top_playerName_team">
					<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="top_player_name"><%=pko %></span></a>
					<span class="top_player_team">[<%=tko %>]</span>
				</div>
				<span class="top_player_record"><%=goals %></span>
				<span class="top_player_unit">&nbsp;골&nbsp;</span>
			</div>
			<% }
			if(i>=1) {%>
			<div class="space"></div>
			<div class="least_player_info">
				<span class="least_player_ranking"><%=rank %></span>
				<div class="least_playerName_team">
					<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="least_player_name"><%=pko %></span></a>
					<span class="least_player_team">[<%=tko %>]</span>
				</div>
				<span class="least_player_record"><%=goals %></span>
				<span class="least_player_unit">&nbsp;골&nbsp;</span>	
			</div>
			<% } } %>
		</div>
		
		
		<div id="rank_top4">
		<div class="top4_title">도움</div>
		<%
		for(int i = 0; i < 4; i++) {
			tid_sofa = PlayerRank_Assists.get(i).getTid_sofa();
			pid_sofa = PlayerRank_Assists.get(i).getPid_sofa();
			rank = PlayerRank_Assists.get(i).getRank();
			pko = PlayerRank_Assists.get(i).getPko();
			pimg = PlayerRank_Assists.get(i).getPimg();
			tko = PlayerRank_Assists.get(i).getTko();
			timg = PlayerRank_Assists.get(i).getTimg();
			assists = PlayerRank_Assists.get(i).getAssists();
			if(i==0) {%>
			<div class="top_player">
				<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="top_player_img"></a>
			</div>
			<div class="top_player_info">
				<span class="top_player_ranking"><%=rank %></span>
				<div class="top_playerName_team">
					<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="top_player_name"><%=pko %></span></a>
					<span class="top_player_team">[<%=tko %>]</span>
				</div>
				<span class="top_player_record"><%=assists %></span>
				<span class="top_player_unit">&nbsp;회&nbsp;</span>	
			</div>
			<% }
			if(i>=1) {%>
			<div class="space"></div>
			<div class="least_player_info">
				<span class="least_player_ranking"><%=rank %></span>
				<div class="least_playerName_team">
					<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="least_player_name"><%=pko %></span></a>
					<span class="least_player_team">[<%=tko %>]</span>
				</div>
				<span class="least_player_record"><%=assists %></span>
				<span class="least_player_unit">&nbsp;회&nbsp;</span>
			</div>
			<% } }%>
		</div>
		
		
		<div id="rank_top4">
		<div class="top4_title">공격 포인트</div>
		<% 
		for(int i = 0; i<4; i++) {
			tid_sofa = PlayerRank_Attack_Points.get(i).getTid_sofa();
			pid_sofa = PlayerRank_Attack_Points.get(i).getPid_sofa();
			rank = PlayerRank_Attack_Points.get(i).getRank();
			pko = PlayerRank_Attack_Points.get(i).getPko();
			pimg = PlayerRank_Attack_Points.get(i).getPimg();
			tko = PlayerRank_Attack_Points.get(i).getTko();
			timg = PlayerRank_Attack_Points.get(i).getTimg();
			attack_points = PlayerRank_Attack_Points.get(i).getAttack_points();
			if(i==0) {%>
			<div class="top_player">
				<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="top_player_img"></a>
			</div>
			<div class="top_player_info">
				<span class="top_player_ranking"><%=rank %></span>
				<div class="top_playerName_team">
					<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="top_player_name"><%=pko %></span></a>
					<span class="top_player_team">[<%=tko %>]</span>
				</div>
				<span class="top_player_record"><%=attack_points %></span>
				<span class="top_player_unit">&nbsp;P&nbsp;</span>
			</div>
			<% }
			if(i>=1) {%>
			<div class="space"></div>
			<div class="least_player_info">
				<span class="least_player_ranking"><%=rank %></span>
				<div class="least_playerName_team">
					<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="least_player_name"><%=pko %></span></a>
					<span class="least_player_team">[<%=tko %>]</span>
				</div>
				<span class="least_player_record"><%=attack_points %></span>
				<span class="least_player_unit">&nbsp;P&nbsp;</span>
			</div>
			<% } }%>
		</div>
		
		
		<div id="rank_top4">
		<div class="top4_title">슈팅</div>
		<% 
		for(int i = 0; i<4; i++) {
			tid_sofa = PlayerRank_Total_Shots.get(i).getTid_sofa();
			pid_sofa = PlayerRank_Total_Shots.get(i).getPid_sofa();
			rank = PlayerRank_Total_Shots.get(i).getRank();
			pko = PlayerRank_Total_Shots.get(i).getPko();
			pimg = PlayerRank_Total_Shots.get(i).getPimg();
			tko = PlayerRank_Total_Shots.get(i).getTko();
			timg = PlayerRank_Total_Shots.get(i).getTimg();
			total_shots = PlayerRank_Total_Shots.get(i).getTotal_shots();
			if(i==0) {%>
			<div class="top_player">
				<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="top_player_img"></a>
			</div>
			<div class="top_player_info">
				<span class="top_player_ranking"><%=rank %></span>
				<div class="top_playerName_team">
					<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="top_player_name"><%=pko %></span></a>
					<span class="top_player_team">[<%=tko %>]</span>
				</div>
				<span class="top_player_record"><%=total_shots %></span>
				<span class="top_player_unit">&nbsp;회&nbsp;</span>	
			</div>
			<% }
			if(i>=1) {%>
			<div class="space"></div>
			<div class="least_player_info">
				<span class="least_player_ranking"><%=rank %></span>
				<div class="least_playerName_team">
					<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="least_player_name"><%=pko %></span></a>
					<span class="least_player_team">[<%=tko %>]</span>
				</div>
				<span class="least_player_record"><%=total_shots %></span>
				<span class="least_player_unit">&nbsp;회&nbsp;</span>
			</div>
			<% } }%>
		</div>
		<!--ㅡㅡㅡㅡㅡㅡㅡ 선수 기록별 랭킹 END ㅡㅡㅡㅡㅡㅡㅡ-->
		
		<div id="category_intro">부문 별 선수 기록</div>
		
		<!--ㅡㅡㅡㅡㅡㅡ 부문 별 선수 기록 START ㅡㅡㅡㅡㅡㅡ-->
		<div id="player_category">
			<div class="player_2">순위</div>
			<div class="player_name">&nbsp;선수</div>
			<div class="player_team">&nbsp;팀</div>
			<div class="player_3">경기</div>
		<% if(category.equals("goals")) { %>
			<div class="player_category_choice_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=goals" class="player_category_choice_deco">득점</a></div>
		<%} else { %>
			<div class="player_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=goals" class="player_category_deco">득점</a></div>
		<% } %>
		<% if(category.equals("assists")) { %>
			<div class="player_category_choice_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=assists" class="player_category_choice_deco">도움</a></div>
		<%} else { %>
			<div class="player_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=assists" class="player_category_deco">도움</a></div>
		<% } %>
		<% if(category.equals("attack_points")) { %>
			<div class="player_category_choice_6"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=attack_points" class="player_category_choice_deco">공격포인트</a></div>
		<%} else { %>
			<div class="player_6"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=attack_points" class="player_category_deco">공격포인트</a></div>
		<% } %>
		<% if(category.equals("total_shots")) { %>
			<div class="player_category_choice_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=total_shots" class="player_category_choice_deco">슈팅</a></div>
		<%} else { %>
			<div class="player_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=total_shots" class="player_category_deco">슈팅</a></div>
		<% } %>
		<% if(category.equals("shots_on_target")) { %>
			<div class="player_category_choice_5"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=shots_on_target" class="player_category_choice_deco">유효 슈팅</a></div>
		<%} else { %>
			<div class="player_5"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=shots_on_target" class="player_category_deco">유효 슈팅</a></div>
		<% } %>
		<% if(category.equals("penalties_taken")) { %>
			<div class="player_category_choice_5"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=penalties_taken" class="player_category_choice_deco">패널티 킥</a></div>
		<%} else { %>
			<div class="player_5"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=penalties_taken" class="player_category_deco">패널티 킥</a></div>
		<% } %>
		<% if(category.equals("fouls")) { %>
			<div class="player_category_choice_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=fouls" class="player_category_choice_deco">파울</a></div>
		<%} else { %>
			<div class="player_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=fouls" class="player_category_deco">파울</a></div>
		<% } %>
		<% if(category.equals("yellow_cards")) { %>
			<div class="player_category_choice_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=yellow_cards" class="player_category_choice_deco">경고</a></div>
		<%} else { %>
			<div class="player_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=yellow_cards" class="player_category_deco">경고</a></div>
		<% } %>
		<% if(category.equals("red_cards")) { %>
			<div class="player_category_choice_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=red_cards" class="player_category_choice_deco">퇴장</a></div>
		<%} else { %>
			<div class="player_3"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=red_cards" class="player_category_deco">퇴장</a></div>
		<% } %>
		<% if(category.equals("offsides")) { %>
			<div class="player_category_choice_6"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=offsides" class="player_category_choice_deco">오프사이드</a></div>
		<%} else { %>
			<div class="player_6"><a href="PlayerRank.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&category=offsides" class="player_category_deco">오프사이드</a></div>
		<% } %>
		</div>
		<% if(category.equals("goals")) {
			for(int i = 0; i<PlayerRank.size(); i++) {
				tid_sofa = PlayerRank.get(i).getTid_sofa();
				pid_sofa = PlayerRank.get(i).getPid_sofa();
				rank = PlayerRank.get(i).getRank();
				pko = PlayerRank.get(i).getPko();
				pimg = PlayerRank.get(i).getPimg();
				tko = PlayerRank.get(i).getTko();
				timg = PlayerRank.get(i).getTimg();
				appearances = PlayerRank.get(i).getAppearances();
				goals = PlayerRank.get(i).getGoals();
				assists = PlayerRank.get(i).getAssists();
				attack_points = PlayerRank.get(i).getAttack_points();
				total_shots = PlayerRank.get(i).getTotal_shots();
				shots_on_target = PlayerRank.get(i).getShots_on_target();
				penalties_taken = PlayerRank.get(i).getPenalties_taken();
				fouls = PlayerRank.get(i).getFouls();
				yellow_cards = PlayerRank.get(i).getYellow_cards();
				red_cards = PlayerRank.get(i).getRed_cards();
				offsides = PlayerRank.get(i).getOffsides();
		%>
		<div id="player_record">
			<div class="player_2"><%=rank %></div>
			<div class="record_player_img"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="record_player_img_size"></a></div>
			<div class="record_player_name">&nbsp;<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="record_player_name_a"><%=pko %></span></a></div>
			<div class="player_team">&nbsp;<%=tko %></div>
			<div class="player_3"><%=appearances %></div>
			<% if(category.equals("goals") && i<19 ) {%>
			<div class="record_player_3_choice"><%=goals %></div> 
			<%} else {%> 
			<div class="record_player_3_choice_last"><%=goals %></div>
			<%} %>
			<div class="player_3"><%=assists %></div>
			<div class="player_6"><%=attack_points %></div>
			<div class="player_3"><%=total_shots %></div>
			<div class="player_5"><%=shots_on_target %></div>
			<div class="player_5"><%=penalties_taken %></div>
			<div class="player_3"><%=fouls %></div>
			<div class="player_3"><%=yellow_cards %></div>
			<div class="player_3"><%=red_cards %></div>
			<div class="player_5"><%=offsides %></div>
		</div>
		<%} } %>	
		
		<% if(category.equals("assists")) {
			for(int i = 0; i<PlayerRank_Assists.size(); i++) {
				tid_sofa = PlayerRank_Assists.get(i).getTid_sofa();
				pid_sofa = PlayerRank_Assists.get(i).getPid_sofa();
				rank = PlayerRank_Assists.get(i).getRank();
				pko = PlayerRank_Assists.get(i).getPko();
				pimg = PlayerRank_Assists.get(i).getPimg();
				tko = PlayerRank_Assists.get(i).getTko();
				timg = PlayerRank_Assists.get(i).getTimg();
				appearances = PlayerRank_Assists.get(i).getAppearances();
				goals = PlayerRank_Assists.get(i).getGoals();
				assists = PlayerRank_Assists.get(i).getAssists();
				attack_points = PlayerRank_Assists.get(i).getAttack_points();
				total_shots = PlayerRank_Assists.get(i).getTotal_shots();
				shots_on_target = PlayerRank_Assists.get(i).getShots_on_target();
				penalties_taken = PlayerRank_Assists.get(i).getPenalties_taken();
				fouls = PlayerRank_Assists.get(i).getFouls();
				yellow_cards = PlayerRank_Assists.get(i).getYellow_cards();
				red_cards = PlayerRank_Assists.get(i).getRed_cards();
				offsides = PlayerRank_Assists.get(i).getOffsides(); 
		%>
		<div id="player_record">
			<div class="player_2"><%=rank %></div>
			<div class="record_player_img"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="record_player_img_size"></a></div>
			<div class="record_player_name">&nbsp;<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="record_player_name_a"><%=pko %></span></a></div>
			<div class="player_team">&nbsp;<%=tko %></div>
			<div class="player_3"><%=appearances %></div>
			<div class="player_3"><%=goals %></div>
			<% if(category.equals("assists") && i<19 ) {%>
			<div class="record_player_3_choice"><%=assists %></div> 
			<%} else {%> 
			<div class="record_player_3_choice_last"><%=assists %></div>
			<%} %>
			<div class="player_6"><%=attack_points %></div>
			<div class="player_3"><%=total_shots %></div>
			<div class="player_5"><%=shots_on_target %></div>
			<div class="player_5"><%=penalties_taken %></div>
			<div class="player_3"><%=fouls %></div>
			<div class="player_3"><%=yellow_cards %></div>
			<div class="player_3"><%=red_cards %></div>
			<div class="player_5"><%=offsides %></div>
		</div>
		<%} } %>
				
		<% if(category.equals("attack_points")) {
			for(int i = 0; i<PlayerRank_Attack_Points.size(); i++) {
				tid_sofa = PlayerRank_Attack_Points.get(i).getTid_sofa();
				pid_sofa = PlayerRank_Attack_Points.get(i).getPid_sofa();
				rank = PlayerRank_Attack_Points.get(i).getRank();
				pko = PlayerRank_Attack_Points.get(i).getPko();
				pimg = PlayerRank_Attack_Points.get(i).getPimg();
				tko = PlayerRank_Attack_Points.get(i).getTko();
				timg = PlayerRank_Attack_Points.get(i).getTimg();
				appearances = PlayerRank_Attack_Points.get(i).getAppearances();
				goals = PlayerRank_Attack_Points.get(i).getGoals();
				assists = PlayerRank_Attack_Points.get(i).getAssists();
				attack_points = PlayerRank_Attack_Points.get(i).getAttack_points();
				total_shots = PlayerRank_Attack_Points.get(i).getTotal_shots();
				shots_on_target = PlayerRank_Attack_Points.get(i).getShots_on_target();
				penalties_taken = PlayerRank_Attack_Points.get(i).getPenalties_taken();
				fouls = PlayerRank_Attack_Points.get(i).getFouls();
				yellow_cards = PlayerRank_Attack_Points.get(i).getYellow_cards();
				red_cards = PlayerRank_Attack_Points.get(i).getRed_cards();
				offsides = PlayerRank_Attack_Points.get(i).getOffsides(); 
			%>
		<div id="player_record">
			<div class="player_2"><%=rank %></div>
			<div class="record_player_img"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="record_player_img_size"></a></div>
			<div class="record_player_name">&nbsp;<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="record_player_name_a"><%=pko %></span></a></div>
			<div class="player_team">&nbsp;<%=tko %></div>
			<div class="player_3"><%=appearances %></div>
			<div class="player_3"><%=goals %></div>
			<div class="player_3"><%=assists %></div>
			<% if(category.equals("attack_points") && i<19 ) {%>
			<div class="record_player_6_choice"><%=attack_points %></div> 
			<%} else {%> 
			<div class="record_player_6_choice_last"><%=attack_points %></div>
			<%} %>
			<div class="player_3"><%=total_shots %></div>
			<div class="player_5"><%=shots_on_target %></div>
			<div class="player_5"><%=penalties_taken %></div>
			<div class="player_3"><%=fouls %></div>
			<div class="player_3"><%=yellow_cards %></div>
			<div class="player_3"><%=red_cards %></div>
			<div class="player_5"><%=offsides %></div>
		</div>
		<%} } %>
		
		<% if(category.equals("total_shots")) {
			for(int i = 0; i<PlayerRank_Total_Shots.size(); i++) {
				tid_sofa = PlayerRank_Total_Shots.get(i).getTid_sofa();
				pid_sofa = PlayerRank_Total_Shots.get(i).getPid_sofa();
				rank = PlayerRank_Total_Shots.get(i).getRank();
				pko = PlayerRank_Total_Shots.get(i).getPko();
				pimg = PlayerRank_Total_Shots.get(i).getPimg();
				tko = PlayerRank_Total_Shots.get(i).getTko();
				timg = PlayerRank_Total_Shots.get(i).getTimg();
				appearances = PlayerRank_Total_Shots.get(i).getAppearances();
				goals = PlayerRank_Total_Shots.get(i).getGoals();
				assists = PlayerRank_Total_Shots.get(i).getAssists();
				attack_points = PlayerRank_Total_Shots.get(i).getAttack_points();
				total_shots = PlayerRank_Total_Shots.get(i).getTotal_shots();
				shots_on_target = PlayerRank_Total_Shots.get(i).getShots_on_target();
				penalties_taken = PlayerRank_Total_Shots.get(i).getPenalties_taken();
				fouls = PlayerRank_Total_Shots.get(i).getFouls();
				yellow_cards = PlayerRank_Total_Shots.get(i).getYellow_cards();
				red_cards = PlayerRank_Total_Shots.get(i).getRed_cards();
				offsides = PlayerRank_Total_Shots.get(i).getOffsides();
			%>
		<div id="player_record">
			<div class="player_2"><%=rank %></div>
			<div class="record_player_img"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="record_player_img_size"></a></div>
			<div class="record_player_name">&nbsp;<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="record_player_name_a"><%=pko %></span></a></div>
			<div class="player_team">&nbsp;<%=tko %></div>
			<div class="player_3"><%=appearances %></div>
			<div class="player_3"><%=goals %></div>
			<div class="player_3"><%=assists %></div>
			<div class="player_6"><%=attack_points %></div>
			<% if(category.equals("total_shots") && i<19 ) {%>
			<div class="record_player_3_choice"><%=total_shots %></div> 
			<%} else {%> 
			<div class="record_player_3_choice_last"><%=total_shots %></div>
			<%} %>
			<div class="player_5"><%=shots_on_target %></div>
			<div class="player_5"><%=penalties_taken %></div>
			<div class="player_3"><%=fouls %></div>
			<div class="player_3"><%=yellow_cards %></div>
			<div class="player_3"><%=red_cards %></div>
			<div class="player_5"><%=offsides %></div>
		</div>
		<%} } %>
		
		<% if(category.equals("shots_on_target")) {
			for(int i = 0; i<PlayerRank_Assists.size(); i++) {
				tid_sofa = PlayerRank_Shots_On_Target.get(i).getTid_sofa();
				pid_sofa = PlayerRank_Shots_On_Target.get(i).getPid_sofa();
				rank = PlayerRank_Shots_On_Target.get(i).getRank();
				pko = PlayerRank_Shots_On_Target.get(i).getPko();
				pimg = PlayerRank_Shots_On_Target.get(i).getPimg();
				tko = PlayerRank_Shots_On_Target.get(i).getTko();
				timg = PlayerRank_Shots_On_Target.get(i).getTimg();
				appearances = PlayerRank_Shots_On_Target.get(i).getAppearances();
				goals = PlayerRank_Shots_On_Target.get(i).getGoals();
				assists = PlayerRank_Shots_On_Target.get(i).getAssists();
				attack_points = PlayerRank_Shots_On_Target.get(i).getAttack_points();
				total_shots = PlayerRank_Shots_On_Target.get(i).getTotal_shots();
				shots_on_target = PlayerRank_Shots_On_Target.get(i).getShots_on_target();
				penalties_taken = PlayerRank_Shots_On_Target.get(i).getPenalties_taken();
				fouls = PlayerRank_Shots_On_Target.get(i).getFouls();
				yellow_cards = PlayerRank_Shots_On_Target.get(i).getYellow_cards();
				red_cards = PlayerRank_Shots_On_Target.get(i).getRed_cards();
				offsides = PlayerRank_Shots_On_Target.get(i).getOffsides();
			%>
		<div id="player_record">
			<div class="player_2"><%=rank %></div>
			<div class="record_player_img"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="record_player_img_size"></a></div>
			<div class="record_player_name">&nbsp;<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="record_player_name_a"><%=pko %></span></a></div>
			<div class="player_team">&nbsp;<%=tko %></div>
			<div class="player_3"><%=appearances %></div>
			<div class="player_3"><%=goals %></div>
			<div class="player_3"><%=assists %></div>
			<div class="player_6"><%=attack_points %></div>
			<div class="player_3"><%=total_shots %></div>
			<% if(category.equals("shots_on_target") && i<19 ) {%>
			<div class="record_player_5_choice"><%=shots_on_target %></div> 
			<%} else {%> 
			<div class="record_player_5_choice_last"><%=shots_on_target %></div>
			<%} %>
			<div class="player_5"><%=penalties_taken %></div>
			<div class="player_3"><%=fouls %></div>
			<div class="player_3"><%=yellow_cards %></div>
			<div class="player_3"><%=red_cards %></div>
			<div class="player_5"><%=offsides %></div>
		</div>
		<%} } %>
		
		<% if(category.equals("penalties_taken")) {
			for(int i = 0; i<PlayerRank_Penalties_Taken.size(); i++) {
				tid_sofa = PlayerRank_Penalties_Taken.get(i).getTid_sofa();
				pid_sofa = PlayerRank_Penalties_Taken.get(i).getPid_sofa();
				rank = PlayerRank_Penalties_Taken.get(i).getRank();
				pko = PlayerRank_Penalties_Taken.get(i).getPko();
				pimg = PlayerRank_Penalties_Taken.get(i).getPimg();
				tko = PlayerRank_Penalties_Taken.get(i).getTko();
				timg = PlayerRank_Penalties_Taken.get(i).getTimg();
				appearances = PlayerRank_Penalties_Taken.get(i).getAppearances();
				goals = PlayerRank_Penalties_Taken.get(i).getGoals();
				assists = PlayerRank_Penalties_Taken.get(i).getAssists();
				attack_points = PlayerRank_Penalties_Taken.get(i).getAttack_points();
				total_shots = PlayerRank_Penalties_Taken.get(i).getTotal_shots();
				shots_on_target = PlayerRank_Penalties_Taken.get(i).getShots_on_target();
				penalties_taken = PlayerRank_Penalties_Taken.get(i).getPenalties_taken();
				fouls = PlayerRank_Penalties_Taken.get(i).getFouls();
				yellow_cards = PlayerRank_Penalties_Taken.get(i).getYellow_cards();
				red_cards = PlayerRank_Penalties_Taken.get(i).getRed_cards();
				offsides = PlayerRank_Penalties_Taken.get(i).getOffsides();
			%>
		<div id="player_record">
			<div class="player_2"><%=rank %></div>
			<div class="record_player_img"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="record_player_img_size"></a></div>
			<div class="record_player_name">&nbsp;<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="record_player_name_a"><%=pko %></span></a></div>
			<div class="player_team">&nbsp;<%=tko %></div>
			<div class="player_3"><%=appearances %></div>
			<div class="player_3"><%=goals %></div>
			<div class="player_3"><%=assists %></div>
			<div class="player_6"><%=attack_points %></div>
			<div class="player_3"><%=total_shots %></div>
			<div class="player_5"><%=shots_on_target %></div>
			<% if(category.equals("penalties_taken") && i<19 ) {%>
			<div class="record_player_5_choice"><%=penalties_taken %></div> 
			<%} else {%> 
			<div class="record_player_5_choice_last"><%=penalties_taken %></div>
			<%} %>
			<div class="player_3"><%=fouls %></div>
			<div class="player_3"><%=yellow_cards %></div>
			<div class="player_3"><%=red_cards %></div>
			<div class="player_5"><%=offsides %></div>
		</div>
		<%} } %>
		
		<% if(category.equals("fouls")) {
			for(int i = 0; i<PlayerRank_Fouls.size(); i++) {
				tid_sofa = PlayerRank_Fouls.get(i).getTid_sofa();
				pid_sofa = PlayerRank_Fouls.get(i).getPid_sofa();
				rank = PlayerRank_Fouls.get(i).getRank();
				pko = PlayerRank_Fouls.get(i).getPko();
				pimg = PlayerRank_Fouls.get(i).getPimg();
				tko = PlayerRank_Fouls.get(i).getTko();
				timg = PlayerRank_Fouls.get(i).getTimg();
				appearances = PlayerRank_Fouls.get(i).getAppearances();
				goals = PlayerRank_Fouls.get(i).getGoals();
				assists = PlayerRank_Fouls.get(i).getAssists();
				attack_points = PlayerRank_Fouls.get(i).getAttack_points();
				total_shots = PlayerRank_Fouls.get(i).getTotal_shots();
				shots_on_target = PlayerRank_Fouls.get(i).getShots_on_target();
				penalties_taken = PlayerRank_Fouls.get(i).getPenalties_taken();
				fouls = PlayerRank_Fouls.get(i).getFouls();
				yellow_cards = PlayerRank_Fouls.get(i).getYellow_cards();
				red_cards = PlayerRank_Fouls.get(i).getRed_cards();
				offsides = PlayerRank_Fouls.get(i).getOffsides();
			%>
		<div id="player_record">
			<div class="player_2"><%=rank %></div>
			<div class="record_player_img"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="record_player_img_size"></a></div>
			<div class="record_player_name">&nbsp;<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="record_player_name_a"><%=pko %></span></a></div>
			<div class="player_team">&nbsp;<%=tko %></div>
			<div class="player_3"><%=appearances %></div>
			<div class="player_3"><%=goals %></div>
			<div class="player_3"><%=assists %></div>
			<div class="player_6"><%=attack_points %></div>
			<div class="player_3"><%=total_shots %></div>
			<div class="player_5"><%=shots_on_target %></div>
			<div class="player_5"><%=penalties_taken %></div>
			<% if(category.equals("fouls") && i<19 ) {%>
			<div class="record_player_3_choice"><%=fouls %></div> 
			<%} else {%> 
			<div class="record_player_3_choice_last"><%=fouls %></div>
			<%} %>
			<div class="player_3"><%=yellow_cards %></div>
			<div class="player_3"><%=red_cards %></div>
			<div class="player_5"><%=offsides %></div>
		</div>
		<%} } %>
		
		<% if(category.equals("yellow_cards")) {
			for(int i = 0; i<PlayerRank_Yellow_Cards.size(); i++) {
				tid_sofa = PlayerRank_Yellow_Cards.get(i).getTid_sofa();
				pid_sofa = PlayerRank_Yellow_Cards.get(i).getPid_sofa();
				rank = PlayerRank_Yellow_Cards.get(i).getRank();
				pko = PlayerRank_Yellow_Cards.get(i).getPko();
				pimg = PlayerRank_Yellow_Cards.get(i).getPimg();
				tko = PlayerRank_Yellow_Cards.get(i).getTko();
				timg = PlayerRank_Yellow_Cards.get(i).getTimg();
				appearances = PlayerRank_Yellow_Cards.get(i).getAppearances();
				goals = PlayerRank_Yellow_Cards.get(i).getGoals();
				assists = PlayerRank_Yellow_Cards.get(i).getAssists();
				attack_points = PlayerRank_Yellow_Cards.get(i).getAttack_points();
				total_shots = PlayerRank_Yellow_Cards.get(i).getTotal_shots();
				shots_on_target = PlayerRank_Yellow_Cards.get(i).getShots_on_target();
				penalties_taken = PlayerRank_Yellow_Cards.get(i).getPenalties_taken();
				fouls = PlayerRank_Yellow_Cards.get(i).getFouls();
				yellow_cards = PlayerRank_Yellow_Cards.get(i).getYellow_cards();
				red_cards = PlayerRank_Yellow_Cards.get(i).getRed_cards();
				offsides = PlayerRank_Yellow_Cards.get(i).getOffsides();
			%>
		<div id="player_record">
			<div class="player_2"><%=rank %></div>
			<div class="record_player_img"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="record_player_img_size"></a></div>
			<div class="record_player_name">&nbsp;<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="record_player_name_a"><%=pko %></span></a></div>
			<div class="player_team">&nbsp;<%=tko %></div>
			<div class="player_3"><%=appearances %></div>
			<div class="player_3"><%=goals %></div>
			<div class="player_3"><%=assists %></div>
			<div class="player_6"><%=attack_points %></div>
			<div class="player_3"><%=total_shots %></div>
			<div class="player_5"><%=shots_on_target %></div>
			<div class="player_5"><%=penalties_taken %></div>
			<div class="player_3"><%=fouls %></div>
			<% if(category.equals("yellow_cards") && i<19 ) {%>
			<div class="record_player_3_choice"><%=yellow_cards %></div> 
			<%} else {%> 
			<div class="record_player_3_choice_last"><%=yellow_cards %></div>
			<%} %>
			<div class="player_3"><%=red_cards %></div>
			<div class="player_5"><%=offsides %></div>
		</div>
		<%} } %>
		
		<% if(category.equals("red_cards")) {
			for(int i = 0; i<PlayerRank_Red_Cards.size(); i++) {
				tid_sofa = PlayerRank_Red_Cards.get(i).getTid_sofa();
				pid_sofa = PlayerRank_Red_Cards.get(i).getPid_sofa();
				rank = PlayerRank_Red_Cards.get(i).getRank();
				pko = PlayerRank_Red_Cards.get(i).getPko();
				pimg = PlayerRank_Red_Cards.get(i).getPimg();
				tko = PlayerRank_Red_Cards.get(i).getTko();
				timg = PlayerRank_Red_Cards.get(i).getTimg();
				appearances = PlayerRank_Red_Cards.get(i).getAppearances();
				goals = PlayerRank_Red_Cards.get(i).getGoals();
				assists = PlayerRank_Red_Cards.get(i).getAssists();
				attack_points = PlayerRank_Red_Cards.get(i).getAttack_points();
				total_shots = PlayerRank_Red_Cards.get(i).getTotal_shots();
				shots_on_target = PlayerRank_Red_Cards.get(i).getShots_on_target();
				penalties_taken = PlayerRank_Red_Cards.get(i).getPenalties_taken();
				fouls = PlayerRank_Red_Cards.get(i).getFouls();
				yellow_cards = PlayerRank_Red_Cards.get(i).getYellow_cards();
				red_cards = PlayerRank_Red_Cards.get(i).getRed_cards();
				offsides = PlayerRank_Red_Cards.get(i).getOffsides();
			%>
		<div id="player_record">
			<div class="player_2"><%=rank %></div>
			<div class="record_player_img"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="record_player_img_size"></a></div>
			<div class="record_player_name">&nbsp;<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="record_player_name_a"><%=pko %></span></a></div>
			<div class="player_team">&nbsp;<%=tko %></div>
			<div class="player_3"><%=appearances %></div>
			<div class="player_3"><%=goals %></div>
			<div class="player_3"><%=assists %></div>
			<div class="player_6"><%=attack_points %></div>
			<div class="player_3"><%=total_shots %></div>
			<div class="player_5"><%=shots_on_target %></div>
			<div class="player_5"><%=penalties_taken %></div>
			<div class="player_3"><%=fouls %></div>
			<div class="player_3"><%=yellow_cards %></div>
			<% if(category.equals("red_cards") && i<19 ) {%>
			<div class="record_player_3_choice"><%=red_cards %></div> 
			<%} else {%> 
			<div class="record_player_3_choice_last"><%=red_cards %></div>
			<%} %>
			<div class="player_5"><%=offsides %></div>
		</div>
		<%} } %>
		
		<% if(category.equals("offsides")) {
			for(int i = 0; i<PlayerRank_Offsides.size(); i++) {
				tid_sofa = PlayerRank_Offsides.get(i).getTid_sofa();
				pid_sofa = PlayerRank_Offsides.get(i).getPid_sofa();
				rank = PlayerRank_Offsides.get(i).getRank();
				pko = PlayerRank_Offsides.get(i).getPko();
				pimg = PlayerRank_Offsides.get(i).getPimg();
				tko = PlayerRank_Offsides.get(i).getTko();
				timg = PlayerRank_Offsides.get(i).getTimg();
				appearances = PlayerRank_Offsides.get(i).getAppearances();
				goals = PlayerRank_Offsides.get(i).getGoals();
				assists = PlayerRank_Offsides.get(i).getAssists();
				attack_points = PlayerRank_Offsides.get(i).getAttack_points();
				total_shots = PlayerRank_Offsides.get(i).getTotal_shots();
				shots_on_target = PlayerRank_Offsides.get(i).getShots_on_target();
				penalties_taken = PlayerRank_Offsides.get(i).getPenalties_taken();
				fouls = PlayerRank_Offsides.get(i).getFouls();
				yellow_cards = PlayerRank_Offsides.get(i).getYellow_cards();
				red_cards = PlayerRank_Offsides.get(i).getRed_cards();
				offsides = PlayerRank_Offsides.get(i).getOffsides();
		%>
		<div id="player_record">
			<div class="player_2"><%=rank %></div>
			<div class="record_player_img"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" class="record_player_img_size"></a></div>
			<div class="record_player_name">&nbsp;<a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sslug=<%=sslug %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><span class="record_player_name_a"><%=pko %></span></a></div>
			<div class="player_team">&nbsp;<%=tko %></div>
			<div class="player_3"><%=appearances %></div>
			<div class="player_3"><%=goals %></div>
			<div class="player_3"><%=assists %></div>
			<div class="player_6"><%=attack_points %></div>
			<div class="player_3"><%=total_shots %></div>
			<div class="player_5"><%=shots_on_target %></div>
			<div class="player_5"><%=penalties_taken %></div>
			<div class="player_3"><%=fouls %></div>
			<div class="player_3"><%=yellow_cards %></div>
			<div class="player_3"><%=red_cards %></div>
			<% if(category.equals("offsides") && i<19 ) {%>
			<div class="record_player_5_choice"><%=offsides %></div> 
			<%} else {%> 
			<div class="record_player_5_choice_last"><%=offsides %></div>
			<%} %>
		</div>
		<%} } %>
		<!--ㅡㅡㅡㅡㅡㅡㅡ 부문 별 선수 기록 END ㅡㅡㅡㅡㅡㅡㅡ-->
		
	</div>
</div>
</body>
</html>