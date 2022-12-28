package service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.dtoSquad;
import jdbc.DAO;

public class Squad implements Service{
	
	static ArrayList<dtoSquad> squad = new ArrayList<dtoSquad>();
	
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
		int tid_sofa = Integer.parseInt(request.getParameter("tid_sofa"));
		int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
		int sid_daum = Integer.parseInt(request.getParameter("sid_daum"));
		
		ArrayList<dtoSquad> squad = new ArrayList<dtoSquad>();		

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
		
		squad = dao.get_Squad(lid_sofa, tid_sofa, sid_sofa);
		
		request.setAttribute("league", league);
		request.setAttribute("team", team);
		request.setAttribute("tid_daum", tid_daum);
		request.setAttribute("tid_sofa", tid_sofa);
		request.setAttribute("sid_sofa", sid_sofa);
		request.setAttribute("sid_daum", sid_daum);
		
		request.setAttribute("squad", squad);
		
	}
	
}
