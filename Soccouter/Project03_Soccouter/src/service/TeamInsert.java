package service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import backup.InfoLeagueStat;
import dto.dtoSquad_player;

public class Squad2 implements Service{
	static InfoLeagueStat ils = new InfoLeagueStat();
	static ArrayList<dtoSquad_player> squadArray = new ArrayList<dtoSquad_player>();
	
	@Override
	public void start(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String league = request.getParameter("league");
		String team = request.getParameter("team");
		int tid_daum = Integer.parseInt(request.getParameter("tid_daum"));
		
		Connection.Response response2;
		Document document = null;
		try {
			//response2 = Jsoup.connect("https://sports.daum.net/team/"+league+"/"+tid_daum+"/squad")
			response2 = Jsoup.connect("https://sports.daum.net/prx/hermes/api/person/list.ison?leagueCode="+league+"&teamId="+tid_daum+"")
					.method(Connection.Method.GET)
					.ignoreContentType(true)
					.execute();
			document = response2.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String text = document.text();
		
		ArrayList<dtoSquad_player> squadArray = new ArrayList<dtoSquad_player>();
		
		String[] sentance = text.split("\\{\"personId");
		
		int pid_daum = 0;
		
		for(int i=1; i<sentance.length; i++) {				
			
			// 플레이어 아이디 (sofa와 통용되는)
			int pid_sofa = 0;
            if(sentance[i].indexOf("cpPersonId\":\"") > 0 ){
            	String tmpPheight = sentance[i].substring(sentance[i].indexOf("cpPersonId\":\"")+"cpPersonId\":\"".length());
            	pid_sofa = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf("\",")));
            } else {
            	pid_sofa = 0;
            }
            
            // 팀 아이디 (sofa와 통용되는)
            int tid_sofa = 0;
            if(sentance[i].indexOf("cpTeamId\":\"") > 0 ){
               String tmpPheight = sentance[i].substring(sentance[i].indexOf("cpTeamId\":\"")+"cpTeamId\":\"".length());
               tid_sofa = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf("\",")));
            } else {
            	tid_sofa = 0;
            }	
			
            // 플레이어 아이디 (daum)
			if(sentance[i].indexOf(":") > 0) {
				String tmpPheight = sentance[i].substring(sentance[i].indexOf(":")+":".length());
				pid_daum = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
			} else {
				pid_daum = 0;
			}
			
            String[] temp = sentance[i].split("nameMain");
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
            if(sentance[i].indexOf("backNumber\":\"") > 0 ){
               String tmpPheight = sentance[i].substring(sentance[i].indexOf("backNumber\":\"")+"backNumber\":\"".length());
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
            String[] http = sentance[i].split("imageUrl\":");
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
            
            String psid_daum = pid_daum + "/" + tid_daum;
            
            
            Document document3 = null;
            Connection.Response response3 = null;
			try {
				response3 = Jsoup.connect("https://sports.daum.net/prx/hermes/api/team/rank.json?leagueCode="+league+"&seasonKey=20212022&page=1&pageSize=100"+"")
						.method(Connection.Method.GET)
						.ignoreContentType(true)
						.execute();
			} catch (IOException e1) { e1.printStackTrace(); }
			try { 
				document3 = response3.parse();
			} catch (IOException e) { 
				e.printStackTrace(); 
				}
			          
			//String html = document3.html();
			String text2 = document3.text();   
			
			String[] sentance3 = text2.split("\"teamId");
			
			//팀 아이디가 속한 종합 순위 배열 찾기
			int k = 0;
			for(int j = 1; j < sentance3.length; j++) {     
	            if(sentance3[j].indexOf("{\"rank\":") > 0 ){
	               String tmpPheight = sentance3[j].substring(sentance3[j].indexOf("{\"rank\":")+"{\"rank\":".length());
	               k = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
	            } else {
	            	k = 0;
	            }
			}
			System.out.println("k : " + k);
			// 순위
			int ranking = k;
			     	   
            // 경기수
            int total_game = 0;       
            if(sentance3[k].indexOf("\"game\":") > 0 ){
               String tmpPheight = sentance3[k].substring(sentance3[k].indexOf("\"game\":")+"\"game\":".length());
               total_game = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
            } else {
            	total_game = 0;
            }
            
            // 이긴 경기수
            int win = 0;       
            if(sentance3[k].indexOf("win\":") > 0 ){
               String tmpPheight = sentance3[k].substring(sentance3[k].indexOf("win\":")+"win\":".length());
               win = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
            } else {
            	win = 0;
            }
            
            // 비긴 경기수
            int draw = 0;       
            if(sentance3[k].indexOf("draw\":") > 0 ){
               String tmpPheight = sentance3[k].substring(sentance3[k].indexOf("draw\":")+"draw\":".length());
               draw = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
            } else {
            	draw = 0;
            }
            
            // 진 경기수
            int loss = 0;       
            if(sentance3[k].indexOf("loss\":") > 0 ){
               String tmpPheight = sentance3[k].substring(sentance3[k].indexOf("loss\":")+"loss\":".length());
               loss = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
            } else {
            	loss = 0;
            }
            
            // 득점
            int gf = 0;       
            if(sentance3[k].indexOf("gf\":") > 0 ){
               String tmpPheight = sentance3[k].substring(sentance3[k].indexOf("gf\":")+"gf\":".length());
               gf = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
            } else {
            	gf = 0;
            }
            
            // 실점
            int ga = 0;       
            if(sentance3[k].indexOf("ga\":") > 0 ){
               String tmpPheight = sentance3[k].substring(sentance3[k].indexOf("ga\":")+"ga\":".length());
               ga = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
            } else {
            	ga = 0;
            }
            
            // 득실차
            int gd = 0;       
            if(sentance3[k].indexOf("gd\":") > 0 ){
               String tmpPheight = sentance3[k].substring(sentance3[k].indexOf("gd\":")+"gd\":".length());
               gd = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
            } else {
            	gd = 0;
            }
            
            // 승점
            int pts = 0;       
            if(sentance3[k].indexOf("\"pts\":") > 0 ){
               String tmpPheight = sentance3[k].substring(sentance3[k].indexOf("\"pts\":")+"\"pts\":".length());
               pts = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf("},")));
            } else {
            	pts = 0;
            }		
    		
            //짧은 팀 한금령
            String team_ko_short = null;       
            if(sentance[k].indexOf("shortNameKo\":\"") > 0 ){
               String tmpPheight = sentance[k].substring(sentance[k].indexOf("shortNameKo\":\"")+"shortNameKo\":\"".length());
               team_ko_short = tmpPheight.substring(0, tmpPheight.indexOf("\""));
            } else {
            	team_ko_short = null;
            }
            
            System.out.println(league+"/"+team+"/"+pid_sofa+"/"+tid_sofa+"/"+pid_daum+"/"+pname_ko+"/"+back_num+"/"+position_eng+"/"+position_ko+"/"+
                    p_img_url+"/"+no_img_url+"/"+team_img_url+"/"+total_game+"/"+win+"/"+draw+"/"+loss+"/"+gf+"/"+ga+"/"+gd+"/"+pts);
            
            dtoSquad_player dto = new dtoSquad_player(psid_daum, pid_sofa, tid_sofa, pid_daum, tid_daum, pname_ko, back_num,
            		position_eng, position_ko, p_img_url, no_img_url, team_img_url, ranking, win, draw, loss, pts, gf, ga, total_game, team_ko_short);
            squadArray.add(dto);    
		}	
		
			
		request.setAttribute("league", league);
		request.setAttribute("team", team);
		request.setAttribute("tid_daum", tid_daum);
		request.setAttribute("Squad", squadArray);
	}
	
}
