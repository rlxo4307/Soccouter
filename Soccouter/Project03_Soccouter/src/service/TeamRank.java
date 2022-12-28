package service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.dtoTeamRank;
import jdbc.DAO;

public class TeamRank implements Service{

	static ArrayList<dtoTeamRank> teamRank = new ArrayList<dtoTeamRank>();
	
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
		int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
		int sid_daum = Integer.parseInt(request.getParameter("sid_daum"));
		
		System.out.println("league : " + league);
		System.out.println("sslug : " + sslug);
		System.out.println("sid_daum : " + sid_daum);
		
		ArrayList<dtoTeamRank> teamRank = new ArrayList<dtoTeamRank>();
		
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
		
		System.out.println("lid_sofa : " + lid_sofa);
		
		
		teamRank = dao.get_TeamRank(lid_sofa, sid_daum, sslug);
		
		System.out.println("teamRank.java 진행 완료 request 실행");
		
		request.setAttribute("teamRank", teamRank);
		request.setAttribute("league", league);
		request.setAttribute("sslug", sslug);
		request.setAttribute("sid_sofa", sid_sofa);
		request.setAttribute("sid_daum", sid_daum);
				
	}
	
}
