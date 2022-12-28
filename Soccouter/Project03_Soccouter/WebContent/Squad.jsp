<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.dtoSquad" %>
<%@ page import="jdbc.DAO" %>

<%
	ArrayList<dto.dtoSquad> squad = (ArrayList)request.getAttribute("squad");
	
	String league = request.getParameter("league");
	String team = request.getParameter("team");
	int tid_daum = Integer.parseInt(request.getParameter("tid_daum"));
	int tid_sofa = Integer.parseInt(request.getParameter("tid_sofa"));
	int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
	int sid_daum = Integer.parseInt(request.getParameter("sid_daum"));
	
	String timg = "http://t1.daumcdn.net/media/img-section/sports13/logo/team/14/"+tid_sofa+"_300300.png";
	String no_img_url = "https://t1.daumcdn.net/media/img-section/sports13/player/noimage/square_m.png";
	int pid_sofa = 0;
	String pimg = null;
	String pnum = null;
	String pko = null;
	String pposition = null;
	
	int ranking = 0;
	int win = 0;
	int draw = 0;
	int loss = 0;
	int pts = 0;
	int gf = 0;
	int ga = 0;
	int gd = 0;
	int total_game = 0;
	
	if(squad.size() != 0) {
		pid_sofa = squad.get(0).getPid_sofa();
		pimg = squad.get(0).getPimg();
		pnum = squad.get(0).getPnum();
		pko = squad.get(0).getPko();
		pposition = squad.get(0).getPposition();
		ranking = squad.get(0).getRanking();
		win = squad.get(0).getWin();
		draw = squad.get(0).getDraw();
		loss = squad.get(0).getLoss();
		pts = squad.get(0).getPts();
		gf = squad.get(0).getGf();
		ga = squad.get(0).getGa();
		gd = squad.get(0).getDraw();
		total_game = squad.get(0).getTotal_game();
	}
	
	DAO dao = new DAO();
	int fw = dao.get_position_num(tid_sofa, "F");
	int mf = dao.get_position_num(tid_sofa, "M");
	int df = dao.get_position_num(tid_sofa, "D");
	int gk = dao.get_position_num(tid_sofa, "G");
