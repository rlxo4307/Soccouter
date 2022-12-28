package dataLoad;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class InfoAllplayer1 {

	public static void main(String[] args) throws IOException {
		System.out.println("::::::::::::::::::::::Player DB 갱신 시작 (1)::::::::::::::::::::::");
		DAO dao = new DAO();
		int cnt = 0;

		// sofa 플레이어 리스트 저장 시작 ===================================================================

		String [] lidarr = {"35", "23", "17", "34", "8" };
		String [] sidarr = {"37166", "37475", "37036", "37167", "37223"};
		
		
		for (int a = 0; a < lidarr.length; a++) {
			System.out.println(lidarr[a]+" / "+sidarr[a]+" 시작");
			String lid = lidarr[a];									//	리그 아이디
			String sid = sidarr[a]; 								//	시즌 아이디

			// 1	pid_sofa
			String[] sofaPlayerID = new String [1000];
			// 6	pslug
			String[] sofaPlayerSplug	 = new String [1000];
			
			
			for (int j = 0; j < 10; j++) {
				
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+lid+"/season/"+sid+"/statistics?limit=100&offset="+j*100+"&order=-rating&accumulation=total&group=summary")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true)
		                .execute();
				Document document = response.parse();
				
				String html = document.html();
				String text = document.text();		
				text = text.replace("\n", "");
				html = html.replace("\n", "");
			
	
				String tmpAllPlayer = text.substring(text.indexOf("{\"results\":[{\"")+"{\"results\":[{\"".length());			// 위 아래 불필요 항목 제거한 후 통 Str
				String[] tmpAllPlayerList = tmpAllPlayer.split("\"\\}\\}\\},\\{\"");													// 통 str에서 구분자로 나눈 1줄 데이터
	
				// for문 break 조건 시작
				// 조회한 페이지(j)가 전체 페이지(stopPage)보다 많거나 같으면 멈추기
				int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1));	 
				if ( j == stopPage ) {
					break;
				//	for문 break 조건 끝
	
				} else {		
					// 정보 메모리에 저장 시작
	
					for (int k = 0; k < tmpAllPlayerList.length ; k++) {
						
						// System.out.println(tmpAllPlayerList[k]);		// 자를 대상
						
						// 1	pid_sofa
						String tmpSofaPlayerID = tmpAllPlayerList[k].substring(tmpAllPlayerList[k].indexOf("\"id\"")+"\"id\"".length()+1);
						sofaPlayerID[k+(j*100)] = tmpSofaPlayerID.substring(0, tmpSofaPlayerID.indexOf("}"));
						
						// 7	pslug
						String tmpSofaPlayerSplug	 = tmpAllPlayerList[k].substring(tmpAllPlayerList[k].indexOf("\"slug\"")+"\"slug\"".length()+2);
						sofaPlayerSplug[k+(j*100)] = tmpSofaPlayerSplug.substring(0, tmpSofaPlayerSplug.indexOf("\""));
						
					}
				}
			}
			
			// 메모리에 저장된 플레이어 리스트 DB에 저장 시작 ===============================
			for (int j = 0; j < 1000;  j++) {
				if ( sofaPlayerID[j] == null) {
					System.out.println("선수 추가 종료 =====================================");
					break;
				} else {
					String pen = "";
					
					
					// plsug에 "-"를 기준으로 자름
					String[] penTmpArr = null;
					if (sofaPlayerSplug[j].indexOf("-") > 0) {		// plsug에 "-"가 있다면.
						penTmpArr = sofaPlayerSplug[j].split("-");
						
						// 자른것들 마다 첫글자 대문자로 변경
						for (int i = 0; i < penTmpArr.length; i++) {
							penTmpArr[i] = penTmpArr[i].substring(0, 1).toUpperCase() + penTmpArr[i].substring(1);
							
							// 이름을 공백 빼고 저장
							// 6 pen
							switch (penTmpArr.length) {
							case 2 :
								pen = penTmpArr[0] +" "+ penTmpArr[1];
								break;
							case 3 :
								pen = penTmpArr[0] +" "+ penTmpArr[1] +" "+ penTmpArr[2];
								break;
							case 4 :
								pen = penTmpArr[0] +" "+ penTmpArr[1] +" "+ penTmpArr[2] +" "+ penTmpArr[3];
								break;
							case 5 :
								pen = penTmpArr[0] +" "+ penTmpArr[1] +" "+ penTmpArr[2] +" "+ penTmpArr[3] +" "+ penTmpArr[4];
								break;
							}
						}
					} else {
						// 자를것이 없다면 그대로 pen에 저장
						pen = sofaPlayerSplug[j].substring(0, 1).toUpperCase() + sofaPlayerSplug[j].substring(1);;
					}
	
					int pid_sofa = Integer.parseInt(sofaPlayerID[j]); 
					String pslug = sofaPlayerSplug[j];
					
					cnt = cnt+1;
					System.out.print(cnt+"번째 ");
					dao.insertPlayer(pid_sofa, pen, pslug);
					
				}
			}
		}
		System.out.println("::::::::::::::::::::::Player DB 갱신 완료 (1)::::::::::::::::::::::");
	}
}