package jdbc;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.dtoCheer;

@WebServlet("/UserSearchServlet") // JSON형태로 돌려주는 것이 서블렛의 역할
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 넘어오는 값들을 UTF-8로 처리하겠다
		response.setContentType("text/html;charset=UTF-8");
		String teamName = request.getParameter("teamName"); // 사용자가 입력한 것을
		response.getWriter().write(getJSON(teamName)); // JSON형태로 화면에 출력할 수 있게 만든다
	}
	
	// JSON : 파싱하기 쉬운 하나의 형태, 특정 회원을 검색했을 때 그 정보가 JSON 형태로 출력되는데 그것을 다시 파싱해서 분석하여 우리에게 보여줌
	// 그래서 서버 역할을 하는 서블렛은 JSON을 만든다. index -> (요청) -> JSON -> (응답) -> index
	public String getJSON(String teamName) { 
		if(teamName == null) teamName = "";
		StringBuffer result = new StringBuffer("");
		result.append("{\"reuslt\":[");
		DAO DaoCheer = new DAO();
		ArrayList<dtoCheer> userList = DaoCheer.search(teamName);
		for(int i = 0; i < userList.size(); i++) { // 개별 사용자 정보들을 JSON으로 출력할 수 있게
			result.append("[{\"value\": \"" + userList.get(i).getId() + "\"},");
			result.append("{\"value\": \"" + userList.get(i).getTeam() + "\"},");
			result.append("{\"value\": \"" + userList.get(i).getComm() + "\"},");
			result.append("{\"value\": \"" + userList.get(i).getUpdateDate() + "\"}],");
		} // 하나의 회원정보를 배열의 원소 형태로 보여주는 것
		result.append("]}"); // 열은 것을 닫아준다
		return result.toString(); // 마지막으로 결과를 반환
	}
	
}