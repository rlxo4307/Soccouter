package dataLoad;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class InfoAllplayer3 {

	public static void main(String[] args) throws IOException {
		System.out.println("::::::::::::::::::::::Player DB 갱신 시작 (3)::::::::::::::::::::::");
		DAO dao = new DAO();

		// daum 플레이어 리스트 저장 시작 ===================================================================
		// epl		// bundesliga		// primera		// seriea		// ligue1		
		
		String[] lenArr = {"epl", "bundesliga" , "primera", "seriea" , "ligue1"};

		for (int set = 0; set < lenArr.length; set++) {
			String len = lenArr[set] ;	
				
			Connection.Response response = Jsoup.connect("https://sports.daum.net/team/"+len)
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
	
			String html = document.html();
			html = html.replace("\n", "");
			
	
			// 불필요 부분 자르기
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
			
			
			//리그 명과 팀명을 활용해서 선수 저장 하기
			for (int i = 0; i < leagueTeam.length; i++) {
				
				if ( leagueTeam[i] != null ) {
					response = Jsoup.connect("https://sports.daum.net/prx/hermes/api/person/list.json?leagueCode="+len+"&teamId="+leagueTeam[i])
			                .method(Connection.Method.GET)
			                .ignoreContentType(true)
			                .execute();
					document = response.parse();
	
					String text = document.text();		
					text = text.substring(text.indexOf("\"list\":[{\"personId\":")+9, text.indexOf("}]}"));
					
					String[] tmpPlayer = text.split("\\},\\{");
					String [][] teamPlayerList = new String [tmpPlayer.length][15];
					
					for (int j = 0; j < tmpPlayer.length; j++) {
							//System.out.println("base "+ tmpPlayer[j]);
						
							// 2	pid_daum
							teamPlayerList[j][2] = tmpPlayer[j].substring(tmpPlayer[j].indexOf("\"personId\":")+"\"personId\":".length(), tmpPlayer[j].indexOf("\"cpPersonId\":\"")-1);	
							
							// 5	pko
							teamPlayerList[j][5] = tmpPlayer[j].substring(tmpPlayer[j].indexOf("nameMain\":\"")+"nameMain\":\"".length(), tmpPlayer[j].indexOf(",\"nameKo\":")-1);		// pko
					
							// 6	pen (where 확인용)
							String tmpPen = tmpPlayer[j].substring(tmpPlayer[j].indexOf("nameEn")+"nameEn".length()+3);
							teamPlayerList[j][6] = tmpPen.substring(0, tmpPen.indexOf("\",\""));
							
					}
					// daum 정보 토대로 정보추가
					for (int j = 0; j < tmpPlayer.length;  j++) {
						int pid_daum = Integer.parseInt(teamPlayerList[j][2]); 
						String pko = teamPlayerList[j][5];
						String pen = teamPlayerList[j][6];
						dao.updatePlayer1(pid_daum, pko, pen);
					}
				}	
			}
		}
		System.out.println("::::::::::::::::::::::Player DB 갱신 완료 (3)::::::::::::::::::::::");
	}
}