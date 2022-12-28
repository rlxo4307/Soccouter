package service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.dtoTeam;
import jdbc.DAO;

public class Team implements Service{
	//static ArrayList<dtoSquad_timg> squad_timg = new ArrayList<dtoSquad_timg>();
	static ArrayList<dtoTeam> team = new ArrayList<dtoTeam>();
	
	@Override
	public void start(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String league = request.getParameter("league");
		String lslid = request.getParameter("lslid");
		int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
		int sid_daum = Integer.parseInt(request.getParameter("sid_daum"));
		
		ArrayList<dtoTeam> team = new ArrayList<dtoTeam>();
		
		DAO dao = new DAO();
		
		int lid_sofa = 0;
		
		if(league.equals("epl")) {
			lid_sofa = 17;
			sid_sofa = 41886;
		} else if(league.equals("primera")) {
			lid_sofa = 8;
			sid_sofa = 42409;
		} else if(league.equals("bundesliga")) {
			lid_sofa = 35;
			sid_sofa = 42268;
		} else if(league.equals("seriea")) {
			lid_sofa = 23;
			sid_sofa = 42415;
		} else if(league.equals("ligue1")) {
			lid_sofa = 34;
			sid_sofa = 42273;
		}
		
		//squad_timg = dao.get_Squad_timg();
		team = dao.get_Team(lid_sofa);
		
		request.setAttribute("team", team);
		request.setAttribute("league", league);
		request.setAttribute("lslid", lslid);
		request.setAttribute("sid_sofa", sid_sofa);
		request.setAttribute("sid_daum", sid_daum);
		
	}
	
}
