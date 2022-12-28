package backup;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class InfoAllplayer_backup {

	public static void main(String[] args) throws IOException {
		System.out.println("::::::::::::::::::::::선수 정보 갱신 시작::::::::::::::::::::::");
		DAO dao = new DAO();
		
		// epl		// bundesliga		// primera		// seriea		// ligue1			
		String len = "epl" ;	
//		String lko = "프리미어리그";
//		String lid = "premier-league/17";
		
		Connection.Response response = Jsoup.connect("https://sports.daum.net/team/"+len)
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .execute();
		Document document = response.parse();

		String html = document.html();
		String text = document.text();		
		text = text.replace("\n", "");
		html = html.replace("\n", "");
		

		String a = "<div class=\"cont_item\">           ";
		String b = "           </div>          </div> </li>        </ul>       </div>      </div>      <div id=\"mAside\">       ";
		html = html.substring(html.indexOf(a)+a.length(), html.indexOf(b));

		
		String[] tmp_html = html.split("<div class=\"cont_item\">           ");			//	팀 정보 리소스 그대로. (라인별)
		String[] leagueTeam = new String [30];													//	팀 정보 기록
		for (int i = 0; i < tmp_html.length; i++) {
			
			// 해당 리그에 존재하는 팀아이디 구하기
			String tmpA = "a href=\"/sports/team/";
			String tmpB = "/news";
			String tmpAA = tmp_html[i].substring(tmp_html[i].indexOf(tmpA)+tmpA.length(), tmp_html[i].indexOf(tmpB));
			leagueTeam[i] =  tmpAA.substring(tmpAA.indexOf("/")+1);

		}
		
		// DAUM -> 추출된 팀에서 선수정보 추출
		for (int i = 0; i < leagueTeam.length; i++) {
			
			if ( leagueTeam[i] != null ) {
				response = Jsoup.connect("https://sports.daum.net/prx/hermes/api/person/list.json?leagueCode="+len+"&teamId="+leagueTeam[i])
		                .method(Connection.Method.GET)
		                .ignoreContentType(true)
		                .execute();
				document = response.parse();

				text = document.text();		
				text = text.substring(text.indexOf("\"list\":[{\"personId\":")+9, text.indexOf("}]}"));
				
				String[] tmpPlayer = text.split("\\},\\{");
				String [][] teamPlayerList = new String [tmpPlayer.length][15];
				
				for (int j = 0; j < tmpPlayer.length; j++) {
//						System.out.println("base "+ tmpPlayer[j]);
						// 0	pid_daum
						teamPlayerList[j][0] = tmpPlayer[j].substring(tmpPlayer[j].indexOf("\"personId\":")+"\"personId\":".length(), tmpPlayer[j].indexOf("\"cpPersonId\":\"")-1);	
						
						// 2	pimg
						String tmpPlayerImg = tmpPlayer[j].substring(tmpPlayer[j].indexOf("imageUrl")+"imageUrl".length()+3);
						tmpPlayerImg = tmpPlayerImg.substring(tmpPlayerImg.indexOf("imageUrl")+"imageUrl".length()+3);
						teamPlayerList[j][2] = tmpPlayerImg.substring(0, tmpPlayerImg.indexOf("\""));	
						
						// 3	pnum
						teamPlayerList[j][3] = tmpPlayer[j].substring(tmpPlayer[j].indexOf("\"backNumber\":\"")+"\"backNumber\":\"".length(), tmpPlayer[j].indexOf("\",\"nameMain\":\""));	
						
						// 4	pko
						teamPlayerList[j][4] = tmpPlayer[j].substring(tmpPlayer[j].indexOf("nameMain\":\"")+"nameMain\":\"".length(), tmpPlayer[j].indexOf(",\"nameKo\":")-1);		// pko
						
						// 5	pen
						String tmpPen = tmpPlayer[j].substring(tmpPlayer[j].indexOf("nameEn")+"nameEn".length()+3);
						teamPlayerList[j][5] = tmpPen.substring(0, tmpPen.indexOf("\",\""));		

						// 10	tko
						String tmpTeamName = tmpPlayer[j].substring(tmpPlayer[j].indexOf("shortNameKo\":\"")+"shortNameKo\":\"".length());						
						teamPlayerList[j][10] = tmpTeamName.substring(0, tmpTeamName.indexOf("\""));	
						
						// 12	tid_daum
						teamPlayerList[j][12] = tmpPlayer[j].substring(tmpPlayer[j].indexOf("\"team\":{\"teamId\":")+"\"team\":{\"teamId\":".length(), tmpPlayer[j].indexOf(",\"cpTeamId\":\""));	 
						

						// 14	timg
						String tmpTeamImg= tmpPlayer[j].substring(tmpPlayer[j].indexOf("imageUrl")+"imageUrl".length()+3);
						teamPlayerList[j][14] = tmpTeamImg.substring(0 , tmpTeamImg.indexOf("\""));	

				}
				// daum 정보 토대로 정보추가
				for (int j = 0; j < tmpPlayer.length;  j++) {
					int pid_daum = Integer.parseInt(teamPlayerList[j][0]); 
					String pimg = teamPlayerList[j][2];
					int pnum = Integer.parseInt(teamPlayerList[j][3]);
					String pko = teamPlayerList[j][4];
					String pen = teamPlayerList[j][5];
					// lko
					// len
					// lid
					String tko	=	teamPlayerList[j][10];
					int tid_daum = Integer.parseInt(teamPlayerList[j][12]);
					String timg = teamPlayerList[j][14];

					//dao.insertPlayer(pid_daum, pimg, pnum, pko, pen, lko, len, lid, tko, tid_daum, timg);
					//System.out.println("daum 처리 시작" + pid_daum +" / "+ pimg +" / "+ pnum +" / "+ pko +" / "+ pen +" / "+ lko +" / "+ len +" / "+ lid +" / "+ tko +" / "+ tid_daum +" / "+ timg);
				}
			}	
		}
		
		
		// sofa 플레이어 리스트 정보 daum 항목에 더하기
		// 1	pid_sofa
		String[] sofaPlayerID = new String [1000];
		// 5	pen (where 처리용)
		String[] sofaPlayerName	 = new String [1000];
		// 6	pslug
		String[] sofaPlayerSplug	 = new String [1000];
		//	11	ten
		String[] sofaTeamEnName = new String [1000];
		// 13 tid_sofa
		String[] sofaTeamId = new String [1000];
		//	14	tslug (sofa)
		String[] sofaTeamSlug = new String [1000];
		for (int j = 0; j < 10; j++) {
			
			response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/17/season/37036/statistics?limit=100&offset="+j*100+"&order=-rating&accumulation=total&group=summary")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			document = response.parse();
			
			html = document.html();
			text = document.text();		
			text = text.replace("\n", "");
			html = html.replace("\n", "");
		

			String tmpAllPlayer = text.substring(text.indexOf("{\"results\":[{\"")+"{\"results\":[{\"".length());
			
			String[] tmpAllPlayerList = tmpAllPlayer.split("\"\\}\\}\\},\\{\"");

			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( j == stopPage ) {
				break;
			} else {

				for (int k = 0; k < tmpAllPlayerList.length ; k++) {
					System.out.println(tmpAllPlayerList[k]);
					
					// 1	pid_sofa
					String tmpSofaPlayerID = tmpAllPlayerList[k].substring(tmpAllPlayerList[k].indexOf("\"id\"")+"\"id\"".length()+1);
					sofaPlayerID[k+(j*100)] = tmpSofaPlayerID.substring(0, tmpSofaPlayerID.indexOf("}"));
					
					// 5	pen (where 처리용)
					String tmpSofaPlayerName = tmpAllPlayerList[k].substring(tmpAllPlayerList[k].indexOf("\"name\"")+"\"name\"".length()+2);
					sofaPlayerName[k+(j*100)] = tmpSofaPlayerName.substring(0, tmpSofaPlayerName.indexOf("\""));

					// 6	pslug
					String tmpSofaPlayerSplug	 = tmpAllPlayerList[k].substring(tmpAllPlayerList[k].indexOf("\"slug\"")+"\"slug\"".length()+2);
					sofaPlayerSplug[k+(j*100)] = tmpSofaPlayerSplug.substring(0, tmpSofaPlayerSplug.indexOf("\""));
					
					//	11	ten
					String tmpSofaTeamEnName	 = tmpAllPlayerList[k].substring(tmpAllPlayerList[k].indexOf("\"shortName\"")+"\"shortName\"".length()+2);
					sofaTeamEnName[k+(j*100)] = tmpSofaTeamEnName.substring(0, tmpSofaTeamEnName.indexOf("\""));
					
					// 13 tid_sofa
					String tmpSofaTeamId = tmpAllPlayerList[k].substring(tmpAllPlayerList[k].indexOf("\"id\"")+"\"id\"".length()+1);
					tmpSofaTeamId = tmpSofaTeamId.substring(tmpSofaTeamId.indexOf("\"id\"")+"\"id\"".length()+1);
					tmpSofaTeamId = tmpSofaTeamId.substring(tmpSofaTeamId.indexOf("\"id\"")+"\"id\"".length()+1);
					sofaTeamId[k+(j*100)] = tmpSofaTeamId.substring(0, tmpSofaTeamId.indexOf(","));
					
					//	15	tslug (sofa)
					String tmpSofaTeamSlug = tmpAllPlayerList[k].substring(tmpAllPlayerList[k].indexOf("\"slug\"")+"\"slug\"".length()+2);
					tmpSofaTeamSlug = tmpSofaTeamSlug.substring(tmpSofaTeamSlug.indexOf("\"slug\"")+"\"slug\"".length()+2);
					sofaTeamSlug[k+(j*100)] = tmpSofaTeamSlug.substring(0, tmpSofaTeamSlug.indexOf("\""));
					
					
				}
			}
		}
		for (int j = 0; j < 1000;  j++) {
			if ( sofaPlayerID[j] == null) {
				break;
			} else {
				int pid_sofa = Integer.parseInt(sofaPlayerID[j]); 
				String pen = sofaPlayerName[j];
				String pslug = sofaPlayerSplug[j];
				String ten = sofaTeamEnName[j];
				int tid_sofa = Integer.parseInt(sofaTeamId[j]);
				String tslug = sofaTeamSlug[j];

//				dao.sofaUpdatePlayer(pid_sofa, pen, pslug, ten, tid_sofa, tslug);
//				System.out.println("SOFA처리 시작 "+ pid_sofa +" / "+ pen +" / "+ pslug +" / "+ ten +" / "+ tid_sofa +" / "+ tslug);
			}
		}
	}
}