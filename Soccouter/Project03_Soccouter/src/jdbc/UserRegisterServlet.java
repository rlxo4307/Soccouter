package jdbc;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.dtoCheer;

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 넘어오는 값들을 UTF-8로 처리하겠다
		response.setContentType("text/html;charset=UTF-8");
		String id = request.getParameter("id"); // 사용자가 입력한 것을
		String team = request.getParameter("team");
		String comm = request.getParameter("comm");
//		String updateDate = request.getParameter("updateDate");
		
//		LocalDateTime now = LocalDateTime.now();
//		System.out.println(now);
//		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));

		response.getWriter().write(register(id, team, comm) + ""); // JSON형태로 화면에 출력할 수 있게 만든다
	}
	
	
	public int register(String id, String team, String comm) {
		
		dtoCheer dtoCheer = new dtoCheer();
		try {
			dtoCheer.setId(id);
			dtoCheer.setTeam(team);
			dtoCheer.setComm(comm);
			
		} catch (Exception e) {
			return 0;
		}
		return new DAO().register(dtoCheer);
	}
	
	
}
