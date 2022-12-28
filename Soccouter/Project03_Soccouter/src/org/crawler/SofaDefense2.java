package org.jsoup.sofascore.defense;
import java.io.IOException;

import DAO.PlayerStatDAO;
import org.jsoup.nodes.Document;
import org.jsoup.Conn;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import DAO.SofaDefenseDAO2;

public class SofaTotalDefense {

	public static void main(String[] args) throws IOException {
		System.out.println("::::::::::::::::::::::선수 정보 갱신 시작 (ATTACK)::::::::::::::::::::::");
		SofaDefenseDAO2 dao = new SofaDefenseDAO2();
		
		String league = "17";																// 리그 코드
		String season = "37036";														// 시즌 코드
		

		// 메모리 set 준비 =========================================================
		// Attack set end ----------------------------------------------
		String[] pid_sofa6 = new String[1000];										//	0	플레이어 ID		
		String[] pid_sofa7 = new String[1000];										//	0	플레이어 ID		
		// Defence set end ----------------------------------------------		
		String[] pid_sofa8 = new String[1000];										//	0	플레이어 ID
		// Pass set end ----------------------------------------------

		
		
		// Defence Set =========================================================
		String[] tackles = new String [1000];											//	24	태클 수
		String[] interceptions = new String [1000];									//	25	가로채기
		String[] penalty_committed = new String [1000];							//	26	패널티 제공
		String[] clearances = new String [1000];									//	27	걷어내기
		String[] errors_lead_to_goal = new String [1000];						//	28	실점 허용 실책
		// Set 1 end ----------------------------------------------
		String[] errors_lead_to_shot = new String [1000];						//	29	슈팅 허용 실책
		String[] own_goals = new String [1000];									//	30	자책골
		String[] dribbled_past = new String [1000];								//	31	드리블 허용
		String[] blocked_shots = new String [1000];								//	32	슈팅 블록킹
		String[] clean_sheets = new String [1000];									//	33	무실점 경기
		// Set 2 end ----------------------------------------------
		
		
		
		//	Defence Set 시작 ========================================================= 
		//	Set 6 메모리 등록 Start =====================================================
		//	tackles, Interceptions, penalty_committed, clearances, errors_lead_to_goal
		for (int i = 0; i < 1; i++) {		
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
					System.out.print("tackles "+tackles[j+(i*100)]+" / ");
					
					
		            // 25 interceptions 가로채기
					if(sentance[j+1].indexOf(",\"interceptions\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"interceptions\":")+",\"interceptions\":".length());
						interceptions[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						interceptions[j+(i*100)] = "0";
					}
					System.out.print("interceptions "+interceptions[j+(i*100)]+" / ");
		            
		            
					// 26 penalty_committed 패널티 제공
		            if(sentance[j+1].indexOf("\"penaltyConceded\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("\"penaltyConceded\":")+"\"penaltyConceded\":".length());
		            	penalty_committed[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	penalty_committed[j+(i*100)] = "0";
		            }
		            System.out.print("penalty_committed "+penalty_committed[j+(i*100)]+" / ");
		            
		            
		            // 27 clearances 걷어내기
		            if(sentance[j+1].indexOf("\"clearances\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("\"clearances\":")+"\"clearances\":".length());
		            	clearances[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	clearances[j+(i*100)] = "0";
		            }
		            System.out.print("clearances "+clearances[j+(i*100)]+" / ");
					
		            // 28 errors_lead_to_goal 실점 허용 실책
		            if(sentance[j+1].indexOf("\"errorLeadToGoal\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("\"errorLeadToGoal\":")+"\"errorLeadToGoal\":".length());
		            	errors_lead_to_goal[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	errors_lead_to_goal[j+(i*100)] = "0";
		            }
		            System.out.print("errors_lead_to_goal "+errors_lead_to_goal[j+(i*100)]+" / ");
		            
		            
		            // b pid_sofa 플레이어ID
		            String[] temp = sentance[j+1].split(",\"id");
		            if(temp[1].indexOf(":") > 0 ){
		            	String tmpPheight = temp[1].substring(temp[1].indexOf(":")+":".length());
		            	pid_sofa6[j+(i*100)] = tmpPheight.substring(0, temp[1].indexOf("}")-2);
		            } else {
		            	pid_sofa6[j+(i*100)] = "0";
		            }
		            System.out.print("pid_sofa6 "+pid_sofa6[j+(i*100)]+" / ");
		            
		            
		            // e tid_sofa 팀ID
		            tid_sofa[j+(i*100)] = temp[3].substring(2, temp[3].indexOf(",\"teamColors\""));	   
		            System.out.println("tid_sofa "+tid_sofa[j+(i*100)]);
				}
			}
		}
		
		

		
		//	Set 7 메모리 등록 Start =====================================================
		//	errors_lead_to_shot, own_goals, dribbled_past, blocked_shots, clean_sheets
		for (int i = 0; i < 1; i++) {		
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
					if(sentance[j+1].indexOf("eadToShot\":") > 0) {
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf("eadToShot\":")+"eadToShot\":".length());
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
			
			String psid = pid_sofa[]+"/"+sid_sofa+"/"+lid_sofa+"/"+tid_sofa;
			
			//쿼리 처리
			dao.sofaPlayerAttackStatUpdate(i_pid_sofa, d_rating, i_golas, i_big_chances_missed, i_succ_dribbles, d_successful_dribbles_per, i_total_shots, i_shots_on_target, 
					i_shots_off_target, d_goal_conversion_per, i_penalties_taken, i_penalty_golas, i_penalty_won, i_shots_from_set_piece, i_free_kick_goals, i_goals_from_inside_the_box, 
					i_goals_from_outside_the_box, i_headed_goals, i_left_foot_goals, i_right_foot_goals, i_hit_woodwork, i_offsides, d_penalty_conversion_per, d_set_piece_conversion_per);

		}		
		
	}
}