package service;

//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class SquadInesrt {

	public static void main(String[] args) throws IOException {
		
		String[] leagueCode = {"epl", "bundesliga", "primera", "seriea", "ligue1"};
		// 리그 코드 순서대로, 팀 아이디는 '가나다' 순서대로
		int[][] teamId = { 
				{283, 1321, 268, 253, 266, 258, 246, 263, 277, 313, 271, 247, 250, 252, 292, 256, 254, 251, 274, 249},
				{1122, 35957, 600950, 1127, 1145, 516328, 1125, 1146, 1129, 1142, 1143, 121583, 1137, 1123, 1132, 1141, 1124, 1135},
				{1059, 1075, 1069, 35956, 1080, 35955, 1076, 1062, 1061, 1063, 1067, 1068, 1073, 1071, 1058, 1057, 222283, 1070, 1078, 1087},
				{1091, 1088, 1089, 1099, 1100, 1094, 1096, 227478, 601207, 1102, 601208, 601015, 1105, 227477, 1110, 1098, 1093, 1092, 1101, 1097},
				{1155, 1580, 1578, 1602, 1587, 1163, 1598, 1591, 1581, 1251, 1588, 121584, 1594, 1792, 424992, 1218, 1260, 601206, 1599, 1191}
		};
		
		for(int i=0; i < leagueCode.length; i++) {
			for(int j=0; j < teamId[i].length; j++) {
			
				Connection.Response response = Jsoup.connect("https://sports.daum.net/prx/hermes/api/person/list.ison?leagueCode="+leagueCode[i]+"&teamId="+teamId[i][j]+"")
				.method(Connection.Method.GET)
				.ignoreContentType(true)
				.execute();
				Document document = response.parse();
				          
				String text = document.text();   
				
				String[] sentance = text.split("\\{\"personId");
				        
				for(int x=1; x<sentance.length; x++) {				
					
					// 플레이어 아이디 (sofa와 통용되는)
					int pid_sofa = 0;
		            if(sentance[x].indexOf("cpPersonId\":\"") > 0 ){
		            	String tmpPheight = sentance[x].substring(sentance[x].indexOf("cpPersonId\":\"")+"cpPersonId\":\"".length());
		            	pid_sofa = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf("\",")));
		            } else {
		            	pid_sofa = 0;
		            }
		            
		            // 팀 아이디 (sofa와 통용되는)
		            int tid_sofa = 0;
		            if(sentance[x].indexOf("cpTeamId\":\"") > 0 ){
		               String tmpPheight = sentance[x].substring(sentance[x].indexOf("cpTeamId\":\"")+"cpTeamId\":\"".length());
		               tid_sofa = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf("\",")));
		            } else {
		            	tid_sofa = 0;
		            }	
					
		            // 플레이어 아이디 (daum)
					int pid_daum = 0;
					if(sentance[x].indexOf(":") > 0) {
						String tmpPheight = sentance[x].substring(sentance[x].indexOf(":")+":".length());
						pid_daum = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
					} else {
						pid_daum = 0;
					}
							
		            String[] temp = sentance[x].split("nameMain");
		            // 팀 한글명
		            String team_ko = null;       
		            if(temp[2].indexOf(":\"") > 0 ){
		               String tmpPheight = temp[2].substring(temp[2].indexOf(":\"")+":\"".length());
		               team_ko = tmpPheight.substring(0, tmpPheight.indexOf("\""));
		            } else {
		            	team_ko = null;
		            }
		            	   
		            // 플레이어 한글명
		            String pname_ko = null;  
		            if(temp[1].indexOf(":") > 0 ){
		               String tmpPheight = temp[1].substring(temp[1].indexOf(":\"")+":\"".length());
		               pname_ko = tmpPheight.substring(0, tmpPheight.indexOf("\""));
		            } else {
		            	pname_ko = null;
		            }
		            
		            // 등번호
		            String back_number = null;
		            if(sentance[x].indexOf("backNumber\":\"") > 0 ){
		               String tmpPheight = sentance[x].substring(sentance[x].indexOf("backNumber\":\"")+"backNumber\":\"".length());
		               if (tmpPheight.indexOf("\",") == 0) {
		            	   back_number = null;
		               } else {
		            	   back_number = tmpPheight.substring(0, tmpPheight.indexOf("\","));
		               }
		            } else {
		            	back_number = null;
		            }
			        String back_num = "No."+back_number;         
		            
		            
		            // 포지션(영문)
		            String position_eng = null;
		            if(temp[3].indexOf(":\"") > 0 ){
		               String tmpPheight = temp[3].substring(temp[3].indexOf(":\"")+":\"".length());
		               position_eng = tmpPheight.substring(0, tmpPheight.indexOf("\""));
		            } else {
		            	position_eng = null;
		            }
		            
		            // 포지션(한글)
		            String position_ko = null;
		            String[] temp2 = temp[3].split("nameKo\"");
		            if(temp2[1].indexOf("\"") > 0 ){
		            	String tmpPheight = temp2[1].substring(temp2[1].indexOf("\"")+"\"".length());
		            	position_ko = tmpPheight.substring(0, temp2[1].indexOf("\",")-2);
					} else {
						position_ko = null;
					}
		            
		            //선수 이미지 url
		            String[] http = sentance[x].split("imageUrl\":");
		            String p_img_url = null;
		            if(http[2].indexOf("http") > 0 ){
		               String tmpPheight = http[2].substring(http[2].indexOf("\"")+"\"".length());
		               p_img_url = tmpPheight.substring(0, tmpPheight.indexOf(".jpg\"")+4);
		            } else {
		            	p_img_url = null;
		            }
		            
		            //없는 선수 이미지 url
		            String no_img_url = "https://t1.daumcdn.net/media/img-section/sports13/player/noimage/square_m.png";	
		            
		            //팀 이미지 url
		            String team_img_url = null;
		            if(http[1].indexOf("http") > 0 ){
		               String tmpPheight = http[1].substring(http[1].indexOf("\"")+"\"".length());
		               team_img_url = tmpPheight.substring(0, tmpPheight.indexOf(".png\"")+4);
		            } else {
		            	team_img_url = null;
		            }
		        	
					int tid_daum = teamId[i][j];
					
					
					String psid_daum = pid_daum + "/" + tid_daum;
					
					
					//int[] seasonKey = {20102011, 20112012, 20122013, 20132014, 20142015, 20152016, 20162017, 20172018, 20182019, 20192020, 20202021, 20212022};
					int seasonKey = 20212022;
					
					Connection.Response response2 = Jsoup.connect("https://sports.daum.net/prx/hermes/api/team/rank.json?leagueCode="+leagueCode[i]+"&seasonKey="+seasonKey+"&page=1&pageSize=100"+"")
							.method(Connection.Method.GET)
							.ignoreContentType(true)
							.execute();
							Document document3 = response2.parse();
							          
							String html = document3.html();
							String text2 = document3.text();   
							
							String[] sentance2 = text2.split("\"teamId");

						
					// 순위
					int ranking = j+1;
					     	   
		            // 경기수
		            int total_game = 0;       
		            if(sentance2[j+1].indexOf("\"game\":") > 0 ){
		               String tmpPheight = sentance2[j+1].substring(sentance2[j+1].indexOf("\"game\":")+"\"game\":".length());
		               total_game = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	total_game = 0;
		            }
		            
		            // 이긴 경기수
		            int win = 0;       
		            if(sentance2[j+1].indexOf("win\":") > 0 ){
		               String tmpPheight = sentance2[j+1].substring(sentance2[j+1].indexOf("win\":")+"win\":".length());
		               win = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	win = 0;
		            }
		            
		            // 비긴 경기수
		            int draw = 0;       
		            if(sentance2[j+1].indexOf("draw\":") > 0 ){
		               String tmpPheight = sentance2[j+1].substring(sentance2[j+1].indexOf("draw\":")+"draw\":".length());
		               draw = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	draw = 0;
		            }
		            
		            // 진 경기수
		            int loss = 0;       
		            if(sentance2[j+1].indexOf("loss\":") > 0 ){
		               String tmpPheight = sentance2[j+1].substring(sentance2[j+1].indexOf("loss\":")+"loss\":".length());
		               loss = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	loss = 0;
		            }
		            
		            // 득점
		            int gf = 0;       
		            if(sentance2[j+1].indexOf("gf\":") > 0 ){
		               String tmpPheight = sentance2[j+1].substring(sentance2[j+1].indexOf("gf\":")+"gf\":".length());
		               gf = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	gf = 0;
		            }
		            
		            // 실점
		            int ga = 0;       
		            if(sentance2[j+1].indexOf("ga\":") > 0 ){
		               String tmpPheight = sentance2[j+1].substring(sentance2[j+1].indexOf("ga\":")+"ga\":".length());
		               ga = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	ga = 0;
		            }
		            
		            // 득실차
		            int gd = 0;       
		            if(sentance2[j+1].indexOf("gd\":") > 0 ){
		               String tmpPheight = sentance2[j+1].substring(sentance2[j+1].indexOf("gd\":")+"gd\":".length());
		               gd = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	gd = 0;
		            }
		            
		            // 승점
		            int pts = 0;       
		            if(sentance2[j+1].indexOf("\"pts\":") > 0 ){
		               String tmpPheight = sentance2[j+1].substring(sentance2[j+1].indexOf("\"pts\":")+"\"pts\":".length());
		               pts = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf("}")));
		            } else {
		            	pts = 0;
		            }
					
		            System.out.print(team_ko+"팀 " + " / ");			    	   
					System.out.print(pname_ko+"선수"+" / " );	    	  			    	   
					System.out.print("pid_sofa : "+pid_sofa +" / ");			           
					System.out.print("tid_sofa : "+tid_sofa+" / ");			           
					System.out.print("pid_daum : "+ pid_daum +" / ");			           
					System.out.print("tid_daum : "+ tid_daum +" / ");		
					System.out.print("등번호 : "+ back_num +" / ");
					System.out.println("포지션 : "+ position_eng + " " +position_ko + " / ");		
		                      
		            DAO dao = new DAO();
					dao.insertSquad(psid_daum, pid_sofa, tid_sofa, pid_daum, tid_daum, pname_ko, back_num,
		            		position_eng, position_ko, p_img_url, no_img_url, team_img_url, ranking, win, draw, loss, pts, gf, ga, total_game);
				
					
				}
			}
		}
	}
	
}