package org.crawler;
import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.nodes.Document;

import jdbc.DAO;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class Copy {

	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException, SQLException {
	
		DAO dao = new DAO();
		
		// epl		   17   41886
		// primera      8   42409
		// bundesliga  35   42268
		// seriea      23   42415
		// ligue1      34   42273

		int league[] = {17, 8, 35, 23, 34} ;										// 리그 코드
		int season[] = {41886, 42409, 42268, 42415, 42273};							// 시즌 코드
		
		String country[] = { "england", "spain", "germany", "italy", "france" };
		
		String lname_sofa[] = { "premier-league", "laliga", "bundesliga", "serie-a", "ligue-1"};
		String lname_daum[] = { "epl", "primera", "bundesliga", "seriea", "ligue1" };
		
		String lko[] = { "프리미어리그", "라리가", "분데스리가", "세리에A", "리그1" };
		
		int sid_sofa[][] = new int[5][20];
		int sid_daum[] = new int[20];
		
		int tid_sofa[][] = new int[5][20];
		int tid_daum[][] = new int[5][20];
		

		
		
		System.out.println("::::::::::::::::::::::시즌 정보 갱신 시작 (SEASON)::::::::::::::::::::::");
		
		for (int i = 0; i < 5; i++) {
				
				Connection.Response response = Jsoup.connect("https://www.sofascore.com/tournament/football/"+country[i]+"/"+lname_sofa[i]+"/"+league[i])
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
		                .execute();
				Document document = response.parse();
				
				String html = document.html();
				String text = document.text();		
				html = html.replace("\n", "");
				
				String tmp = "";
				String SeasonList[] = html.split("\"year\":\"");
				
			for(int j = 0; j < 20; j ++) {
				
				String SName = SeasonList[j+1].substring(0, SeasonList[j+1].indexOf("\""));
				if(SName.equals("09/10")) break;
				
				int SYear = Integer.parseInt(SName.substring(0, SName.indexOf("/")));
				sid_daum[j] = Integer.parseInt("20"+SYear+"20"+(SYear+1));
				
				tmp = SeasonList[j+1].substring(SeasonList[j+1].indexOf("\"id\":")+"\"id\":".length());
				sid_sofa[i][j]= Integer.parseInt(tmp.substring(0, tmp.indexOf("}")));
				
				String SSlug = country[i] + '/' + lname_sofa[i] + '/' + league[i] + '/' + "season" + '/' + sid_sofa[i][j];
				
				dao.insertSeason(sid_sofa[i][j], sid_daum[j], SYear, league[i], SName, SSlug);
				
			}
		}
		

		
			
		System.out.println("::::::::::::::::::::::팀 정보 갱신 시작 (TEAM)::::::::::::::::::::::");
		
		for (int i = 0; i < 5; i++) {
			for(int j = 0; j < 20; j++) {
				
				if(lname_daum[i].equals("bundesliga") && j==18) break;
					
				Connection.Response response = Jsoup.connect("https://sports.daum.net/prx/hermes/api/team/rank.json?leagueCode="+lname_daum[i]+"&seasonKey=20222023&page=1&pageSize=100")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
		                .execute();
				Document document = response.parse();
				
				String html = document.html();
				String text = document.text();		
				text = text.replace("\n", "");
				
				String tmp = "";
				String tmpList[] = text.split("\"teamId\"");
				
				tmp = tmpList[j+1].substring(tmpList[j+1].indexOf(":")+":".length());
				tid_daum[i][j] = Integer.parseInt(tmp.substring(0, tmp.indexOf(",")));
				
				tmp = tmp.substring(tmp.indexOf("cpTeamId\":\"")+"cpTeamId\":\"".length());
				tid_sofa[i][j] = Integer.parseInt(tmp.substring(0, tmp.indexOf("\"")));
				
				tmp = tmp.substring(tmp.indexOf("shortNameKo\":\"")+"shortNameKo\":\"".length());
				String TKo = tmp.substring(0, tmp.indexOf("\""));
				
				tmp = tmp.substring(tmp.indexOf("imageUrl\":\"")+"imageUrl\":\"".length());
				String TImg = tmp.substring(0, tmp.indexOf("\""));
			
				
				Connection.Response response2 = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[i]+"/season/"+sid_sofa[i][0]+"/standings/total")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
		                .execute();
				Document document2 = response2.parse();
				
				String html2 = document2.html();
				String text2 = document2.text();		
				text2 = text2.replace("\n", "");
				
				String tmp2 = "";
				String tmpList2[] = text2.split("team\":\\{\"name\":\"");
				
				String TEn = tmpList2[j+1].substring(0, tmpList2[j+1].indexOf("\""));
				
				tmp2 = tmpList2[j+1].substring(tmpList2[j+1].indexOf("slug\":\"")+"slug\":\"".length());
				String TSlug = tmp2.substring(0, tmp2.indexOf("\""));
				
				String LSlid = lname_daum[i] + "/20222023/" + (j+1);
				
				dao.insertTeam(tid_sofa[i][j], tid_daum[i][j], league[i], TKo, TEn, TSlug, TImg, LSlid);
			}
		}
		
		
		
		
		System.out.println("::::::::::::::::::::::팀 전적 정보 갱신 시작 (TEAMRANK)::::::::::::::::::::::");
		
		for (int i = 0; i < 5; i++) {
			for(int j = 0; j < 20; j++) {	
				for(int k = 0; k < 20; k++) {
					
					if(lname_daum[i].equals("bundesliga") && k==18) break;
					
					Connection.Response response = Jsoup.connect("https://sports.daum.net/prx/hermes/api/team/rank.json?leagueCode="+lname_daum[i]+"&seasonKey="+sid_daum[j]+"&page=1&pageSize=100")
			                .method(Connection.Method.GET)
			                .ignoreContentType(true).ignoreHttpErrors(true)
			                .execute();
					Document document = response.parse();
					
					String html = document.html();
					String text = document.text();		
					text = text.replace("\n", "");
					
					String tmp = "";
					String tmpList[] = text.split("\"teamId\":");
					
					tmp = tmpList[k+1].substring(tmpList[k+1].indexOf("cpTeamId\":\"")+"cpTeamId\":\"".length());
					int tid_sofa_teamRank = Integer.parseInt(tmp.substring(0, tmp.indexOf("\"")));
					
					tmp = tmp.substring(tmp.indexOf("{\"rank\":")+"{\"rank\":".length());
					int ranking = Integer.parseInt(tmp.substring(0, tmp.indexOf(",")));
					
					tmp = tmp.substring(tmp.indexOf("game\":")+"game\":".length());
					int game = Integer.parseInt(tmp.substring(0, tmp.indexOf(",")));
					
					tmp = tmp.substring(tmp.indexOf("win\":")+"win\":".length());
					int win = Integer.parseInt(tmp.substring(0, tmp.indexOf(",")));
					
					tmp = tmp.substring(tmp.indexOf("draw\":")+"draw\":".length());
					int draw = Integer.parseInt(tmp.substring(0, tmp.indexOf(",")));
					
					tmp = tmp.substring(tmp.indexOf("loss\":")+"loss\":".length());
					int loss = Integer.parseInt(tmp.substring(0, tmp.indexOf(",")));
					
					tmp = tmp.substring(tmp.indexOf("gf\":")+"gf\":".length());
					int gf = Integer.parseInt(tmp.substring(0, tmp.indexOf(",")));
					
					tmp = tmp.substring(tmp.indexOf("ga\":")+"ga\":".length());
					int ga = Integer.parseInt(tmp.substring(0, tmp.indexOf(",")));
					
					tmp = tmp.substring(tmp.indexOf("gd\":")+"gd\":".length());
					int gd = Integer.parseInt(tmp.substring(0, tmp.indexOf(",")));
					
					tmp = tmp.substring(tmp.indexOf("pts\":")+"pts\":".length());
					int pts = Integer.parseInt(tmp.substring(0, tmp.indexOf("}")));
					
					String leagueCode = lname_daum[i] + "/" + sid_daum[j] + "/" + (j+1);
							
					System.out.println(tid_sofa_teamRank+"/"+ranking+"/"+game+"/"+win+"/"+draw+"/"+loss+"/"+gf+"/"+ga+"/"+gd+"/"+pts+"/"+leagueCode);
					
					dao.insertTeamRank(ranking, tid_sofa_teamRank, game, win, draw, loss, gf, ga, gd, pts, league[i], sid_daum[j]);
			
				}
				if(sid_daum[j] == 20102011) break;
			}
		}
		

		
		
		// 메모리 set 준비 ===========================================================
		String[] pid_sofa1 = new String[1000];										//	0 플레이어 ID
		String[] pid_sofa2 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa3 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa4 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa5 = new String[1000];										//	0 플레이어 ID
		String[] pid_sofa6 = new String[1000];										//	0 플레이어 ID
		String[] pid_sofa7 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa8 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa9 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa10 = new String[1000];										//	0 플레이어 ID
		String[] pid_sofa11 = new String[1000];										//	0 플레이어 ID
		String[] pid_sofa12 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa13 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa14 = new String[1000];										//	0 플레이어 ID		
		String[] pid_sofa15 = new String[1000];										//	0 플레이어 ID
		String[] pid_sofa16 = new String[1000];										//	0 플레이어 ID
		
		String[] pid_daum = new String[10000];										//  0 플레이어 ID DAUM
		String[] pid_sofa_imsi = new String[10000];									//  0 플레이어 ID SOFA
		String[] PNum_imsi = new String[10000];										//  0 플레이어 임시 등번호
		String[] PKo_imsi = new String[10000];										//  0 플레이어 임시 한글명
		String[] PPosition_imsi = new String[10000];								//  0 플레이어 임시 포지션
		String[] PEnFirstName_imsi = new String[10000];								//  0 플레이어 임시 영문명 성
		String[] PEnFirstName_imsi2 = new String[10000];							//  0 플레이어 임시 영문명 성2
		String[] PEnLastName_imsi = new String[10000];								//  0 플레이어 임시 영문명 이름
		String[] PEnLastName_imsi2 = new String[10000];								//  0 플레이어 임시 영문명 이름2
		String[] PEn_imsi = new String[10000];										//  0 플레이어 임시 영문명
		String[] PBirth_imsi = new String[10000];									//  0 플레이어 임시 생년월일
		String[] PHeight_imsi = new String[10000];									//  0 플레이어 임시 신장
		String[] tid_sofa_imsi = new String[10000];									//  0 플레이어 임시 ID TEAM
		
		String[] pslug = new String[1000];											//  0 플레이어 Slug
		String[] rating = new String[1000]; 										//	1 	평점
		// Attack Set ===========================
		String[] goals = new String [1000];											//	2 	득점 
		String[] big_chances_missed = new String [1000];							//	3 	실패한 결정적 기회
		String[] succ_dribbles  = new String [1000];								//	4 	드리블 성공
		String[] successful_dribbles_per = new String [1000];	 					//	5 	드리블 성공률	
		// Set 1 end ----------------------------------------------
		String[] total_shots = new String [1000];									//	6 	슈팅 횟수
		String[] shots_on_target = new String [1000];								//	7 	유효 슈팅
		String[] shots_off_target = new String [1000];								//	8 	무효 슈팅
		String[] goal_conversion_per = new String [1000];							//	9 	득점 전환율(득점/전체슛)
		String[] penalties_taken = new String [1000];								//	10	패널티 시도
		// Set 2 end ----------------------------------------------
		String[] penalty_goals = new String [1000];									//	11	패널티 득점
		String[] penalty_won = new String [1000];									//	12	승부차기 성공
		String[] shots_from_set_piece = new String [1000];							//	13	세트피스 슈팅
		String[] free_kick_goals = new String [1000];								//	14	프리킥 득점
		String[] goals_from_inside_the_box = new String [1000];						//	15	박스 안 득점
		// Set 3 end ----------------------------------------------	
		String[] goals_from_outside_the_box = new String [1000];					//	16	박스 밖 득점
		String[] headed_goals = new String [1000];									//	17	헤더 득점
		String[] left_foot_goals = new String [1000];								//	18	왼발 득점
		String[] right_foot_goals = new String [1000];								//	19	오른발 득점
		String[] hit_woodwork = new String [1000];									//	20	골대 강타
		// Set 4 end ----------------------------------------------
		String[] offsides = new String [1000];										//	21	오프사이드
		String[] penalty_conversion_per = new String [1000];						//	22	패널티 득점 확률
		String[] set_piece_conversion_per = new String [1000];						//	23	세트피스 득점 확률
		// Set 5 end
		
		// Defense Set ===========================================================
		String[] tackles = new String [1000];										//	24	태클 수
		String[] interceptions = new String [1000];									//	25	가로채기
		String[] penalty_committed = new String [1000];								//	26	패널티 제공
		String[] clearances = new String [1000];									//	27	걷어내기
		String[] errors_lead_to_goal = new String [1000];							//	28	실점 허용 실책
		// Set 6 end ----------------------------------------------
		String[] errors_lead_to_shot = new String [1000];							//	29	슈팅 허용 실책
		String[] own_goals = new String [1000];										//	30	자책골
		String[] dribbled_past = new String [1000];									//	31	드리블 허용
		String[] blocked_shots = new String [1000];									//	32	슈팅 블록킹
		String[] clean_sheets = new String [1000];									//	33	무실점 경기
		// Set 7 end ----------------------------------------------
		
		
		// Passing Set ============================================================
		String[] big_chances_created= new String[1000];								// 34 결정적 기회 창출
		String[] assists = new String[1000];										// 35 도움
		String[] acc_passes = new String[1000];										// 36 성공한 패스
		String[] inaccurated_passes = new String[1000];								// 37 실패한 패스
		String[] total_passes= new String[1000];									// 38 전체 패스 수
		// Set 8 end ----------------------------------------------
		String[] accurate_passes_per= new String[1000];								// 39 패스 성공률
		String[] key_passes= new String[1000];										// 40 키 패스
		String[] accurate_crosses= new String[1000];								// 41 크로스
		String[] accurate_crosses_per= new String[1000];							// 42 크로스 성공률
		String[] acc_long_ball= new String[1000];									// 43 롱 패스
		// Set 9 end ----------------------------------------------
		String[] accurate_long_balls_per= new String[1000];							// 44 롱패스 성공률
		String[] passes_to_assist= new String[1000];								// 45 패스로 달성한 도움
		// Set 10 end ---------------------------------------------
		
		
		// Other Set ==============================================================
		String[] yellow_cards = new String [1000];									//	46 경고
		String[] red_cards = new String [1000];										//	47	퇴장
		String[] ground_duels_won = new String [1000];								//	48	지면 경합 승리
		String[] ground_duels_won_per = new String [1000];							//	49	지면 경합 승률
		String[] aerial_duels_won = new String [1000];								//	50	공중 경합 승리
		// Set 11 end ---------------------------------------------
		String[] aerial_duels_won_per = new String [1000];							//	51	공중 경합 승률
		String[] total_duels_won = new String [1000];								//	52	전체 경합 승리
		String[] total_duels_won_per = new String [1000];							//	53	전체 경합 승률
		String[] minutes_played = new String [1000];								//	54	출장 시간(분)
		String[] was_fouled = new String [1000];									//	55	당한 반칙
		// Set 12 end ---------------------------------------------
		String[] fouls = new String [1000];											//	56	반칙
		String[] dispossessed = new String [1000];									//	57	비 드리블 상황에서 공격권 상실
		String[] possession_lost = new String [1000];								//	58	드리블 중 공격권 상실(턴오버)
		String[] appearances = new String [1000];									//	59	전체 출전
		String[] started = new String [1000];										//	60	선발 출전
		// Set 13 end ---------------------------------------------
		
		
		// GoalKeeper Set =========================================================
		String[] saves = new String [1000];											//	61  선방
		String[] penalties_faced = new String [1000];								//	62	진행된 패널티 킥
		String[] penalties_saved = new String [1000];								//	63	퍼널티 킥 선방
		String[] saves_from_inside_box = new String [1000];							//	64	박스 안 선방
		String[] saved_shots_from_outside_the_box = new String [1000];				//	65	박스 밖 선방
		// Set 14 end ----------------------------------------------
		String[] goals_conceded_inside_the_box = new String [1000];					//	66	박스 안 실점
		String[] goals_conceded_outside_the_box = new String [1000];				//	67	박스 밖 실점
		String[] punches = new String [1000];										//	68	펀칭
		String[] runs_out = new String [1000];										//	69	전진 수비
		String[] successful_runs_out = new String [1000];							//	70	성공한 전진 수비
		// Set 15 end ----------------------------------------------
		String[] high_claims = new String [1000];									//	71	공중볼 처리
		String[] crosses_not_claimed = new String [1000];							//	72	처리못한 크로스
		// Set 16 end ----------------------------------------------
		
		
		
		// 5대 리그 Crawling 시작	
		System.out.println("::::::::::::::::::::::선수 정보 갱신 시작 (ATTACK)::::::::::::::::::::::");
		for (int a = 0; a < 5; a ++) {
			
			//	Set 1 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 1 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, Rating, goals, big_chances_missed, succ_dribbles, successful_dribbles_per
			for (int i = 0; i < 10; i++) {		
						
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=goals%2CbigChancesMissed%2CsuccessfulDribbles%2CsuccessfulDribblesPercentage%2Crating&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
							
						// 0	플레이어 Team ID (SOFA)
						String[] tmp_tid_sofa = tmpAllPlayerList[j].split("\"id\":");
						tid_sofa_imsi[j+(i*100)] = tmp_tid_sofa[3].substring(0, tmp_tid_sofa[3].indexOf(",\""));
						
						// 0	플레이어 Slug (SOFA)
						String[] tmp_pslug = tmpAllPlayerList[j].split("slug\":\"");
						pslug[j+(i*100)] = tmp_pslug[1].substring(0, tmp_pslug[1].indexOf("\""));
									
						// 1	평점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"rating\":")+"\"rating\":".length());
						rating[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));			
						
						// 2	득점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("goals\":")+"goals\":".length());
						goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));	
						
						// 3 	실패한 결정적 기회
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf(",\"bigChancesMissed\":")+",\"bigChancesMissed\":".length());
						big_chances_missed[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));		
						
						// 4	드리블 성공
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf(",\"successfulDribbles\":")+",\"successfulDribbles\":".length());
						succ_dribbles[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
							
						// 5 	드리블 성공률	
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("successfulDribblesPercentage\":")+"successfulDribblesPercentage\":".length());
						successful_dribbles_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa1[j+(i*100)] + " | ");
