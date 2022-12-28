package backup;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class LoadPlayerStat_old {

	public static void main(String[] args) throws IOException {
		System.out.println("::::::::::::::::::::::선수 정보 갱신 시작 (ATTACK)::::::::::::::::::::::");
		DAO dao = new DAO();
		
		String league = "17";																// 리그 코드
		String season = "37036";														// 시즌 코드
		
		// 메모리 set 준비 =========================================================
		String[] pid_sofa1 = new String[1000];										//	0 플레이어 ID
		String[] pid_sofa2 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa3 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa4 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa5 = new String[1000];										//	0 플레이어 ID		
		
		String[] rating = new String[1000]; 											//	1 평점
		// Attack Set ===========================
		String[] golas = new String [1000];											//	2 	득점 
		String[] big_chances_missed = new String [1000];						//	3 	실패한 결정적 기회
		String[] succ_dribbles  = new String [1000];								//	4 	드리블 성공
		String[] successful_dribbles_per = new String [1000];	 				//	5 	드리블 성공률	
		// Set 1 end ----------------------------------------------
		String[] total_shots = new String [1000];									//	6 	슈팅 횟수
		String[] shots_on_target = new String [1000];								//	7 	유효 슈팅
		String[] shots_off_target = new String [1000];								//	8 	무효 슈팅
		String[] goal_conversion_per = new String [1000];						//	9 	득점 전환율(득점/전체슛)
		String[] penalties_taken = new String [1000];								//	10	패널티 시도
		// Set 2 end ----------------------------------------------
		String[] penalty_golas = new String [1000];								//	11	패널티 득점
		String[] penalty_won = new String [1000];									//	12	승부차기 성공
		String[] shots_from_set_piece = new String [1000];						//	13	세트피스 슈팅
		String[] free_kick_goals = new String [1000];								//	14	프리킥 득점
		String[] goals_from_inside_the_box = new String [1000];				//	15	박스 안 득점
		// Set 3 end ----------------------------------------------
		String[] goals_from_outside_the_box = new String [1000];				//	16	박스 밖 득점
		String[] headed_goals = new String [1000];								//	17	헤더 득점
		String[] left_foot_goals = new String [1000];								//	18	왼발 득점
		String[] right_foot_goals = new String [1000];								//	19	오른발 득점
		String[] hit_woodwork = new String [1000];								//	20	골대 강타
		// Set 4 end ----------------------------------------------
		String[] offsides = new String [1000];										//	21	오프사이드
		String[] penalty_conversion_per = new String [1000];					//	22	패널티 득점 확률
		String[] set_piece_conversion_per = new String [1000];					//	23	세트피스 득점 확률
		
		
		//	Set 1 메모리 등록 Start =====================================================
		//	pid_sofa, Rating, golas, big_chances_missed, succ_dribbles
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=goals%2CbigChancesMissed%2CsuccessfulDribbles%2CsuccessfulDribblesPercentage%2Crating&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String html = document.html();
			String text = document.text();		
			text = text.replace("\n", "");
			
			String tmp = "";
			String tmpAllPlayer = text.substring(text.indexOf("{\"results\":[{\"")+"{\"results\":[{\"".length());
			String[] tmpAllPlayerList = tmpAllPlayer.split("\"\\}\\}\\},\\{\"");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {
				for (int j = 0; j < tmpAllPlayerList.length ; j++) {				
					// 0	플레이어 ID (SOFA)
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"id\":")+"\"id\":".length());
					pid_sofa1[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
					//System.out.print(pid_sofa1[j+(i*100)] + " | ");
					
					// 1	평점
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"rating\":")+"\"rating\":".length());
					rating[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(rating[j+(i*100)] + " | ");
					
					// 2	득점
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("goals\":")+"goals\":".length());
					golas[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(golas[j+(i*100)] + " | ");	
					
					//	3 	실패한 결정적 기회
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf(",\"bigChancesMissed\":")+",\"bigChancesMissed\":".length());
					big_chances_missed[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(big_chances_missed[j+(i*100)] + " | ");	
					
					//	4 	드리블 성공
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf(",\"successfulDribbles\":")+",\"successfulDribbles\":".length());
					succ_dribbles[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(succ_dribbles[j+(i*100)] + " | ");	
					
					//	5 	드리블 성공률	
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("successfulDribblesPercentage\":")+"successfulDribblesPercentage\":".length());
					successful_dribbles_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.println(successful_dribbles_per[j+(i*100)]);			

				}
			}
		}
		
		

		
		//	Set 2 메모리 등록 Start =====================================================
		//	pid_sofa, total_shots, shots_on_target, shots_off_target, goal_conversion_per, penalties_taken
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=totalShots%2CshotsOnTarget%2CshotsOffTarget%2CgoalConversionPercentage%2CpenaltiesTaken&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String html = document.html();
			String text = document.text();		
			text = text.replace("\n", "");
			
			String tmp = "";
			String tmpAllPlayer = text.substring(text.indexOf("{\"results\":[{\"")+"{\"results\":[{\"".length());
			String[] tmpAllPlayerList = tmpAllPlayer.split("\"\\}\\}\\},\\{\"");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {

				for (int j = 0; j < tmpAllPlayerList.length ; j++) {
					// 0	플레이어 ID (SOFA)
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"id\":")+"\"id\":".length());
					pid_sofa2[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
					//System.out.print(pid_sofa2[j+(i*100)] + " | ");
					
					// 6 	슈팅 횟수
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("totalShots\":")+"totalShots\":".length());
					total_shots[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(total_shots[j+(i*100)] + " | ");
					
					// 7 	유효 슈팅
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"shotsOnTarget\":")+"\"shotsOnTarget\":".length());
					shots_on_target[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(shots_on_target[j+(i*100)] + " | ");	
					
					//	8 	무효 슈팅
					tmp = html.substring(html.indexOf("\"shotsOffTarget\":")+"\"shotsOffTarget\":".length());
					shots_off_target[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(shots_off_target[j+(i*100)] + " | ");	
		
					//	9 	득점 전환율(득점/전체슛)
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"goalConversionPercentage\":")+"\"goalConversionPercentage\":".length());
					goal_conversion_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(goal_conversion_per[j+(i*100)] + " | ");	
					
					//	10	패널티 시도
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"penaltiesTaken\":")+"\"penaltiesTaken\":".length());
					penalties_taken[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.println(penalties_taken[j+(i*100)]);			

				}
			}
		}
		

		
		
		
		
		//	Set 3 메모리 등록 Start =====================================================
		//	pid_sofa, penalty_golas, penalty_won, shots_from_set_piece, free_kick_goals, goals_from_inside_the_box
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=penaltyGoals%2CpenaltyWon%2CshotFromSetPiece%2CfreeKickGoal%2CgoalsFromInsideTheBox&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String html = document.html();
			String text = document.text();		
			text = text.replace("\n", "");
			
			String tmp = "";
			String tmpAllPlayer = text.substring(text.indexOf("{\"results\":[{\"")+"{\"results\":[{\"".length());
			String[] tmpAllPlayerList = tmpAllPlayer.split("\"\\}\\}\\},\\{\"");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {
				for (int j = 0; j < tmpAllPlayerList.length ; j++) {
				
					// 0	플레이어 ID (SOFA)
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"id\":")+"\"id\":".length());
					pid_sofa3[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
					//System.out.print(pid_sofa3[j+(i*100)] + " | ");
				
					// 11	패널티 득점
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("penaltyGoals\":")+"penaltyGoals\":".length());
					penalty_golas[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(penalty_golas[j+(i*100)] + " | ");
					
					// 12	승부차기 성공
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"penaltyWon\":")+"\"penaltyWon\":".length());
					penalty_won[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(penalty_won[j+(i*100)] + " | ");	
					
					//	13	세트피스 슈팅
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"shotFromSetPiece\":")+"\"shotFromSetPiece\":".length());
					shots_from_set_piece[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(shots_from_set_piece[j+(i*100)] + " | ");	
		
					//	14	프리킥 득점
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"freeKickGoal\":")+"\"freeKickGoal\":".length());
					free_kick_goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(free_kick_goals[j+(i*100)] + " | ");	
					
					//	15	박스 안 득점
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"goalsFromInsideTheBox\":")+"\"goalsFromInsideTheBox\":".length());
					goals_from_inside_the_box[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.println(goals_from_inside_the_box[j+(i*100)]);			

				}
			}
		}
		


		
		//	Set 4 메모리 등록 Start =====================================================
		//	pid_sofa, goals_from_outside_the_box, headed_goals, left_foot_goals, right_foot_goals, hit_woodwork
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=goalsFromOutsideTheBox%2CheadedGoals%2CleftFootGoals%2CrightFootGoals%2ChitWoodwork&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String html = document.html();
			String text = document.text();		
			text = text.replace("\n", "");
			
			String tmp = "";
			String tmpAllPlayer = text.substring(text.indexOf("{\"results\":[{\"")+"{\"results\":[{\"".length());
			String[] tmpAllPlayerList = tmpAllPlayer.split("\"\\}\\}\\},\\{\"");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {
				for (int j = 0; j < tmpAllPlayerList.length ; j++) {
				
					// 0	플레이어 ID (SOFA)
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"id\":")+"\"id\":".length());
					pid_sofa4[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
					//System.out.print(pid_sofa4[j+(i*100)] + " | ");
					
					// 16	박스 밖 득점
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("goalsFromOutsideTheBox\":")+"goalsFromOutsideTheBox\":".length());
					goals_from_outside_the_box[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(goals_from_outside_the_box[j+(i*100)] + " | ");
					
					// 17	헤더 득점
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"headedGoals\":")+"\"headedGoals\":".length());
					headed_goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(headed_goals[j+(i*100)] + " | ");	
					
					//	18	왼발 득점
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"leftFootGoals\":")+"\"leftFootGoals\":".length());
					left_foot_goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(left_foot_goals[j+(i*100)] + " | ");	
		
					//	19	오른발 득점
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"rightFootGoals\":")+"\"rightFootGoals\":".length());
					right_foot_goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(right_foot_goals[j+(i*100)] + " | ");	
					
					//	20	골대 강타
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"hitWoodwork\":")+"\"hitWoodwork\":".length());
					hit_woodwork[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.println(hit_woodwork[j+(i*100)]);			

				}
			}
		}
		

		
		

		
		//	Set 5 메모리 등록 Start =====================================================
		//	pid_sofa, offsides, penalty_conversion_per, set_piece_conversion_per
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=offsides%2CpenaltyConversion%2CsetPieceConversion&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String html = document.html();
			String text = document.text();		
			text = text.replace("\n", "");
			
			String tmp = "";
			String tmpAllPlayer = text.substring(text.indexOf("{\"results\":[{\"")+"{\"results\":[{\"".length());
			String[] tmpAllPlayerList = tmpAllPlayer.split("\"\\}\\}\\},\\{\"");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {
				
				for (int j = 0; j < tmpAllPlayerList.length ; j++) {
				
					// 0	플레이어 ID (SOFA)
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"id\":")+"\"id\":".length());
					pid_sofa5[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
					//System.out.print(pid_sofa5[j+(i*100)] + " | ");
									
					// 21	오프사이드
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("offsides\":")+"offsides\":".length());
					offsides[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(offsides[j+(i*100)] + " | ");
					
					// 22	패널티 득점 확률
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"penaltyConversion\":")+"\"penaltyConversion\":".length());
					penalty_conversion_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.print(penalty_conversion_per[j+(i*100)] + " | ");	
					
					//	23	세트피스 득점 확률
					tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"setPieceConversion\":")+"\"setPieceConversion\":".length());
					set_piece_conversion_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
					//System.out.println(set_piece_conversion_per[j+(i*100)]);	
		
					
				}
			}
		}		
		
		// DB에 등록 시작
		for (int i = 0; i < 1000 ; i++) {
			if( pid_sofa1[i] == null ) {	break;		}
			
			if ( pid_sofa1[i].equals(pid_sofa2[i]) && pid_sofa1[i].equals(pid_sofa3[i]) &&  pid_sofa1[i].equals(pid_sofa4[i]) &&  pid_sofa1[i].equals(pid_sofa5[i]) ) {
				System.out.print("Cehck! ["+ i +"]번째  ");
			} else {
				System.out.println("ERROR!!!!! ["+ i +"]번째");
				break;
			}
			
			// 타입 변경
			int i_pid_sofa = Integer.parseInt(pid_sofa1[i]);
			double d_rating = Double.parseDouble(rating[i]);
			int i_golas = Integer.parseInt(golas[i]);
			int i_big_chances_missed = Integer.parseInt(big_chances_missed[i]);
			int i_succ_dribbles = Integer.parseInt(succ_dribbles[i]);
			double d_successful_dribbles_per = Double.parseDouble(successful_dribbles_per[i]);
			int i_total_shots = Integer.parseInt(total_shots[i]);
			int i_shots_on_target = Integer.parseInt(shots_on_target[i]);
			int i_shots_off_target = Integer.parseInt(shots_off_target[i]);
			double d_goal_conversion_per = Double.parseDouble(goal_conversion_per[i]);
			int i_penalties_taken = Integer.parseInt(penalties_taken[i]);
			int i_penalty_golas = Integer.parseInt(penalty_golas[i]);
			int i_penalty_won = Integer.parseInt(penalty_won[i]);
			int i_shots_from_set_piece = Integer.parseInt(shots_from_set_piece[i]);
			int i_free_kick_goals = Integer.parseInt(free_kick_goals[i]);
			int i_goals_from_inside_the_box = Integer.parseInt(goals_from_inside_the_box[i]);
			int i_goals_from_outside_the_box = Integer.parseInt(goals_from_outside_the_box[i]);
			int i_headed_goals = Integer.parseInt(headed_goals[i]);
			int i_left_foot_goals = Integer.parseInt(left_foot_goals[i]);
			int i_right_foot_goals = Integer.parseInt(right_foot_goals[i]);
			int i_hit_woodwork = Integer.parseInt(hit_woodwork[i]);
			int i_offsides = Integer.parseInt(offsides[i]);
			double d_penalty_conversion_per = Double.parseDouble(penalty_conversion_per[i]);
			double d_set_piece_conversion_per = Double.parseDouble(set_piece_conversion_per[i]);
			
			// 쿼리 처리
			dao.sofaPlayerAttackStatUpdate(i_pid_sofa, d_rating, i_golas, i_big_chances_missed, i_succ_dribbles, d_successful_dribbles_per, i_total_shots, i_shots_on_target, 
					i_shots_off_target, d_goal_conversion_per, i_penalties_taken, i_penalty_golas, i_penalty_won, i_shots_from_set_piece, i_free_kick_goals, i_goals_from_inside_the_box, 
					i_goals_from_outside_the_box, i_headed_goals, i_left_foot_goals, i_right_foot_goals, i_hit_woodwork, i_offsides, d_penalty_conversion_per, d_set_piece_conversion_per);

		}		
	}
}