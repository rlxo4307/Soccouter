package org.jsoup.sofascore.goalkeeper;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import DAO.SofaDefenseDAO2;

public class SofaTotalGoalKeeper {

	public static void main(String[] args) throws IOException {
		System.out.println("::::::::::::::::::::::선수 정보 갱신 시작 (ATTACK)::::::::::::::::::::::");
		SofaDefenseDAO2 dao = new SofaDefenseDAO2();
		
		String league = "17";															// 리그 코드
		String season = "37036";														// 시즌 코드
		

		// 메모리 set 준비 =========================================================
		// Other set end ----------------------------------------------
		String[] tid_sofa1 = new String[1000];                                 		//  0   팀 ID
		String[] tid_sofa2 = new String[1000];                                 		//  0   팀 ID
		String[] tid_sofa3 = new String[1000];                                 		//  0   팀 ID
		String[] pid_sofa1 = new String[1000];										//	0	플레이어 ID		
		String[] pid_sofa2 = new String[1000];										//	0	플레이어 ID
		String[] pid_sofa3 = new String[1000];										//	0	플레이어 ID
		// Other set end
		
		
		// Other Set =========================================================
		String[] saves = new String [1000];											//	66  선방
		String[] penalties_faced = new String [1000];								//	67	진행된 패널티 킥
		String[] penalties_saved = new String [1000];								//	68	퍼널티 킥 선방
		String[] saves_from_inside_box = new String [1000];							//	69	박스 안 선방
		String[] saved_shots_from_outside_the_box = new String [1000];				//	70	박스 밖 선방
		// Set 1 end ----------------------------------------------
		String[] goals_conceded_inside_the_box = new String [1000];					//	71	박스 안 실점
		String[] goals_conceded_outside_the_box = new String [1000];				//	72	박스 밖 실점
		String[] punches = new String [1000];										//	73	펀칭
		String[] runs_out = new String [1000];										//	74	전진 수비
		String[] successful_runs_out = new String [1000];							//	75	성공한 전진 수비
		// Set 2 end ----------------------------------------------
		String[] high_claims = new String [1000];									//	76	공중볼 처리
		String[] crosses_not_claimed = new String [1000];							//	77	처리못한 크로스
		// Set 3 end ----------------------------------------------
		
		
		
		//	GoalKeeper Set 시작 ========================================================= 
		//	Set 1 메모리 등록 Start =====================================================
		//	saves, penalties_faced, penalties_saved, saves_from_inside_box, saved_shots_from_outside_the_box
		for (int i = 0; i < 1; i++) {		
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
					System.out.print("saves "+saves[j+(i*100)]+" / ");
					
					
		            // 67 penalties_faced 진행된 패널티 킥
					if(sentance[j+1].indexOf(",\"penaltyFaced\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"penaltyFaced\":")+",\"penaltyFaced\":".length());
						penalties_faced[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						penalties_faced[j+(i*100)] = "0";
					}
					System.out.print("penalties_faceds "+penalties_faced[j+(i*100)]+" / ");
		            
		            
					// 68 penalties_saved 패널티 킥 선방
		            if(sentance[j+1].indexOf(",\"penaltySave\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"penaltySave\":")+",\"penaltySave\":".length());
		            	penalties_saved[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	penalties_saved[j+(i*100)] = "0";
		            }
		            System.out.print("penalties_saved "+penalties_saved[j+(i*100)]+" / ");
		            
		            
		            // 69 saves_from_inside_box 박스 안 선방
		            if(sentance[j+1].indexOf(",\"savedShotsFromInsideTheBox\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"savedShotsFromInsideTheBox\":")+",\"savedShotsFromInsideTheBox\":".length());
		            	saves_from_inside_box[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	saves_from_inside_box[j+(i*100)] = "0";
		            }
		            System.out.print("saves_from_inside_box "+saves_from_inside_box[j+(i*100)]+" / ");
					
		            // 70 saved_shots_from_outside_the_box 박스 밖 선방
		            if(sentance[j+1].indexOf(",\"savedShotsFromOutsideTheBox\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"savedShotsFromOutsideTheBox\":")+",\"savedShotsFromOutsideTheBox\":".length());
		            	saved_shots_from_outside_the_box[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	saved_shots_from_outside_the_box[j+(i*100)] = "0";
		            }
		            System.out.print("saved_shots_from_outside_the_box "+saved_shots_from_outside_the_box[j+(i*100)]+" / ");
		            
		            
		            // b pid_sofa 플레이어ID
		            String[] temp = sentance[j+1].split(",\"id");
		            if(temp[1].indexOf(":") > 0 ){
		            	String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
		            	pid_sofa1[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
		            } else {
		            	pid_sofa1[j+(i*100)] = "0";
		            }
		            System.out.print("pid_sofa1 "+pid_sofa1[j+(i*100)]+" / ");
		            
		            // e tid_sofa 팀ID
		            if(temp[3].indexOf(":") > 0 )
		            tid_sofa1[j+(i*100)] = temp[3].substring(2, temp[3].indexOf(",\"teamColors\""));
		            else {
		            	pid_sofa1[j+(i*100)] = "0";
		            }
		            System.out.println("tid_sofa1 "+tid_sofa1[j+(i*100)]);
				}
			}
		}
		
		

		
		//	Set 2 메모리 등록 Start =====================================================
		//	goals_conceded_inside_the_box, goals_conceded_outside_the_box, punches, runs_out, successful_runs_out
		for (int i = 0; i < 1; i++) {		
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
						pid_sofa2[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
					} else {
						pid_sofa2[j+(i*100)] = "0";
					}
					System.out.print("pid_sofa2 "+pid_sofa2[j+(i*100)]+" / ");
					
					// e tid_sofa 팀ID
		            if(temp[3].indexOf(":") > 0 )
		            tid_sofa2[j+(i*100)] = temp[3].substring(2, temp[3].indexOf(",\"teamColors\""));         
		            else {
		            	pid_sofa2[j+(i*100)] = "0";
		            }
		            System.out.println("tid_sofa2 "+tid_sofa2[j+(i*100)]);
			        
				}
			}
		}
	
		
		
		