//						System.out.print(tid_sofa_imsi[j+(i*100)] + " | ");
//						System.out.print(rating[j+(i*100)] + " | ");
//						System.out.print(goals[j+(i*100)] + " | ");	
//						System.out.print(big_chances_missed[j+(i*100)] + " | ");
//						System.out.print(succ_dribbles[j+(i*100)] + " | ");
//						System.out.println(successful_dribbles_per[j+(i*100)]);		
						
					}
				}
			}
			
			
			
			//	Set 2 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 2 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, total_shots, shots_on_target, shots_off_target, goal_conversion_per, penalties_taken
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=totalShots%2CshotsOnTarget%2CshotsOffTarget%2CgoalConversionPercentage%2CpenaltiesTaken&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						
						// 6 	슈팅 횟수
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("totalShots\":")+"totalShots\":".length());
						total_shots[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						// 7 	유효 슈팅
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"shotsOnTarget\":")+"\"shotsOnTarget\":".length());
						shots_on_target[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						//	8 	무효 슈팅
						tmp = html.substring(html.indexOf("\"shotsOffTarget\":")+"\"shotsOffTarget\":".length());
						shots_off_target[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
									
						//	9 	득점 전환율(득점/전체슛)
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"goalConversionPercentage\":")+"\"goalConversionPercentage\":".length());
						goal_conversion_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						//	10	패널티 시도
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"penaltiesTaken\":")+"\"penaltiesTaken\":".length());
						penalties_taken[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa2[j+(i*100)] + " | ");
