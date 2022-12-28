package org.crawler;

import java.io.IOException;
import org.jsoup.nodes.Document;

import jdbc.DAO;

import org.jsoup.Connection;
import org.jsoup.Jsoup;


public class SofaDefense {

	public static void main(String[] args) throws IOException {
		// Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
		int Season = 37166;
		int League = 35;
				
		//첫번째 스텟들
		for(int i = 0; i < 10; i++) {
				
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+League+"/season/"+Season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=tackles%2Cinterceptions%2CpenaltyConceded%2Cclearances%2CerrorLeadToGoal&filters=position.in.G~D~M~F")
			.method(Connection.Method.GET)
			.ignoreContentType(true)
			.execute();
			Document document = response.parse();
			          
			String html = document.html();
			String text = document.text();   
			        
			String pageCheck1 = null;
			String pageCheck2 = null;
			        
			String[] sentance = text.split("\\{\"tac");
			        
			for(int j=1; j<sentance.length; j++) {				
				
				int tackles = 0;
				if(sentance[j].indexOf("les\":") > 0) {
					String tmpPheight = sentance[j].substring(sentance[j].indexOf("les\":")+"les\":".length());
					tackles = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
				} else {
	            	tackles = 0;
				}
				
	            int interceptions = 0;
	            if(sentance[j].indexOf(",\"interceptions\":") > 0 ){
	               String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"interceptions\":")+",\"interceptions\":".length());
	               interceptions = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	            } else {
	            	interceptions = 0;
	            }
				//String interceptions2 = sentance[j].substring(sentance[j].indexOf("\"interceptions\":")+("\"interceptions\":").length(), sentance[j].indexOf(",\"penaltyConceded"));
				
	            int penaltyConceded = 0;
	            if(sentance[j].indexOf("\"penaltyConceded\":") > 0 ){
	               String tmpPheight = sentance[j].substring(sentance[j].indexOf("\"penaltyConceded\":")+"\"penaltyConceded\":".length());
	               penaltyConceded = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	            } else {
	            	penaltyConceded = 0;
	            }
		        //String penaltyConceded2 = sentance[j].substring(sentance[j].indexOf(",\"penaltyConceded\":")+(",\"penaltyConceded\":").length(), sentance[j].indexOf(",\"clearances"));
				
	            int clearances = 0;
	            if(sentance[j].indexOf("\"clearances\":") > 0 ){
	               String tmpPheight = sentance[j].substring(sentance[j].indexOf("\"clearances\":")+"\"clearances\":".length());
	               clearances = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	            } else {
	            	clearances = 0;
	            }
		        //String clearances2 = sentance[j].substring(sentance[j].indexOf(",\"clearances\":")+(",\"clearances\":").length(), sentance[j].indexOf(",\"errorLeadToGoa"));
				
	            int errorLeadToGoal = 0;
	            if(sentance[j].indexOf("\"errorLeadToGoal\":") > 0 ){
	               String tmpPheight = sentance[j].substring(sentance[j].indexOf("\"errorLeadToGoal\":")+"\"errorLeadToGoal\":".length());
	               errorLeadToGoal = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	            } else {
	            	errorLeadToGoal = 0;
	            }
		        //String errorLeadToGoal2 = sentance[j].substring(sentance[j].indexOf(",\"errorLeadToGoal\":")+(",\"errorLeadToGoal\":").length(), sentance[j].indexOf(",\"player\":{\""));
	            
	            
	            String[] temp = sentance[j].split(",\"id");
	            int pid_sofa = 0;
	            if(temp[1].indexOf(":") > 0 ){
	               String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
	               pid_sofa = Integer.parseInt(tmpPheight.substring(0, temp[1].indexOf("}")-2));
	            } else {
	            	pid_sofa = 0;
	            }
		        //String pid_sofa2 = sentance[j].substring(sentance[j].indexOf(",\"id\":")+(",\"id\":").length(),sentance[j].indexOf("},\"team\":{\""));
				
			    String tid_sofa2 = temp[3].substring(2, temp[3].indexOf(",\"teamColors\""));
					    	   			    	   
				int sid_sofa = Season;
				int lid_sofa = League;
				int tid_sofa = Integer.parseInt(tid_sofa2);
				   	   
				String psid = pid_sofa+"/"+sid_sofa+"/"+lid_sofa+"/"+tid_sofa;
					    	   
				pageCheck1 = text.substring(text.indexOf("\"page\":")+("\"page\":").length(), text.indexOf(",\"pages\":"));
				pageCheck2 = text.substring(text.indexOf("\"pages\":")+("\"pages\":").length(), text.indexOf("\"pages\":")+("\"pages\":").length()+1);
				
				String name = sentance[j].substring(sentance[j].indexOf("\":{\"name\":\"")+("\":{\"name\":\"").length(), sentance[j].indexOf("\",\"slug\""));
				
//				System.out.println((i+1)+"번쨰 페이지 ");			    	   
//				System.out.print(j+"번째 선수 : "+name+" / " );	    	  			    	   
//				System.out.print("태클:"+tackles +" / ");			           
//				System.out.print(interceptions+" / ");			           
//				System.out.print(penaltyConceded+" / ");			           
//				System.out.print(clearances+" / ");		
//				System.out.print(errorLeadToGoal+" / ");
//				System.out.print(pid_sofa+" / ");			           
//				System.out.print(tid_sofa+" / ");	  
//				System.out.println();
				
				
				DAO sdao = new DAO();
				sdao.firstFive(psid, pid_sofa, sid_sofa, lid_sofa, tid_sofa, tackles, interceptions, penaltyConceded, clearances, errorLeadToGoal);
				
					      
				}
			System.out.println(Integer.parseInt(pageCheck1)+"of"+Integer.parseInt(pageCheck2));
			if (Integer.parseInt(pageCheck1) == Integer.parseInt(pageCheck2)) 
				break;
			}
				
				
		//두번째 스텟들
		for(int i = 0; i < 7; i++) {
				
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+League+"/season/"+Season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=errorLeadToShot%2CownGoals%2CdribbledPast%2CblockedShots%2CcleanSheet&filters=position.in.G~D~M~F")
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
			    
		
			int errors_lead_to_shot = 0;
			if(sentance[j].indexOf("eadToShot\":") > 0) {
				String tmpPheight = sentance[j].substring(sentance[j].indexOf("eadToShot\":")+"eadToShot\":".length());
				errors_lead_to_shot = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
			} else {
				errors_lead_to_shot = 0;
			}
				
	        int own_goals = 0;
	        if(sentance[j].indexOf("\"ownGoals\":") > 0 ){
	           String tmpPheight = sentance[j].substring(sentance[j].indexOf("\"ownGoals\":")+"\"ownGoals\":".length());
	           own_goals = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	        } else {
	        	own_goals = 0;
            }
			
	        
	        int dribbled_past = 0;
	        if(sentance[j].indexOf("\"dribbledPast\":") > 0 ){
	           String tmpPheight = sentance[j].substring(sentance[j].indexOf("\"dribbledPast\":")+"\"dribbledPast\":".length());
	           dribbled_past = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	        } else {
	        	dribbled_past = 0;
	        }
		    
	        
	        int blocked_shots = 0;
	        if(sentance[j].indexOf("\"blockedShots\":") > 0 ){
	           String tmpPheight = sentance[j].substring(sentance[j].indexOf("\"blockedShots\":")+"\"blockedShots\":".length());
	           blocked_shots = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	        } else {
	        	blocked_shots = 0;
	        }
	        
	        
	       int clean_sheets = 0;
	       if(sentance[j].indexOf("\"errorLeadToGoal\":") > 0 ){
	           String tmpPheight = sentance[j].substring(sentance[j].indexOf("\"errorLeadToGoal\":")+"\"errorLeadToGoal\":".length());
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
	       	//String pid_sofa2 = sentance[j].substring(sentance[j].indexOf(",\"id\":")+(",\"id\":").length(),sentance[j].indexOf("},\"team\":{\""));
				
	       String tid_sofa2 = temp[3].substring(2, temp[3].indexOf(",\"teamColors\""));
	    	   
			int sid_sofa = Season;
			int lid_sofa = League;
			int tid_sofa = Integer.parseInt(tid_sofa2);
				   	   
			String psid = pid_sofa+"/"+sid_sofa+"/"+lid_sofa+"/"+tid_sofa;
					    	   
			pageCheck1 = text.substring(text.indexOf("\"page\":")+("\"page\":").length(), text.indexOf(",\"pages\":"));
			pageCheck2 = text.substring(text.indexOf("\"pages\":")+("\"pages\":").length(), text.indexOf("\"pages\":")+("\"pages\":").length()+1);

			
			String name = sentance[j].substring(sentance[j].indexOf("\":{\"name\":\"")+("\":{\"name\":\"").length(), sentance[j].indexOf("\",\"slug\""));
				
			System.out.println((i+1)+"번쨰 페이지 ");			    	   
			System.out.print(j+"번째 선수 : "+name+" / " );	    	  			    	   
			System.out.print("에러 리드 샷 투 골:"+errors_lead_to_shot +" / ");			           
			System.out.print(own_goals+" / ");			           
			System.out.print(dribbled_past+" / ");			           
			System.out.print(blocked_shots+" / ");		
			System.out.print(clean_sheets+" / ");
			System.out.print(pid_sofa+" / ");			           
			System.out.print(tid_sofa+" / ");	  
			System.out.println();
			
			
			PlayerStatDAO sdao = new PlayerStatDAO();
			sdao.secondFive(errors_lead_to_shot, own_goals, dribbled_past, blocked_shots, clean_sheets, psid);
				
					      
			}
			if (Integer.parseInt(pageCheck1) == Integer.parseInt(pageCheck2)) 
				break;
			}
			
		
	}
}