package dataLoad;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class LoadTeam1 {

	public static void main(String[] args) {
		System.out.println("::::::::::::::::::::::Team DB 갱신 시작 (1)::::::::::::::::::::::");
		
		DAO dao = new DAO();
		String[] leagueArr_daum = {"epl", "primera", "bundesliga", "seriea", "ligue1"};
		String[] leagueArr	= {"17", "8", "35", "23", "34"};
		String[] seasonArr	= {"37036", "37223", "37166", "37475", "37167"};
		String sid_daum = "20212022";
		int lid_sofa = Integer.parseInt(leagueArr[0]);
		int cnt = 0;
		
		Connection.Response response;
		Document document = null;
		
		for (int set = 0; set < seasonArr.length; set++) {
			System.out.println("[리그] "+leagueArr_daum[set]);
				
			try {
				response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+leagueArr[set]+"/season/"+seasonArr[set]+"/standings/total")
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
			String substrA = "[{\"team";
			String substrB = "}],\"id\"";
			
			String htmlSubstr = html.substring(html.indexOf(substrA)+2,html.indexOf(substrB) );	// 필요 없는 내용 앞뒤로 자르기
			String[] htmlSplit = htmlSubstr.split("\\},\\{");													// 자른거에서 한줄씩 분리
	
			//System.out.println(html);
			
			String tmp ="";
			
			for (int i = 0; i < htmlSplit.length; i++) {
				
				// 1 tid_sofa 팀아이디 sofa
				tmp = htmlSplit[i].substring(htmlSplit[i].indexOf("\"id\":")+"\"id\":".length());
				tmp = tmp.substring(tmp.indexOf("\"id\":")+"\"id\":".length());
				int tid_sofa = Integer.parseInt(tmp.substring(0, tmp.indexOf(",\"")));
				//System.out.print(tid_sofa+" / ");			
				
				// 6 tslug 내부표기법 저장
				tmp = htmlSplit[i].substring(htmlSplit[i].indexOf("\"slug\":\"")+"\"slug\":\"".length());
				String tslug = tmp.substring(0, tmp.indexOf("\","));
				//System.out.print(tslug+" / ");
				
				
				// 5 ten 팀 영문명 시작 ----------------------------------------- 
				String ten = "";
				String[] tenTmpArr = null;
				if (tslug.indexOf("-") > 0) {		// plsug에 "-"가 있다면.
					tenTmpArr = tslug.split("-");
					
					// 자른것들 마다 첫글자 대문자로 변경
					for (int j = 0; j < tenTmpArr.length; j++) {
						tenTmpArr[j] = tenTmpArr[j].substring(0, 1).toUpperCase() + tenTmpArr[j].substring(1);
					}
					// 이름을 공백 빼고 저장
					// 6 pen
					switch (tenTmpArr.length) {
					case 2 :
						ten = tenTmpArr[0] +" "+ tenTmpArr[1];
						break;
					case 3 :
						ten = tenTmpArr[0] +" "+ tenTmpArr[1] +" "+ tenTmpArr[2];
						break;
					case 4 :
						ten = tenTmpArr[0] +" "+ tenTmpArr[1] +" "+ tenTmpArr[2] +" "+ tenTmpArr[3];
						break;
					case 5 :
						ten = tenTmpArr[0] +" "+ tenTmpArr[1] +" "+ tenTmpArr[2] +" "+ tenTmpArr[3] +" "+ tenTmpArr[4];
						break;
					}	
				} else {
					// 자를것이 없다면 그대로 pen에 저장
					ten = tslug.substring(0, 1).toUpperCase() + tslug.substring(1);;
				}
				// 5 ten 팀영문명 종료-----------------------------------------		
				//System.out.println(ten);
				
				String lslid = leagueArr_daum[set]+"/"+sid_daum+"/"+(i+1);
				
				cnt = cnt+1;
				System.out.print(cnt+"번째 ");
				dao.loadTeam1(tid_sofa, lid_sofa, ten, tslug, lslid);	
			}
		}
		System.out.println("::::::::::::::::::::::Team DB 갱신 완료 (1)::::::::::::::::::::::");
	}
}