//						System.out.print(total_shots[j+(i*100)] + " | ");
//						System.out.print(shots_on_target[j+(i*100)] + " | ");	
//						System.out.print(shots_off_target[j+(i*100)] + " | ");	
//						System.out.print(goal_conversion_per[j+(i*100)] + " | ");	
//						System.out.println(penalties_taken[j+(i*100)]);			
						
					}
				}
			}
			
	
						
			//	Set 3 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 3 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, penalty_goals, penalty_won, shots_from_set_piece, free_kick_goals, goals_from_inside_the_box
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=penaltyGoals%2CpenaltyWon%2CshotFromSetPiece%2CfreeKickGoal%2CgoalsFromInsideTheBox&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
											
						// 11	패널티 득점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("penaltyGoals\":")+"penaltyGoals\":".length());
						penalty_goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						// 12	승부차기 성공
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"penaltyWon\":")+"\"penaltyWon\":".length());
						penalty_won[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						//	13	세트피스 슈팅
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"shotFromSetPiece\":")+"\"shotFromSetPiece\":".length());
						shots_from_set_piece[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));							
			
						//	14	프리킥 득점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"freeKickGoal\":")+"\"freeKickGoal\":".length());
						free_kick_goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						//	15	박스 안 득점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"goalsFromInsideTheBox\":")+"\"goalsFromInsideTheBox\":".length());
						goals_from_inside_the_box[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));

