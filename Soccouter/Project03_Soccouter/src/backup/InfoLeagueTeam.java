package backup;
import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class InfoLeagueTeam {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		
		// epl
		// bundesliga
		// primera
		// seriea
		// ligue1
			
		String leageName = "epl" ;
		Connection.Response response = Jsoup.connect("https://sports.daum.net/team/"+leageName)
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
		String[][] leagueTeam = new String [30][3];											//	팀 정보 기록 [코드][이름][이미지]
		for (int i = 0; i < tmp_html.length; i++) {
			
			// 팀 코드 구하기
			String tmpA = "a href=\"/sports/team/";
			String tmpB = "/news";
			String tmpAA = tmp_html[i].substring(tmp_html[i].indexOf(tmpA)+tmpA.length(), tmp_html[i].indexOf(tmpB));
			leagueTeam[i][0] =  tmpAA.substring(tmpAA.indexOf("/")+1);
			
			// 팀 이름 구하기
			String tmpC = "<strong class=\"tit_thumb\">";
			String tmpD = "</strong> </a>           <div class=\"detail_thumb\">";
			leagueTeam[i][1] =  tmp_html[i].substring(tmp_html[i].indexOf(tmpC)+tmpC.length(), tmp_html[i].indexOf(tmpD));
			
			// 팀 이미지 구하기
			String tmpE = "img src=\"";
			String tmpF = "\" class=\"thumb_g\"";
			leagueTeam[i][2] =  tmp_html[i].substring(tmp_html[i].indexOf(tmpE)+tmpE.length(), tmp_html[i].indexOf(tmpF));
			
			System.out.print(leagueTeam[i][0] +"\t");
			System.out.print(leagueTeam[i][1] +"\t");
			System.out.println(leagueTeam[i][2]);
			
		}
	}
}