%>
<!DOCTYPE html>
<html>
<head>
<style>

	html, body { margin:0px; padding:0px; width:100%; height:100%; }
	
	a { text-decoration:none; font-size:0.9vw; color:black; }
	
	#title { font-size:1.3vw; background-color:#DF3030; height:3vw; }
	.title_text_position { margin-left:20%; margin-right:20%; }
	.title_text_box { margin: 0.4vw 0px 0px 1vw; float:left; }
	#title_text { text-decoration: none; color:white; }
	
	#wrapper { margin-left:20%; margin-right:20%; border-radius:0px; }
	
	#team { background-color:#272727; width:100%; }
	
	#team_info { width:59vw; display:inline-block; }           
	.team_logo { text-align:center; width:12vw; float:left; border-radius:50%; }            
	.logo_team { width:12vw; height:12vw; }
	
	.team_name { width:23vw; height:10vw; float:left; }
    .team_name_text { font-size:1.9vw; color:white; float:left;
    				margin:2vw 0px 0px 1.5vw; }
      
      
    #team_overall { width:22vw; float:right; }           

	.overall_season{ margin:1.4vw 0px 0.8vw 0px; display:inline-block; }
	.season_kor { font-size:1.6vw; color:#FFD700; }
	.season_num { font-size:1.5vw; color:#FFD700; }
	
	.overall_ranking { }
	.ranking_info { font-size:1.42vw; width:23%;
					 color:white; display:inline-block; }
	.ranking_info_text { font-size:1vw; color:#999999; }
	
	#info_tab { width:93%; height:2vw; display:inline-block; background-color:#272727; }
	.tab_choice{ width:10%; font-size:1.3vw; color:white; float:left;
				margin:0px 0px 2vw 1.5vw; }
	
	
	#squad_and_comment { width:100%; }
	
	#squad_title { width:100%; height: 50px; float:left;
    					padding-top: 10px; font-weight: 600; font-size: 1.3vw; }
    
    #squad_hr { width:100%; float:left; margin-top:1.85vw; }
    					
    #squad { width:66%; float:left;}
    .squad_list { width:7.7vw; height:12vw; float:left; margin-bottom:10px; }
    .squad_img { width:7.7vw; height:7.7vw; }
    .squad_img_size { text-align:center; width:6.6vw; height:6.6vw; border-radius:50%; }
    .squad_name { text-align:center; width:5.7vw; font-size:0.9vw; margin-bottom:2px; }
    .squad_pnum { text-align:center; width:5.7vw; font-size:0.8vw; color:#999999; }
    
    
    #comment_right { width:15%; padding: 30px 30px 30px 30px; float:left;
    		 background-color:#FFFFFF; height: 55.5vw; }
    .comment_table { border:1px solid #BBBBBB; margin-top:10px; padding: 16px 22px 22px 22px; }
    .comment_table_text { font-size: 0.82vw; padding: 5px 0px 5px 0px; }
    .comment_table_written { font-size: 0.78vw; color:#999999; padding: 5px 0px 5px 0px; }
    
</style>
<meta charset="UTF-8">
<title>${param.team}</title>
</head>
<body>
<!--ㅡㅡㅡㅡㅡㅡㅡ 타이틀 ㅡㅡㅡㅡㅡㅡㅡ-->
	<div id="title">
		<div class="title_text_position">
			<div class="title_text_box">
				<b><a href="TeamRank.do?league=epl&sid_daum=20222023&sslug=premier-league&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>" id="title_text">순위</a></b>&nbsp;&nbsp;
			</div>
			<div class="title_text_box">
				<b><a href="Team.do?league=epl&lslid=20222023&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>" id="title_text">팀/선수</a></b>
			</div>
		</div>
	</div><br>
<!--ㅡㅡㅡㅡㅡ 메인페이지 정렬 ㅡㅡㅡㅡㅡ-->
<section>
<div id="wrapper">
	<!--ㅡㅡㅡㅡㅡㅡㅡ 팀, 선수단, 응원정보 START ㅡㅡㅡㅡㅡㅡㅡ-->
	<div id="team">
		<!--ㅡㅡㅡㅡㅡㅡ 팀 정보 START ㅡㅡㅡㅡㅡㅡ-->
		<div id="team_info">
		
			<!--ㅡㅡㅡㅡㅡ 팀 로고 STARTㅡㅡㅡㅡㅡㅡ-->
            <div class="team_logo">
            	<img src="<%= timg %>" alt="${param.team}" class="logo_team">
			</div>
			<!--ㅡㅡㅡㅡㅡ 팀 로고 END ㅡㅡㅡㅡㅡㅡㅡ-->
			
			<!--ㅡㅡㅡㅡㅡㅡ 팀 명 STARTㅡㅡㅡㅡㅡㅡ-->
			<div class="team_name">
				<div class="team_name_text">
					<b>${param.team}</b>
				</div>
			</div>
			<div id="team_overall">
			<div class="overall_season">
				<strong class="season_kor"><span class="season_num">22/23</span> 시즌</strong>
			</div>
			<div class="overall_ranking">
				<div class="ranking_info">
			        <span class="ranking_info_text">순위</span><br>
			        <%=ranking %>    
			    </div>
		    	<div class="ranking_info">
			        <span class="ranking_info_text">승</span><br>
			        <%=win %>
		        </div>
		        <div class="ranking_info">
			        <span class="ranking_info_text">무</span><br>
			        <%=draw %>
		        </div>
		        <div class="ranking_info">
			        <span class="ranking_info_text">패</span><br>
			        <%=loss %>
		        </div>
		        <div class="ranking_info">
			        <span class="ranking_info_text">승점</span><br>
			        <%=pts %>
			    </div>
		    	<div class="ranking_info">
			        <span class="ranking_info_text">득점</span><br>
			        <%=gf %>
		        </div>
		        <div class="ranking_info">
			        <span class="ranking_info_text">실점</span><br>
			        <%=ga %>
		        </div>
		        <div class="ranking_info">
			        <span class="ranking_info_text">경기</span><br>
			        <%=total_game %>
		        </div>
			</div>
			<!--ㅡㅡㅡㅡㅡㅡ팀 시즌 정보 END ㅡㅡㅡㅡㅡㅡ-->	
		</div>
			<!--ㅡㅡㅡㅡㅡㅡ 팀 명 END ㅡㅡㅡㅡㅡㅡㅡ-->
		</div>
		<!--ㅡㅡㅡㅡㅡㅡ 팀 정보 END ㅡㅡㅡㅡㅡㅡ-->
			
		
		<!--ㅡㅡㅡㅡㅡㅡㅡ (선수단,응원정보)선택 창 STRAT ㅡㅡㅡㅡㅡㅡ-->
		<div id="info_tab">
           	<div class="tab_choice">
           		<a id="title_text" href="Squad.do?league=<%=league %>&team=<%=team %>&tid_daum=<%=tid_daum %>&tid_sofa=<%=tid_sofa %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>"><b>선수단</b></a>
           	</div>
           	<div class="tab_choice">
           		<a id="title_text" href="Cheer.do?league=<%=league %>&team=<%=team %>&tid_daum=<%=tid_daum %>&tid_sofa=<%=tid_sofa %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>"><b>응원댓글</b></a>
           	</div>
       	</div>
       	<!--ㅡㅡㅡㅡㅡㅡㅡㅡ 선수단, 응원정보 END ㅡㅡㅡㅡㅡㅡㅡ-->
       	
	</div>
	<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 팀, (선수단,응원정보)선택 창 END ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
	
	<div id="squad_and_comment">
	<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 선수단 SRART ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
	<div id="squad">
	<b id="squad_title">공격수 <%=fw %></b>
	<% for(int i=0; i<squad.size(); i++) {
		if(squad.get(i).getPposition().equals("F")) { %>
		<% if(squad.size() != 0) {
			pid_sofa = squad.get(i).getPid_sofa();
			pimg = squad.get(i).getPimg();
			pko = squad.get(i).getPko();
			pnum = squad.get(i).getPnum(); 
		}%>
		<div class="squad_list">
			<div class="squad_img">
				<a href="PlayerStat.do?league=<%=league %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" onerror="<%=no_img_url %>" class="squad_img_size" alt=" <%=pko%>"></a>
			</div>
			<div class="squad_name"><a href="PlayerStat.do?league=<%=league %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><%=pko%></a></div>
			<div class="squad_pnum">No.<%=pnum %></div>
		</div> <% } } %>
	<div id="squad_hr"><hr></div>
	
		
	<b id="squad_title">미드필더 <%=mf %></b>	
	<% for(int i=0; i<squad.size(); i++) {
		if(squad.get(i).getPposition().equals("M") ) { %>
		<% if(squad.size() != 0) {
			pid_sofa = squad.get(i).getPid_sofa();
			pimg = squad.get(i).getPimg();
			pko = squad.get(i).getPko();
			pnum = squad.get(i).getPnum(); 
		}%>
		<div class="squad_list">
			<div class="squad_img">
				<a href="PlayerStat.do?league=<%=league %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&sid_daum=<%=sid_daum %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" onerror="<%=no_img_url %>" class="squad_img_size" alt=" <%=pko%>"></a>
			</div>
			<div class="squad_name"><a href="PlayerStat.do?league=<%=league %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><%=pko%></a></div>
			<div class="squad_pnum">No.<%=pnum %></div>
		</div> <% } } %>
	<div id="squad_hr"><hr></div>
		
		
	<b id="squad_title">수비수 <%=df %></b>	
	<% for(int i=0; i<squad.size(); i++) {
		if(squad.get(i).getPposition().equals("D") ) { %>
		<% if(squad.size() != 0) {
			pid_sofa = squad.get(i).getPid_sofa();
			pimg = squad.get(i).getPimg();
			pko = squad.get(i).getPko();
			pnum = squad.get(i).getPnum(); 
		}%>	
		<div class="squad_list">
			<div class="squad_img">
				<a href="PlayerStat.do?league=<%=league %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" onerror="<%=no_img_url %>" class="squad_img_size" alt=" <%=pko%>"></a>
			</div>
			<div class="squad_name"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><%=pko%></a></div>
			<div class="squad_pnum">No.<%=pnum %></div>
		</div> <% } } %>
	<div id="squad_hr"><hr></div>
	
	
	<b id="squad_title">골키퍼 <%=gk %></b>
	<% for(int i=0; i<squad.size(); i++) {
		if(squad.get(i).getPposition().equals("G") ) { %>
		<% if(squad.size() != 0) {
			pid_sofa = squad.get(i).getPid_sofa();
			pimg = squad.get(i).getPimg();
			pko = squad.get(i).getPko();
			pnum = squad.get(i).getPnum(); 
		}%>
		<div class="squad_list">
			<div class="squad_img">
				<a href="PlayerStat.do?league=<%=league %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><img src="<%=pimg %>" onerror="<%=no_img_url %>" class="squad_img_size" alt=" <%=pko%>"></a>
			</div>
			<div class="squad_name"><a href="PlayerStat.do?league=<%=league %>&sid_daum=<%=sid_daum %>&sid_sofa=<%=sid_sofa %>&tid_sofa=<%=tid_sofa %>&pid_sofa=<%=pid_sofa %>&category=goals"><%=pko%></a></div>
			<div class="squad_pnum">No.<%=pnum %></div>
		</div> <% } } %>
	</div>
	</div>
	<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 선수단 END ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
</section>
<aside>
	<!--ㅡㅡㅡㅡㅡㅡ 우측 페이지 댓글 START ㅡㅡㅡㅡㅡㅡㅡㅡ-->
	<div id="comment_right">
		<div class="comment_table">
			<table border=0>
				<tr><td class="comment_table_text">유럽에선 아직도 인종차별이 모든 사회속에 깊히 깔려 있다. 손흥민 출생지가 유럽이면 3위 안에 들었을거다.</td></tr>
				<tr><td class="comment_table_written">차범근 06.15 11:54</td></tr>
			</table>
		</div>
		<div class="comment_table">
			<table border=0>
				<tr><td class="comment_table_text">손흥민은 스포츠계의 '대통령'이다.</td></tr>
				<tr><td class="comment_table_written">박지성 06.11 21:10</td></tr>
			</table>
		</div>
		<div class="comment_table">
			<table border=0>
				<tr><td class="comment_table_text">우리나라와 전세계 프리미어리그에서 아시아인이 득점왕이 나온 사실은 이루 말할수없는 전무후무한 축구역사의 기쁨. 대한민국 손흥민 만세</td></tr>
				<tr><td class="comment_table_written">안정환 06.10 13:40</td></tr>
			</table>
		</div>
	</div>
	<!--ㅡㅡㅡㅡㅡㅡㅡ 우측 페이지 댓글 END ㅡㅡㅡㅡㅡㅡㅡㅡ-->

	<!--ㅡㅡㅡㅡㅡㅡㅡㅡ 선수단, 댓글 END ㅡㅡㅡㅡㅡㅡㅡㅡ-->
</aside>
</body>
</html>