//						System.out.print(pid_sofa3[j+(i*100)] + " | ");
//						System.out.print(penalty_goals[j+(i*100)] + " | ");
//						System.out.print(penalty_won[j+(i*100)] + " | ");	
//						System.out.print(shots_from_set_piece[j+(i*100)] + " | ");
//						System.out.print(free_kick_goals[j+(i*100)] + " | ");	
//						System.out.println(goals_from_inside_the_box[j+(i*100)]);			
	
					}
				}
			}
			
		
			
			//	Set 4 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 4 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, goals_from_outside_the_box, headed_goals, left_foot_goals, right_foot_goals, hit_woodwork
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=goalsFromOutsideTheBox%2CheadedGoals%2CleftFootGoals%2CrightFootGoals%2ChitWoodwork&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
											
						// 16	박스 밖 득점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("goalsFromOutsideTheBox\":")+"goalsFromOutsideTheBox\":".length());
						goals_from_outside_the_box[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
						// 17	헤더 득점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"headedGoals\":")+"\"headedGoals\":".length());
						headed_goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
						//	18	왼발 득점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"leftFootGoals\":")+"\"leftFootGoals\":".length());
						left_foot_goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
			
						//	19	오른발 득점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"rightFootGoals\":")+"\"rightFootGoals\":".length());
						right_foot_goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
													
						//	20	골대 강타
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"hitWoodwork\":")+"\"hitWoodwork\":".length());
						hit_woodwork[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa4[j+(i*100)] + " | ");
//						System.out.print(goals_from_outside_the_box[j+(i*100)] + " | ");
//						System.out.print(headed_goals[j+(i*100)] + " | ");	
//						System.out.print(left_foot_goals[j+(i*100)] + " | ");	
//						System.out.print(right_foot_goals[j+(i*100)] + " | ");
//						System.out.println(hit_woodwork[j+(i*100)]);			
	
					}
				}
			}
			
	
			
			//	Set 5 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 5 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, offsides, penalty_conversion_per, set_piece_conversion_per
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=offsides%2CpenaltyConversion%2CsetPieceConversion&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
										
						// 21	오프사이드
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("offsides\":")+"offsides\":".length());
						offsides[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						// 22	패널티 득점 확률
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"penaltyConversion\":")+"\"penaltyConversion\":".length());
						penalty_conversion_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						//	23	세트피스 득점 확률
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"setPieceConversion\":")+"\"setPieceConversion\":".length());
						set_piece_conversion_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa5[j+(i*100)] + " | ");
//						System.out.print(offsides[j+(i*100)] + " | ");
//						System.out.print(penalty_conversion_per[j+(i*100)] + " | ");	
//						System.out.println(set_piece_conversion_per[j+(i*100)]);	
						
					}
				}
			}
			
			
			
			//  Defense Set
			//	Set 6 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::선수 정보 갱신 시작 (DEFENCE)::::::::::::::::::::::");
			System.out.println("::::::::::::::::::::::Set 6 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, tackles, interceptions, penalty_committed, clearances, errors_lead_to_goal
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=tackles%2Cinterceptions%2CpenaltyConceded%2Cclearances%2CerrorLeadToGoal&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa6[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
																
						// 24	태클 수
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("tackles\":")+"tackles\":".length());
						tackles[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						// 25	가로채기
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"interceptions\":")+"\"interceptions\":".length());
						interceptions[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
													
						// 26	패널티 제공
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"penaltyConceded\":")+"\"penaltyConceded\":".length());
						penalty_committed[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						// 27	걷어내기
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("clearances\":")+"clearances\":".length());
						clearances[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
											
						// 28	실점 허용 실책
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"errorLeadToGoal\":")+"\"errorLeadToGoal\":".length());
						errors_lead_to_goal[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa6[j+(i*100)] + " | ");
//						System.out.print(tackles[j+(i*100)] + " | ");
//						System.out.print(interceptions[j+(i*100)] + " | ");
//						System.out.print(penalty_committed[j+(i*100)] + " | ");	
//						System.out.print(clearances[j+(i*100)] + " | ");
//						System.out.println(errors_lead_to_goal[j+(i*100)]);	
								
					}
				}
			}
			
						
			
			//	Set 7 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 7 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, errors_lead_to_shot, own_goals, dribbled_past, blocked_shots, clean_sheets
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=errorLeadToShot%2CownGoals%2CdribbledPast%2CblockedShots%2CcleanSheet&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa7[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
																
						// 29 슈팅 허용 실책
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("errorLeadToShot\":")+"errorLeadToShot\":".length());
						errors_lead_to_shot[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
											
						// 30	자책골
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("ownGoals\":")+"ownGoals\":".length());
						own_goals[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						// 31	드리블 허용
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("dribbledPast\":")+"dribbledPast\":".length());
						dribbled_past[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						// 32	슈팅 블록킹
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("blockedShots\":")+"blockedShots\":".length());
						blocked_shots[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						// 33	무실점 경기
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"cleanSheet\":")+"\"cleanSheet\":".length());
						clean_sheets[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa7[j+(i*100)] + " | ");
//						System.out.print(errors_lead_to_shot[j+(i*100)] + " | ");
//						System.out.print(own_goals[j+(i*100)] + " | ");
//						System.out.print(dribbled_past[j+(i*100)] + " | ");	
//						System.out.print(blocked_shots[j+(i*100)] + " | ");
//						System.out.println(clean_sheets[j+(i*100)]);		
						
					}
				}
			}
			
					
			
			//  Passing Set
			//	Set 8 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::선수 정보 갱신 시작 (PASSING)::::::::::::::::::::::");
			System.out.println("::::::::::::::::::::::Set 8 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, big_chances_created, assists, acc_passes, inaccurated_passes, total_passes
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=bigChancesCreated%2Cassists%2CaccuratePasses%2CinaccuratePasses%2CtotalPasses&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa8[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));					
										
						// 34	결정적 기회 창출
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("bigChancesCreated\":")+"bigChancesCreated\":".length());
						big_chances_created[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
											
						// 35 도움
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"assists\":")+"\"assists\":".length());
						assists[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
											
						// 36 성공한 패스
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"accuratePasses\":")+"\"accuratePasses\":".length());
						acc_passes[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
													
						// 37 실패한 패스
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("inaccuratePasses\":")+"inaccuratePasses\":".length());
						inaccurated_passes[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
											
						// 38 전체 패스 수
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"totalPasses\":")+"\"totalPasses\":".length());
						total_passes[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa8[j+(i*100)] + " | ");
//						System.out.print(big_chances_created[j+(i*100)] + " | ");
//						System.out.print(assists[j+(i*100)] + " | ");	
//						System.out.print(acc_passes[j+(i*100)] + " | ");
//						System.out.print(inaccurated_passes[j+(i*100)] + " | ");
//						System.out.println(total_passes[j+(i*100)]);	
					
					}
				}
			}
			
					
			
			//	Set 9 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 9 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, accurate_passes_per, key_passes, accurate_crosses, accurate_crosses_per, acc_long_ball
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=accuratePassesPercentage%2CaccurateOwnHalfPasses%2CaccurateOppositionHalfPasses%2CaccurateFinalThirdPasses%2CkeyPasses&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa9[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
										
						// 39 패스 성공률
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("accuratePassesPercentage\":")+"accuratePassesPercentage\":".length());
						accurate_passes_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
						// 40 키 패스
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"accurateOwnHalfPasses\":")+"\"accurateOwnHalfPasses\":".length());
						key_passes[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));					
						
						// 41 크로스
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"accurateOppositionHalfPasses\":")+"\"accurateOppositionHalfPasses\":".length());
						accurate_crosses[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
													
						// 42 크로스 성공률
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("accurateFinalThirdPasses\":")+"accurateFinalThirdPasses\":".length());
						accurate_crosses_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						// 43 롱 패스
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"keyPasses\":")+"\"keyPasses\":".length());
						acc_long_ball[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa9[j+(i*100)] + " | ");
