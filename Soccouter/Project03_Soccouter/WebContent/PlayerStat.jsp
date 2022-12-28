<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.dtoPlayerStat" %>
<%@ page import="jdbc.DAO" %>
<% 
	System.out.println("PlayerStat.jsp 페이지 진입");
	dtoPlayerStat PlayerStat = (dtoPlayerStat)request.getAttribute("PlayerStat");
	String league = request.getParameter("league");
	String sslug = request.getParameter("sslug");
	String category = request.getParameter("category");
	int sid_daum = Integer.parseInt(request.getParameter("sid_daum"));
	int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
	
	String no_img_url = "https://t1.daumcdn.net/media/img-section/sports13/player/noimage/square_m.png";
	int tid_sofa = 0;
	int pid_sofa = 0;
	String tko = null;
	String timg = null;
	String pko = null;
	String pen = null;
	String pimg = null;
	String pslug = null;
	int pheight = 0;
	String pbirth = null;
	String pnation = null;
	String pnationimg = null;
	String pfoot = null;
	String pfootko = null;
	String pposition = null;
	String position = null;
	
	int appearances = 0;
	double rating = 0;
	int shooting = 0;
	int passes = 0;
	int dribbles = 0;
	int minutes_played = 0;
	int saves = 0;
	int clean_sheets = 0;
	double conceded_per_game = 0;
	
	int individual_skill = 0;
	int genius = 0;
	int concentration = 0;
	int active = 0;
	int boldness = 0;
	int composure = 0;
	int cooperation = 0;
	int physicals = 0;
	int punches = 0;
	int runs_out = 0;
	int high_claims = 0;
	int saves_from_inside_box = 0;
	int saved_shots_from_outside_the_box = 0;
	int penalties_saved = 0;
	
	int goal_conversion_per = 0;
	int headed_goals = 0;
	int free_kick_goals = 0;
	int penalty_goals = 0;
	int crosses = 0;
	int one_on_one_mark = 0;
	int tackles = 0;
	int sight = 0;
	int defense_position = 0;
	int interceptions = 0;
	int clearances = 0;
	int blocked_shots = 0;
	
	if(PlayerStat != null) {
		tid_sofa = PlayerStat.getTid_sofa();
		pid_sofa = PlayerStat.getPid_sofa();
		tko = PlayerStat.getTko();
		timg = PlayerStat.getTimg();
		pko = PlayerStat.getPko();
		pen = PlayerStat.getPen();
		pimg = PlayerStat.getPimg();
		pslug = PlayerStat.getPslug();
		pheight = PlayerStat.getPheight();
		pbirth = PlayerStat.getPbirth();
		pnation = PlayerStat.getPnation();
		pnationimg = PlayerStat.getPnationimg();
		pfoot = PlayerStat.getPfoot();
		pposition = PlayerStat.getPposition();
		
		appearances = PlayerStat.getAppearances();
		rating = PlayerStat.getRating();
		shooting = PlayerStat.getShooting();
		passes = PlayerStat.getPasses();
		dribbles = PlayerStat.getDribbles();
		minutes_played = PlayerStat.getMinutes_played();
		saves = PlayerStat.getSaves();
		clean_sheets = PlayerStat.getClean_sheets();
		conceded_per_game = PlayerStat.getConceded_per_game();
		
		individual_skill = PlayerStat.getIndividual_skill();
		genius = PlayerStat.getGenius();
		concentration = PlayerStat.getConcentration();
		active = PlayerStat.getActive();
		boldness = PlayerStat.getBoldness();
		composure = PlayerStat.getComposure();
		cooperation = PlayerStat.getCooperation();
		physicals = PlayerStat.getPhysicals();
		punches = PlayerStat.getPunches();
		runs_out = PlayerStat.getRuns_out();
		high_claims = PlayerStat.getHigh_claims();
		saves_from_inside_box = PlayerStat.getSaves_from_inside_box();
		saved_shots_from_outside_the_box = PlayerStat.getSaved_shots_from_outside_the_box();
		penalties_saved = PlayerStat.getPenalties_saved();
		
		goal_conversion_per = PlayerStat.getGoal_conversion_per();
		headed_goals = PlayerStat.getHeaded_goals();
		free_kick_goals = PlayerStat.getFree_kick_goals();
		penalty_goals = PlayerStat.getPenalty_goals();
		crosses = PlayerStat.getCrosses();
		one_on_one_mark = PlayerStat.getOne_on_one_mark();
		tackles = PlayerStat.getTackles();
		sight = PlayerStat.getSight();
		defense_position = PlayerStat.getDefense_position();
		interceptions = PlayerStat.getInterceptions();
		clearances = PlayerStat.getClearances();
		blocked_shots = PlayerStat.getBlocked_shots();
	}
	
	DAO dao = new DAO();
