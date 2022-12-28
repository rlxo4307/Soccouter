package org.jsoup.sofascore.other;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import DAO.SofaDefenseDAO2;

public class SofaTotalOther {

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
		
		
		// Other Set ============================================================
		String[] yellow_cards = new String [1000];							//	46 경고
		String[] red_cards = new String [1000];								//	47	퇴장
		String[] ground_duels_won = new String [1000];						//	48	지면 경합 승리
		String[] ground_duels_won_per = new String [1000];					//	49	지면 경합 승률
		String[] aerial_duels_won = new String [1000];						//	50	공중 경합 승리
		// Set 1 end ----------------------------------------------
		String[] aerial_duels_won_per = new String [1000];					//	51	공중 경합 승률
		String[] total_duels_won = new String [1000];						//	52	전체 경합 승리
		String[] total_duels_won_per = new String [1000];					//	53	전체 경합 승률
		String[] minutes_played = new String [1000];						//	54	출장 시간(분)
		String[] was_fouled = new String [1000];							//	55	당한 반칙
		// Set 2 end ----------------------------------------------
		String[] fouls = new String [1000];									//	56	반칙
		String[] dispossessed = new String [1000];							//	57	비 드리블 상황에서 공격권 상실
		String[] possession_lost = new String [1000];						//	58	드리블 중 공격권 상실(턴오버)
		String[] appearances = new String [1000];							//	59	전체 출전
		String[] started = new String [1000];								//	60	선발 출전
		// Set 3 end ----------------------------------------------
		
		
		
		//	Other Set 시작 ========================================================= 
		//	Set 1 메모리 등록 Start =====================================================
		//	yellow_cards, red_cards, ground_duels_won, ground_duels_won_per, aerial_duels_won
		for (int i = 0; i < 1; i++) {		
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
					System.out.print("yellow_cards "+yellow_cards[j+(i*100)]+" / ");
					
					
		            // 52 red_cards 퇴장
					if(sentance[j+1].indexOf(",\"redCards\":") > 0 ){
						String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"redCards\":")+",\"redCards\":".length());
						red_cards[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
					} else {
						red_cards[j+(i*100)] = "0";
					}
					System.out.print("red_cards "+red_cards[j+(i*100)]+" / ");
		            
		            
					// 53 ground_duels_won 지면 경합 승리
		            if(sentance[j+1].indexOf(",\"groundDuelsWon\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"groundDuelsWon\":")+",\"groundDuelsWon\":".length());
		            	ground_duels_won[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	ground_duels_won[j+(i*100)] = "0";
		            }
		            System.out.print("ground_duels_won "+ground_duels_won[j+(i*100)]+" / ");
		            
		            
		            // 54 ground_duels_won_per 지면 경합 승률
		            if(sentance[j+1].indexOf(",\"groundDuelsWonPercentage\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"groundDuelsWonPercentage\":")+",\"groundDuelsWonPercentage\":".length());
		            	ground_duels_won_per[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	ground_duels_won_per[j+(i*100)] = "0";
		            }
		            System.out.print("ground_duels_won_per "+ground_duels_won_per[j+(i*100)]+" / ");
					
		            // 55 aerial_duels_won 공중 경합 승리
		            if(sentance[j+1].indexOf(",\"aerialDuelsWon\":") > 0 ){
		            	String tmpPheight = sentance[j+1].substring(sentance[j+1].indexOf(",\"aerialDuelsWon\":")+",\"aerialDuelsWon\":".length());
		            	aerial_duels_won[j+(i*100)] = tmpPheight.substring(0, tmpPheight.indexOf(","));
		            } else {
		            	aerial_duels_won[j+(i*100)] = "0";
		            }
		            System.out.print("aerial_duels_won "+aerial_duels_won[j+(i*100)]+" / ");
		            
		            
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
		//	aerial_duels_won_per, Total_duels_won, Total_duels_won_per, Minutes_played, Was_fouled
		for (int i = 0; i < 1; i++) {		
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
			
			String sid_sofa = season;
			String lid_sofa = league;
			
			String psid = pid_sofa1[i]+"/"+sid_sofa+"/"+lid_sofa+"/"+tid_sofa1[i];
			
			//쿼리 처리
			dao.sofaPlayerOtherStatUpdate(i_pid_sofa, i_yellow_cards, i_red_cards, i_ground_duels_won, d_ground_duels_won_per
					,i_aerial_duels_won, d_aerial_duels_won_per, i_total_duels_won, d_total_duels_won_per, i_minutes_played
					,i_was_fouled, i_fouls, i_dispossessed, i_appearances, i_started);

		}		
		
	}
}