//						System.out.print(accurate_passes_per[j+(i*100)] + " | ");
//						System.out.print(key_passes[j+(i*100)] + " | ");	
//						System.out.print(accurate_crosses[j+(i*100)] + " | ");
//						System.out.print(accurate_crosses_per[j+(i*100)] + " | ");
//						System.out.println(acc_long_ball[j+(i*100)]);	
				
					}
				}
			}
				
				
	
			//	Set 10 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 10 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, accurate_long_balls_per, passes_to_assist
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=accurateLongBallsPercentage%2CpassToAssist&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa10[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
											
						// 44 롱패스 성공률
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("accurateLongBallsPercentage\":")+"accurateLongBallsPercentage\":".length());
						accurate_long_balls_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));					
						
						// 45 패스로 달성한 도움
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"passToAssist\":")+"\"passToAssist\":".length());
						passes_to_assist[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa10[j+(i*100)] + " | ");
//						System.out.print(accurate_long_balls_per[j+(i*100)] + " | ");
//						System.out.println(passes_to_assist[j+(i*100)]);
					}
				}
			}
			
			
						
			//  Other Set
			//	Set 11 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::선수 정보 갱신 시작 (OTHER)::::::::::::::::::::::");
			System.out.println("::::::::::::::::::::::Set 11 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, yellow_cards, red_cards, ground_duels_won, ground_duels_won_per, aerial_duels_won
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=yellowCards%2CredCards%2CgroundDuelsWon%2CgroundDuelsWonPercentage%2CaerialDuelsWon&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa11[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
																
						// 46  경고
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("yellowCards\":")+"yellowCards\":".length());
						yellow_cards[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						// 47	퇴장
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"redCards\":")+"\"redCards\":".length());
						red_cards[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));					
						
						// 48	지면 경합 승리
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"groundDuelsWon\":")+"\"groundDuelsWon\":".length());
						ground_duels_won[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));							
						
						// 49	지면 경합 승률
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("groundDuelsWonPercentage\":")+"groundDuelsWonPercentage\":".length());
						ground_duels_won_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
								
						// 50	공중 경합 승리
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"aerialDuelsWon\":")+"\"aerialDuelsWon\":".length());
						aerial_duels_won[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
	
//						System.out.print(pid_sofa11[j+(i*100)] + " | ");
//						System.out.print(yellow_cards[j+(i*100)] + " | ");
//						System.out.print(red_cards[j+(i*100)] + " | ");	
//						System.out.print(ground_duels_won[j+(i*100)] + " | ");
//						System.out.print(ground_duels_won_per[j+(i*100)] + " | ");
//						System.out.println(aerial_duels_won[j+(i*100)]);	
								
					}
				}
			}
			
					
			
			//	Set 12 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 12 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, aerial_duels_won_per, total_duels_won, total_duels_won_per, minutes_played, was_fouled
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=aerialDuelsWonPercentage%2CtotalDuelsWon%2CtotalDuelsWonPercentage%2CminutesPlayed%2CwasFouled&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa12[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
																
						// 51	공중 경합 승률
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("aerialDuelsWonPercentage\":")+"aerialDuelsWonPercentage\":".length());
						aerial_duels_won_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
											
						// 52	전체 경합 승리
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"totalDuelsWon\":")+"\"totalDuelsWon\":".length());
						total_duels_won[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						// 53	전체 경합 승률
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"totalDuelsWonPercentage\":")+"\"totalDuelsWonPercentage\":".length());
						total_duels_won_per[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						// 54	출장 시간(분)
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("minutesPlayed\":")+"minutesPlayed\":".length());
						minutes_played[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));					
						
						// 55	당한 반칙
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"wasFouled\":")+"\"wasFouled\":".length());
						was_fouled[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa12[j+(i*100)] + " | ");
//						System.out.print(aerial_duels_won_per[j+(i*100)] + " | ");
//						System.out.print(total_duels_won[j+(i*100)] + " | ");	
//						System.out.print(total_duels_won_per[j+(i*100)] + " | ");
//						System.out.print(minutes_played[j+(i*100)] + " | ");
//						System.out.println(was_fouled[j+(i*100)]);	
								
					}
				}
			}
						
			
			
			//	Set 13 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 13 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, fouls, dispossessed, possession_lost, appearances, started
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=fouls%2Cdispossessed%2CpossessionLost%2Cappearances%2CmatchesStarted&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa13[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
															
						// 56	반칙
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("fouls\":")+"fouls\":".length());
						fouls[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));					
						
						// 57	비 드리블 상황에서 공격권 상실
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"dispossessed\":")+"\"dispossessed\":".length());
						dispossessed[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						// 58	드리블 중 공격권 상실(턴오버)
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"possessionLost\":")+"\"possessionLost\":".length());
						possession_lost[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						// 59	전체 출전
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("appearances\":")+"appearances\":".length());
						appearances[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));					
						
						// 60	선발 출전
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"matchesStarted\":")+"\"matchesStarted\":".length());
						started[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa13[j+(i*100)] + " | ");
//						System.out.print(fouls[j+(i*100)] + " | ");
//						System.out.print(dispossessed[j+(i*100)] + " | ");
//						System.out.print(possession_lost[j+(i*100)] + " | ");
//						System.out.print(appearances[j+(i*100)] + " | ");
//						System.out.println(started[j+(i*100)]);		
						
					}
				}
			}
			
					
			
			//  GoalKeeper Set
			//	Set 14 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::선수 정보 갱신 시작 (GOALKEEPER)::::::::::::::::::::::");
			System.out.println("::::::::::::::::::::::Set 14 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, saves, penalties_faced, penalties_saved, saves_from_inside_box, saved_shots_from_outside_the_box
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=saves%2CpenaltyFaced%2CpenaltySave%2CsavedShotsFromInsideTheBox%2CsavedShotsFromOutsideTheBox&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa14[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
																
						// 61  선방
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("saves\":")+"saves\":".length());
						saves[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));					
						
						// 62	진행된 패널티 킥
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"penaltyFaced\":")+"\"penaltyFaced\":".length());
						penalties_faced[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));						
						
						// 63	퍼널티 킥 선방
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"penaltySave\":")+"\"penaltySave\":".length());
						penalties_saved[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));							
						
						// 64	박스 안 선방
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("savedShotsFromInsideTheBox\":")+"savedShotsFromInsideTheBox\":".length());
						saves_from_inside_box[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						// 65	박스 밖 선방
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"savedShotsFromOutsideTheBox\":")+"\"savedShotsFromOutsideTheBox\":".length());
						saved_shots_from_outside_the_box[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa14[j+(i*100)] + " | ");
