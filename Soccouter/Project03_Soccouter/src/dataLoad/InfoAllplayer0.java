package dataLoad;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class InfoAllplayer0 {

	public static void main(String[] args) throws IOException {
		System.out.println("::::::::::::::::::::::선수 정보 갱신 시작::::::::::::::::::::::");
		DAO dao = new DAO();
		
		// epl
		// bundesliga
		// primera
		// seriea
		// ligue1
			
		//String len = "epl" ;	
		//String lko = "프리미어리그";
		//String lid = "premier-league/17";
		
		String [] pid_sofa = new String [1000];
		pid_sofa = dao.getPidList();
		
		// 17
		String[] pheight = new String [1000];
		// 18
		String[] pnation = new String [1000];
		// 19 
		String[] pnationimg = new String [1000];
		// 20
		String[] pbirth = new String [1000];
		// 21
		String[] pfoot = new String [1000];
		// 22
		String[] pposition = new String [1000];
		
		for (int i = 0; i < pid_sofa.length; i++) {
			if( pid_sofa[i] == null) {
				break;
			} else {
				Connection.Response response = Jsoup.connect("https://www.sofascore.com/player/"+pid_sofa[i])
		                .method(Connection.Method.GET)
		                .ignoreContentType(true)
		                .execute();
				Document document = response.parse();

				String html = document.html();
				String text = document.text();		
				html = html.replace("\n", "");
				//System.out.println(html);

				// 17 키
				String tmpPheight = html.substring(html.indexOf("\",\"height\":\"")+"\",\"height\":\"".length());
				pheight[i] = tmpPheight.substring(0, tmpPheight.indexOf(" "));
				System.out.print(pheight[i] + " / ");
				
				// 18 국가명
				String tmpPnation = html.substring(html.indexOf("<span class=\"u-pL8\">")+"<span class=\"u-pL8\">".length());
				pnation[i] = tmpPnation.substring(0, tmpPnation.indexOf("<"));
				System.out.print(pnation[i] + " / ");
				
				// 19 국가 이미지
				String tmpPbirth = html.substring(html.indexOf("<img src=\"/static/images/flags/")+"<img src=\"/static/images/flags/".length());
				pbirth[i] = tmpPbirth.substring(0, tmpPbirth.indexOf(".png"));
				System.out.print(pbirth[i] + " / ");
				
				// 20 출생
				String tmpPnationimg = html.substring(html.indexOf("\"birthDate\":\"")+"\"birthDate\":\"".length());
				pnationimg[i] = tmpPnationimg.substring(0, 10);
				System.out.print(pnationimg[i] + " / ");
				
				// 21 선호발
				String tmpPfoot = html.substring(html.indexOf("\"preferredFoot\":\"")+"\"preferredFoot\":\"".length());
				pfoot[i] = tmpPfoot.substring(0, tmpPfoot.indexOf("\""));
				System.out.print(pfoot[i] + " / ");
				
				// 22 포지션
				String tmpPposition = html.substring(html.indexOf("\"position\":\"")+"\"position\":\"".length());
				pposition[i] = tmpPposition.substring(0, tmpPposition.indexOf("\""));
				System.out.println(pposition[i]);
				
				
				int tmpPid_sofa = Integer.parseInt(pid_sofa[i].substring(pid_sofa[i].indexOf("/")+1)) ;
				

				dao.sofaUpdatePlayer2( pheight[i], pnation[i], pbirth[i], pnationimg[i], pfoot[i], pposition[i], tmpPid_sofa);
				//System.out.println("SOFA처리 시작 "+ pid_sofa +" / "+ pen +" / "+ pslug +" / "+ ten +" / "+ tid_sofa +" / "+ tslug);

			}
		}
	}
}