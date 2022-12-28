package backup;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class InfoPlayerNowSeason {

	public static void main(String[] args) throws IOException {
		
		String playerNumber = "1156992"; 
		
		Connection.Response response = Jsoup.connect("https://sports.daum.net/player/epl/"+playerNumber+"/record")
                .method(Connection.Method.GET)
                .execute();
		Document document = response.parse();	
		String text = document.text();
		
		if(text.matches(".*경기당득점.*")) {
			
		}
		
		
		
		response = Jsoup.connect("https://sports.daum.net/player/epl/1156992/record")
                .method(Connection.Method.GET)
                .execute();
		document = response.parse();
		String html = document.html();
		text = document.text();		
		
		System.out.println(text);
		
		text = text.replace("\n", "");
		html = html.replace("\n", "");
		// text = text.replace("페이지로 이동", "");
		// System.out.println(html);
		
		
		// text 1차 가공
		String textSubstr_A = "순위 팀/선수 해외축구 선수 본문 팀/선수 ";
		String textSubstr_B = " 선수 상세 메뉴 뉴스 영상 시즌기록 응원댓글 더보기 ";
		String textSubstr = text.substring(text.indexOf(textSubstr_A)+textSubstr_A.length(), text.indexOf(textSubstr_B));
		
		System.out.println(textSubstr);

		// 이름
		String name_A = "해외축구 선수 본문 팀/선수 ";
		String name_B = " No.";
		String name = text.substring(text.indexOf(name_A)+name_A.length(), text.indexOf(name_B));
		
		// 등번호
		String number_A = "div class=\"info_basic\">           <span class=\"txt_info\">";
		String number_B = "</span>           <a href=\"";
		String number = html.substring(html.indexOf(number_A)+number_A.length(), html.indexOf(number_B));
		
		// 소속팀
		String team_A = "data-tiara-layer=\"top btn_team\">";
		String team_B = "<span class=\"ico_teamplayer ico_arr\">페이지로 이동";
		String team = html.substring(html.indexOf(team_A)+team_A.length(), html.indexOf(team_B));
		
		// 출생
		String born_A = " 출생 ";
		String born_B = " 키 ";
		String born = text.substring(text.indexOf(born_A)+born_A.length(), text.indexOf(born_B));
		System.out.println(born);
		
		// 포지션
		String position_A = " 포지션 ";
		String position_B = " 몸무게 ";
		String position = text.substring(text.indexOf(position_A)+position_A.length(), text.indexOf(position_B));
		
		// 키
		String height_A = " 키 ";
		String height_B = " 포지션 ";
		String height = text.substring(text.indexOf(height_A)+height_A.length(), text.indexOf(height_B));
		
		// 몸무게
		String weight_A = " 몸무게 ";
		String weight_B = "kg ";
		String weight = text.substring(text.indexOf(weight_A)+weight_A.length(), text.indexOf(weight_B)+2);

	
		
		// 경기
		String match_A = " 경기 ";
		String match_B = " 득점 ";
		String match = text.substring(text.indexOf(match_A)+match_A.length(), text.indexOf(match_B));
		
		// 득점
		String goals_A = " 득점 ";
		String goals_B = " 도움 ";
		String goals = text.substring(text.indexOf(goals_A)+goals_A.length(), text.indexOf(goals_B));
		
		// 도움
		String assist_A = " 도움 ";
		String assist_B = " 공격P ";
		String assist = text.substring(text.indexOf(assist_A)+assist_A.length(), text.indexOf(assist_B));
		
		// 공격 포인트
		String aPoint_A = " 공격P ";
		String aPoint_B = " 슈팅 ";
		String aPoint = text.substring(text.indexOf(aPoint_A)+aPoint_A.length(), text.indexOf(aPoint_B));
		System.out.println(aPoint);
				
		// 슈팅
		String shots_A = " 슈팅 ";
		String shots_B = " 유효슈팅 ";
		String shots = text.substring(text.indexOf(shots_A)+shots_A.length(), text.indexOf(shots_B));		
		
		// 유효 슈팅
		String shotsOnTarget_A = " 유효슈팅 ";
		String shotsOnTarget_B = " 경기당득점 ";
		String shotsOnTarget = text.substring(text.indexOf(shotsOnTarget_A)+shotsOnTarget_A.length(), text.indexOf(shotsOnTarget_B));		
		
		// 경기당 득점
		String goalsPerMatch_A = " 경기당득점 ";
		String goalsPerMatch_B = " 출장(분) ";
		String goalsPerMatch = text.substring(text.indexOf(goalsPerMatch_A)+goalsPerMatch_A.length(), text.indexOf(goalsPerMatch_B));		
		
		// 출장(분)
		String matchTime_A = " 출장(분) ";
		String matchTime_B = " 선수 상세 메뉴 뉴스 영상 시즌기록 응원댓글 더보기 ";
		String matchTime = text.substring(text.indexOf(matchTime_A)+matchTime_A.length(), text.indexOf(matchTime_B));		
		
	}
}