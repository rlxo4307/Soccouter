package service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.dtoPlayerStat;
import jdbc.DAO;

public class PlayerStat implements Service{

	static dtoPlayerStat PlayerStat = new dtoPlayerStat();
	
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
		String category = request.getParameter("category");
		int sid_daum = Integer.parseInt(request.getParameter("sid_daum"));
		int sid_sofa = Integer.parseInt(request.getParameter("sid_sofa"));
		int tid_sofa = Integer.parseInt(request.getParameter("tid_sofa"));
		int pid_sofa = Integer.parseInt(request.getParameter("pid_sofa"));
		
		
		System.out.println("league : " + league);
		System.out.println("sslug : " + sslug);
		System.out.println("sid_daum : " + sid_daum);
		
		dtoPlayerStat PlayerStat = new dtoPlayerStat();
		
		DAO dao = new DAO();
		
//		int lid_sofa = 0;
//		if(league.equals("epl")) {
//			lid_sofa = 17;
//		} else if(league.equals("primera")) {
//			lid_sofa = 8;
//		} else if(league.equals("bundesliga")) {
//			lid_sofa = 35;
//		} else if(league.equals("seriea")) {
//			lid_sofa = 23;
//		} else if(league.equals("ligue1")) {
//			lid_sofa = 34;
//		}
		
		PlayerStat = dao.get_PlayerStat(sid_sofa, tid_sofa, pid_sofa);
		
		System.out.println("PlayerStat.java request 실행");
		
		request.setAttribute("PlayerStat", PlayerStat);
		request.setAttribute("league", league);
		request.setAttribute("sslug", sslug);
		request.setAttribute("category", category);
		request.setAttribute("sid_daum", sid_daum);
		request.setAttribute("sid_sofa", sid_sofa);
		
		System.out.println("PlayerStat.java request 완료");
	}
	
}