%>
<%!	
	String star = null;

	public String star(int x) {
		if(x>=91)
			star = "★★★★★";
		else if(x>=81)
			star = "☆★★★★";
		else if(x>=71)
			star = "★★★★";
		else if(x>=61)
			star = "☆★★★";
		else if(x>=51)
			star = "★★★";
		else if(x>=41)
			star = "☆★★";
		else if(x>=31)
			star = "★★";
		else if(x>=21)
			star = "☆★";
		else if(x>=11)
			star = "★";
		else
			star = "☆";
		return star;
	}
%>
<!DOCTYPE html>
<html>
<head>
<style>
	
	#start { margin-left:7vw; margin-right:7vw; height:91vh; border: 1px solid #FFD700; }
		
	#left { width: 40vw; float:left; height:82vh; border: 1px solid yellow;
			margin: 6vh 1vw 3vh 1.5vw; }
			
	a { text-decoration:none }
	
	#leftTop { width:40vw; height:27vh; }
	.leftTop_left { width:23vw; height:28vh; float:left; }
	.leftTop_left_ask { font-size:16px; color:white; padding-left:0.3vw; }
	.leftTop_left_name { width:23vw; height:4.2vh; border: 1px solid skyblue; }
	.leftTop_left_nameKo { width:23vw; height:1.9vh; font-size:23px; color:white; }
	.leftTop_left_nameEn { width:23vw; height:1.5vh; font-size:14px; color:white; }
	.leftTop_left_position { width:23vw; height:2.8vh; border: 1px solid skyblue; }
	.leftTop_left_position_FW { width:23vw; height:2.8vh; font-size:21px; color:#EE3030;  }
	.leftTop_left_position_MF { width:23vw; height:2.8vh; font-size:21px; color:skyblue; }
	.leftTop_left_position_DF { width:23vw; height:2.8vh; font-size:21px; color:#30EE30; }
	.leftTop_left_position_GK { width:23vw; height:2.8vh; font-size:21px; color:gold; }
	.leftTop_left_nation { width:23vw; height:6.3vh; border: 1px solid skyblue; }
	.leftTop_left_nationName { width:17vw; height:4.3vh; color:#E0E0E0; float:left; padding-top:2vh; }
	.leftTop_left_nationImg { width:6vw; height:6vh; float:left; }
	.nationImg { width:60px; height:60px; display:block; margin-left:auto; margin-right:auto; }
	.leftTop_left_club { width:23vw; height:6.3vh; border: 1px solid skyblue; }
	.leftTop_left_club_league_name { width:17vw; float:left; }
	.leftTop_left_clubLeague { width:17vw; height:2.7vh; color:#E0E0E0; padding-top:0.3vh; }
	.leftTop_left_clubName { width:17vw; height:2.4vh; color:#E0E0E0; padding-top:0.5vh; }
	.leftTop_left_clubImg { width:6vw; height:6vh; float:left; }
	.clubImg { width:60px; height:60px; display:block; margin-left:auto; margin-right:auto; }
	.leftTop_left_birth { width:23vw; height:2.6vh; color:#E0E0E0; padding-top:0.6vh; border: 1px solid skyblue; }
	.leftTop_left_foot { width:23vw; height:2.6vh; color:#E0E0E0; padding-top:0.5vh; border: 1px solid skyblue; }
	
	.leftTop_right { width:16.5vw; height:28vh; float:left; }
	.playerImg { text-align:center; width:15vw; height:20vh; float:right; }
	
	#leftMiddle { width:40vw; height:10vh; background-color:gray; }
	.leftMiddleBox { width:6.6vw; height:8vh; float:left;}
	.leftMiddleBox_name { width:6.6vw; height:3vh; color:#FFE050; font-size:20px; text-align:center; float:left; border: 1px solid gold; }
	.leftMiddleBox_name_4 { width:6.6vw; height:2.9vh; color:#FFE050; font-size:17px; text-align:center; float:left; border: 1px solid gold;
							padding-top:0.1vh; }
	.leftMiddleBox_name_5 { width:6.6vw; height:2.5vh; color:#FFE050; font-size:13px; text-align:center; float:left; border: 1px solid gold;
							padding-top:0.5vh; }
	.leftMiddleBox_rank { width:6.6vw; height:4.7vh; color:red; font-size:28px;text-align:center; float:left; border: 1px solid gold;
						padding-top:0.3vh; }
	
	#leftBottom { width:40vw; height:49vh; }
	.leftBottom_title { width:40vw; height:2.6vh; color:#E0E0E0; padding-top:0.4vh; padding-left:0.6vw; border:1px solid:yellow; }
	.leftBottomBox { width:40vw; height: 5vh; font-size:15px; border:1px solid white; }
	.leftBottomBox_category { width:25vw; height:3.5vh; font-size:25px; color:#E0E0E0; padding:0.5vh 0px 0px 0.3vw; float:left;  }
	.leftBottomBox_star { width:12vw; height:4vh; font-size:25px; color:gold; 
						text-align:right; padding:0.2vh 1vw 0px 0px; float:right; }
	
	
	
	#right { width: 39vw; float:right; height:82vh; border: 1px solid orange;
			margin: 6vh 1.5vw 3vh 0px; }
	
	.rightTitle { width:15vw; font-size:35px; color:white; color:skyblue; text-align:center; margin-bottom:2vh; }
	.rightStat { width:39vw; height:45vh; }
	.rightStat_first { width:39vw; height:5vh; float:left; }
	.rightStat_least { width:39vw; height:5vh; float:left; border-top:1px solid skyblue; }
	.rightStat_font { font-size:20px; text-align:center; color:#E0E0E0; float:left; }
	
	progress[value] {
		width:21.5vw;
		height:25px;
	}
	
	.rightStat_progress { margin-top:5px; float:right; }
	.rightStat_statNum { width:3vw; font-size:25px; color:#EE3030; margin:0px 1vw 0px 1vw; float:right; }
	
	.image-G { width:39vw; height:300%; margin-top:35vh; }
	
	
</style>
<meta charset="UTF-8">
<title><%=pko %> 선수 정보</title>
<link rel="stylesheet" href="football.css">
</head>
<body>
<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡ 타이틀 START ㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
<jsp:include page="pageTitleGray.jsp" />
<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 타이틀 END ㅡㅡㅡㅡㅡㅡㅡㅡ-->

<div id="start">

	<div id="left">
	
		<div id="leftTop">
		
			<div class="leftTop_left">
				<div class="leftTop_left_name">
					<div class="leftTop_left_nameKo"><%=pko %></div>
				</div>
				<div class="leftTop_left_birth"><span class="leftTop_left_ask">출생&nbsp;&nbsp;</span><%=pbirth %></div>
				<div class="leftTop_left_nation">
					<div class="leftTop_left_nationName"><span class="leftTop_left_ask">국가&nbsp;&nbsp;</span><%=pnation %></div>
					<div class="leftTop_left_nationImg">
						<img src="<%=pnationimg %>" class="nationImg">
					</div>
				</div>
				<div class="leftTop_left_club">
					<div class="leftTop_left_club_league_name">
						<div class="leftTop_left_clubLeague"><span class="leftTop_left_ask">리그&nbsp;&nbsp;</span><%=league %></div>
						<div class="leftTop_left_clubName"><span class="leftTop_left_ask">클럽&nbsp;&nbsp;</span><%=tko %></div>
					</div>
					<div class="leftTop_left_clubImg">
						<img src="<%=timg %>" class="clubImg">
					</div>
				</div>
				<%
				if(pposition.equals("F")) {
					position="공격수"; %>
				<div class="leftTop_left_position"><span class="leftTop_left_ask">포지션&nbsp;&nbsp;</span><span class="leftTop_left_position_FW"><b><%=position %></b></span></div>
				<% } 
				else if(pposition.equals("M")) {
					position="미드필더"; %>
				<div class="leftTop_left_position"><span class="leftTop_left_ask">포지션&nbsp;&nbsp;</span><span class="leftTop_left_position_MF"><b><%=position %></b></span></div>
				<% } 
				else if(pposition.equals("D")) {
					position="수비수"; %>
				<div class="leftTop_left_position"><span class="leftTop_left_ask">포지션&nbsp;&nbsp;</span><span class="leftTop_left_position_DF"><b><%=position %></b></span></div>
				<% } 
				else if(pposition.equals("G")) {
					position="골키퍼"; %>
				<div class="leftTop_left_position"><span class="leftTop_left_ask">포지션&nbsp;&nbsp;</span><span class="leftTop_left_position_GF"><b><%=position %></b></span></div>
				<% } %>
			
				<%if(pfoot.equals("Right"))
				pfootko = "오른발"; 
				else if(pfoot.equals("Left"))
				pfootko = "왼발";
				else if(pfoot.equals("Both"))
				pfootko = "양발";%>
				<div class="leftTop_left_foot"><span class="leftTop_left_ask">선호하는 발&nbsp;&nbsp;</span><%=pfootko %></div>
			</div>
			
			<div class="leftTop_right">
				<img src="<%=pimg %>" class="playerImg">
			</div>
			
		</div>
			
		<div id="leftMiddle">
			<div class="leftMiddleBox">
				<div class="leftMiddleBox_name">경기</div>		
				<div class="leftMiddleBox_rank"><%=appearances %></div>
			</div>
			<div class="leftMiddleBox">
				<div class="leftMiddleBox_name">평점</div>		
				<div class="leftMiddleBox_rank"><%=rating %></div>
			</div>
			<%if(pposition.equals("F")||pposition.equals("M")||pposition.equals("D")) {%>
			<div class="leftMiddleBox">
				<div class="leftMiddleBox_name">슈팅</div>		
				<div class="leftMiddleBox_rank"><%=shooting %></div>
			</div>
			<div class="leftMiddleBox">
				<div class="leftMiddleBox_name">패스</div>		
				<div class="leftMiddleBox_rank"><%=passes %></div>
			</div>
			<div class="leftMiddleBox">
				<div class="leftMiddleBox_name">드리블</div>		
				<div class="leftMiddleBox_rank"><%=dribbles %></div>
			</div>
			<div class="leftMiddleBox">
				<div class="leftMiddleBox_name_4">출장(분)</div>		
				<div class="leftMiddleBox_rank"><%=minutes_played %></div>
			</div>
			<%} else if(pposition.equals("G")) { %>
			<div class="leftMiddleBox">
				<div class="leftMiddleBox_name">선방</div>		
				<div class="leftMiddleBox_rank"><%=saves %></div>
			</div>
			<div class="leftMiddleBox">
				<div class="leftMiddleBox_name_5">무실점경기</div>		
				<div class="leftMiddleBox_rank"><%=clean_sheets %></div>
			</div>
			<div class="leftMiddleBox">
				<div class="leftMiddleBox_name_5">경기당 실점</div>		
				<div class="leftMiddleBox_rank"><%=conceded_per_game %></div>
			</div>
			<div class="leftMiddleBox">
				<div class="leftMiddleBox_name_4">출장(분)</div>		
				<div class="leftMiddleBox_rank"><%=minutes_played %></div>
			</div>
			<%} %>
			
		</div>
			
		<div id="leftBottom">
			<div class="leftBottom_title">고유 특성</div>
			<%if(pposition.equals("F")||pposition.equals("M")||pposition.equals("D")) {%>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">개인기</div>
				<div class="leftBottomBox_star"><%=star(individual_skill) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">천재성</div>
				<div class="leftBottomBox_star"><%=star(genius) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">집중력</div>
				<div class="leftBottomBox_star"><%=star(concentration) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">적극성</div>
				<div class="leftBottomBox_star"><%=star(active) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">대담성</div>
				<div class="leftBottomBox_star"><%=star(boldness) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">침착성</div>
				<div class="leftBottomBox_star"><%=star(composure) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">팀워크</div>
				<div class="leftBottomBox_star"><%=star(cooperation) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">몸싸움</div>
				<div class="leftBottomBox_star"><%=star(physicals) %></div>
			</div>
			<%} else if(pposition.equals("G")) { %>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">펀칭</div>
				<div class="leftBottomBox_star"><%=star(punches) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">전진수비</div>
				<div class="leftBottomBox_star"><%=star(runs_out) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">공중볼 처리</div>
				<div class="leftBottomBox_star"><%=star(high_claims) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">박스 안 선방</div>
				<div class="leftBottomBox_star"><%=star(saves_from_inside_box) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">박스 밖 선방</div>
				<div class="leftBottomBox_star"><%=star(saved_shots_from_outside_the_box) %></div>
			</div>
			<div class="leftBottomBox">
				<div class="leftBottomBox_category">패널티 킥 선방</div>
				<div class="leftBottomBox_star"><%=star(penalties_saved) %></div>
			</div>
			<%} %>
		</div>	
	</div>
	
	

	<div id="right">
		<div class="rightTitle"><b>상세 능력</b></div>
		<div class="rightStat">
		<%if(pposition.equals("F")||pposition.equals("M")) {%>
			<div class="rightStat_first">
				<div class="rightStat_font">골 결정력</div>
				<div class="rightStat_statNum"><%=goal_conversion_per %></div>
				<div><progress value="<%=goal_conversion_per %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">헤딩</div>
				<div class="rightStat_statNum"><%=headed_goals %></div>
				<div><progress value="<%=headed_goals %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">프리킥</div>
				<div class="rightStat_statNum"><%=free_kick_goals %></div>
				<div><progress value="<%=free_kick_goals %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">패널티 킥</div>
				<div class="rightStat_statNum"><%=penalty_goals %></div>
				<div><progress value="<%=penalty_goals %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">크로스</div>
				<div class="rightStat_statNum"><%=crosses %></div>
				<div><progress value="<%=crosses %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">일대일 마크</div>
				<div class="rightStat_statNum"><%=one_on_one_mark %></div>
				<div><progress value="<%=one_on_one_mark %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">태클</div>
				<div class="rightStat_statNum"><%=tackles %></div>
				<div><progress value="<%=tackles %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">시야</div>
				<div class="rightStat_statNum"><%=sight %></div>
				<div><progress value="<%=sight %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
		<%} else if(pposition.equals("D")) { %>
			<div class="rightStat_first">
				<div class="rightStat_font">일대일 마크</div>
				<div class="rightStat_statNum"><%=one_on_one_mark %></div>
				<div><progress value="<%=one_on_one_mark %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">태클</div>
				<div class="rightStat_statNum"><%=tackles %></div>
				<div><progress value="<%=tackles %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">수비 위치</div>
				<div class="rightStat_statNum"><%=defense_position %></div>
				<div><progress value="<%=defense_position %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">가르채기</div>
				<div class="rightStat_statNum"><%=interceptions %></div>
				<div><progress value="<%=interceptions %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">걷어내기</div>
				<div class="rightStat_statNum"><%=clearances %></div>
				<div><progress value="<%=clearances %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">슈팅 방어</div>
				<div class="rightStat_statNum"><%=blocked_shots %></div>
				<div><progress value="<%=blocked_shots %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">시야</div>
				<div class="rightStat_statNum"><%=sight %></div>
				<div><progress value="<%=sight %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">헤딩</div>
				<div class="rightStat_statNum"><%=headed_goals %></div>
				<div><progress value="<%=headed_goals %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">프리킥</div>
				<div class="rightStat_statNum"><%=free_kick_goals %></div>
				<div><progress value="<%=free_kick_goals %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
			<div class="rightStat_first">
				<div class="rightStat_font">크로스</div>
				<div class="rightStat_statNum"><%=crosses %></div>
				<div><progress value="<%=crosses %>" max="100" class="rightStat_progress"></progress></div>	
			</div>
		<%} else if (pposition.equals("G")) { %>
			<div class="image-G">
				<img src="image/빈운동장.jpg" width="100%" height="300" >
			</div>
		<%} %>
		</div>
		
	</div>
	
	
	
</div>

</body>
</html>