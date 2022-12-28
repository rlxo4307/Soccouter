package service;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class SofaDefense {

	public static void main(String[] args) throws IOException {
		// Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
		int Season[] = { 37166, 37223, 37167, 37036, 37475};
		int League[] = { 35, 8, 34, 17, 23 };	
		int a = 0;
		//두번째 스텟들
		for(int i = 0; i < 5; i++) {
			for(int k=0; k < 10; k++) {
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+League[i]+"/season/"+Season[i]+"/statistics?limit=100&order=-rating&offset="+k*100+"&accumulation=total&fields=errorLeadToShot%2CownGoals%2CdribbledPast%2CblockedShots%2CcleanSheet&filters=position.in.G~D~M~F")
			.method(Connection.Method.GET)
			.ignoreContentType(true)
			.execute();
			Document document = response.parse();
			          
			String html = document.html();
			String text = document.text();   
			        
			String pageCheck1 = null;
			String pageCheck2 = null;
			        
			String[] sentance = text.split("\\{\"error");
	        
			for(int j=1; j<sentance.length; j++) {
			    
	        
		       int clean_sheets = 0;
		       if(sentance[j].indexOf("cleanSheet\":") >= 0 ){
		           String tmpPheight = sentance[j].substring(sentance[j].indexOf("cleanSheet\":")+"cleanSheet\":".length());
		           clean_sheets = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		       } else {
		    	   clean_sheets = 0;
	           }
		        
	            
	       String[] temp = sentance[j].split(",\"id");
	       int pid_sofa = 0;
	       if(temp[1].indexOf(":") > 0 ){
	    	   String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
	           pid_sofa = Integer.parseInt(tmpPheight.substring(0, temp[1].indexOf("}")-2));
	        } else {
	          	pid_sofa = 0;
	        }
				
	       String tid_sofa2 = temp[3].substring(2, temp[3].indexOf(",\"teamColors\""));
	    	   
			int sid_sofa = Season[i];
			int lid_sofa = League[i];
			int tid_sofa = Integer.parseInt(tid_sofa2);
					    	   
			pageCheck1 = text.substring(text.indexOf("\"page\":")+("\"page\":").length(), text.indexOf(",\"pages\":"));
			pageCheck2 = text.substring(text.indexOf("\"pages\":")+("\"pages\":").length(), text.indexOf("\"pages\":")+("\"pages\":").length()+1);

			
			String name = sentance[j].substring(sentance[j].indexOf("\":{\"name\":\"")+("\":{\"name\":\"").length(), sentance[j].indexOf("\",\"slug\""));			    	   
			System.out.print(j+"번째 선수 : "+name+" / " );		
			System.out.print("무실점 경기 : " + clean_sheets+" / ");
			System.out.print(tid_sofa+" / ");
			System.out.print(pid_sofa+" / ");	
			System.out.print(pid_sofa+" / ");
			System.out.println();

			DAO dao = new DAO();
			dao.Update_clean_sheets(clean_sheets, tid_sofa, pid_sofa);
				
					      
			}
			a+=1;
			if (Integer.parseInt(pageCheck1) == Integer.parseInt(pageCheck2)) 
				break;
			}
			System.out.print("a : " + a);
			System.out.println();
		}
			
		
	}
}