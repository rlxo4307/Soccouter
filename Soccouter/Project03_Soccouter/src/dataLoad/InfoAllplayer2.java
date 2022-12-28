package dataLoad;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class InfoAllplayer2 {

	public static void main(String[] args) throws IOException {
		System.out.println("::::::::::::::::::::::Player DB 갱신 시작 (2)::::::::::::::::::::::");
		DAO dao = new DAO();
		int cnt = 0;

		String [] pslugID = dao.getPidList();
		String[] tmp = null;
		
		for (int i = 1020; i < pslugID.length; i++) {
			if( pslugID[i] == null) {
				break;
			} else {
				Connection.Response response = Jsoup.connect("https://www.sofascore.com/player/"+pslugID[i])
		                .method(Connection.Method.GET)
		                .ignoreContentType(true)
		                .execute();
				Document document = response.parse();

				String html = document.html();
				html = html.replace("\n", "");
				//System.out.println(html);

				// 1 플레이어 아이디
				int pid_sofa = Integer.parseInt(pslugID[i].substring(pslugID[i].indexOf("/")+1)) ;
				System.out.print("["+pid_sofa+"] ");
				// 3 플레이어 이미지
				String pimg = "https://api.sofascore.app/api/v1/player/"+pid_sofa+"/image"; 

				// 4 백넘버
				String pnum = null;
				
				if(html.indexOf("\"shirtNumber\":") > 0 ){
					String tmpPnum = html.substring(html.indexOf("\"shirtNumber\":")+"\"shirtNumber\":".length());
					pnum = tmpPnum.substring(0, tmpPnum.indexOf(",\""));
				} else {
					pnum = null;
				}
				
				// 8 리그 아이디
				tmp = html.split("\"id\":");
				String lid_sofa =  tmp[11].substring(0, tmp[11].indexOf("},"));
				
				// 9 팀 아이디	
				int tid_sofa = 0;
				if(html.indexOf("<a href=\"/team/football/") > 0 ){
					String tmpTid_sofa = html.substring(html.indexOf("<a href=\"/team/football/")+"<a href=\\\"/team/football/".length());
					tmpTid_sofa = tmpTid_sofa.substring(tmpTid_sofa.indexOf("/")+1);
					tmpTid_sofa = tmpTid_sofa.substring(0, tmpTid_sofa.indexOf("\""));
					tid_sofa = Integer.parseInt(tmpTid_sofa);
				}else {
					tid_sofa = 0;
				}

				// 10 키
				int pheight = 0;
				if(html.indexOf("\",\"height\":\"") > 0 ){
					String tmpPheight = html.substring(html.indexOf("\",\"height\":\"")+"\",\"height\":\"".length());
					pheight = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(" ")));
				} else {
					pheight = 0;
				}

				// 12 국가명
				String tmpPnation = html.substring(html.indexOf("<span class=\"u-pL8\">")+"<span class=\"u-pL8\">".length());
				String pnation = tmpPnation.substring(0, tmpPnation.indexOf("<"));
				
				// 13 국가 이미지
				String tmpPnationimg = html.substring(html.indexOf("<img src=\"/static/images/flags/")+"<img src=\"/static/images/flags/".length());
				String pnationimg = "https://www.sofascore.com/static/images/flags/"+tmpPnationimg.substring(0, tmpPnationimg.indexOf(".png"))+".png";
				
				// 11 출생
				String tmpPbirth = html.substring(html.indexOf("\"birthDate\":\"")+"\"birthDate\":\"".length());
				String pbirth = tmpPbirth.substring(0, 10);
				
				// 14 선호발
				String pfoot = null;
				if(html.indexOf("\"preferredFoot\":\"") > 0 ){
					String tmpPfoot = html.substring(html.indexOf("\"preferredFoot\":\"")+"\"preferredFoot\":\"".length());
					pfoot = tmpPfoot.substring(0, tmpPfoot.indexOf("\""));
				} else {
					pfoot = null;
				}

				// 15 포지션
				String tmpPposition = html.substring(html.indexOf("\"position\":\"")+"\"position\":\"".length());
				String pposition = tmpPposition.substring(0, tmpPposition.indexOf("\""));
				
				// 쿼리 전송 시작
				cnt = cnt+1;
				System.out.print(cnt + "번째 ");
				dao.updatePlayer2( pimg, pnum, lid_sofa, tid_sofa, pheight, pnation, pbirth, pnationimg, pfoot, pposition,  pid_sofa);

			}
		}
		System.out.println("::::::::::::::::::::::Player DB 갱신 완료 (2)::::::::::::::::::::::");
	}
}