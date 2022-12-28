package dataLoad;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jdbc.DAO;

public class LoadPlayerStat {

	public static void main(String[] args) throws IOException {
		System.out.println("::::::::::::::::::::::선수 정보 갱신 시작 (ATTACK)::::::::::::::::::::::");
		DAO dao = new DAO();
		
		String league = "17";																// 리그 코드
		String season = "37036";														// 시즌 코드
		
		// 메모리 set 준비 =========================================================
		String[] tid_sofa = new String[1000];											//	e	팀 ID
		String[] pid_sofa1 = new String[1000];										//	0	플레이어 ID
		String[] pid_sofa2 = new String[1000];										//	0	플레이어 ID		
		String[] pid_sofa3 = new String[1000];										//	0	플레이어 ID		
		String[] pid_sofa4 = new String[1000];										//	0	플레이어 ID		
		String[] pid_sofa5 = new String[1000];										//	0	플레이어 ID
		// Attack set end ----------------------------------------------
		String[] pid_sofa6 = new String[1000];										//	0	플레이어 ID		
		String[] pid_sofa7 = new String[1000];										//	0	플레이어 ID		
		// Defence set end ----------------------------------------------		
		String[] pid_sofa8 = new String[1000];										//	0	플레이어 ID
		String[] pid_sofa9 = new String[1000];										//	0	플레이어 ID
		String[] pid_sofa10 = new String[1000];										//	0	플레이어 ID
		// Pass set end ----------------------------------------------
		String[] pid_sofa11 = new String[1000];										//	0	플레이어 ID
		String[] pid_sofa12 = new String[1000];										//	0	플레이어 ID
		String[] pid_sofa13 = new String[1000];										//	0	플레이어 ID
		// Other set end ----------------------------------------------
		String[] pid_sofa14 = new String[1000];										//	0	플레이어 ID
		String[] pid_sofa15 = new String[1000];										//	0	플레이어 ID
		String[] pid_sofa16 = new String[1000];										//	0	플레이어 ID
		// Goalkeeper set end ----------------------------------------------
		
		
		// Attack Set =========================================================
		// Set 1 ----------------------------------------------
		String[] rating = new String[1000]; 											//	1	평점
		String[] golas = new String [1000];											//	2 	득점 
		String[] big_chances_missed = new String [1000];						//	3 	실패한 결정적 기회
		String[] succ_dribbles  = new String [1000];								//	4 	드리블 성공
		String[] successful_dribbles_per = new String [1000];	 				//	5 	드리블 성공률	
		// Set 2 ----------------------------------------------
		String[] total_shots = new String [1000];									//	6 	슈팅 횟수
		String[] shots_on_target = new String [1000];								//	7 	유효 슈팅
		String[] shots_off_target = new String [1000];								//	8 	무효 슈팅
		String[] goal_conversion_per = new String [1000];						//	9 	득점 전환율(득점/전체슛)
		String[] penalties_taken = new String [1000];								//	10	패널티 시도
		// Set 3 ----------------------------------------------
		String[] penalty_golas = new String [1000];								//	11	패널티 득점
		String[] penalty_won = new String [1000];									//	12	승부차기 성공
		String[] shots_from_set_piece = new String [1000];						//	13	세트피스 슈팅
		String[] free_kick_goals = new String [1000];								//	14	프리킥 득점
		String[] goals_from_inside_the_box = new String [1000];				//	15	박스 안 득점
		// Set 4 ----------------------------------------------
		String[] goals_from_outside_the_box = new String [1000];				//	16	박스 밖 득점
		String[] headed_goals = new String [1000];								//	17	헤더 득점
		String[] left_foot_goals = new String [1000];								//	18	왼발 득점
		String[] right_foot_goals = new String [1000];								//	19	오른발 득점
		String[] hit_woodwork = new String [1000];								//	20	골대 강타
		// Set 5 ----------------------------------------------
		String[] offsides = new String [1000];										//	21	오프사이드
		String[] penalty_conversion_per = new String [1000];					//	22	패널티 득점 확률
		String[] set_piece_conversion_per = new String [1000];					//	23	세트피스 득점 확률
		
		// Defence Set =========================================================
		// Set 6 ----------------------------------------------
		String[] tackles = new String [1000];											//	24	태클 수
		String[] interceptions = new String [1000];									//	25	가로채기
		String[] penalty_committed = new String [1000];							//	26	패널티 제공
		String[] clearances = new String [1000];									//	27	걷어내기
		String[] errors_lead_to_goal = new String [1000];						//	28	실점 허용 실책
		// Set 7 end ----------------------------------------------
		String[] errors_lead_to_shot = new String [1000];						//	29	슈팅 허용 실책
		String[] own_goals = new String [1000];									//	30	자책골
		String[] dribbled_past = new String [1000];								//	31	드리블 허용
		String[] blocked_shots = new String [1000];								//	32	슈팅 블록킹
		String[] clean_sheets = new String [1000];									//	33	무실점 경기
		
		// Pass Set =========================================================
		// Set 8 ----------------------------------------------
		String[] big_chances_created = new String [1000];						//	34	결정적 기회 창출
		String[] assists = new String [1000];											//	35	도움
		String[] acc_passes = new String [1000];									//	36	성공한 패스
		String[] inaccurate_passs = new String [1000];							//	37	실패한 패스
		String[] total_passes = new String [1000];									//	38	전체 패스 수
		// Set 9 ----------------------------------------------
		String[] accurate_passes_per = new String [1000];						//	39	패스 성공률
		String[] key_passes = new String [1000];									//	40	키 패스
		// Set 10 ----------------------------------------------
		String[] accurate_crosses = new String [1000];							//	41	크로스
		String[] accurate_crosses_per = new String [1000];						//	42	크로스 성공률
		String[] acc_long_ball = new String [1000];								//	43	롱 패스
		String[] accurate_long_balls_per = new String [1000];					//	44	롱패스 성공률
		String[] passes_to_assist = new String [1000];								//	45	패스로 달성한 도움

		// Other Set =========================================================
		// Set 11 ----------------------------------------------
		String[] yellow_cards = new String [1000];									//	46	경고
		String[] red_cards = new String [1000];										//	47	퇴장
		String[] ground_duels_won = new String [1000];							//	48	지면 경합 승리
		String[] ground_duels_won_per = new String [1000];					//	49	지면 경합 승률
		String[] aerial_duels_won = new String [1000];							//	50	공중 경합 승리
		// Set 12 ----------------------------------------------
		String[] aerial_duels_won_per = new String [1000];						//	51	공중 경합 승률
		String[] total_duels_won = new String [1000];								//	52	전체 경합 승리
		String[] total_duels_won_per = new String [1000];						//	53	전체 경합 승률
		String[] minutes_played = new String [1000];								//	54	출장 시간(분)
		String[] was_fouled = new String [1000];									//	55	당한 반칙
		// Set 13 ----------------------------------------------
		String[] fouls = new String [1000];											//	56	반칙
		String[] dispossessed = new String [1000];									//	57	비 드리블 상황에서 공격권 상실
		String[] possession_lost = new String [1000];								//	58	드리블 중 공격권 상실(턴오버)
		String[] appearances = new String [1000];									//	59	전체 출전
		String[] started = new String [1000];											//	60	선발 출전

		// Goalkeeper Set =========================================================
		// Set 14 ----------------------------------------------
		String[] saves = new String [1000];											//	61	선방
		String[] penalties_faced = new String [1000];								//	62	진행된 패널티 킥
		String[] penalties_saved = new String [1000];								//	63	퍼널티 킥 선방
		String[] saves_from_inside_box = new String [1000];					//	64	박스 안 선방
		String[] saved_shots_from_outside_the_box = new String [1000];	//	65	박스 밖 선방
		// Set 15 ----------------------------------------------
		String[] goals_conceded_inside_the_box = new String [1000];		//	66	박스 안 실점
		String[] goals_conceded_outside_the_box = new String [1000];		//	67	박스 밖 실점
		String[] punches = new String [1000];										//	68	펀칭
		String[] runs_out = new String [1000];										//	69	전진 수비
		String[] successful_runs_out = new String [1000];						//	70	성공한 전진 수비
		// Set 16 ----------------------------------------------
		String[] high_claims = new String [1000];									//	71	공중볼 처리
		String[] crosses_not_claimed = new String [1000];						//	72	처리못한 크로스
		
		

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
		
		
		
		
		
		
		
		
		
		//	Defence Set 시작 ========================================================= 
		//	Set 6 메모리 등록 Start =====================================================
		//	tackles, Interceptions, penalty_committed, clearances, errors_lead_to_goal
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=tackles%2Cinterceptions%2CpenaltyConceded%2Cclearances%2CerrorLeadToGoal&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
			text = text.replace("\n", "");
			
			String[] sentance = text.split("\\{\"tac");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {
				for (int j = 0; j < sentance.length-1 ; j++) {		
					
					// 24 tackles 태클 수
					if(sentance[j+1].indexOf("les\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("les\":")+"les\":".length());
						tackles[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						tackles[j+(i*100)] = "0";
					}
					//System.out.print("tackles "+tackles[j+(i*100)]+" / ");
					
					
		            // 25 interceptions 가로채기
					if(sentance[j+1].indexOf(",\"interceptions\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"interceptions\":")+",\"interceptions\":".length());
						interceptions[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						interceptions[j+(i*100)] = "0";
					}
					//System.out.print("interceptions "+interceptions[j+(i*100)]+" / ");
		            
		            
					// 26 penalty_committed 패널티 제공
		            if(sentance[j+1].indexOf("\"penaltyConceded\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("\"penaltyConceded\":")+"\"penaltyConceded\":".length());
		            	penalty_committed[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	penalty_committed[j+(i*100)] = "0";
		            }
		            //System.out.print("penalty_committed "+penalty_committed[j+(i*100)]+" / ");
		            
		            
		            // 27 clearances 걷어내기
		            if(sentance[j+1].indexOf("\"clearances\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("\"clearances\":")+"\"clearances\":".length());
		            	clearances[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	clearances[j+(i*100)] = "0";
		            }
		           // System.out.print("clearances "+clearances[j+(i*100)]+" / ");
					
		            // 28 errors_lead_to_goal 실점 허용 실책
		            if(sentance[j+1].indexOf("\"errorLeadToGoal\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("\"errorLeadToGoal\":")+"\"errorLeadToGoal\":".length());
		            	errors_lead_to_goal[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	errors_lead_to_goal[j+(i*100)] = "0";
		            }
		            // System.out.print("errors_lead_to_goal "+errors_lead_to_goal[j+(i*100)]+" / ");
		            
		            
		            // b pid_sofa 플레이어ID
		            String[] temp = sentance[j+1].split(",\"id");
		            if(temp[1].indexOf(":") > 0 ){
		            	String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
		            	pid_sofa6[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
		            } else {
		            	pid_sofa6[j+(i*100)] = "0";
		            }
		            // System.out.print("pid_sofa6 "+pid_sofa6[j+(i*100)]+" / ");
		            
		            
		            // e tid_sofa 팀ID
		            tid_sofa[j+(i*100)] = temp[3].substring(2, temp[3].indexOf(",\"teamColors\""));	   
		            // System.out.println("tid_sofa "+tid_sofa[j+(i*100)]);
				}
			}
		}
		
		

		
		//	Set 7 메모리 등록 Start =====================================================
		//	errors_lead_to_shot, own_goals, dribbled_past, blocked_shots, clean_sheets
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=errorLeadToShot%2CownGoals%2CdribbledPast%2CblockedShots%2CcleanSheet&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
			text = text.replace("\n", "");
			
			String[] sentance = text.split("\\{\"error");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {

				for (int j = 0; j < sentance.length-1 ; j++) {
					
					// 29 errors_lead_to_shot 슈팅 허용 실책
					if(sentance[j+1].indexOf("LeadToShot\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("LeadToShot\":")+"LeadToShot\":".length());
						errors_lead_to_shot[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						errors_lead_to_shot[j+(i*100)] = "0";
					}
					//System.out.print("errors_lead_to_shot "+errors_lead_to_shot[j+(i*100)]+" / ");
					
					//30 own_goals 자책골
					if(sentance[j+1].indexOf("\"ownGoals\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("\"ownGoals\":")+"\"ownGoals\":".length());
						own_goals[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						own_goals[j+(i*100)] = "0";
					}
		            //System.out.print("own_goals "+own_goals[j+(i*100)]+" / ");
					
					// 31 dribbled_past 드리블 허용
					if(sentance[j+1].indexOf("\"dribbledPast\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("\"dribbledPast\":")+"\"dribbledPast\":".length());
						dribbled_past[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						dribbled_past[j+(i*100)] = "0";
					}
					//System.out.print("dribbled_past "+dribbled_past[j+(i*100)]+" / ");

					// 32 blocked_shots 슈팅 블록킹
					if(sentance[j+1].indexOf("\"blockedShots\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("\"blockedShots\":")+"\"blockedShots\":".length());
						blocked_shots[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						blocked_shots[j+(i*100)] = "0";
					}
					//System.out.print("blocked_shots "+blocked_shots[j+(i*100)]+" / ");

					// 33 clean_sheets 결정적 기회 창출
					if(sentance[j+1].indexOf("\"errorLeadToGoal\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("\"errorLeadToGoal\":")+"\"errorLeadToGoal\":".length());
						clean_sheets[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						clean_sheets[j+(i*100)] = "0";
					}
					//System.out.print("clean_sheets "+clean_sheets[j+(i*100)]+" / ");
					
					// b pid_sofa 플레이어ID
					String[] temp = sentance[j+1].split(",\"id");
					if(temp[1].indexOf(":") > 0 ){
						String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
						pid_sofa7[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
					} else {
						pid_sofa7[j+(i*100)] = "0";
					}
					//System.out.println("pid_sofa7 "+pid_sofa7[j+(i*100)]);
			        
				}
			}
		}
		
		
		
		//	Pass Set 시작 =========================================================
		//	Set 8 메모리 등록 Start =====================================================
		//	big_chances_created, assists, acc_passes, inaccurate_passs, total_passes
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=bigChancesCreated%2Cassists%2CaccuratePasses%2CinaccuratePasses%2CtotalPasses&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
			text = text.replace("\n", "");
			
			String[] sentance = text.split("\"bigCh");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {

				for (int j = 0; j < sentance.length-1 ; j++) {
					
					// 34 big_chances_created 결정적 기회 창출
					if(sentance[j+1].indexOf("cesCreated\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("cesCreated\":")+"cesCreated\":".length());
						big_chances_created[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						big_chances_created[j+(i*100)] = "0";
					}
					//System.out.print("big_chances_created "+big_chances_created[j+(i*100)]+" / ");
					
					// 35 assists 결정적 기회 창출
		            if(sentance[j+1].indexOf(",\"assists\":") > 0 ){
		               String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"assists\":")+",\"assists\":".length());
		               assists[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	assists[j+(i*100)] = "0";
		            }
		            //System.out.print("assists "+assists[j+(i*100)]+" / ");
		            
		            // 36 acc_passes 성공한 패스
		            if(sentance[j+1].indexOf(",\"accuratePasses\":") > 0 ){
		               String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"accuratePasses\":")+",\"accuratePasses\":".length());
		               acc_passes[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	acc_passes[j+(i*100)] = "0";
		            }
		            //System.out.print("acc_passes "+acc_passes[j+(i*100)]+" / ");
		            
		            // 37 inaccurate_passs 실패한 패스
		            if(sentance[j+1].indexOf(",\"inaccuratePasses\":") > 0 ){
		               String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"inaccuratePasses\":")+",\"inaccuratePasses\":".length());
		               inaccurate_passs[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	inaccurate_passs[j+(i*100)] = "0";
		            }
		            //System.out.print("inaccurate_passs "+inaccurate_passs[j+(i*100)]+" / ");
		            
