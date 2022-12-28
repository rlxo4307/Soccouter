package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backup.InfoLeagueStat;
import dto.dtoLeagueStat;
import service.Cheer;
import service.PlayerNowSeason;
import service.PlayerRank;
import service.PlayerStat;
import service.Service;
import service.Squad;
import service.Team;
import service.TeamRank;
import service.leagueStas;

@WebServlet("*.do") // Front Controll
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static InfoLeagueStat ils = new InfoLeagueStat();
	static ArrayList<dtoLeagueStat> arrayLeagueStats = new ArrayList<dtoLeagueStat>();
   
    public Controller() {
        super();
    }

    
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		// *.do 구하기
		String serviceCode = request.getRequestURI().substring(request.getContextPath().length());
		String str = null;
		Service service = null;
		
		
		switch(serviceCode) {
		
		 case "/leagueStat.do": // /leagueStas.do 에서 Leaguestat.do로 바꿈
			 str = "leagueStat.jsp";
			 service = new leagueStas();
			 service.start(request, response);
	         break;

		 case "/playerNowSeason.do":
			 str = "playerNowSeason.jsp";
			 service = new PlayerNowSeason();
			 service.start(request, response);
	         break;
	         
		 case "/Squad.do":
			 str = "Squad.jsp";
			 service = new Squad();
			 service.start(request, response);
			 break;
			 
		 case "/Team.do":
			 str = "Team.jsp";
			 service = new Team();
			 service.start(request, response);
			 break;
			 
		 case "/TeamRank.do":
			 str = "TeamRank.jsp";
			 service = new TeamRank();
			 service.start(request, response);
			 break;
			 
		 case "/PlayerRank.do":
			 str = "PlayerRank.jsp";
			 service = new PlayerRank();
			 service.start(request, response);
			 break;
			 
		 case "/PlayerStat.do":
			 str = "PlayerStat.jsp";
			 service = new PlayerStat();
			 service.start(request, response);
			 break;
		
		 case "/Cheer.do":
			 str = "Cheer.jsp";
			 service = new Cheer();
			 service.start(request, response);
			 break;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(str);
		rd.forward(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
