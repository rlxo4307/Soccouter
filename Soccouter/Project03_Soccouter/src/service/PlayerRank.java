package service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.dtoPlayerRank;
import dto.dtoPlayerRank_Assists;
import dto.dtoPlayerRank_Attack_Points;
import dto.dtoPlayerRank_Fouls;
import dto.dtoPlayerRank_Offsides;
import dto.dtoPlayerRank_Penalties_Taken;
import dto.dtoPlayerRank_Red_Cards;
import dto.dtoPlayerRank_Shots_On_Target;
import dto.dtoPlayerRank_Total_Shots;
import dto.dtoPlayerRank_Yellow_Cards;
import jdbc.DAO;

public class PlayerRank implements Service{

	static ArrayList<dtoPlayerRank> PlayerRank = new ArrayList<dtoPlayerRank>();
//	static ArrayList<dtoPlayerRank> PlayerRankTest = new ArrayList<dtoPlayerRank>();

	static ArrayList<dtoPlayerRank_Assists> PlayerRank_Assists = new ArrayList<dtoPlayerRank_Assists>();
	static ArrayList<dtoPlayerRank_Total_Shots> PlayerRank_Total_Shots = new ArrayList<dtoPlayerRank_Total_Shots>();
	static ArrayList<dtoPlayerRank_Attack_Points> PlayerRank_Attack_Point = new ArrayList<dtoPlayerRank_Attack_Points>();
	static ArrayList<dtoPlayerRank_Shots_On_Target> PlayerRank_Shots_On_Target = new ArrayList<dtoPlayerRank_Shots_On_Target>();
	static ArrayList<dtoPlayerRank_Penalties_Taken> PlayerRank_Penalties_Taken = new ArrayList<dtoPlayerRank_Penalties_Taken>();
	static ArrayList<dtoPlayerRank_Fouls> PlayerRank_Fouls = new ArrayList<dtoPlayerRank_Fouls>();
	static ArrayList<dtoPlayerRank_Yellow_Cards> PlayerRank_Yellow_Cards = new ArrayList<dtoPlayerRank_Yellow_Cards>();
	static ArrayList<dtoPlayerRank_Red_Cards> PlayerRank_Red_Cards = new ArrayList<dtoPlayerRank_Red_Cards>();
	static ArrayList<dtoPlayerRank_Offsides> PlayerRank_Offsides = new ArrayList<dtoPlayerRank_Offsides>();
		