//						System.out.print(saves[j+(i*100)] + " | ");
//						System.out.print(penalties_faced[j+(i*100)] + " | ");	
//						System.out.print(penalties_saved[j+(i*100)] + " | ");
//						System.out.print(saves_from_inside_box[j+(i*100)] + " | ");
//						System.out.println(saved_shots_from_outside_the_box[j+(i*100)]);	
								
					}
				}
			}
			
			
			
			
			//	Set 15 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 15 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, goals_conceded_inside_the_box, goals_conceded_outside_the_box, punches, runs_out, successful_runs_out
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=goalsConcededInsideTheBox%2CgoalsConcededOutsideTheBox%2Cpunches%2CrunsOut%2CsuccessfulRunsOut&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa15[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
														
						// 66	박스 안 실점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("goalsConcededInsideTheBox\":")+"goalsConcededInsideTheBox\":".length());
						goals_conceded_inside_the_box[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
											
						// 67	박스 밖 실점
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"goalsConcededOutsideTheBox\":")+"\"goalsConcededOutsideTheBox\":".length());
						goals_conceded_outside_the_box[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						// 68	펀칭
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"punches\":")+"\"punches\":".length());
						punches[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
												
						// 69	전진 수비
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("runsOut\":")+"runsOut\":".length());
						runs_out[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
											
						// 70	성공한 전진 수비
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"successfulRunsOut\":")+"\"successfulRunsOut\":".length());
						successful_runs_out[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa15[j+(i*100)] + " | ");