		            // 38 total_passes 전체 패스 수
		            if(sentance[j+1].indexOf(",\"totalPasses\":") > 0 ){
		               String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"totalPasses\":")+",\"totalPasses\":".length());
		               total_passes[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	total_passes[j+(i*100)] = "0";
		            }
		            //System.out.print("total_passes "+total_passes[j+(i*100)]+" / ");
		            
					// b pid_sofa 플레이어ID            
		            String[] temp = sentance[j+1].split(",\"id");
		            if(temp[1].indexOf(":") > 0 ){
		               String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
		               pid_sofa8[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
		            } else {
		            	pid_sofa8[j+(i*100)] = "0";
		            }
		            //System.out.println("pid_sofa8 "+pid_sofa8[j+(i*100)]);
				}
			}
		}

		//	Set 9 메모리 등록 Start =====================================================
		//	errors_lead_to_shot, own_goals, dribbled_past, blocked_shots, clean_sheets
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=accuratePassesPercentage%2CaccurateOwnHalfPasses%2CaccurateOppositionHalfPasses%2CaccurateFinalThirdPasses%2CkeyPasses&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
			text = text.replace("\n", "");
			
			String[] sentance = text.split("\"accuratePas");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {

				for (int j = 0; j < sentance.length-1 ; j++) {
					
					// 39 accurate_passes_per 패스 성공률
					if(sentance[j+1].indexOf("sPercentage\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("sPercentage\":")+"sPercentage\":".length());
						accurate_passes_per[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						accurate_passes_per[j+(i*100)] = "0";
					}
		           // System.out.print("accurate_passes_per "+accurate_passes_per[j+(i*100)]+" / ");
						
			        // 40 key_passes 키 패스
			       if(sentance[j+1].indexOf(",\"keyPasses\":") > 0 ){
			           String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"keyPasses\":")+",\"keyPasses\":".length());
			           key_passes[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
			       } else {
			    	   key_passes[j+(i*100)] = "0";
		           }
		            //System.out.print("key_passes "+key_passes[j+(i*100)]+" / ");

			       
					// b pid_sofa 플레이어ID
			       String[] temp = sentance[j+1].split(",\"id");
			       if(temp[1].indexOf(":") > 0 ){
			    	   String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
			           pid_sofa9[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
			        } else {
			          	pid_sofa9[j+(i*100)] = "0";
			        }
		           //System.out.println("pid_sofa9 "+pid_sofa9[j+(i*100)]);
				}
			}
		}
		

		//	Set 10 메모리 등록 Start =====================================================
		//	errors_lead_to_shot, own_goals, dribbled_past, blocked_shots, clean_sheets
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=accurateCrosses%2CaccurateCrossesPercentage%2CaccurateLongBalls%2CaccurateLongBallsPercentage%2CpassToAssist&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
			text = text.replace("\n", "");
			
			String[] sentance = text.split("\\{\"accur");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {

				for (int j = 0; j < sentance.length-1 ; j++) {
					
					// 41 accurate_crosses 크로스
					if(sentance[j+1].indexOf("eCrosses\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("eCrosses\":")+"eCrosses\":".length());
						accurate_crosses[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						accurate_crosses[j+(i*100)] = "0";
					}
		            //System.out.print("accurate_crosses "+accurate_crosses[j+(i*100)]+" / ");
		            
					// 42 accurate_crosses_per 크로스 성공률
			        if(sentance[j+1].indexOf(",\"accurateCrossesPercentage\":") > 0 ){
			           String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"accurateCrossesPercentage\":")+",\"accurateCrossesPercentage\":".length());
			           accurate_crosses_per[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
			        } else {
			        	accurate_crosses_per[j+(i*100)] = "0";
		            }
		            //System.out.print("accurate_crosses_per "+accurate_crosses_per[j+(i*100)]+" / ");
			        
			        // 43 acc_long_ball 롱패스
			        if(sentance[j+1].indexOf(",\"accurateLongBalls\":") > 0 ){
			           String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"accurateLongBalls\":")+",\"accurateLongBalls\":".length());
			           acc_long_ball[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
			        } else {
			        	acc_long_ball[j+(i*100)] = "0";
			        }
		            //System.out.print("acc_long_ball "+acc_long_ball[j+(i*100)]+" / ");
			        
			        // 44 accurate_long_balls_per 롱패스 성공률
			        if(sentance[j+1].indexOf(",\"accurateLongBallsPercentage\":") > 0 ){
			           String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"accurateLongBallsPercentage\":")+",\"accurateLongBallsPercentage\":".length());
			           accurate_long_balls_per[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
			        } else {
			        	accurate_long_balls_per[j+(i*100)] = "0";
			        }
		            //System.out.print("accurate_long_balls_per "+accurate_long_balls_per[j+(i*100)]+" / ");
			        
			        // 45 passes_to_assist 패스로 달성한 도움
			       if(sentance[j+1].indexOf(",\"passToAssist\":") > 0 ){
			           String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"passToAssist\":")+",\"passToAssist\":".length());
			           passes_to_assist[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
			       } else {
			    	   passes_to_assist[j+(i*100)] = "0";
		           }
		            //System.out.print("passes_to_assist "+passes_to_assist[j+(i*100)]+" / ");
			       
					// b pid_sofa 플레이어ID
			       String[] temp = sentance[j+1].split(",\"id");
			       if(temp[1].indexOf(":") > 0 ){
			    	   String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
			           pid_sofa10[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
			        } else {
			          	pid_sofa10[j+(i*100)] = "0";
			        }
		            //System.out.println("pid_sofa10 "+pid_sofa10[j+(i*100)]);		        
				}
			}
		}
		
		
		
		//	Other Set 시작 ========================================================= 
		//	Set 11 메모리 등록 Start =====================================================
		//	yellow_cards, red_cards, ground_duels_won, ground_duels_won_per, aerial_duels_won
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=yellowCards%2CredCards%2CgroundDuelsWon%2CgroundDuelsWonPercentage%2CaerialDuelsWon&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
//			text = text.replace("\n", "");
			
			String[] sentance = text.split("\\{\"yel");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {
				for (int j = 0; j < sentance.length-1 ; j++) {		
					
					// 51 yellow_cards 경고
					if(sentance[j].indexOf("owCards\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("owCards\":")+"owCards\":".length());
						yellow_cards[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						yellow_cards[j+(i*100)] = "0";
					}
					//System.out.print("yellow_cards "+yellow_cards[j+(i*100)]+" / ");
					
					
		            // 52 red_cards 퇴장
					if(sentance[j+1].indexOf(",\"redCards\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"redCards\":")+",\"redCards\":".length());
						red_cards[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						red_cards[j+(i*100)] = "0";
					}
					//System.out.print("red_cards "+red_cards[j+(i*100)]+" / ");
		            
		            
					// 53 ground_duels_won 지면 경합 승리
		            if(sentance[j+1].indexOf(",\"groundDuelsWon\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"groundDuelsWon\":")+",\"groundDuelsWon\":".length());
		            	ground_duels_won[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	ground_duels_won[j+(i*100)] = "0";
		            }
		            //System.out.print("ground_duels_won "+ground_duels_won[j+(i*100)]+" / ");
		            
		            
		            // 54 ground_duels_won_per 지면 경합 승률
		            if(sentance[j+1].indexOf(",\"groundDuelsWonPercentage\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"groundDuelsWonPercentage\":")+",\"groundDuelsWonPercentage\":".length());
		            	ground_duels_won_per[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	ground_duels_won_per[j+(i*100)] = "0";
		            }
		            //System.out.print("ground_duels_won_per "+ground_duels_won_per[j+(i*100)]+" / ");
					
		            // 55 aerial_duels_won 공중 경합 승리
		            if(sentance[j+1].indexOf(",\"aerialDuelsWon\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"aerialDuelsWon\":")+",\"aerialDuelsWon\":".length());
		            	aerial_duels_won[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	aerial_duels_won[j+(i*100)] = "0";
		            }
		            //System.out.print("aerial_duels_won "+aerial_duels_won[j+(i*100)]+" / ");
		            
		            
		            // b pid_sofa 플레이어ID
		            String[] temp = sentance[j+1].split(",\"id");
		            if(temp[1].indexOf(":") > 0 ){
		            	String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
		            	pid_sofa11[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
		            } else {
		            	pid_sofa11[j+(i*100)] = "0";
		            }
		            //System.out.print("pid_sofa11 "+pid_sofa11[j+(i*100)]+" / ");
		            
				}
			}
		}
		
		

		
		//	Set 12 메모리 등록 Start =====================================================
		//	aerial_duels_won_per, Total_duels_won, Total_duels_won_per, Minutes_played, Was_fouled
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=aerialDuelsWonPercentage%2CtotalDuelsWon%2CtotalDuelsWonPercentage%2CminutesPlayed%2CwasFouled&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
//			text = text.replace("\n", "");
			
			String[] sentance = text.split("\\{\"aerialDuelsW");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {

				for (int j = 0; j < sentance.length-1 ; j++) {
					
					// 56 aerial_duels_won_per 공중 경합 승률
					if(sentance[j+1].indexOf("nPercentage\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("nPercentage\":")+"nPercentage\":".length());
						aerial_duels_won_per[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						aerial_duels_won_per[j+(i*100)] = "0";
					}
					//System.out.print("errors_lead_to_shot "+errors_lead_to_shot[j+(i*100)]+" / ");
					
					// 57 total_duels_won 전체 경합 승리
					if(sentance[j+1].indexOf(",\"totalDuelsWon\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"totalDuelsWon\":")+",\"totalDuelsWon\":".length());
						total_duels_won[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						total_duels_won[j+(i*100)] = "0";
					}
		            //System.out.print("own_goals "+own_goals[j+(i*100)]+" / ");
					
					// 58 total_duels_won_per 전체 경합 승률
					if(sentance[j+1].indexOf(",\"totalDuelsWonPercentage\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"totalDuelsWonPercentage\":")+",\"totalDuelsWonPercentage\":".length());
						total_duels_won_per[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						total_duels_won_per[j+(i*100)] = "0";
					}
					//System.out.print("dribbled_past "+dribbled_past[j+(i*100)]+" / ");

					// 59 minutes_played 출장 시간(분)
					if(sentance[j+1].indexOf(",\"minutesPlayed\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"minutesPlayed\":")+",\"minutesPlayed\":".length());
						minutes_played[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						minutes_played[j+(i*100)] = "0";
					}
					//System.out.print("blocked_shots "+blocked_shots[j+(i*100)]+" / ");
 
					// 60 was_fouled 당한 반칙
					if(sentance[j+1].indexOf(",\"wasFouled\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"wasFouled\":")+",\"wasFouled\":".length());
						was_fouled[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						was_fouled[j+(i*100)] = "0";
					}
					//System.out.print("clean_sheets "+clean_sheets[j+(i*100)]+" / ");
					
					// b pid_sofa 플레이어ID
					String[] temp = sentance[j+1].split(",\"id");
					if(temp[1].indexOf(":") > 0 ){
						String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
						pid_sofa12[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
					} else {
						pid_sofa12[j+(i*100)] = "0";
					}
					//System.out.print("pid_sofa2 "+pid_sofa12[j+(i*100)]+" / ");
					

				}
			}
		}
	
		
		
		