	@Override
	public void start(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String league = request.getParameter("league");
		String sslug = request.getParameter("sslug");
		int sid_daum = Integer.parseInt(request.getParameter("sid_daum"));
		int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
		String category = request.getParameter("category");
		
		System.out.println("PlayerRank.java league : " + league);
		System.out.println("PlayerRank.java sslug : " + sslug);
		System.out.println("PlayerRank.java sid_daum : " + sid_daum);
		System.out.println("PlayerRank.java category : " + category);
		
		ArrayList<dtoPlayerRank> PlayerRank = new ArrayList<dtoPlayerRank>();
//		ArrayList<dtoPlayerRank> PlayerRankTest = new ArrayList<dtoPlayerRank>();
		ArrayList<dtoPlayerRank_Assists> PlayerRank_Assists = new ArrayList<dtoPlayerRank_Assists>();
		ArrayList<dtoPlayerRank_Total_Shots> PlayerRank_Total_Shots = new ArrayList<dtoPlayerRank_Total_Shots>();
		ArrayList<dtoPlayerRank_Attack_Points> PlayerRank_Attack_Points = new ArrayList<dtoPlayerRank_Attack_Points>();
		ArrayList<dtoPlayerRank_Shots_On_Target> PlayerRank_Shots_On_Target = new ArrayList<dtoPlayerRank_Shots_On_Target>();
		ArrayList<dtoPlayerRank_Penalties_Taken> PlayerRank_Penalties_Taken = new ArrayList<dtoPlayerRank_Penalties_Taken>();
		ArrayList<dtoPlayerRank_Fouls> PlayerRank_Fouls = new ArrayList<dtoPlayerRank_Fouls>();
		ArrayList<dtoPlayerRank_Yellow_Cards> PlayerRank_Yellow_Cards = new ArrayList<dtoPlayerRank_Yellow_Cards>();
		ArrayList<dtoPlayerRank_Red_Cards> PlayerRank_Red_Cards = new ArrayList<dtoPlayerRank_Red_Cards>();
		ArrayList<dtoPlayerRank_Offsides> PlayerRank_Offsides = new ArrayList<dtoPlayerRank_Offsides>();
		
		DAO dao = new DAO();
		
		int lid_sofa = 0;
		if(league.equals("epl")) {
			lid_sofa = 17;
		} else if(league.equals("primera")) {
			lid_sofa = 8;
		} else if(league.equals("bundesliga")) {
			lid_sofa = 35;
		} else if(league.equals("seriea")) {
			lid_sofa = 23;
		} else if(league.equals("ligue1")) {
			lid_sofa = 34;
		}
		
		System.out.println("PlayerRank.java lid_sofa : " + lid_sofa);
		
		PlayerRank = dao.get_PlayerRank(lid_sofa, sid_daum, sslug, "goals");
//		PlayerRankTest = dao.get_PlayerRank(lid_sofa, sid_daum, sslug, category);
		PlayerRank_Assists = dao.get_PlayerRank_Assists(lid_sofa, sid_daum, sslug, "assists");
		PlayerRank_Total_Shots = dao.get_PlayerRank_Total_Shots(lid_sofa, sid_daum, sslug, "total_shots");
		PlayerRank_Attack_Points = dao.get_PlayerRank_Attack_Points(lid_sofa, sid_daum, sslug, "attack_points");
		PlayerRank_Shots_On_Target = dao.get_PlayerRank_Shots_On_Target(lid_sofa, sid_daum, sslug, "shots_on_target");
		PlayerRank_Penalties_Taken = dao.get_PlayerRank_Penalties_Taken(lid_sofa, sid_daum, sslug, "penalties_taken");
		PlayerRank_Fouls = dao.get_PlayerRank_Fouls(lid_sofa, sid_daum, sslug, "fouls");
		PlayerRank_Yellow_Cards = dao.get_PlayerRank_Yellow_Cards(lid_sofa, sid_daum, sslug, "yellow_cards");
		PlayerRank_Red_Cards = dao.get_PlayerRank_Red_Cards(lid_sofa, sid_daum, sslug, "red_cards");
		PlayerRank_Offsides = dao.get_PlayerRank_Offsides(lid_sofa, sid_daum, sslug, "oddsides");
		
		if(PlayerRank_Assists.size()==0) {
			System.out.println("PlayerRank_Assists 객체는 생성되지 않았습니다");
		}
		if(PlayerRank_Total_Shots.size()==0) {
			System.out.println("PlayerRank_Assists 객체는 생성되지 않았습니다");
		}
		if(PlayerRank_Attack_Points.size()==0) {
			System.out.println("PlayerRank_Assists 객체는 생성되지 않았습니다");
		}
		if(PlayerRank_Shots_On_Target.size()==0) {
			System.out.println("PlayerRank_Assists 객체는 생성되지 않았습니다");
		}
		if(PlayerRank_Penalties_Taken.size()==0) {
			System.out.println("PlayerRank_Assists 객체는 생성되지 않았습니다");
		}
		if(PlayerRank_Fouls.size()==0) {
			System.out.println("PlayerRank_Assists 객체는 생성되지 않았습니다");
		}
		if(PlayerRank_Yellow_Cards.size()==0) {
			System.out.println("PlayerRank_Assists 객체는 생성되지 않았습니다");
		}
		if(PlayerRank_Red_Cards.size()==0) {
			System.out.println("PlayerRank_Assists 객체는 생성되지 않았습니다");
		}
		if(PlayerRank_Offsides.size()==0) {
			System.out.println("PlayerRank_Assists 객체는 생성되지 않았습니다");
		}
		
		System.out.println("dao.get_PlayerRank_& 진행 완료 || request 실행");
		
		request.setAttribute("PlayerRank", PlayerRank);
//		request.setAttribute("PlayerRankTest", PlayerRankTest);
		request.setAttribute("PlayerRank_Assists", PlayerRank_Assists);
		request.setAttribute("PlayerRank_Total_Shots", PlayerRank_Total_Shots);
		request.setAttribute("PlayerRank_Attack_Points", PlayerRank_Attack_Points);
		request.setAttribute("PlayerRank_Shots_On_Target", PlayerRank_Shots_On_Target);
		request.setAttribute("PlayerRank_Penalties_Taken", PlayerRank_Penalties_Taken);
		request.setAttribute("PlayerRank_Fouls", PlayerRank_Fouls);
		request.setAttribute("PlayerRank_Yellow_Cards", PlayerRank_Yellow_Cards);
		request.setAttribute("PlayerRank_Red_Cards", PlayerRank_Red_Cards);
		request.setAttribute("PlayerRank_Offsides", PlayerRank_Offsides);
		
		request.setAttribute("league", league);
		request.setAttribute("sslug", sslug);
		request.setAttribute("sid_daum", sid_daum);
		request.setAttribute("sid_sofa", sid_sofa);
		request.setAttribute("category", category);
				
	}
	
}
