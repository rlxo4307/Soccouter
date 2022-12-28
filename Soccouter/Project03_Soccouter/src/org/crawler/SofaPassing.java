package org.jsoup.sofascore.passing;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import DAO.SofaPassingDAO;

public class SofaBundesPassing {

	public static void main(String[] args) throws IOException {
		// Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
		int Season = 37166;
		int League = 35;
				
		//첫번째 스텟들
		for(int i = 0; i < 10; i++) {
				
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+League+"/season/"+Season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=bigChancesCreated%2Cassists%2CaccuratePasses%2CinaccuratePasses%2CtotalPasses&filters=position.in.G~D~M~F")
			.method(Connection.Method.GET)
			.ignoreContentType(true)
			.execute();
			Document document = response.parse();
			          
			String html = document.html();
			String text = document.text();   
			        
			String pageCheck1 = null;
			String pageCheck2 = null;
			        
			String[] sentance = text.split("\"bigChan");
			        
			for(int j=1; j<sentance.length; j++) {				
				
				int big_chances_created = 0;
				if(sentance[j].indexOf("esCreated\":") > 0) {
					String tmpPheight = sentance[j].substring(sentance[j].indexOf("esCreated\":")+"esCreated\":".length());
					big_chances_created = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
				} else {
					big_chances_created = 0;
				}
				
	            int assists = 0;
	            if(sentance[j].indexOf(",\"assists\":") > 0 ){
	               String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"assists\":")+",\"assists\":".length());
	               assists = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	            } else {
	            	assists = 0;
	            }
				
	            int acc_passes = 0;
	            if(sentance[j].indexOf(",\"accuratePasses\":") > 0 ){
	               String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"accuratePasses\":")+",\"accuratePasses\":".length());
	               acc_passes = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	            } else {
	            	acc_passes = 0;
	            }
		                 
	            int inaccurate_passs = 0;
	            if(sentance[j].indexOf(",\"inaccuratePasses\":") > 0 ){
	               String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"inaccuratePasses\":")+",\"inaccuratePasses\":".length());
	               inaccurate_passs = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	            } else {
	            	inaccurate_passs = 0;
	            }
		        
	            int total_passes = 0;
	            if(sentance[j].indexOf(",\"totalPasses\":") > 0 ){
	               String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"totalPasses\":")+",\"totalPasses\":".length());
	               total_passes = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	            } else {
	            	total_passes = 0;
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
				System.out.print("빅찬스 메이크:"+big_chances_created +" / ");			           
				System.out.print(assists+" / ");			           
				System.out.print(acc_passes+" / ");			           
				System.out.print(inaccurate_passs+" / ");		
				System.out.print(total_passes+" / ");
				System.out.print(pid_sofa+" / ");			           
				System.out.print(tid_sofa+" / ");	  
				System.out.println();
				
				
				SofaPassingDAO sdao = new SofaPassingDAO();
				sdao.firstFive(big_chances_created, assists, acc_passes, inaccurate_passs, total_passes, psid);
				
					      
				}
			System.out.println(Integer.parseInt(pageCheck1)+"of"+Integer.parseInt(pageCheck2));
			if (Integer.parseInt(pageCheck1) == Integer.parseInt(pageCheck2)) 
				break;
			}
				
				
		//두번째 스텟들
		for(int i = 0; i < 10; i++) {
				
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+League+"/season/"+Season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=accuratePassesPercentage%2CaccurateOwnHalfPasses%2CaccurateOppositionHalfPasses%2CaccurateFinalThirdPasses%2CkeyPasses&filters=position.in.G~D~M~F")
			.method(Connection.Method.GET)
			.ignoreContentType(true)
			.execute();
			Document document = response.parse();
			          
			String html = document.html();
			String text = document.text();   
			        
			String pageCheck1 = null;
			String pageCheck2 = null;
			        
			String[] sentance = text.split("\"accuratePasse");
	        
			for(int j=1; j<sentance.length; j++) {
			    
		
			double accurate_passes_per = 0;
			if(sentance[j].indexOf("Percentage\":") > 0) {
				String tmpPheight = sentance[j].substring(sentance[j].indexOf("Percentage\":")+"Percentage\":".length());
				accurate_passes_per = Double.parseDouble(tmpPheight.substring(0, tmpPheight.indexOf(",")));
			} else {
				accurate_passes_per = 0;
			}
				
//	        int accurateOwnHalfPasses = 0;
//	        if(sentance[j].indexOf(",\"accurateOwnHalfPasses\":") > 0 ){
//	           String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"accurateOwnHalfPasses\":")+",\"accurateOwnHalfPasses\":".length());
//	           accurateOwnHalfPasses = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
//	        } else {
//	        	accurateOwnHalfPasses = 0;
//            }
//			
//	        
//	        int accurateOppositionHalfPasses = 0;
//	        if(sentance[j].indexOf(",\"accurateOppositionHalfPasses\":") > 0 ){
//	           String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"accurateOppositionHalfPasses\":")+",\"accurateOppositionHalfPasses\":".length());
//	           accurateOppositionHalfPasses = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
//	        } else {
//	        	accurateOppositionHalfPasses = 0;
//	        }
//		    
//	        
//	        double accurateFinalThirdPasses = 0;
//	        if(sentance[j].indexOf(",\"accurateFinalThirdPasses\":") > 0 ){
//	           String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"accurateFinalThirdPasses\":")+",\"accurateFinalThirdPasses\":".length());
//	           accurateFinalThirdPasses = Double.parseDouble(tmpPheight.substring(0, tmpPheight.indexOf(",")));
//	        } else {
//	        	accurateFinalThirdPasses = 0;
//	        }
	        
	        
	       int key_passes = 0;
	       if(sentance[j].indexOf(",\"keyPasses\":") > 0 ){
	           String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"keyPasses\":")+",\"keyPasses\":".length());
	           key_passes = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	       } else {
	    	   key_passes = 0;
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
			System.out.print("어퀄레이트 패스 퍼:"+accurate_passes_per +" / ");			           
			System.out.print(key_passes+" / ");			           
			System.out.print(pid_sofa+" / ");			           
			System.out.print(tid_sofa+" / ");	  
			System.out.println();
			
			
			SofaPassingDAO sdao = new SofaPassingDAO();
			sdao.secondTwo(accurate_passes_per, key_passes, psid);
				
					      
			}
			System.out.println(Integer.parseInt(pageCheck1)+"of"+Integer.parseInt(pageCheck2));
			if (Integer.parseInt(pageCheck1) == Integer.parseInt(pageCheck2)) 
				break;
			}
		
		//세번째 스텟들
		for(int i = 0; i < 10; i++) {
				
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+League+"/season/"+Season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=accurateCrosses%2CaccurateCrossesPercentage%2CaccurateLongBalls%2CaccurateLongBallsPercentage%2CpassToAssist&filters=position.in.G~D~M~F")
			.method(Connection.Method.GET)
			.ignoreContentType(true)
			.execute();
			Document document = response.parse();
			          
			String html = document.html();
			String text = document.text();   
			        
			String pageCheck1 = null;
			String pageCheck2 = null;
			        
			String[] sentance = text.split("\\{\"accurat");
	        
			for(int j=1; j<sentance.length; j++) {
			    
		
			int accurate_crosses = 0;
			if(sentance[j].indexOf("Crosses\":") > 0) {
				String tmpPheight = sentance[j].substring(sentance[j].indexOf("Crosses\":")+"Crosses\":".length());
				accurate_crosses = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
			} else {
				accurate_crosses = 0;
			}
				
	        double accurate_crosses_per = 0;
	        if(sentance[j].indexOf(",\"accurateCrossesPercentage\":") > 0 ){
	           String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"accurateCrossesPercentage\":")+",\"accurateCrossesPercentage\":".length());
	           accurate_crosses_per = Double.parseDouble(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	        } else {
	        	accurate_crosses_per = 0;
            }
			
	        
	        int acc_long_ball = 0;
	        if(sentance[j].indexOf(",\"accurateLongBalls\":") > 0 ){
	           String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"accurateLongBalls\":")+",\"accurateLongBalls\":".length());
	           acc_long_ball = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	        } else {
	        	acc_long_ball = 0;
	        }
		    
	        
	        double accurate_long_balls_per = 0;
	        if(sentance[j].indexOf(",\"accurateLongBallsPercentage\":") > 0 ){
	           String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"accurateLongBallsPercentage\":")+",\"accurateLongBallsPercentage\":".length());
	           accurate_long_balls_per = Double.parseDouble(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	        } else {
	        	accurate_long_balls_per = 0;
	        }
	        
	        
	       int passes_to_assist = 0;
	       if(sentance[j].indexOf(",\"passToAssist\":") > 0 ){
	           String tmpPheight = sentance[j].substring(sentance[j].indexOf(",\"passToAssist\":")+",\"passToAssist\":".length());
	           passes_to_assist = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	       } else {
	    	   passes_to_assist = 0;
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
			System.out.print("어쿼레잍 크로스 : "+accurate_crosses +" / ");			           
			System.out.print(accurate_crosses_per+" / ");			           
			System.out.print(acc_long_ball+" / ");			           
			System.out.print(accurate_long_balls_per+" / ");		
			System.out.print(passes_to_assist+" / ");
			System.out.print(pid_sofa+" / ");			           
			System.out.print(tid_sofa+" / ");	  
			System.out.println();
			
			
			SofaPassingDAO sdao = new SofaPassingDAO();
			sdao.thirdFive(accurate_crosses, accurate_crosses_per, acc_long_ball, accurate_long_balls_per, passes_to_assist, psid);
			
					      
			}
			System.out.println(Integer.parseInt(pageCheck1)+"of"+Integer.parseInt(pageCheck2));
			if (Integer.parseInt(pageCheck1) == Integer.parseInt(pageCheck2)) 
				break;
			}
			
		
	}
}