//		Set 13 메모리 등록 Start =====================================================
		//	errors_lead_to_shot, own_goals, dribbled_past, blocked_shots, clean_sheets
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=fouls%2Cdispossessed%2CpossessionLost%2Cappearances%2CmatchesStarted&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
//			text = text.replace("\n", "");
			
			String[] sentance = text.split("\\{\"fo");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {

				for (int j = 0; j < sentance.length-1 ; j++) {
					
					// 61 fouls 가한 반칙
					if(sentance[j+1].indexOf("ls\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("ls\":")+"ls\":".length());
						fouls[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						fouls[j+(i*100)] = "0";
					}
					//System.out.print("errors_lead_to_shot "+errors_lead_to_shot[j+(i*100)]+" / ");
					
					// 62 dispossessed 비 드리블 상황에서 공격권 상실
					if(sentance[j+1].indexOf(",\"dispossessed\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"dispossessed\":")+",\"dispossessed\":".length());
						dispossessed[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						dispossessed[j+(i*100)] = "0";
					}
		            //System.out.print("own_goals "+own_goals[j+(i*100)]+" / ");
					
					// 63 possession_lost 드리블 중 공격권 상실(턴오버)
					if(sentance[j+1].indexOf(",\"possessionLost\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"possessionLost\":")+",\"possessionLost\":".length());
						possession_lost[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						possession_lost[j+(i*100)] = "0";
					}
					//System.out.print("dribbled_past "+dribbled_past[j+(i*100)]+" / ");

					// 64 appearances 전체 출전
					if(sentance[j+1].indexOf(",\"appearances\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"appearances\":")+",\"appearances\":".length());
						appearances[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						appearances[j+(i*100)] = "0";
					}
					//System.out.print("blocked_shots "+blocked_shots[j+(i*100)]+" / ");

					// 65 started 선발 출전
					if(sentance[j+1].indexOf(",\"matchesStarted\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"matchesStarted\":")+",\"matchesStarted\":".length());
						started[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						started[j+(i*100)] = "0";
					}
					//System.out.print("clean_sheets "+clean_sheets[j+(i*100)]+" / ");
					
					// b pid_sofa 플레이어ID
					String[] temp = sentance[j+1].split(",\"id");
					if(temp[1].indexOf(":") > 0 ){
						String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
						pid_sofa13[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
					} else {
						pid_sofa13[j+(i*100)] = "0";
					}
					//System.out.println("pid_sofa13 "+pid_sofa13[j+(i*100)]);
				
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//	GoalKeeper Set 시작 ========================================================= 
		//	Set 14 메모리 등록 Start =====================================================
		//	saves, penalties_faced, penalties_saved, saves_from_inside_box, saved_shots_from_outside_the_box
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=saves%2CpenaltyFaced%2CpenaltySave%2CsavedShotsFromInsideTheBox%2CsavedShotsFromOutsideTheBox&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
//			text = text.replace("\n", "");
			
			String[] sentance = text.split("\\{\"sa");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {
				for (int j = 0; j < sentance.length-1 ; j++) {		
					
					// 66 saves 선방
					if(sentance[j].indexOf("es\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("es\":")+"es\":".length());
						saves[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						saves[j+(i*100)] = "0";
					}
					//System.out.print("saves "+saves[j+(i*100)]+" / ");
					
					
		            // 67 penalties_faced 진행된 패널티 킥
					if(sentance[j+1].indexOf(",\"penaltyFaced\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"penaltyFaced\":")+",\"penaltyFaced\":".length());
						penalties_faced[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						penalties_faced[j+(i*100)] = "0";
					}
					//System.out.print("penalties_faceds "+penalties_faced[j+(i*100)]+" / ");
		            
		            
					// 68 penalties_saved 패널티 킥 선방
		            if(sentance[j+1].indexOf(",\"penaltySave\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"penaltySave\":")+",\"penaltySave\":".length());
		            	penalties_saved[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	penalties_saved[j+(i*100)] = "0";
		            }
		            //System.out.print("penalties_saved "+penalties_saved[j+(i*100)]+" / ");
		            
		            
		            // 69 saves_from_inside_box 박스 안 선방
		            if(sentance[j+1].indexOf(",\"savedShotsFromInsideTheBox\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"savedShotsFromInsideTheBox\":")+",\"savedShotsFromInsideTheBox\":".length());
		            	saves_from_inside_box[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	saves_from_inside_box[j+(i*100)] = "0";
		            }
		            //System.out.print("saves_from_inside_box "+saves_from_inside_box[j+(i*100)]+" / ");
					
		            // 70 saved_shots_from_outside_the_box 박스 밖 선방
		            if(sentance[j+1].indexOf(",\"savedShotsFromOutsideTheBox\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"savedShotsFromOutsideTheBox\":")+",\"savedShotsFromOutsideTheBox\":".length());
		            	saved_shots_from_outside_the_box[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	saved_shots_from_outside_the_box[j+(i*100)] = "0";
		            }
		            //System.out.print("saved_shots_from_outside_the_box "+saved_shots_from_outside_the_box[j+(i*100)]+" / ");
		            
		            
		            // b pid_sofa 플레이어ID
		            String[] temp = sentance[j+1].split(",\"id");
		            if(temp[1].indexOf(":") > 0 ){
		            	String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
		            	pid_sofa14[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
		            } else {
		            	pid_sofa14[j+(i*100)] = "0";
		            }
		            //System.out.print("pid_sofa14 "+pid_sofa14[j+(i*100)]+" / ");
		           
				}
			}
		}
		
		

		
		//	Set 15 메모리 등록 Start =====================================================
		//	goals_conceded_inside_the_box, goals_conceded_outside_the_box, punches, runs_out, successful_runs_out
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=goalsConcededInsideTheBox%2CgoalsConcededOutsideTheBox%2Cpunches%2CrunsOut%2CsuccessfulRunsOut&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
//			text = text.replace("\n", "");
			
			String[] sentance = text.split("\\{\"goalsConced");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {

				for (int j = 0; j < sentance.length-1 ; j++) {
					
					// 71 goals_conceded_inside_the_box 박스 안 실점
					if(sentance[j+1].indexOf("dInsideTheBox\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("dInsideTheBox\":")+"dInsideTheBox\":".length());
						goals_conceded_inside_the_box[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						goals_conceded_inside_the_box[j+(i*100)] = "0";
					}
					//System.out.print("errors_lead_to_shot "+errors_lead_to_shot[j+(i*100)]+" / ");
					
					// 72 goals_conceded_outside_the_box 박스 밖 실점
					if(sentance[j+1].indexOf(",\"totalDuelsWon\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"goalsConcededOutsideTheBox\":")+",\"goalsConcededOutsideTheBox\":".length());
						goals_conceded_outside_the_box[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						goals_conceded_outside_the_box[j+(i*100)] = "0";
					}
		            //System.out.print("own_goals "+own_goals[j+(i*100)]+" / ");
					
					// 73 punches 펀칭
					if(sentance[j+1].indexOf(",\"punches\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"punches\":")+",\"punches\":".length());
						punches[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						punches[j+(i*100)] = "0";
					}
					//System.out.print("dribbled_past "+dribbled_past[j+(i*100)]+" / ");

					// 74 runs_out 전진 수비
					if(sentance[j+1].indexOf(",\"runsOut\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"runsOut\":")+",\"runsOut\":".length());
						runs_out[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						runs_out[j+(i*100)] = "0";
					}
					//System.out.print("blocked_shots "+blocked_shots[j+(i*100)]+" / ");
 
					// 75 successful_runs_out 성공한 전진 수비
					if(sentance[j+1].indexOf(",\"runsOut\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"runsOut\":")+",\"runsOut\":".length());
						successful_runs_out[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						successful_runs_out[j+(i*100)] = "0";
					}
					//System.out.print("clean_sheets "+clean_sheets[j+(i*100)]+" / ");
					
					// b pid_sofa 플레이어ID
					String[] temp = sentance[j+1].split(",\"id");
					if(temp[1].indexOf(":") > 0 ){
						String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
						pid_sofa15[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
					} else {
						pid_sofa15[j+(i*100)] = "0";
					}
					//System.out.print("pid_sofa15 "+pid_sofa15[j+(i*100)]+" / ");
					
				}
			}
		}
	
		
		
		
//		Set 16 메모리 등록 Start =====================================================
		//	errors_lead_to_shot, own_goals, dribbled_past, blocked_shots, clean_sheets
		for (int i = 0; i < 10; i++) {		
			Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league+"/season/"+season+"/statistics?limit=100&order=-rating&offset="+i*100+"&accumulation=total&fields=highClaims%2CcrossesNotClaimed&filters=position.in.G~D~M~F")
	                .method(Connection.Method.GET)
	                .ignoreContentType(true)
	                .execute();
			Document document = response.parse();
			
			String text = document.text();		
//			text = text.replace("\n", "");
			
			String[] sentance = text.split("\\{\"highC");
			
			int stopPage = Integer.parseInt(text.substring(text.indexOf(",\"pages\":")+",\"pages\":".length(), text.indexOf(",\"pages\":")+",\"pages\":".length()+1)); 
			if ( i == stopPage ) {
				break;	
			}else {

				for (int j = 0; j < sentance.length-1 ; j++) {
					
					// 76 high_claims 공중볼 처리
					if(sentance[j+1].indexOf("aims\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("aims\":")+"aims\":".length());
						high_claims[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						high_claims[j+(i*100)] = "0";
					}
					//System.out.print("errors_lead_to_shot "+errors_lead_to_shot[j+(i*100)]+" / ");
					
					// 77 crosses_not_claimed 처리못한 크로스
					if(sentance[j+1].indexOf(",\"crossesNotClaimed\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"crossesNotClaimed\":")+",\"crossesNotClaimed\":".length());
						crosses_not_claimed[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						crosses_not_claimed[j+(i*100)] = "0";
					}
		            //System.out.print("own_goals "+own_goals[j+(i*100)]+" / ");
					
				
					// b pid_sofa 플레이어ID
					String[] temp = sentance[j+1].split(",\"id");
					if(temp[1].indexOf(":") > 0 ){
						String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
						pid_sofa16[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
					} else {
						pid_sofa16[j+(i*100)] = "0";
					}
					//System.out.println("pid_sofa16 "+pid_sofa16[j+(i*100)]);
				
				}
			}
		}

		// DB에 등록 시작
		for (int i = 0; i < 1000 ; i++) {
			if( pid_sofa1[i] == null ) {	break;		}
			
			if ( 
					pid_sofa1[i].equals(pid_sofa2[i]) && pid_sofa1[i].equals(pid_sofa3[i]) && pid_sofa1[i].equals(pid_sofa4[i]) &&  pid_sofa1[i].equals(pid_sofa5[i]) &&
					pid_sofa1[i].equals(pid_sofa6[i]) && pid_sofa1[i].equals(pid_sofa7[i]) && pid_sofa1[i].equals(pid_sofa8[i]) && pid_sofa1[i].equals(pid_sofa9[i]) && 
					pid_sofa1[i].equals(pid_sofa10[i]) && pid_sofa1[i].equals(pid_sofa11[i]) && pid_sofa1[i].equals(pid_sofa12[i]) && pid_sofa1[i].equals(pid_sofa13[i]) && 
					pid_sofa1[i].equals(pid_sofa14[i]) && pid_sofa1[i].equals(pid_sofa15[i]) && pid_sofa1[i].equals(pid_sofa16[i])
					) {
				
				System.out.print("Cehck! ["+ i +"]번째  ");
			} else {
				System.out.println("ERROR!!!!! ["+ i +"]번째");
				break;
			}
			
			
			// 타입 변경
			int i_pid_sofa = Integer.parseInt(pid_sofa1[i]);
			int i_sid_sofa = Integer.parseInt(season);
			int i_lid_sofa = Integer.parseInt(league);
			int i_tid_sofa = Integer.parseInt(tid_sofa[i]);
			String s_psid = i_pid_sofa +"/"+ i_sid_sofa +"/"+ i_lid_sofa +"/"+ i_tid_sofa;
			
			// Attack Set =============================================
			// Set 1 ----------------------------------------------------------------------------------
			double d_rating = Double.parseDouble(rating[i]);
			int i_golas = Integer.parseInt(golas[i]);
			int i_big_chances_missed = Integer.parseInt(big_chances_missed[i]);
			int i_succ_dribbles = Integer.parseInt(succ_dribbles[i]);
			double d_successful_dribbles_per = Double.parseDouble(successful_dribbles_per[i]);
			
			// Set 2 ----------------------------------------------------------------------------------
			int i_total_shots = Integer.parseInt(total_shots[i]);
			int i_shots_on_target = Integer.parseInt(shots_on_target[i]);
			int i_shots_off_target = Integer.parseInt(shots_off_target[i]);
			double d_goal_conversion_per = Double.parseDouble(goal_conversion_per[i]);
			int i_penalties_taken = Integer.parseInt(penalties_taken[i]);
			
			// Set 3 ----------------------------------------------------------------------------------
			int i_penalty_golas = Integer.parseInt(penalty_golas[i]);
			int i_penalty_won = Integer.parseInt(penalty_won[i]);
			int i_shots_from_set_piece = Integer.parseInt(shots_from_set_piece[i]);
			int i_free_kick_goals = Integer.parseInt(free_kick_goals[i]);
			int i_goals_from_inside_the_box = Integer.parseInt(goals_from_inside_the_box[i]);
			
			// Set 4 ----------------------------------------------------------------------------------
			int i_goals_from_outside_the_box = Integer.parseInt(goals_from_outside_the_box[i]);
			int i_headed_goals = Integer.parseInt(headed_goals[i]);
			int i_left_foot_goals = Integer.parseInt(left_foot_goals[i]);
			int i_right_foot_goals = Integer.parseInt(right_foot_goals[i]);
			int i_hit_woodwork = Integer.parseInt(hit_woodwork[i]);
			
			// Set 5 ----------------------------------------------------------------------------------
			int i_offsides = Integer.parseInt(offsides[i]);
			double d_penalty_conversion_per = Double.parseDouble(penalty_conversion_per[i]);
			double d_set_piece_conversion_per = Double.parseDouble(set_piece_conversion_per[i]);
			
			
			// Defence Set =============================================
			// Set 6 ----------------------------------------------------------------------------------
			int i_tackles = Integer.parseInt(tackles[i]);
			int i_interceptions = Integer.parseInt(interceptions[i]);
			int i_penalty_committed = Integer.parseInt(penalty_committed[i]);
			int i_clearances = Integer.parseInt(clearances[i]);
			int i_errors_lead_to_goal = Integer.parseInt(errors_lead_to_goal[i]);
			
			// Set 7 ----------------------------------------------------------------------------------
			int i_errors_lead_to_shot = Integer.parseInt(errors_lead_to_shot[i]);
			int i_own_goals = Integer.parseInt(own_goals[i]);
			int i_dribbled_past = Integer.parseInt(dribbled_past[i]);
			int i_blocked_shots = Integer.parseInt(blocked_shots[i]);
			int i_clean_sheets = Integer.parseInt(clean_sheets[i]);
			
			
			// Pass Set =============================================
			// Set 8 ----------------------------------------------------------------------------------
		  	int i_big_chances_created = Integer.parseInt(big_chances_created[i]);
		  	int i_assists = Integer.parseInt(assists[i]);
		  	int i_acc_passes = Integer.parseInt(acc_passes[i]);
		  	int i_inaccurate_passs = Integer.parseInt(inaccurate_passs[i]);
		  	int i_total_passes = Integer.parseInt(total_passes[i]);

			// Set 9 ----------------------------------------------------------------------------------
		  	double d_accurate_passes_per = Double.parseDouble(accurate_passes_per[i]);
		  	int i_key_passes = Integer.parseInt(key_passes[i]);
		  	
			// Set 10 ----------------------------------------------------------------------------------
		  	int i_accurate_crosses = Integer.parseInt(accurate_crosses[i]);
		  	double d_accurate_crosses_per = Double.parseDouble(accurate_crosses_per[i]);
		  	int i_acc_long_ball = Integer.parseInt(acc_long_ball[i]);
		  	double d_accurate_long_balls_per = Double.parseDouble(accurate_long_balls_per[i]);
		  	int i_passes_to_assist = Integer.parseInt(passes_to_assist[i]);
		  	
			// Other Set =============================================
			// Set 11 ----------------------------------------------------------------------------------
		  	int i_yellow_cards = Integer.parseInt(yellow_cards[i]);
		  	int i_red_cards = Integer.parseInt(red_cards[i]);
		  	int i_ground_duels_won = Integer.parseInt(ground_duels_won[i]);
		  	double d_ground_duels_won_per = Double.parseDouble(ground_duels_won_per[i]);
		  	int	 i_aerial_duels_won = Integer.parseInt(aerial_duels_won[i]);
		  	
			// Set 12 ----------------------------------------------------------------------------------
		  	double d_aerial_duels_won_per = Double.parseDouble(aerial_duels_won_per[i]);
		  	int i_total_duels_won = Integer.parseInt(total_duels_won[i]);
		  	double d_total_duels_won_per = Double.parseDouble(total_duels_won_per[i]);
		  	int i_minutes_played = Integer.parseInt(minutes_played[i]);
		  	int i_was_fouled = Integer.parseInt(was_fouled[i]);
		  	
			// Set 13 ----------------------------------------------------------------------------------			
		  	int i_fouls = Integer.parseInt(fouls[i]);
		  	int i_dispossessed = Integer.parseInt(dispossessed[i]);
		  	int i_possession_lost = Integer.parseInt(possession_lost[i]);
		  	int i_appearances = Integer.parseInt(appearances[i]);
		  	int i_started = Integer.parseInt(started[i]);
		  	
			// Goalkeeper Set =============================================
			// Set 14 ----------------------------------------------------------------------------------
		  	int i_saves = Integer.parseInt(saves[i]);
		  	int i_penalties_faced = Integer.parseInt(penalties_faced[i]);
		  	int i_penalties_saved = Integer.parseInt(penalties_saved[i]);
		  	int i_saves_from_inside_box = Integer.parseInt(saves_from_inside_box[i]);
		  	int i_saved_shots_from_outside_the_box = Integer.parseInt(saved_shots_from_outside_the_box[i]);

			// Set 15 ----------------------------------------------------------------------------------
		  	int i_goals_conceded_inside_the_box = Integer.parseInt(goals_conceded_inside_the_box[i]);
		  	int i_goals_conceded_outside_the_box = Integer.parseInt(goals_conceded_outside_the_box[i]);
		  	int i_punches = Integer.parseInt(punches[i]);
		  	int i_runs_out = Integer.parseInt(runs_out[i]);
		  	int i_successful_runs_out = Integer.parseInt(successful_runs_out[i]);
		  	
			// Set 16 ----------------------------------------------------------------------------------
		  	int i_high_claims = Integer.parseInt(high_claims[i]);
		  	int i_crosses_not_claimed = Integer.parseInt(crosses_not_claimed[i]);
			
			// 쿼리 처리
			dao.PlayerStatUpdate(
				// ETC Set
				s_psid, i_pid_sofa, i_sid_sofa, i_lid_sofa, i_tid_sofa,
				
				// Attack Set
				d_rating, i_golas, i_big_chances_missed, i_succ_dribbles, d_successful_dribbles_per, 
				i_total_shots, i_shots_on_target, i_shots_off_target, d_goal_conversion_per, i_penalties_taken, 
				i_penalty_golas, i_penalty_won, i_shots_from_set_piece, i_free_kick_goals, i_goals_from_inside_the_box, 
				i_goals_from_outside_the_box, i_headed_goals, i_left_foot_goals, i_right_foot_goals, i_hit_woodwork,
				i_offsides, d_penalty_conversion_per, d_set_piece_conversion_per,

				// Defence Set
				i_tackles, i_interceptions, i_penalty_committed, i_clearances, i_errors_lead_to_goal,
				i_errors_lead_to_shot, i_own_goals, i_dribbled_past, i_blocked_shots, i_clean_sheets,
				
				// Pass Set
				i_big_chances_created, i_assists, i_acc_passes, i_inaccurate_passs, i_total_passes,
				d_accurate_passes_per, i_key_passes,
				i_accurate_crosses, d_accurate_crosses_per, i_acc_long_ball, d_accurate_long_balls_per, i_passes_to_assist,
				
				// Other Set
				i_yellow_cards, i_red_cards, i_ground_duels_won, d_ground_duels_won_per, i_aerial_duels_won, 
				d_aerial_duels_won_per, i_total_duels_won, d_total_duels_won_per, i_minutes_played, i_was_fouled, 
				i_fouls, i_dispossessed, i_possession_lost, i_appearances, i_started,			
				
				// Goalkeeper Set
				i_saves, i_penalties_faced, i_penalties_saved, i_saves_from_inside_box, i_saved_shots_from_outside_the_box, 
				i_goals_conceded_inside_the_box, i_goals_conceded_outside_the_box, i_punches, i_runs_out, i_successful_runs_out, 
				i_high_claims, i_crosses_not_claimed 					
			); 

		}		
		
	}
}