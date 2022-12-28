package backup;
import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class InfoTeamPlayer {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		
		
		
		Connection.Response response = Jsoup.connect("https://sports.daum.net/prx/hermes/api/person/list.json?leagueCode=epl&teamId=249")
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .execute();
		Document document = response.parse();

		String html = document.html();
		String text = document.text();		
		text = text.replace("\n", "");
		html = html.replace("\n", "");
		
		//System.out.println(text);
		
		text = text.substring(text.indexOf("\"list\":[{\"personId\":")+9, text.indexOf("}]}"));
		//System.out.println(text);
		String[] tmpPlayer = text.split("\\},\\{");
		System.out.println(tmpPlayer[0]);
		// "personId": // ,"cpPersonId":"
		// "backNumber":" // ","nameMain":"
		//	nameMain":"	//	","nameKo":"
		// ","birthDate":" // ","height":
		// ","height": // ,"weight":
		// ,"weight": // ,"birthNationCode":
		// position":{"nameMain":" // ","nameKo":"
		// "imageUrl":" // ","firstName":"
		
		String [][] teamPlayerList = new String [50][8];
		
		for (int i = 0; i < tmpPlayer.length; i++) {
			for (int j = 0; j < 8; j++) {
				switch ( j ) {
				case 0 :		// personId
					teamPlayerList[i][j] = tmpPlayer[i].substring(tmpPlayer[i].indexOf("\"personId\":")+"\"personId\":".length(), tmpPlayer[i].indexOf("\"cpPersonId\":\"")-1);
					System.out.print(teamPlayerList[i][j] + "\t");
					break;
					
				case 1 : 		// backNumber
					teamPlayerList[i][j] = tmpPlayer[i].substring(tmpPlayer[i].indexOf("\"backNumber\":\"")+"\"backNumber\":\"".length(), tmpPlayer[i].indexOf("\",\"nameMain\":\""));
					System.out.print(teamPlayerList[i][j] + "\t");
					break;
					
				case 2 : 		// nameMain
					teamPlayerList[i][j] = tmpPlayer[i].substring(tmpPlayer[i].indexOf("nameMain\":\"")+"nameMain\":\"".length(), tmpPlayer[i].indexOf(",\"nameKo\":")-1);
					System.out.print(teamPlayerList[i][j] + "\t");
					break;	
					
				case 3 : 		// birthDate
					teamPlayerList[i][j] = tmpPlayer[i].substring(tmpPlayer[i].indexOf("\",\"birthDate\":\"")+"\",\"birthDate\":\"".length(), tmpPlayer[i].indexOf("\",\"height\":"));
					System.out.print(teamPlayerList[i][j] + "\t");
					break;	
					
				case 4 : 		// height
					teamPlayerList[i][j] = tmpPlayer[i].substring(tmpPlayer[i].indexOf("\",\"height\":")+"\",\"height\":".length(), tmpPlayer[i].indexOf(",\"weight\":"));
					if (teamPlayerList[i][j].equals("null")) {	// 데이터가 없다면. 
						teamPlayerList[i][j] = "-";
					}
					System.out.print(teamPlayerList[i][j] + "\t");
					break;	
					
				case 5 : 		// weight
					teamPlayerList[i][j] = tmpPlayer[i].substring(tmpPlayer[i].indexOf(",\"weight\":")+",\"weight\":".length(), tmpPlayer[i].indexOf(",\"birthNationCode\":"));
					if (teamPlayerList[i][j].equals("null")) {	// 데이터가 없다면. 
						teamPlayerList[i][j] = "-";
					}
					System.out.print(teamPlayerList[i][j] + "\t");
					break;	
					
				case 6 : 		// position
					teamPlayerList[i][j] = tmpPlayer[i].substring(tmpPlayer[i].indexOf("position\":{\"nameMain\":\"")+"position\":{\"nameMain\":\"".length(), tmpPlayer[i].indexOf("position\":{\"nameMain\":\"")+"position\":{\"nameMain\":\"".length()+2);
					if (teamPlayerList[i][j].equals("null")) {	// 데이터가 없다면. 
						teamPlayerList[i][j] = "-";
					}
					System.out.print(teamPlayerList[i][j] + "\t");
					break;	
				
				case 7 : 		// imageUrl
					teamPlayerList[i][j] = tmpPlayer[i].substring(tmpPlayer[i].lastIndexOf("\"imageUrl\":\"")+"\"imageUrl\":\"".length(), tmpPlayer[i].indexOf("\",\"firstName\":\""));
					if (teamPlayerList[i][j].equals("null")) {	// 데이터가 없다면. 
						teamPlayerList[i][j] = "-";
					}
					System.out.print(teamPlayerList[i][j] + "\t");
					break;	
				}
			}
			System.out.println(); 
		}


	}
}