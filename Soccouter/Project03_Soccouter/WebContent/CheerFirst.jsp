<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="dto.dtoSquad" %>
<%@ page import="dto.dtoMem" %>
<%@ page import="dto.dtoCheer" %>
<%@ page import="jdbc.DAO" %>
<%
	ArrayList<dto.dtoSquad> squad = (ArrayList)request.getAttribute("squad");
	ArrayList<dto.dtoCheer> cheer = (ArrayList)request.getAttribute("cheer");
	
	int cheersize = 0;
	
	if(cheer != null) {
		cheersize = cheer.size();
	}
	
	dtoMem mem = null;
	String id = null;
	
	if(mem != null) {
		out.println("mem is not null");
		mem = (dtoMem)session.getAttribute("login");
		
		id = mem.getId();
	}
	
	
	
	
	String league = request.getParameter("league");
	String team = request.getParameter("team");
	int tid_daum = Integer.parseInt(request.getParameter("tid_daum"));
	int tid_sofa = Integer.parseInt(request.getParameter("tid_sofa"));
	int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
	int sid_daum = Integer.parseInt(request.getParameter("sid_daum"));
	
	String timg = "http://t1.daumcdn.net/media/img-section/sports13/logo/team/14/"+tid_sofa+"_300300.png";
	String no_img_url = "https://t1.daumcdn.net/media/img-section/sports13/player/noimage/square_m.png";
	
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
	
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	String date = null;
	
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.css">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script type="text/javascript">
		var request = new XMLHttpRequest();
		function searchFunction() {
			request.open("Post", "./UserSearchServlet?cheerName=" + encodeURIComponent(document.getElementById("cheerName").value), true);
			request.onreadystatechange = searchProcess;
			request.send(null);
		}
		function searchProcess() {
			var table = document.getElementById("ajaxTable");
			table.innerHTML = "";
			if(request.readyState = 4 && request.status == 200) {
				var object = eval('(' + request.responseText + ')');
				var result = object.result;
				for(var i = 0; i < result.length; i++) {
					var row = table.insertRow(0);
					for(var j = 0; j < result[i].length; j++) {
						var cell = row.insertCell(j);
						cell.innerHTML = result[i][j].value;
					}
				}
			}
		}
		/* window.onload = function() {
			searchFunction();
		} */
	</script>
<style>
	
	html, body { margin:0px; padding:0px; width:100%; height:100%; }
	
	a { text-decoration:none; font-size:0.9vw; color:white; }
	hr { width:100%; }
	
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
	
	
	#comment { height:17vh; border:1px solid yellow; }
	.comment_ko_num { margin:2vh 0px 1vh 0px;  }
	.comment_ko { font-size:13px; padding:0.3vh 0.2vw 0px 0px; float:left; }
	.comment_num { font-size:16px; float:left; }
	.comment_type { width:100%; border:1px solid gray; float:left; }
	.comment_login { border:1px solid gray; background-color:gold; margin-right:5px; margin-bottom:2px; float:right; }
	.comment_login_style { font-size:16px; color:black; }
	
	#cheer_sort_margin { height:3vh; margin-top:0.4vh; }
	.cheer_sort { font-size:14px; }
	
	#cheer { border:1px solid gold; margin-top:15px; margin-bottom:15px; }
	.cheer_id_date {  }
	.cheer_id { font-size:10px; color:skyblue; float:left; margin-right:8px; }
	.cheer_date { font-size:10px; color:gray; }
	.cheer_text { font-size:12px; }
	#comment_textarea { width:59vw; }		
	
	#show { width:100%; height:3vh; font-size:18px; text-align:center; 
			padding-top:0.5vh; border:1px solid black; }
</style>
<title>Insert title here</title>
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
<!--ㅡㅡㅡㅡㅡㅡ 타이틀 END ㅡㅡㅡㅡㅡㅡ-->
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
           		<a id="title_text" href="cheer.do?league=<%=league %>&team=<%=team %>&tid_daum=<%=tid_daum %>&tid_sofa=<%=tid_sofa %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>"><b>응원댓글</b></a>
           	</div>
       	</div>
       	<!--ㅡㅡㅡㅡㅡㅡㅡㅡ 선수단, 응원정보 END ㅡㅡㅡㅡㅡㅡㅡ-->
	</div>
	<!--ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 팀, (선수단,응원정보)선택 창 END ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ-->
	<div id="comment">
		<div class="comment_ko_num">
			<div class="comment_ko">댓글</div>
			<div class="comment_num"><%=cheersize %></div>
			<div class="comment_login"><a href="login/login.jsp?league=<%=league %>&team=<%=team %>&tid_daum=<%=tid_daum %>&tid_sofa=<%=tid_sofa %>&sid_sofa=<%=sid_sofa %>&sid_daum=<%=sid_daum %>"><span class="comment_login_style">로그인</span></a></div>
		</div>
		
		<div class="comment_type">
		<form id="cheerName">
			<textarea name="comment" id="comment_textarea" cols="100" rows="3" 
				<%if(id != null) { %>placeholder="댓글을 입력하세요."
				<% } else { %>placeholder="댓글을 작성하려면 로그인이 필요합니다.">
				<% } %></textarea>
				<button  onclick="cheerFunction();" type="button">입력</button>
		</form>		
			<%-- <form action="Cheer.do" method="post">
				<textarea name="comment" id="comment_textarea" cols="100" rows="3" 
				<%if(id != null) { %>placeholder="댓글을 입력하세요."
				<% } else { %>placeholder="댓글을 작성하려면 로그인이 필요합니다.">
				<% } %></textarea>	
				<input type="hidden" name="id" value=<%=id %>>
				<input type="hidden" name="league" value=<%=league %>>
				<input type="hidden" name="team" value=<%=team %>>
				<input type="hidden" name="tid_daum" value=<%=tid_daum %>>
				<input type="hidden" name="tid_sofa" value=<%=tid_sofa %>>
				<input type="hidden" name="sid_sofa" value=<%=sid_sofa %>>
				<input type="hidden" name="sid_daum" value=<%=sid_daum %>>
   				<input type="submit" value="입력">
			</form> --%>
		</div>
	</div>
	
	<div id="cheer_sort_margin">
		<span class="cheer_sort">최신순</span>&nbsp;&nbsp;
		<span class="cheer_sort">과거순</span>
	</div>
	
	<%-- <% for(int i = 0; i < cheersize; i++) { 
	date = dateFormat.format(cheer.get(i).getUpdateDate());%>
	<hr>
	<div id="cheer">
		<div class="cheer_id_date">
			<div class="cheer_id"><%=cheer.get(i).getId() %></div><div class="cheer_date"><%=date %></div>
			<div class="font-text"><%=cheer.get(i).getComm() %></div>
		</div>
	</div>
	<% } %> --%>
	
	
	<table style="text-align: center; border: 1px solid #dddddd">
		<tbody id="ajaxTable">
			<tr>
				<td class="cheer_id"></td>
				<td class="cheer_date"></td>
				<td class="font-text"></td>
			</tr>
		</tbody>
	</table>
	
	<div class="container">
		<table class="table" style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="2" style="backgruond-color:">
				</tr>
			</thead>
		</table>
	</div>
	
	<!--ㅡㅡㅡㅡㅡ 댓글창 더 보기 START ㅡㅡㅡㅡㅡ-->
	<br>
	<div id="show">
		더보기
	</div>
	<!--ㅡㅡㅡㅡㅡㅡ 댓글창 더 보기 END ㅡㅡㅡㅡㅡㅡ-->
</div>
</body>
</html>