//		Set 3 메모리 등록 Start =====================================================
		//	errors_lead_to_shot, own_goals, dribbled_past, blocked_shots, clean_sheets
		for (int i = 0; i < 1; i++) {		
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
						pid_sofa3[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
					} else {
						pid_sofa3[j+(i*100)] = "0";
					}
					System.out.println("pid_sofa3 "+pid_sofa3[j+(i*100)]);
					
					// e tid_sofa 팀ID
		            if(temp[3].indexOf(":") > 0 )
		            tid_sofa3[j+(i*100)] = temp[3].substring(2, temp[3].indexOf(",\"teamColors\""));         
		            else {
		            	pid_sofa3[j+(i*100)] = "0";
		            }
		            System.out.println("tid_sofa3 "+tid_sofa3[j+(i*100)]);
			        
				}
			}
		}
		

		// DB에 등록 시작
		for (int i = 0; i < 1000 ; i++) {
			if( pid_sofa1[i] == null ) {	break;		}
			
			if ( pid_sofa1[i].equals(pid_sofa2[i]) && pid_sofa1[i].equals(pid_sofa3[i]) ) {
				System.out.print("Cehck! ["+ i +"]번째  ");
			} else {
				System.out.println("ERROR!!!!! ["+ i +"]번째");
				break;
			}
			
			// 타입 변경
			int i_pid_sofa = Integer.parseInt(pid_sofa1[i]);
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
			
				
			String sid_sofa = season;
			String lid_sofa = league;
			
			String psid = pid_sofa1[i]+"/"+sid_sofa+"/"+lid_sofa+"/"+tid_sofa1[i];
			
			//쿼리 처리
			dao.sofaPlayerGoalKeeperStatUpdate(i_pid_sofa, saves, penalties_faced, penalties_saved, saves_from_inside_box
					, saved_shots_from_outside_the_box, goals_conceded_inside_the_box, goals_conceded_outside_the_box
					, punches, runs_out, successful_runs_out, high_claims, crosses_not_claimed);

		}		
		
	}
}