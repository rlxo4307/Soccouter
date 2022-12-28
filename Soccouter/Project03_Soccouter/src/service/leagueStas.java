package service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import backup.InfoLeagueStat;
import dto.dtoLeagueStat;

public class leagueStas implements Service{
	static InfoLeagueStat ils = new InfoLeagueStat();
	static ArrayList<dtoLeagueStat> arrayLeagueStats = new ArrayList<dtoLeagueStat>();
	
	@Override
	public void start(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String league = request.getParameter("league");
		String year = request.getParameter("year");
		Connection.Response response2;
		Document document = null;
		try {
			response2 = Jsoup.connect("https://sports.news.naver.com/wfootball/record/index?category="+league+"&year="+year+"&tab=team")
			        .method(Connection.Method.GET)
			        .execute();
			document = response2.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}


		String html = document.html();
		String text = document.text();

		
		//=====================================
		int idx1a = html.indexOf("RecordList\":[");
		int idx1b = html.indexOf("sortedTeamRecord");
		
		// 시즌 정보
		String season = html.substring(html.indexOf("<h4 class=\"record_h\"><span>")+"<h4 class=\"record_h\"><span>".length(), html.indexOf("</span> 팀 순위</h4>"));
		String rankTable = html.substring(idx1a+14, idx1b-13); 
		System.out.println(rankTable);
		
		String[] rankLine = rankTable.split("\\},\\{");			//	기본 한줄
		String[] teamName = rankTable.split("\\},\\{");			//	팀
		String[] gameCount = rankTable.split("\\},\\{");		//	경기 수
		String[] gainPoint = rankTable.split("\\},\\{");			//	승점
		String[] won = rankTable.split("\\},\\{");					//	승
		String[] drawn = rankTable.split("\\},\\{");				//	무
		String[] lost = rankTable.split("\\},\\{");					//	패
		String[] gainGoal = rankTable.split("\\},\\{");			//	득점
		String[] loseGoal = rankTable.split("\\},\\{");			//	실점
		String[] goalGap = rankTable.split("\\},\\{");			//	득실차
		
		
		ArrayList<dtoLeagueStat> arrayLeagueStats = new ArrayList<dtoLeagueStat>();

		for (int i = 0; i < rankLine.length; i++) {
			String state_bar = "";
			switch ( i+1 ) {
			case 1 :
				state_bar= "class=\"state_bar1\"";	break;
			case 2:
				state_bar= "class=\"state_bar1\"";	break;
			case 3:
				switch ( league ) {
				 case "epl" : 				state_bar= "class=\"state_bar1\"";	break;
				 case "primera" :			state_bar= "class=\"state_bar1\"";	break;
				 case "bundesliga" :		state_bar= "class=\"state_bar1\"";	break;
				 case "seria" :				state_bar= "class=\"state_bar1\"";	break;
				 case "ligue1" :				state_bar= "class=\"state_bar2\"";	break;
				}
				break;
			case 4:
				switch ( league ) {
				 case "epl" : 				state_bar= "class=\"state_bar1\"";	break;
				 case "primera" :			state_bar= "class=\"state_bar1\"";	break;
				 case "bundesliga" :		state_bar= "class=\"state_bar1\"";	break;
				 case "seria" :				state_bar= "class=\"state_bar1\"";	break;
				 case "ligue1" :				state_bar= "class=\"state_bar3\"";	break;
				}
			case 5:
				switch ( league ) {
				 case "epl" : 				state_bar= "class=\"state_bar3\"";	break;
				 case "primera" :			state_bar= "class=\"state_bar3\"";	break;
				 case "bundesliga" :		state_bar= "class=\"state_bar3\"";	break;
				 case "seria" :				state_bar= "class=\"state_bar3\"";	break;
				}
				break;
			case 6:
				switch ( league ) {
				 case "epl" : 				state_bar= "class=\"state_bar3\"";	break;
				 case "primera" :			state_bar= "class=\"state_bar3\"";	break;
				 case "bundesliga" :		state_bar= "class=\"state_bar3\"";	break;
				 case "seria" :				state_bar= "class=\"state_bar3\"";	break;
				}
				break;
			case 17:
				switch ( league ) {
				 case "bundesliga" :		state_bar= "class=\"state_bar4\"";	break;
				}
				break;
			case 18:
				switch ( league ) {
				 case "epl" : 				state_bar= "class=\"state_bar4\"";	break;
				 case "primera" :			state_bar= "class=\"state_bar4\"";	break;
				 case "bundesliga" :		state_bar= "class=\"state_bar4\"";	break;
				 case "seria" :				state_bar= "class=\"state_bar4\"";	break;
				}
				break;
			case 19:
				switch ( league ) {
				 case "epl" : 				state_bar= "class=\"state_bar4\"";	break;
				 case "primera" :			state_bar= "class=\"state_bar4\"";	break;
				 case "seria" :				state_bar= "class=\"state_bar4\"";	break;
				 case "ligue1" :				state_bar= "class=\"state_bar4\"";	break;
				}
				break;
			case 20:
				switch ( league ) {
				 case "epl" : 				state_bar= "class=\"state_bar4\"";	break;
				 case "primera" :			state_bar= "class=\"state_bar4\"";	break;
				 case "seria" :				state_bar= "class=\"state_bar4\"";	break;
				 case "ligue1" :				state_bar= "class=\"state_bar4\"";	break;
				}
				break;
			}

			

			// 순위 저장
			int rank = i+1;
			// 팀
			int idxTeamName_a = teamName[i].indexOf("teamName");
			int idxTeamName_b = teamName[i].indexOf("gainGoal");
			String dtoTeamName = teamName[i].substring(idxTeamName_a+11, idxTeamName_b-3 );
			// 경기수
			int idxGameCount_a = gameCount[i].indexOf("gameCount");
			int idxGameCount_b = gameCount[i].indexOf("lastResult");
			int dtoGameCount = Integer.parseInt(gameCount[i].substring(idxGameCount_a+11, idxGameCount_b-2 ));
			// 승점
			int idxGainPoint_a = gainPoint[i].indexOf("gainPoint");
			int idxGainPoint_b = gainPoint[i].indexOf("teamCode");
			int dtoGainPoint = Integer.parseInt(gainPoint[i].substring(idxGainPoint_a+11, idxGainPoint_b-2 ));
			// 승
			int idxWon_a = won[i].indexOf("won");
			int idxWon_b = won[i].indexOf("goalGap");
			int dtoWon = Integer.parseInt(won[i].substring(idxWon_a+5, idxWon_b-2 ));
			// 무
			int idxDrawn_a = drawn[i].indexOf("drawn");
			int dtoDrawn = Integer.parseInt(drawn[i].substring(idxDrawn_a+7, drawn[i].length()));
			// 패
			int idxLost_a = lost[i].indexOf("lost");
			int idxLost_b = lost[i].indexOf("won");
			int dtoLost = Integer.parseInt(lost[i].substring(idxLost_a+6, idxLost_b-2 ));
			// 득점
			int idxGainGoal_a = gainGoal[i].indexOf("gainGoal");
			int idxGainGoal_b = gainGoal[i].indexOf("gameCount");
			int dtoGainGoal = Integer.parseInt(gainGoal[i].substring(idxGainGoal_a+10, idxGainGoal_b-2 ));
			// 실점
			int idxLoseGoal_a = loseGoal[i].indexOf("loseGoal");
			int idxLoseGoal_b = loseGoal[i].indexOf("foul");
			int dtoLoseGoal = Integer.parseInt(loseGoal[i].substring(idxLoseGoal_a+10, idxLoseGoal_b-2 ));
			// 득실차
			int idxGoalGap_a = goalGap[i].indexOf(",\"goalGap\":");
			String tmpgoalGap = goalGap[i].substring(idxGoalGap_a+11);
			int idxGoalGap_b = tmpgoalGap.indexOf(",\"");
			int dtoGoalGap = Integer.parseInt(tmpgoalGap.substring(0, idxGoalGap_b ));
			dtoLeagueStat dls = new dtoLeagueStat(state_bar, league, season, rank, dtoTeamName, dtoGameCount, dtoGainPoint, dtoWon, dtoDrawn, dtoLost, dtoGainGoal, dtoLoseGoal, dtoGoalGap);
			arrayLeagueStats.add(dls);
		}
		 
		 String leagueName = null;
		 switch(league) {
		 case "epl" : leagueName = "프리미어리그"; break;
		 case "primera" : leagueName = "라리가"; break;
		 case "bundesliga" : leagueName = "분데스리가"; break;
		 case "seria" : leagueName = "세리에 A"; break;
		 case "ligue1" : leagueName = "리그 1"; break;
		 }
		 request.setAttribute("leagueName", leagueName);
		 request.setAttribute("season", arrayLeagueStats.get(0).getSeason());
		 request.setAttribute("leagueStat", arrayLeagueStats); 
	}
}
