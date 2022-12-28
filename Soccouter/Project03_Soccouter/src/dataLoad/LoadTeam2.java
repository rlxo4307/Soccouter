package dataLoad;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class LoadTeam2 {

	public static void main(String[] args) {
		System.out.println("::::::::::::::::::::::Team DB 갱신 시작 (2)::::::::::::::::::::::");
		
		DAO dao = new DAO();

		String[] leagueArr = {"epl", "primera", "bundesliga", "seriea", "ligue1"};
		String[] seasonArr = {"20212022", "20212022", "20212022", "20212022", "20212022"};
		int cnt = 0;
		
		Connection.Response response;
		Document document = null;
		
		for (int set = 0; set < leagueArr.length; set++) {
			System.out.println("[리그] "+leagueArr[set]);
				
			try {
				response = Jsoup.connect("https://sports.daum.net/prx/hermes/api/team/rank.json?leagueCode="+leagueArr[set]+"&seasonKey="+seasonArr[set]+"&page=1&pageSize=100")
				        .method(Connection.Method.GET)
				        .ignoreContentType(true)
				        .execute();
				document = response.parse();
			} catch (IOException e) {
				System.out.println("Jsoup 에러");
				e.printStackTrace();
			}
			
			String html = document.html();
			html = html.replace("\n", "");
			
			String htmlSubstr = html.substring(html.indexOf("[{\"teamId")+2, html.indexOf("}],\""));
			String[] htmlSplit = htmlSubstr.split("\\},\\{");
			
			
			String tmp = "";
			for (int i = 0; i < htmlSplit.length; i++) {
				
				// 2 tid_daum 팀 아이디 다음
				tmp = htmlSplit[i].substring(htmlSplit[i].indexOf("\"teamId\":")+"\"teamId\":".length());
				int tid_daum = Integer.parseInt(tmp.substring(0, tmp.indexOf(",\""))); 
				//System.out.print(tid_daum+" / ");
				
				// 4 tko 팀 한글명
				tmp = htmlSplit[i].substring(htmlSplit[i].indexOf("\"shortNameKo\":\"")+"\"shortNameKo\":\"".length());
				String tko = tmp.substring(0, tmp.indexOf("\",")); 
				//System.out.print(tko+" / ");
				
				// 7 timg 팀 이미지
				tmp = htmlSplit[i].substring(htmlSplit[i].indexOf("\"imageUrl\":\"")+"\"imageUrl\":\"".length());
				String timg = tmp.substring(0, tmp.indexOf("\",")); 
				//System.out.print(timg+" / ");
				
				// 8 lslid WHERE 처리용
				String lslid = leagueArr[set] +"/"+ seasonArr[set] +"/"+ (i+1);
				//System.out.println(lslid);
				
				cnt = cnt+1;
				System.out.print(cnt+"번째 ");
				dao.loadTeam2(tid_daum, tko, timg, lslid);	
			}
		}
		System.out.println("::::::::::::::::::::::Team DB 갱신 완료 (2)::::::::::::::::::::::");
	}
}
