package service;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class TeamRankInsert {

	public static void main(String[] args) throws IOException {

		String[] leagueCode = {"epl", "primera", "bundesliga", "seriea", "ligue1"};
			
		for(int i=0; i < leagueCode.length; i++) {
				
			int[] seasonKey = {20102011, 20112012, 20122013, 20132014, 20142015, 20152016, 20162017, 20172018, 20182019, 20192020, 20202021, 20212022};
			
			for(int j=0; j<seasonKey.length; j++) {
				
				Connection.Response response = Jsoup.connect("https://sports.daum.net/prx/hermes/api/team/rank.json?leagueCode="+leagueCode[i]+"&seasonKey="+seasonKey[j]+"&page=1&pageSize=100"+"")
				.method(Connection.Method.GET)
				.ignoreContentType(true)
				.execute();
				Document document = response.parse();
				          
				String html = document.html();
				String text = document.text();   
				
				String[] sentance = text.split("\"teamId");
				        
				for(int k=1; k<sentance.length; k++) {				
					
					// 순위
					int ranking = k;
					
					// 팀 아이디 (sofa와 통용되는)
		            int tid_sofa = 0;
		            if(sentance[k].indexOf("cpTeamId\":\"") > 0 ){
		               String tmpPheight = sentance[k].substring(sentance[k].indexOf("cpTeamId\":\"")+"cpTeamId\":\"".length());
		               tid_sofa = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf("\",")));
		            } else {
		            	tid_sofa = 0;
		            }	
					
		            	   
		            // 경기수
		            int game = 0;       
		            if(sentance[k].indexOf("\"game\":") > 0 ){
		               String tmpPheight = sentance[k].substring(sentance[k].indexOf("\"game\":")+"\"game\":".length());
		               game = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	game = 0;
		            }
		            
		            // 이긴 경기수
		            int win = 0;       
		            if(sentance[k].indexOf("win\":") > 0 ){
		               String tmpPheight = sentance[k].substring(sentance[k].indexOf("win\":")+"win\":".length());
		               win = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	win = 0;
		            }
		            
		            // 비긴 경기수
		            int draw = 0;       
		            if(sentance[k].indexOf("draw\":") > 0 ){
		               String tmpPheight = sentance[k].substring(sentance[k].indexOf("draw\":")+"draw\":".length());
		               draw = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	draw = 0;
		            }
		            
		            // 진 경기수
		            int loss = 0;       
		            if(sentance[k].indexOf("loss\":") > 0 ){
		               String tmpPheight = sentance[k].substring(sentance[k].indexOf("loss\":")+"loss\":".length());
		               loss = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	loss = 0;
		            }
		            
		            // 득점
		            int gf = 0;       
		            if(sentance[k].indexOf("gf\":") > 0 ){
		               String tmpPheight = sentance[k].substring(sentance[k].indexOf("gf\":")+"gf\":".length());
		               gf = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	gf = 0;
		            }
		            
		            // 실점
		            int ga = 0;       
		            if(sentance[k].indexOf("ga\":") > 0 ){
		               String tmpPheight = sentance[k].substring(sentance[k].indexOf("ga\":")+"ga\":".length());
		               ga = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	ga = 0;
		            }
		            
		            // 득실차
		            int gd = 0;       
		            if(sentance[k].indexOf("gd\":") > 0 ){
		               String tmpPheight = sentance[k].substring(sentance[k].indexOf("gd\":")+"gd\":".length());
		               gd = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf(",")));
		            } else {
		            	gd = 0;
		            }
		            
		            // 승점
		            int pts = 0;       
		            if(sentance[k].indexOf("\"pts\":") > 0 ){
		               String tmpPheight = sentance[k].substring(sentance[k].indexOf("\"pts\":")+"\"pts\":".length());
		               pts = Integer.parseInt(tmpPheight.substring(0, tmpPheight.indexOf("}")));
		            } else {
		            	pts = 0;
		            }
	
					System.out.print("리그코드 : " + leagueCode[i] + " / ");
					System.out.print("팀 아이디 : "+tid_sofa + " / ");
					System.out.print("순위 : "+ ranking + " / ");		
					System.out.print("경기 : "+ game +" / ");
					System.out.print("승 : "+ win +" / ");
					System.out.print("무 : "+ draw +" / ");
					System.out.print("패 : "+ loss +" / ");
					System.out.print("득점 : "+ gf +" / ");
					System.out.print("실점 : "+ ga +" / ");
					System.out.print("득실차 : "+ gd +" / ");
					System.out.println("승점 : "+ pts +" / ");
					
					DAO dao = new DAO();
					dao.insertTeamRank(ranking, tid_sofa, game, win, draw, loss, gf, ga, gd, pts, leagueCode[i], seasonKey[j]);
					}
				}
			}
		}
	
	
}