//						System.out.print(goals_conceded_inside_the_box[j+(i*100)] + " | ");
//						System.out.print(goals_conceded_outside_the_box[j+(i*100)] + " | ");
//						System.out.print(punches[j+(i*100)] + " | ");
//						System.out.print(runs_out[j+(i*100)] + " | ");
//						System.out.println(successful_runs_out[j+(i*100)]);	
								
					}
				}
			}
							
				
				
			//	Set 16 메모리 등록 Start =====================================================
			System.out.println("::::::::::::::::::::::Set 16 메모리 등록 Start::::::::::::::::::::::");
			//	pid_sofa, high_claims, crosses_not_claimed
			for (int i = 0; i < 10; i++) {		
				Connection.Response response = Jsoup.connect("https://api.sofascore.com/api/v1/unique-tournament/"+league[a]+"/season/"+season[a]+"/statistics?limit=100&offset="+(i*100)+"&order=-rating&accumulation=total&fields=highClaims%2CcrossesNotClaimed&filters=position.in.G~D~M~F")
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
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
						pid_sofa16[j+(i*100)] = tmp.substring(0, tmp.indexOf("}"));
														
						// 71	공중볼 처리
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("highClaims\":")+"highClaims\":".length());
						high_claims[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
								
						// 72	처리못한 크로스
						tmp = tmpAllPlayerList[j].substring(tmpAllPlayerList[j].indexOf("\"crossesNotClaimed\":")+"\"crossesNotClaimed\":".length());
						crosses_not_claimed[j+(i*100)] = tmp.substring(0, tmp.indexOf(",\""));
						
//						System.out.print(pid_sofa16[j+(i*100)] + " | ");
//						System.out.print(high_claims[j+(i*100)] + " | ");
//						System.out.println(crosses_not_claimed[j+(i*100)]);	
							
					}
				}
			}
			
			
				
			// DB에 등록 시작 (PlayerStat, PlayerSofa)
			for (int i = 0; i < 1000 ; i++) {
				if( pid_sofa1[i] == null ) {	break;		}
				
				if ( pid_sofa1[i].equals(pid_sofa2[i]) && pid_sofa1[i].equals(pid_sofa3[i]) &&  pid_sofa1[i].equals(pid_sofa4[i]) &&  pid_sofa1[i].equals(pid_sofa5[i])
						&& pid_sofa1[i].equals(pid_sofa6[i]) && pid_sofa1[i].equals(pid_sofa7[i]) &&  pid_sofa1[i].equals(pid_sofa8[i]) &&  pid_sofa1[i].equals(pid_sofa9[i]) 
						&& pid_sofa1[i].equals(pid_sofa10[i]) && pid_sofa1[i].equals(pid_sofa11[i]) &&  pid_sofa1[i].equals(pid_sofa12[i]) &&  pid_sofa1[i].equals(pid_sofa13[i]) 
						&& pid_sofa1[i].equals(pid_sofa14[i]) && pid_sofa1[i].equals(pid_sofa15[i]) &&  pid_sofa1[i].equals(pid_sofa16[i])) {
					System.out.print("Check! PlayerStatInsert ["+ (i+1) +"]번째 League="+(a+1)+"번째 ");
				} else {
					System.out.println("ERROR !!! PlayerStatInsert ["+ (i+1) +"]번째");
					break;
				}
				
				
				// 타입 변경
				int i_pid_sofa = Integer.parseInt(pid_sofa1[i]);
				int i_sid_sofa = season[a];
				int i_lid_sofa = league[a];
				int i_tid_sofa = Integer.parseInt(tid_sofa_imsi[i]);
				
				// Attack
				double d_rating = Double.parseDouble(rating[i]);
				int i_goals = Integer.parseInt(goals[i]);
				int i_big_chances_missed = Integer.parseInt(big_chances_missed[i]);
				int i_succ_dribbles = Integer.parseInt(succ_dribbles[i]);
				double d_successful_dribbles_per = Double.parseDouble(successful_dribbles_per[i]);
				int i_total_shots = Integer.parseInt(total_shots[i]);
				int i_shots_on_target = Integer.parseInt(shots_on_target[i]);
				int i_shots_off_target = Integer.parseInt(shots_off_target[i]);
				double d_goal_conversion_per = Double.parseDouble(goal_conversion_per[i]);
				int i_penalties_taken = Integer.parseInt(penalties_taken[i]);
				int i_penalty_goals = Integer.parseInt(penalty_goals[i]);
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
				
				// Defense
				int i_tackles = Integer.parseInt(tackles[i]);
				int i_interceptions = Integer.parseInt(interceptions[i]);
				int i_penalty_committed = Integer.parseInt(penalty_committed[i]);
				int i_clearances = Integer.parseInt(clearances[i]);
				int i_errors_lead_to_goal = Integer.parseInt(errors_lead_to_goal[i]);
				int i_errors_lead_to_shot = Integer.parseInt(errors_lead_to_shot[i]);
				int i_own_goals = Integer.parseInt(own_goals[i]);
				int i_dribbled_past = Integer.parseInt(dribbled_past[i]);
				int i_blocked_shots = Integer.parseInt(blocked_shots[i]);
				int i_clean_sheets = Integer.parseInt(clean_sheets[i]);
				
				// Passing
				int i_big_chances_created = Integer.parseInt(big_chances_created[i]);
				int i_assists = Integer.parseInt(assists[i]);
				int i_acc_passes = Integer.parseInt(acc_passes[i]);
				int i_inaccurated_passes = Integer.parseInt(inaccurated_passes[i]);
				int i_total_passes = Integer.parseInt(total_passes[i]);
				double d_accurate_passes_per = Double.parseDouble(accurate_passes_per[i]);
				int i_key_passes = Integer.parseInt(key_passes[i]);
				int i_accurate_crosses = Integer.parseInt(accurate_crosses[i]);
				double d_accurate_crosses_per = Double.parseDouble(accurate_crosses_per[i]);
				int i_acc_long_ball = Integer.parseInt(acc_long_ball[i]);
				double d_accurate_long_balls_per = Double.parseDouble(accurate_long_balls_per[i]);
				int i_passes_to_assist = Integer.parseInt(passes_to_assist[i]);
				
				// Other
				int i_yellow_cards = Integer.parseInt(yellow_cards[i]);
				int i_red_cards = Integer.parseInt(red_cards[i]);
				int i_ground_duels_won = Integer.parseInt(ground_duels_won[i]);
				double d_ground_duels_won_per = Double.parseDouble(ground_duels_won_per[i]);
				int i_aerial_duels_won = Integer.parseInt(aerial_duels_won[i]);
				double d_aerial_duels_won_per = Double.parseDouble(aerial_duels_won_per[i]);
				int i_total_duels_won = Integer.parseInt(total_duels_won[i]);
				double d_total_duels_won_per = Double.parseDouble(total_duels_won_per[i]);
				int i_minutes_played = Integer.parseInt(minutes_played[i]);
				int i_was_fouled = Integer.parseInt(was_fouled[i]);
				int i_fouls = Integer.parseInt(fouls[i]);
				int i_dispossessed = Integer.parseInt(dispossessed[i]);
				int i_possession_lost = Integer.parseInt(possession_lost[i]);
				int i_appearances = Integer.parseInt(appearances[i]);
				int i_started = Integer.parseInt(started[i]);
				
				// GoalKeeper
				int i_saves = Integer.parseInt(saves[i]);
				int i_penalties_faced = Integer.parseInt(penalties_faced[i]);
				int i_penalties_saved = Integer.parseInt(penalties_saved[i]);
				int i_saves_from_inside_box = Integer.parseInt(saves_from_inside_box[i]);
				int i_saved_shots_from_outside_the_box = Integer.parseInt(saved_shots_from_outside_the_box[i]);
				int i_goals_conceded_inside_the_box = Integer.parseInt(goals_conceded_inside_the_box[i]);
				int i_goals_conceded_outside_the_box = Integer.parseInt(goals_conceded_outside_the_box[i]);
				int i_punches = Integer.parseInt(punches[i]);
				int i_runs_out = Integer.parseInt(runs_out[i]);
				int i_successful_runs_out = Integer.parseInt(successful_runs_out[i]);
				int i_high_claims = Integer.parseInt(high_claims[i]);
				int i_crosses_not_claimed = Integer.parseInt(crosses_not_claimed[i]);
				
				
				String s_psid = pid_sofa1[i] + "/" + i_sid_sofa + "/" + i_lid_sofa + "/" + tid_sofa_imsi[i];
				
				// 쿼리 처리
				dao.insertPlayerStat(s_psid, i_pid_sofa, i_sid_sofa, i_lid_sofa, i_tid_sofa, 
						d_rating, i_goals, i_big_chances_missed, i_succ_dribbles, d_successful_dribbles_per, i_total_shots, i_shots_on_target, i_shots_off_target, d_goal_conversion_per, i_penalties_taken, i_penalty_goals, i_penalty_won, i_shots_from_set_piece, i_free_kick_goals, i_goals_from_inside_the_box, 
						i_goals_from_outside_the_box, i_headed_goals, i_left_foot_goals, i_right_foot_goals, i_hit_woodwork, i_offsides, d_penalty_conversion_per, d_set_piece_conversion_per,
						i_tackles, i_interceptions, i_penalty_committed, i_clearances, i_errors_lead_to_goal, i_errors_lead_to_shot, i_own_goals, i_dribbled_past, i_blocked_shots, i_clean_sheets, 
						i_big_chances_created, i_assists, i_acc_passes, i_inaccurated_passes, i_total_passes, d_accurate_passes_per, i_key_passes, i_accurate_crosses, d_accurate_crosses_per, i_acc_long_ball, d_accurate_long_balls_per, i_passes_to_assist, 
						i_yellow_cards, i_red_cards, i_ground_duels_won, d_ground_duels_won_per, i_aerial_duels_won, d_aerial_duels_won_per, i_total_duels_won, d_total_duels_won_per, i_minutes_played, i_was_fouled, i_fouls, i_dispossessed, i_possession_lost, i_appearances, i_started, 
						i_saves, i_penalties_faced, i_penalties_saved, i_saves_from_inside_box, i_saved_shots_from_outside_the_box, i_goals_conceded_inside_the_box, i_goals_conceded_outside_the_box, i_punches, i_runs_out, i_successful_runs_out, i_high_claims, i_crosses_not_claimed);
				
				
				
				// PlayerSofa
				String words[] = pslug[i].split("-");
				String change_word[] = new String[words.length];
				String PEn = "";
				
				for(int j = 0; j < words.length; j++) {
					String firstWord = words[j].substring(0, 1).toUpperCase();
					String remainWord = words[j].substring(1).toLowerCase();
					change_word[j] = firstWord + remainWord;
					
					PEn = PEn + change_word[j];
					if(j+1 < words.length)
						PEn += " ";
				}
				
				Connection.Response response = Jsoup.connect("https://www.sofascore.com/player/"+pslug[i]+"/"+pid_sofa1[i])
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
		                .execute();
				Document document = response.parse();
				
				String html = document.html();
				String text = document.text();		
				
				html = html.replace("\n", "");
				
				String tmp = "";
				
				System.out.print("pslug[i]=" + pslug[i] + " / pid_sofa1[i]=" + pid_sofa1[i] + " ");
//				System.out.println("pslug[i]=" + pslug[i] + " / pid_sofa1[i]=" + pid_sofa1[i] + " / " + html);
				
				tmp = html.substring(html.indexOf("birthDate\":\"")+"birthDate\":\"".length());
				String PBirth = tmp.substring(0, tmp.indexOf("T"));
				
				String PImg = "https://api.sofascore.app/api/v1/player/"+pid_sofa1[i]+"/image";
				
				tmp = tmp.substring(tmp.indexOf("src=\"/static/images/flags/")+"src=\"/static/images/flags/".length());
				
				String PNationInitial = tmp.substring(0, tmp.indexOf("."));
				String PNationImg = "https://www.sofascore.com/static/images/flags/"+PNationInitial+".png";
				
				tmp = html.substring(html.indexOf("position\":\"")+"position\":\"".length());
				String PPosition = tmp.substring(0, tmp.indexOf("\""));
				
				tmp = tmp.substring(tmp.indexOf("height\":")+"height\":".length());
				String PHeight = tmp.substring(0, tmp.indexOf(","));
				
				String PFoot = "";
				if(tmp.contains("preferredFoot\":\"")) {
				tmp = tmp.substring(tmp.indexOf("preferredFoot\":\"")+"preferredFoot\":\"".length());
				PFoot = tmp.substring(0, tmp.indexOf("\""));
				} else PFoot = "none";
				
				tmp = tmp.substring(tmp.indexOf("alpha2\":\"")+"alpha2\":\"".length());
				String PNation = tmp.substring(0, tmp.indexOf("\""));
				
				tmp = tmp.substring(tmp.indexOf("shirtNumber\":")+"shirtNumber\":".length());
				String PNum = tmp.substring(0, tmp.indexOf(","));
				
				int i_PNum;
				int i_PHeight;
				
				if(PNum.equals("\"Shirt number\"")) {
					i_PNum = 0;	
				} else if(PNum.length() >= 3) {
					if(html.contains("jerseyNumber\":\"")) {
						tmp = html.substring(html.indexOf("jerseyNumber\":\"")+"jerseyNumber\":\"".length());
						PNum = tmp.substring(0, tmp.indexOf("\""));
						System.out.println("PNum = " + PNum);
						i_PNum = Integer.parseInt(PNum);
					} else {
						i_PNum = 0;
					}
				} else {
					i_PNum = Integer.parseInt(PNum);
				}
					
				if(PHeight.equals("\"Height\"")) { 
					i_PHeight = 0;	
				} else i_PHeight = Integer.parseInt(PHeight);
				
				dao.insertPlayerSofa(i_pid_sofa, PImg, i_PNum, PEn, pslug[i], i_lid_sofa, i_tid_sofa, i_PHeight, PBirth, PNation, PNationImg, PFoot, PPosition);
			}			
		}		
		
		
		
		// PlayerDaum
		for (int i = 0; i < 5; i++) {
			for(int j = 0; j < 20; j++) {
				
				if(lname_daum[i].equals("bundesliga") && j==18) break;
				
				Connection.Response response = Jsoup.connect("https://sports.daum.net/prx/hermes/api/person/list.json?leagueCode="+lname_daum[i]+"&teamId="+tid_daum[i][j])
		                .method(Connection.Method.GET)
		                .ignoreContentType(true).ignoreHttpErrors(true)
		                .execute();
				Document document = response.parse();
				
				String html = document.html();
				String text = document.text();
				
				text = text.replace("\n", "");
				
				String tmp = "";
				String[] tmpPlayerList = text.split("\\{\"personId\":");
				
				for(int k = 0; k < tmpPlayerList.length-1; k++) {
					
					pid_daum[k+(j*100)+(i*2000)] = tmpPlayerList[k+1].substring(0, tmpPlayerList[k+1].indexOf(","));
					
					tmp = tmpPlayerList[k+1].substring(tmpPlayerList[k+1].indexOf("cpPersonId\":\"")+"cpPersonId\":\"".length());
					pid_sofa_imsi[k+(j*100)+(i*2000)] = tmp.substring(0, tmp.indexOf("\""));
					
					tmp = tmp.substring(tmp.indexOf("backNumber\":\"")+"backNumber\":\"".length());
					PNum_imsi[k+(j*100)+(i*2000)] = tmp.substring(0, tmp.indexOf("\""));
					
					tmp = tmp.substring(tmp.indexOf("nameKo\":\"")+"nameKo\":\"".length());
					PKo_imsi[k+(j*100)+(i*2000)] = tmp.substring(0, tmp.indexOf("\""));
					
					tmp = tmp.substring(tmp.indexOf("birthDate\":")+"birthDate\":".length());
					if(tmp.substring(0,1).equals("\"")) 
						PBirth_imsi[k+(j*100)+(i*2000)] = tmp.substring(1, tmp.indexOf("\","));
					else 
						PBirth_imsi[k+(j*100)+(i*2000)] = tmp.substring(0, tmp.indexOf(","));
					
					
					tmp = tmp.substring(tmp.indexOf("position\":{\"nameMain\":\"")+"position\":{\"nameMain\":\"".length());
					PPosition_imsi[k+(j*100)+(i*2000)] = tmp.substring(0, 1);
	
					tmp = tmp.substring(tmp.indexOf("firstNameEn\":\"")+"firstNameEn\":\"".length());
					PEnFirstName_imsi[k+(j*100)+(i*2000)] = tmp.substring(0, tmp.indexOf("\""));					
					String PEnFirstName_imsi2_insert[] = tmp.split(" ");
					PEnFirstName_imsi2[k+(j*100)+(i*2000)] = PEnFirstName_imsi2_insert[0];
						
					tmp = tmp.substring(tmp.indexOf("lastNameEn\":\"")+"lastNameEn\":\"".length());
					PEnLastName_imsi[k+(j*100)+(i*2000)] = tmp.substring(0, tmp.indexOf("\""));
					String PEnLastName_imsi2_insert[] = tmp.split(" ");
					PEnLastName_imsi2[k+(j*100)+(i*2000)] = PEnLastName_imsi2_insert[0];
										
//					tmp = tmp.substring(tmp.indexOf("nameEn\":\"")+"nameEn\":\"".length());
//					PEn_imsi[k+(j*100)+(i*2000)] = tmp.substring(0, tmp.indexOf(" "));
//					String PEn_insert[] = tmp.split(" ");
//					PEn_imsi[k+(j*100)+(i*2000)] = PEn_insert[0];
							
//					tmp = tmp.substring(tmp.indexOf("height\":")+"height\":".length());
//					PHeight_imsi[k+(j*100)+(i*2000)] = tmp.substring(0, tmp.indexOf("."));
					
					
				}
			}
		}
				
				
		//DB에 등록 시작 (PlayerDaum)
		for(int i = 0; i < 10000; i++) {
			if( pid_daum[i] == null ) {	continue;		}
			
			String pid_daum2 = pid_daum[i];
			String pid_sofa_imsi2 = pid_sofa_imsi[i];
			
			String PNum = "";
			if(PNum_imsi[i].equals(""))
				PNum = "0";
			else
				PNum = PNum_imsi[i];
			
			String PKo = PKo_imsi[i];
			
			String PBirth = "";
			if(PBirth_imsi[i].equals("null"))
				PBirth = "null";
			else
				PBirth = PBirth_imsi[i].substring(4,6) + '-' + PBirth_imsi[i].substring(6);
			
				
			String PPosition = PPosition_imsi[i];
			String PEnFirstName = PEnFirstName_imsi[i];
			String PEnFirstName2 = PEnFirstName_imsi2[i]; 
			String PEnLastName = PEnLastName_imsi[i];
			String PEnLastName2 = PEnLastName_imsi2[i];
			
//			String PEn = PEn_imsi[i];
//			String PBirth = PBirth_imsi[i];
//			String PHeight = PHeight_imsi[i];
			
//			if(PBirth.substring(0,1).equals("\""))
//				PBirth = PBirth.substring(1,5) + "-" + PBirth.substring(5,7)+ "-" +PBirth.substring(7,9);
//			else
//				PBirth = "(null)";
//			
//	        if(PHeight.contains("null")) 
//	        	PHeight = "0";
//	        else 
//	        	PHeight.substring(0,3);
//	        
//	        if(PNum == null || PNum.trim().equals("")) {
//	        	PNum = "0";
//	        }
	        
			int i_pid_daum = Integer.parseInt(pid_daum2);
			int i_pid_sofa = Integer.parseInt(pid_sofa_imsi2);
			int i_PNum = Integer.parseInt(PNum);
//			int i_PHeight = Integer.parseInt(PHeight);
			
			
			System.out.print("Crawler_ PlayerDaum | " + i_pid_daum + " | " + i_pid_sofa + " | " + PNum + " | " + PKo + " | " + PBirth + " | " + PPosition + " | " + PEnLastName + " | " + PEnLastName2 + " | " + PEnFirstName + " | " + PEnFirstName2 + " | ");
			
			dao.insertPlayerDaum(i_pid_daum, i_pid_sofa, PKo, i_PNum, PBirth, PPosition, PEnLastName, PEnLastName2, PEnFirstName, PEnFirstName2);
		}
		
		
	}
}