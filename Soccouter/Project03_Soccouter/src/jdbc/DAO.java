package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.dtoCheer;
import dto.dtoMem;
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
import dto.dtoPlayerStat;
import dto.dtoSquad;
import dto.dtoTeam;
import dto.dtoTeamRank;

public class DAO {
	private Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// DB 연결 메소드
	public DAO() { try {	con = new Conn().getConnection(); 	} catch (Exception e) { 	e.printStackTrace();	}	}

	
	
	public void insertLeague(int lid_sofa, String lid_daum, String LEN, String LKO, String LIMG, String LSLUG) throws SQLException {
		String sql = "INSERT INTO LEAGUE(LID_SOFA, LID_DAUM, LEN, LKO, LIMG, LSLUG) VALUES(?, ?, ?, ?, ?, ?)";
																
		try {
		pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, lid_sofa);
		pstmt.setString(2, lid_daum);
		pstmt.setString(3, LEN);
		pstmt.setString(4, LKO);
		pstmt.setString(5, LIMG);
		pstmt.setString(6, LSLUG);
		int cnt = pstmt.executeUpdate();
		
		if(cnt == 1) 
			System.out.println("리그 입력 완료 "+ lid_sofa +" / "+ lid_daum +" / "+ LEN +" / "+ LKO +" / "+ LIMG +" / "+ LSLUG);		
		pstmt.close();

		} catch (Exception e) {
			sql = "UPDATE LEAGUE SET LID_SOFA=?, LID_DAUM=?, LEN=?, LKO=?, LIMG=?, LSLUG=? WHERE LID_DAUM = ?";
			try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, lid_sofa);
			pstmt.setString(3, LEN);
			pstmt.setString(4, LKO);
			pstmt.setString(5, LIMG);
			pstmt.setString(6, LSLUG);
			pstmt.setString(7, lid_daum);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt == 1) 
				System.out.println("리그 업데이트 완료" + lid_sofa +" / "+ lid_daum +" / "+ LEN +" / "+ LKO +" / "+ LIMG +" / "+ LSLUG);
			pstmt.close();
			
			} catch ( Exception e1 ){
				e1.printStackTrace();
				System.out.println("리그 입력/업데이트 실패 !!! " +" / "+ lid_sofa +" / "+ lid_daum +" / "+ LEN +" / "+ LKO +" / "+ LIMG +" / "+ LSLUG);
			} 
		}
	}
	
	
	
	public void insertSeason(int sid_sofa, int sid_daum, int syear, int lid_sofa, String sname, String SSLUG) throws SQLException {
		String sql = "INSERT INTO SEASON(SID_SOFA, SID_DAUM, SYEAR, LID_SOFA, SNAME, SSLUG) VALUES(?, ?, ?, ?, ?, ?)";
																
		try {
		pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, sid_sofa);
		pstmt.setInt(2, sid_daum);
		pstmt.setInt(3, syear);
		pstmt.setInt(4, lid_sofa);
		pstmt.setString(5, sname);
		pstmt.setString(6, SSLUG);
		int cnt = pstmt.executeUpdate();
		
		if(cnt == 1) 
			System.out.println("시즌 입력 완료 "+ sid_sofa +" / "+ sid_daum +" / "+ syear +" / "+ lid_sofa +" / "+ sname +" / "+ SSLUG);		
		pstmt.close();
		
		} catch (Exception e) {
			sql="UPDATE SEASON "
					+ "SET SID_SOFA=?, SID_DAUM = ?, SYEAR=?, LID_SOFA=?, SNAME=?"
	    		      + " WHERE SSLUG = ?";
			try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, sid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setInt(3, syear);
			pstmt.setInt(4, lid_sofa);
			pstmt.setString(5, sname);
			pstmt.setString(6, SSLUG);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt == 1) 
				System.out.println("시즌 업데이트 성공 !!! "+ sid_sofa +" / "+ sid_daum +" / "+ syear +" / "+ lid_sofa +" / "+ sname +" / "+ SSLUG);
			pstmt.close();
			
			} catch ( Exception e1 ){
				e1.printStackTrace();
				System.out.println("시즌 입력/업데이트 실패 !!! "+ sid_sofa +" / "+ sid_daum +" / "+ syear +" / "+ lid_sofa +" / "+ sname +" / "+ SSLUG);
			} 
    	}
	}
	
	
	
	public void insertTeam(int tid_sofa, int tid_daum, int lid_sofa, String TKO, String TEN, String TSLUG, String TIMG, String LSLID) {
		String sql = "INSERT INTO TEAM(TID_SOFA, TID_DAUM, LID_SOFA, TKO, TEN, TSLUG, TIMG, LSLID, updateDate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, sysdate+9/24)";
																
		try {
		pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, tid_sofa);
		pstmt.setInt(2, tid_daum);
		pstmt.setInt(3, lid_sofa);
		pstmt.setString(4, TKO);
		pstmt.setString(5, TEN);
		pstmt.setString(6, TSLUG);
		pstmt.setString(7, TIMG);
		pstmt.setString(8, LSLID);
		int cnt = pstmt.executeUpdate();

		if(cnt == 1) 
			System.out.println("팀 입력 완료 "+ tid_sofa +" / "+ tid_daum +" / "+ lid_sofa +" / "+ TKO+ " / "+ TEN +" / "+ TSLUG +" / "+ TIMG +" / "+ LSLID);
		pstmt.close();
		
		} catch (Exception e) {
			System.out.println(e);
			sql="UPDATE TEAM "
					+ " SET TID_DAUM=?, LID_SOFA=?, TKO=?, TEN=?, TSLUG=?, TIMG=?, LSLID=?, updateDate=sysdate+9/24"
	    		      + " WHERE TID_SOFA=?";
			try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, tid_daum);
			pstmt.setInt(2, lid_sofa);
			pstmt.setString(3, TKO);
			pstmt.setString(4, TEN);
			pstmt.setString(5, TSLUG);
			pstmt.setString(6, TIMG);
			pstmt.setString(7, LSLID);
			pstmt.setInt(8, tid_sofa);
			pstmt.executeUpdate();

			int cnt = pstmt.executeUpdate();
			
			if(cnt == 1) 
				System.out.println("팀 업데이트 완료 " + tid_sofa);
			pstmt.close();
			
			} catch ( Exception e1 ){
				e1.printStackTrace();
				System.out.println("팀 입력/업데이트 실패 !!! "+ tid_sofa +" / "+ tid_daum +" / "+ lid_sofa +" / "+ TKO+ " / "+ TEN +" / "+ TSLUG +" / "+ TIMG +" / "+ LSLID);
			} 
		}
	}
	
	
	
	// teamrank 테이블에 시즌별 경기 정보 삽입
	public void insertTeamRank(int ranking, int tid_sofa, int game, int win, int draw, int loss, 
											int gf, int ga, int gd, int pts, int lid_sofa, int seasonKey) {
		
		String sql = "insert into teamrank values(?,?,?,?,?,?,?,?,?,?,"
				+ " (select DISTINCT sid_sofa from season where lid_sofa = ? and sid_daum = ?), ?)";
		
		try {
		pstmt = con.prepareStatement(sql);

		pstmt.setInt(1, ranking);
		pstmt.setInt(2, tid_sofa);
		pstmt.setInt(3, game);
		pstmt.setInt(4, win);
		pstmt.setInt(5, draw);
		pstmt.setInt(6, loss);
		pstmt.setInt(7, gf);
		pstmt.setInt(8, ga);
		pstmt.setInt(9, gd);
		pstmt.setInt(10, pts);
		pstmt.setInt(11, lid_sofa);
		pstmt.setInt(12, seasonKey);
		pstmt.setInt(13, lid_sofa);
		
		int cnt = pstmt.executeUpdate();
		
		if(cnt == 1)
			System.out.println("Teamrank 입력 성공");
		pstmt.close();
		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("TeamRank 입력 에러");
		}
	}
		
		
		
	public void insertPlayerSofa(int pid_sofa, String PIMG, int PNUM, String PEN, String PSLUG, int lid_sofa,
			int tid_sofa, int PHEIGHT, String PBIRTH, String PNATION, String PNATIONIMG, String PFOOT, String PPOSITION) throws SQLException {
		String sql = "INSERT INTO PLAYER(PID_SOFA, PIMG, PNUM, PKO, PEN, PSLUG, LID_SOFA, TID_SOFA, PHEIGHT, PBIRTH, PNATION, PNATIONIMG, PFOOT, PPOSITION, updateDate) "
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate+9/24)";
																
		try {
		pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, pid_sofa);
		pstmt.setString(2, PIMG);
		pstmt.setInt(3, PNUM);
		pstmt.setString(4, PEN);
		pstmt.setString(5, PEN);
		pstmt.setString(6, PSLUG);
		pstmt.setInt(7, lid_sofa);
		pstmt.setInt(8, tid_sofa);
		pstmt.setInt(9, PHEIGHT);
		pstmt.setString(10, PBIRTH);
		pstmt.setString(11, PNATION);
		pstmt.setString(12, PNATIONIMG);
		pstmt.setString(13, PFOOT);
		pstmt.setString(14, PPOSITION);
		int cnt = pstmt.executeUpdate();

		if(cnt == 1) 
			//System.out.println("Player Sofa 입력 성공 "+ pid_sofa +" / "+ PIMG +" / "+ PNUM +" / "+ PEN + " / "+ PSLUG +" / "+ lid_sofa +" / "+ tid_sofa +" / "+ PHEIGHT +" / "+ PBIRTH +" / "+ PNATION +" / "+ PNATIONIMG +" / "+ PFOOT +" / "+ PPOSITION);
			System.out.println("Player Sofa 입력 성공 "+ pid_sofa);
		pstmt.close();
		
		} catch (Exception e) {
			sql = "UPDATE PLAYER " 
					+ "SET PIMG=?, PNUM=?, PKO=?, PEN=?, PSLUG=?, LID_SOFA=?, TID_SOFA=?, PHEIGHT=?, PBIRTH=?, PNATION=?, PNATIONIMG=?, PFOOT=?, PPOSITION=?, updateDate=sysdate+9/24 "
				+ " WHERE PID_SOFA = ?";
			try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, PIMG);
			pstmt.setInt(2, PNUM);
			pstmt.setString(3, PEN);
			pstmt.setString(4, PEN);
			pstmt.setString(5, PSLUG);
			pstmt.setInt(6, lid_sofa);
			pstmt.setInt(7, tid_sofa);
			pstmt.setInt(8, PHEIGHT);
			pstmt.setString(9, PBIRTH);
			pstmt.setString(10, PNATION);
			pstmt.setString(11, PNATIONIMG);
			pstmt.setString(12, PFOOT);
			pstmt.setString(13, PPOSITION);
			pstmt.setInt(14, pid_sofa);
			
			int cnt = pstmt.executeUpdate();
			if(cnt == 1) 
				System.out.println("Player Sofa 업데이트 성공 "+ pid_sofa +" / "+ PIMG +" / "+ PNUM +" / "+ PEN + " / "+ PSLUG +" / "+ lid_sofa +" / "+ tid_sofa +" / "+ PHEIGHT +" / "+ PBIRTH +" / "+ PNATION +" / "+ PNATIONIMG +" / "+ PFOOT +" / "+ PPOSITION);
			pstmt.close();
			
			} catch ( Exception e1 ) {
				e1.printStackTrace();
				System.out.println("Player Sofa 입력/업데이트 실패!!! " + pid_sofa +" / "+ PIMG +" / "+ PNUM +" / "+ PEN + " / "+ PSLUG +" / "+ lid_sofa +" / "+ tid_sofa +" / "+ PHEIGHT +" / "+ PBIRTH +" / "+ PNATION +" / "+ PNATIONIMG +" / "+ PFOOT +" / "+ PPOSITION);
			}
		}
	}
	
	
	
	public void insertPlayerDaum(int pid_daum, int pid_sofa, String PKO, int PNUM, String PBIRTH, String PPOSITION, String PENLASTNAME, String PENLASTNAME2, String PENFIRSTNAME, String PENFIRSTNAME2) throws SQLException {
		if(PBIRTH.equals("null")) {
			String sql = "INSERT INTO PLAYER(PID_DAUM, PKO, updateDate) VALUES(?, ?, sysdate+9/24)"
					+ " WHERE PID_SOFA = ? OR (PNUM = ? AND PPOSITION LIKE ? AND (PEN LiKE ? OR PEN LIKE ?) AND (PEN LIKE ? OR PEN LIKE ?))";
																	
			try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, pid_daum);
			pstmt.setString(2, PKO);
			pstmt.setInt(3, pid_sofa);
			pstmt.setInt(4, PNUM);
			pstmt.setString(5, PPOSITION);
			pstmt.setString(6, "%"+PENLASTNAME+"%");
			pstmt.setString(7, "%"+PENLASTNAME2+"%");
			pstmt.setString(8, "%"+PENFIRSTNAME+"%");
			pstmt.setString(9, "%"+PENFIRSTNAME2+"%");
			
			int cnt = pstmt.executeUpdate();
				if(cnt == 1) {
//					pstmt.close();
					System.out.println("Player Daum 입력 성공" + " / " + pid_daum + " / " + PNUM + " / " + PBIRTH + " / " + PPOSITION + " / " + PKO + " / " + PENLASTNAME + " / " + PENLASTNAME2 + " / " + PENFIRSTNAME + " / " + PENFIRSTNAME2);
					try {
						System.out.println("player Daum 입력 후 pstmt close 완료");
					} catch (Exception e3) {
						System.out.println("player Daum 입력 후 pstmt close 실패");
						System.out.println(e3);
					}
				} else {
//					pstmt.close();
					System.out.println("Player Daum 입력 실패 cnt=" + cnt + " ");
					try {
						System.out.println("player Daum 입력 실패 후 pstmt close 완료");
					} catch (Exception e3) {
						System.out.println("player Daum 입력 실패 후 pstmt close 실패");
						System.out.println(e3);
					}
				}
//				if(pstmt.isClosed()) {
//					System.out.println("finally 전에 닫혔음");
//				} else {
//					System.out.println("finally 전에 열렸음");
//				}
			
			} catch (Exception e) {
				sql = "UPDATE PLAYER SET PID_DAUM = ?, PKO = ?, updateDate=sysdate+9/24"
						+ " WHERE PID_SOFA = ? OR (PNUM = ? AND PPOSITION LIKE ? AND (PEN LiKE ? OR PEN LIKE ?) AND (PEN LIKE ? OR PEN LIKE ?))";
				try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, pid_daum);
				pstmt.setString(2, PKO);
				pstmt.setInt(3, pid_sofa);
				pstmt.setInt(4, PNUM);
				pstmt.setString(5, PPOSITION);
				pstmt.setString(6, "%"+PENLASTNAME+"%");
				pstmt.setString(7, "%"+PENLASTNAME+"%");
				pstmt.setString(8, "%"+PENFIRSTNAME+"%");
				pstmt.setString(9, "%"+PENFIRSTNAME2+"%");
				
				int cnt = pstmt.executeUpdate();
					if (cnt == 1) {
//						pstmt.close();
						System.out.println("Player Daum 업데이트 성공 "+" / "+ pid_daum + " / " + PNUM + " / " + PBIRTH + " / " + PPOSITION + " / " + PKO + " / " + PENLASTNAME + " / " + PENLASTNAME2 + " / " + PENFIRSTNAME + " / " + PENFIRSTNAME2);
						try {
							System.out.println("player Daum 업데이트 성공 후 pstmt close 완료");
						} catch (Exception e2) {
							System.out.println("player Daum 업데이트 성공 후 pstmt close 실패");
							System.out.println(e2);
						}
					}
					else {
//						pstmt.close();
						System.out.println("Player Daum 업데이트 실패 cnt="+ cnt + " ");
						try {
							System.out.println("player Daum 업데이트 실패 후 pstmt close 완료");
						} catch (Exception e2) {
							System.out.println("player Daum 업데이트 실패 후 pstmt close 실패");
							System.out.println(e2);
						}
					}
//					if(pstmt.isClosed()) {
//						System.out.println("finally 전에 닫혔음");
//					} else {
//						System.out.println("finally 전에 열렸음");
//					}
					
				} catch ( Exception e1 ){
//					pstmt.close();
					e1.printStackTrace();
					System.out.println("Player Daum 입력/업데이트 실패 !!! " + pid_daum + " / " + PNUM + " / " + PBIRTH + " / " + PPOSITION + " / " + PKO + " / " + PENLASTNAME + " / " + PENLASTNAME2 + " / " + PENFIRSTNAME + " / " + PENFIRSTNAME2);
				} 
			} finally {
				try {
					if(pstmt != null || !pstmt.isClosed()) {
						pstmt.close();
						System.out.println("finally 에서 종료");
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
				if(pstmt.isClosed()) {
					System.out.println("finally 에서 닫혀있음");
				} else {
					System.out.println("finally 에서 열렸음");
				}
			}
		} 
		else {
			String sql = "INSERT INTO PLAYER(PID_DAUM, PKO, updateDate) VALUES(?, ?, sysdate+9/24)"
					+ " WHERE PID_SOFA = ? OR (PNUM = ? AND PBIRTH LIKE ? AND PPOSITION LIKE ? AND (PEN LiKE ? OR PEN LIKE ?) AND (PEN LIKE ? OR PEN LIKE ?))";
																	
			try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, pid_daum);
			pstmt.setString(2, PKO);
			pstmt.setInt(3, pid_sofa);
			pstmt.setInt(4, PNUM);
			pstmt.setString(5, "%"+PBIRTH+"%");
			pstmt.setString(6, PPOSITION);
			pstmt.setString(7, "%"+PENLASTNAME+"%");
			pstmt.setString(8, "%"+PENLASTNAME2+"%");
			pstmt.setString(9, "%"+PENFIRSTNAME+"%");
			pstmt.setString(10, "%"+PENFIRSTNAME2+"%");
			
			
			int cnt = pstmt.executeUpdate();
				if(cnt == 1) {
//					pstmt.close();
					System.out.println("Player Daum 입력 성공" + " / " + pid_daum + " / " + PNUM + " / " + PBIRTH + " / " + PPOSITION + " / " + PKO + " / " + PENLASTNAME + " / " + PENLASTNAME2 + " / " + PENFIRSTNAME + " / " + PENFIRSTNAME2);
					try {
						System.out.println("player Daum 입력 후 pstmt close 완료");
					} catch (Exception e3) {
						System.out.println("player Daum 입력 후 pstmt close 실패");
						System.out.println(e3);
					}
				} else {
//					pstmt.close();
					System.out.println("Player Daum 입력 실패 cnt=" + cnt + " ");
					try {
						System.out.println("player Daum 입력 실패 후 pstmt close 완료");
					} catch (Exception e3) {
						System.out.println("player Daum 입력 실패 후 pstmt close 실패");
						System.out.println(e3);
					}
				} 
				
//				if(pstmt.isClosed()) {
//					System.out.println("finally 도착 전 닫혔음");
//				} else {
//					System.out.println("finally 도착 전 열렸음");
//				}
			
			} catch (Exception e) {
				sql = "UPDATE PLAYER SET PID_DAUM = ?, PKO = ?, updateDate=sysdate+9/24"
						+ " WHERE PID_SOFA = ? OR (PNUM = ? AND PBIRTH LIKE ? AND PPOSITION LIKE ? AND (PEN LiKE ? OR PEN LIKE ?) AND (PEN LIKE ? OR PEN LIKE ?))";
				try {
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, pid_daum);
				pstmt.setString(2, PKO);
				pstmt.setInt(3, pid_sofa);
				pstmt.setInt(4, PNUM);
				pstmt.setString(5, "%"+PBIRTH+"%");
				pstmt.setString(6, PPOSITION);
				pstmt.setString(7, "%"+PENLASTNAME+"%");
				pstmt.setString(8, "%"+PENLASTNAME2+"%");
				pstmt.setString(9, "%"+PENFIRSTNAME+"%");
				pstmt.setString(10, "%"+PENFIRSTNAME2+"%");
				
				int cnt = pstmt.executeUpdate();
					if (cnt == 1) {
//						pstmt.close();
						System.out.println("Player Daum 업데이트 성공 "+" / "+ pid_daum + " / " + PNUM + " / " + PBIRTH + " / " + PPOSITION + " / " + PKO + " / " + PENLASTNAME + " / " + PENLASTNAME2 + " / " + PENFIRSTNAME + " / " + PENFIRSTNAME2);
						try {
							System.out.println("player Daum 업데이트 성공 후 pstmt close 완료");
						} catch (Exception e2) {
							System.out.println("player Daum 업데이트 성공 후 pstmt close 실패");
							System.out.println(e2);
						}
					}
					else {
//						pstmt.close();
						System.out.println("Player Daum 업데이트 실패 cnt=" + cnt + " ");
						try {
							System.out.println("player Daum 업데이트 실패 후 pstmt close 완료");
						} catch (Exception e2) {
							System.out.println("player Daum 업데이트 실패 후 pstmt close 실패");
							System.out.println(e2);
						}
					}
//					if(pstmt.isClosed()) {
//						System.out.println("finally 도착 전 닫혔음");
//					} else {
//						System.out.println("finally 도착 전 열렸음");
//					}
					
				} catch ( Exception e1 ){
					e1.printStackTrace();
					System.out.println("Player Daum 입력/업데이트 실패 !!! "+ pid_daum + " / " + PNUM + " / " + PBIRTH + " / " + PPOSITION + " / " + PKO + " / " + PENLASTNAME + " / " + PENFIRSTNAME + " / " + PENFIRSTNAME2);
				}
			} finally {
				try {
					if(pstmt != null || !pstmt.isClosed()) {
						pstmt.close();
						System.out.println("finally 에서 종료");
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
				if(pstmt.isClosed()) {
					System.out.println("finally 에서 닫혀있음");
				} else {
					System.out.println("finally 에서 열렸음");
				}
			}
		}
	}
	
//	public void insertPlayerDaum(int pid_daum, int pid_sofa, String PKO, int PNUM, String PBIRTH, int PHEIGHT) {
//		String sql = "INSERT INTO PLAYER(PID_DAUM, PKO, updateDate) VALUES(?, ?, sysdate+9/24)"
//				+ " WHERE (PNUM = ? AND PBIRTH = ?) AND PHEIGHT BETWEEN ?-1 AND ?+1";
//																
//		try {
//		pstmt = con.prepareStatement(sql);
//		
//		pstmt.setInt(1, pid_daum);
//		pstmt.setString(2, PKO);
//		pstmt.setInt(3, PNUM);
//		pstmt.setString(4, PBIRTH);
//		pstmt.setInt(5, PHEIGHT);
//		pstmt.setInt(6, PHEIGHT);
//		int cnt = pstmt.executeUpdate();
//			if(cnt == 1) {
//				System.out.println("Player Daum 입력 성공" +" / "+ pid_daum +" / "+ PKO +" / "+ PNUM +" / "+ PBIRTH +" / "+ PHEIGHT);
//				try {
//					System.out.println("player Daum 입력 후 pstmt close 완료");
//				} catch (Exception e3) {
//					System.out.println("player Daum 입력 후 pstmt close 실패");
//					System.out.println(e3);
//				}
//			} else {
//				System.out.println("Player Daum 입력 실패");
//				try {
//					System.out.println("player Daum 입력 실패 후 pstmt close 완료");
//				} catch (Exception e3) {
//					System.out.println("player Daum 입력 실패 후 pstmt close 실패");
//					System.out.println(e3);
//				}
//			} pstmt.close();
//		
//		} catch (Exception e) {
//			sql = "UPDATE PLAYER SET PID_DAUM = ?, PKO = ?, updateDate=sysdate+9/24"
//					+ " WHERE (PNUM = ? AND PBIRTH = ?) AND PHEIGHT BETWEEN ?-1 AND ?+1";
//			try {
//			pstmt = con.prepareStatement(sql);
//			
//			pstmt.setInt(1, pid_daum);
//			pstmt.setString(2, PKO);
//			pstmt.setInt(3, PNUM);
//			pstmt.setString(4, PBIRTH);
//			pstmt.setInt(5, PHEIGHT);
//			pstmt.setInt(6, PHEIGHT);
//			int cnt = pstmt.executeUpdate();
//				if (cnt == 1) {
//					System.out.println("Player Daum 업데이트 성공 "+" / "+ pid_daum +" / "+ PKO +" / "+ PNUM +" / "+ PBIRTH +" / "+ PHEIGHT);
//					try {
//						System.out.println("player Daum 업데이트 성공 후 pstmt close 완료");
//					} catch (Exception e2) {
//						System.out.println("player Daum 업데이트 성공 후 pstmt close 실패");
//						System.out.println(e2);
//					}
//				}
//				else {
//					System.out.println("Player Daum 업데이트 실패");
//					try {
//						System.out.println("player Daum 업데이트 실패 후 pstmt close 완료");
//					} catch (Exception e2) {
//						System.out.println("player Daum 업데이트 실패 후 pstmt close 실패");
//						System.out.println(e2);
//					}
//				} pstmt.close();
//				
//			} catch ( Exception e1 ){
//				e1.printStackTrace();
//				System.out.println("Player Daum 입력/업데이트 실패 !!! "+ pid_daum +" / "+ PKO +" / "+ PNUM +" / "+ PBIRTH +" / "+ PHEIGHT);
//			}
//		} finally {
//			try {
//				if(pstmt != null) pstmt.close();
//			} catch(SQLException e) {
//				e.printStackTrace();
//			}	
//		}
//	}
	
	

	public void insertPlayerStat(
		// ETC Set
		String s_psid, int i_pid_sofa, int i_sid_sofa, int i_lid_sofa, int i_tid_sofa,
		
		// Attack Set
		double d_rating, int i_goals, int i_big_chances_missed, int i_succ_dribbles, double d_successful_dribbles_per, 
		int i_total_shots, int i_shots_on_target, int i_shots_off_target, double d_goal_conversion_per, int i_penalties_taken, 
		int i_penalty_goals, int i_penalty_won, int i_shots_from_set_piece, int i_free_kick_goals, int i_goals_from_inside_the_box,
		int i_goals_from_outside_the_box, int i_headed_goals, int i_left_foot_goals, int i_right_foot_goals, int i_hit_woodwork, 
		int i_offsides, double d_penalty_conversion_per, double d_set_piece_conversion_per,
		
		// Defence Set
		int i_tackles, int i_interceptions, int i_penalty_committed, int i_clearances, int i_errors_lead_to_goal,
		int i_errors_lead_to_shot, int i_own_goals, int i_dribbled_past, int i_blocked_shots, int i_clean_sheets,
		
		// Pass Set
		int i_big_chances_created, int i_assists, int i_acc_passes, int i_inaccurate_passs, int i_total_passes,
		double d_accurate_passes_per, int i_key_passes, 
		int i_accurate_crosses, double d_accurate_crosses_per,	int i_acc_long_ball, double d_accurate_long_balls_per, int i_passes_to_assist, 
		
		// Other Set			
		int i_yellow_cards, int i_red_cards, int i_ground_duels_won, double d_ground_duels_won_per, int i_aerial_duels_won,
		double d_aerial_duels_won_per, int i_total_duels_won, double d_total_duels_won_per, int i_minutes_played, int i_was_fouled, 
		int i_fouls, int i_dispossessed, int i_possession_lost, int i_appearances, int i_started,
		
		// Goalkeeper
		int i_saves, int i_penalties_faced, int i_penalties_saved, int i_saves_from_inside_box, int i_saved_shots_from_outside_the_box, 
		int i_goals_conceded_inside_the_box, int i_goals_conceded_outside_the_box, int i_punches, int i_runs_out, int i_successful_runs_out,
		int i_high_claims, int i_crosses_not_claimed
		
		) throws SQLException {
		try {
			String sql="INSERT INTO playerstat VALUES( "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, "
					+ "sysdate+9/24 )";
	
	
			pstmt = con.prepareStatement(sql);
	
			// ETC set --------------------------------
			pstmt.setString(1, s_psid);
			pstmt.setInt(2, i_sid_sofa);
			pstmt.setInt(3, i_lid_sofa);
			pstmt.setInt(4, i_tid_sofa);
			pstmt.setInt(5, i_pid_sofa);
			
			// Attack set ===================
			// 1 set---------------------------------------
			pstmt.setDouble(6, d_rating);
			pstmt.setInt(7, i_goals);
			pstmt.setInt(8, i_big_chances_missed);
			pstmt.setInt(9, i_succ_dribbles);
			pstmt.setDouble(10, d_successful_dribbles_per);
			// 2 set---------------------------------------
			pstmt.setInt(11, i_total_shots);
			pstmt.setInt(12, i_shots_on_target);
			pstmt.setInt(13, i_shots_off_target);
			pstmt.setDouble(14, d_goal_conversion_per);
			pstmt.setInt(15, i_penalties_taken);
			// 3 set---------------------------------------
			pstmt.setInt(16, i_penalty_goals);
			pstmt.setInt(17, i_penalty_won);
			pstmt.setInt(18, i_shots_from_set_piece);
			pstmt.setInt(19, i_free_kick_goals);
			pstmt.setInt(20, i_goals_from_inside_the_box);
			// 4 set---------------------------------------
			pstmt.setInt(21, i_goals_from_outside_the_box);
			pstmt.setInt(22, i_headed_goals);
			pstmt.setInt(23, i_left_foot_goals);
			pstmt.setInt(24, i_right_foot_goals);
			pstmt.setInt(25, i_hit_woodwork);
			// 5 set---------------------------------------
			pstmt.setInt(26, i_offsides);
			pstmt.setDouble(27, d_penalty_conversion_per);
			pstmt.setDouble(28, d_set_piece_conversion_per);
			
			
			// Defence set ===================
			// 6 set---------------------------------------
			pstmt.setInt(29, i_tackles);
			pstmt.setInt(30, i_interceptions);
			pstmt.setInt(31, i_penalty_committed);
			pstmt.setInt(32, i_clearances);
			pstmt.setInt(33, i_errors_lead_to_goal);
			// 7 set---------------------------------------
			pstmt.setInt(34, i_errors_lead_to_shot);
			pstmt.setInt(35, i_own_goals);
			pstmt.setInt(36, i_dribbled_past);
			pstmt.setInt(37, i_blocked_shots);
			pstmt.setInt(38, i_clean_sheets);
			
			// pass set ===================
			// 8 set---------------------------------------
			pstmt.setInt(39, i_big_chances_created);
			pstmt.setInt(40, i_assists);
			pstmt.setInt(41, i_acc_passes);
			pstmt.setInt(42, i_inaccurate_passs);
			pstmt.setInt(43, i_total_passes);
			// 9 set---------------------------------------
			pstmt.setDouble(44, d_accurate_passes_per);
			pstmt.setInt(45, i_key_passes);
			// 10 set---------------------------------------
			pstmt.setInt(46, i_accurate_crosses);
			pstmt.setDouble(47, d_accurate_crosses_per);
			pstmt.setInt(48, i_acc_long_ball);
			pstmt.setDouble(49, d_accurate_long_balls_per);
			pstmt.setInt(50, i_passes_to_assist);
			
			
			// Other set ===================
			// 11 set---------------------------------------
			pstmt.setInt(51, i_yellow_cards);
			pstmt.setInt(52, i_red_cards);
			pstmt.setInt(53, i_ground_duels_won);
			pstmt.setDouble(54, d_ground_duels_won_per);
			pstmt.setInt(55, i_aerial_duels_won);
			// 12 set---------------------------------------
			pstmt.setDouble(56, d_aerial_duels_won_per);
			pstmt.setInt(57, i_total_duels_won);
			pstmt.setDouble(58, d_total_duels_won_per);
			pstmt.setInt(59, i_minutes_played);
			pstmt.setInt(60, i_was_fouled);
			// 13 set---------------------------------------
			pstmt.setInt(61, i_fouls);
			pstmt.setInt(62, i_dispossessed);
			pstmt.setInt(63, i_possession_lost);
			pstmt.setInt(64, i_appearances);
			pstmt.setInt(65, i_started);	
		
			
			// Goalkeeper set ===================
			// 14 set---------------------------------------
			pstmt.setInt(66, i_saves);
			pstmt.setInt(67, i_penalties_faced);
			pstmt.setInt(68, i_penalties_saved);
			pstmt.setInt(69, i_saves_from_inside_box);
			pstmt.setInt(70, i_saved_shots_from_outside_the_box);
			// 15 set---------------------------------------
			pstmt.setInt(71, i_goals_conceded_inside_the_box);
			pstmt.setInt(72, i_goals_conceded_outside_the_box);
			pstmt.setInt(73, i_punches);
			pstmt.setInt(74, i_runs_out);
			pstmt.setInt(75, i_successful_runs_out);
			// 16 set---------------------------------------
			pstmt.setInt(76, i_high_claims);
			pstmt.setInt(77, i_crosses_not_claimed);
		
			int cnt = pstmt.executeUpdate();

			if(cnt == 1) {
				System.out.println("PlayerStat 입력 성공 "+ i_pid_sofa);
//			System.out.println("PlayerStat 입력 성공"
//				+" [ETC set] "
//				+ s_psid +" | "+ i_pid_sofa +" | "+ i_sid_sofa +" | "+ i_lid_sofa +" | "+ i_tid_sofa +" | "
//				
//				+" [Attack set] "
//				+ d_rating +" | "+ i_goals +" | "+ i_big_chances_missed +" | "+ i_succ_dribbles +" | "+ d_successful_dribbles_per +" | "
//				+ i_total_shots +" | "+ i_shots_on_target +" | "+ i_shots_off_target +" | "+ d_goal_conversion_per +" | "+ i_penalties_taken +" | "
//				+ i_penalty_goals +" | "+ i_penalty_won +" | "+ i_shots_from_set_piece +" | "+ i_free_kick_goals +" | "+ i_goals_from_inside_the_box +" | "
//				+ i_goals_from_outside_the_box +" | "+ i_headed_goals +" | "+ i_left_foot_goals +" | "+ i_right_foot_goals +" | "+ i_hit_woodwork +" | "
//				+ i_offsides +" | "+ d_penalty_conversion_per +" | "+ d_set_piece_conversion_per +" | "
//				
//				+" [Defence set] "
//				+ i_tackles +" | "+ i_interceptions +" | "+ i_penalty_committed +" | "+ i_clearances +" | "+ i_errors_lead_to_goal +" | "
//				+ i_errors_lead_to_shot +" | "+ i_own_goals +" | "+ i_dribbled_past +" | "+ i_blocked_shots +" | "+ i_clean_sheets +" | "
//									
//				+" [Pass set] "
//				+ i_big_chances_created +" | "+ i_assists +" | "+ i_acc_passes +" | "+ i_inaccurate_passs +" | "+ i_total_passes +" | "
//				+ d_accurate_passes_per +" | "+ i_key_passes +" | "
//				+ i_accurate_crosses +" | "+ d_accurate_crosses_per +" | "+ i_acc_long_ball +" | "+ d_accurate_long_balls_per +" | "+ i_passes_to_assist +" | "
//
//				+" [Other set] "
//				+ i_yellow_cards +" | "+ i_red_cards +" | "+ i_ground_duels_won +" | "+ d_ground_duels_won_per +" | "+ i_aerial_duels_won +" | "
//				+ d_aerial_duels_won_per +" | "+ i_total_duels_won +" | "+ d_total_duels_won_per +" | "+ i_minutes_played +" | "+ i_was_fouled +" | "
//				+ i_fouls +" | "+ i_dispossessed +" | "+ i_possession_lost +" | "+ i_appearances +" | "+ i_started +" | "
//
//				+" [Goalkeeper set] "
//				+ i_saves +" | "+ i_penalties_faced +" | "+ i_penalties_saved +" | "+ i_saves_from_inside_box +" | "+ i_saved_shots_from_outside_the_box +" | "
//				+ i_goals_conceded_inside_the_box +" | "+ i_goals_conceded_outside_the_box +" | "+ i_punches +" | "+ i_runs_out +" | "+ i_successful_runs_out +" | "
//				+ i_high_claims +" | "+ i_crosses_not_claimed );
						
			} else {
				System.out.println("PlayerStat 입력 실패 "+ i_pid_sofa);
			} pstmt.close();
		} catch (Exception e) {
			try {
				String sql="UPDATE playerstat SET "
					// ETC Set
					+ "pid_sofa=?, sid_sofa=?, lid_sofa=?, tid_sofa=?, "
					// Attack Set
					+ "rating=?, goals=?, big_chances_missed=?, succ_dribbles=?, successful_dribbles_per=?, "
					+ "total_shots=?, shots_on_target=?, shots_off_target=?, goal_conversion_per=?, penalties_taken=?, "
					+ "penalty_goals=?, penalty_won=?, shots_from_set_piece=?, free_kick_goals=?, goals_from_inside_the_box=?, "
					+ "goals_from_outside_the_box=?, headed_goals=?, left_foot_goals=?, right_foot_goals=?, hit_woodwork=?, "
					+ "offsides=?, penalty_conversion_per=?, set_piece_conversion_per=?, "
					// Defence Set
					+ "tackles=?, interceptions=?, penalty_committed=?, clearances=?, errors_lead_to_goal=?, "
					+ "errors_lead_to_shot=?, own_goals=?, dribbled_past=?, blocked_shots=?, clean_sheets=?, "
					// Pass Set
					+ "big_chances_created=?, assists=?, acc_passes=?, inaccurate_passs=?, total_passes=?, "
					+ "accurate_passes_per=?, key_passes=?, "
					+ "accurate_crosses=?, accurate_crosses_per=?, acc_long_ball=?, accurate_long_balls_per=?, passes_to_assist=?, "
					// Other Set
					+ "yellow_cards=?, red_cards=?, ground_duels_won=?, ground_duels_won_per=?, aerial_duels_won=?, "
					+ "aerial_duels_won_per=?, total_duels_won=?, total_duels_won_per=?, minutes_played=?, was_fouled=?, "
					+ "fouls=?, dispossessed=?, possession_lost=?, appearances=?, started=?, "
					// Goalkeeper
					+ "saves=?, penalties_faced=?, penalties_saved=?, saves_from_inside_box=?, saved_shots_from_outside_the_box=?, "
					+ "goals_conceded_inside_the_box=?, goals_conceded_outside_the_box=?, punches=?, runs_out=?, successful_runs_out=?, "
					+ "high_claims=?, crosses_not_claimed=?, "
					// Finish
					+ "updateDate=sysdate+9/24 "
	    		    + " WHERE psid =?";
																		
				pstmt = con.prepareStatement(sql);

				// ETC set --------------------------------
				pstmt.setInt(1, i_pid_sofa);
				pstmt.setInt(2, i_sid_sofa);
				pstmt.setInt(3, i_lid_sofa);
				pstmt.setInt(4, i_tid_sofa);
	
				// Attack set ===================
				// 1 set---------------------------------------
				pstmt.setDouble(5, d_rating);
				pstmt.setInt(6, i_goals);
				pstmt.setInt(7, i_big_chances_missed);
				pstmt.setInt(8, i_succ_dribbles);
				pstmt.setDouble(9, d_successful_dribbles_per);
				// 2 set---------------------------------------
				pstmt.setInt(10, i_total_shots);
				pstmt.setInt(11, i_shots_on_target);
				pstmt.setInt(12, i_shots_off_target);
				pstmt.setDouble(13, d_goal_conversion_per);
				pstmt.setInt(14, i_penalties_taken);
				// 3 set---------------------------------------
				pstmt.setInt(15, i_penalty_goals);
				pstmt.setInt(16, i_penalty_won);
				pstmt.setInt(17, i_shots_from_set_piece);
				pstmt.setInt(18, i_free_kick_goals);
				pstmt.setInt(19, i_goals_from_inside_the_box);
				// 4 set---------------------------------------
				pstmt.setInt(20, i_goals_from_outside_the_box);
				pstmt.setInt(21, i_headed_goals);
				pstmt.setInt(22, i_left_foot_goals);
				pstmt.setInt(23, i_right_foot_goals);
				pstmt.setInt(24, i_hit_woodwork);
				// 5 set---------------------------------------
				pstmt.setInt(25, i_offsides);
				pstmt.setDouble(26, d_penalty_conversion_per);
				pstmt.setDouble(27, d_set_piece_conversion_per);
				
				// Defence set ===================
				// 6 set---------------------------------------
				pstmt.setInt(28, i_tackles);
				pstmt.setInt(29, i_interceptions);
				pstmt.setInt(30, i_penalty_committed);
				pstmt.setInt(31, i_clearances);
				pstmt.setInt(32, i_errors_lead_to_goal);
				// 7 set---------------------------------------
				pstmt.setInt(33, i_errors_lead_to_shot);
				pstmt.setInt(34, i_own_goals);
				pstmt.setInt(35, i_dribbled_past);
				pstmt.setInt(36, i_blocked_shots);
				pstmt.setInt(37, i_clean_sheets);
				
				// pass set ===================
				// 8 set---------------------------------------
				pstmt.setInt(38, i_big_chances_created);
				pstmt.setInt(39, i_assists);
				pstmt.setInt(40, i_acc_passes);
				pstmt.setInt(41, i_inaccurate_passs);
				pstmt.setInt(42, i_total_passes);
				// 9 set---------------------------------------
				pstmt.setDouble(43, d_accurate_passes_per);
				pstmt.setInt(44, i_key_passes);
				// 10 set---------------------------------------
				pstmt.setInt(45, i_accurate_crosses);
				pstmt.setDouble(46, d_accurate_crosses_per);
				pstmt.setInt(47, i_acc_long_ball);
				pstmt.setDouble(48, d_accurate_long_balls_per);
				pstmt.setInt(49, i_passes_to_assist);
				
				// Other set ===================
				// 11 set---------------------------------------
				pstmt.setInt(50, i_yellow_cards);
				pstmt.setInt(51, i_red_cards);
				pstmt.setInt(52, i_ground_duels_won);
				pstmt.setDouble(53, d_ground_duels_won_per);
				pstmt.setInt(54, i_aerial_duels_won);
				// 12 set---------------------------------------
				pstmt.setDouble(55, d_aerial_duels_won_per);
				pstmt.setInt(56, i_total_duels_won);
				pstmt.setDouble(57, d_total_duels_won_per);
				pstmt.setInt(58, i_minutes_played);
				pstmt.setInt(59, i_was_fouled);
				// 13 set---------------------------------------
				pstmt.setInt(60, i_fouls);
				pstmt.setInt(61, i_dispossessed);
				pstmt.setInt(62, i_possession_lost);
				pstmt.setInt(63, i_appearances);
				pstmt.setInt(64, i_started);	
			
				// Goalkeeper set ===================
				// 14 set---------------------------------------
				pstmt.setInt(65, i_saves);
				pstmt.setInt(66, i_penalties_faced);
				pstmt.setInt(67, i_penalties_saved);
				pstmt.setInt(68, i_saves_from_inside_box);
				pstmt.setInt(69, i_saved_shots_from_outside_the_box);
				// 15 set---------------------------------------
				pstmt.setInt(70, i_goals_conceded_inside_the_box);
				pstmt.setInt(71, i_goals_conceded_outside_the_box);
				pstmt.setInt(72, i_punches);
				pstmt.setInt(73, i_runs_out);
				pstmt.setInt(74, i_successful_runs_out);
				// 16 set---------------------------------------
				pstmt.setInt(75, i_high_claims);
				pstmt.setInt(76, i_crosses_not_claimed);
				
				// Where -----------------------------
				pstmt.setString(77, s_psid);
	
				pstmt.executeUpdate();
				System.out.println("PlayerStat 업데이트 성공 "+ i_pid_sofa);
				
				//rs.close();
				//con.close();
				//e.printStackTrace();
				
			} catch (Exception e2){
				System.out.println("PlayerStat 입력/업데이트 실패 "+ i_pid_sofa);
				e2.printStackTrace();
			} pstmt.close();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
	}
	
	
	
//	public void insertPlayer(int pid_sofa, String pen, String pslug) {
//		String sql = "INSERT INTO player(pid_sofa, pen, pslug, updateDate) VALUES(?, ?, ?, sysdate+9/24)";
//																
//		try {
//		pstmt = con.prepareStatement(sql);
//		
//		pstmt.setInt(1, pid_sofa);
//		pstmt.setString(2, pen);
//		pstmt.setString(3, pslug);
//		int cnt = pstmt.executeUpdate();
//
//		if(cnt == 1) {
//			System.out.println("선수 추가 완료 "+ pid_sofa +" / "+ pen +" / "+ pslug);
//		}
//		pstmt.close();
//
//		//rs.close();
//		//con.close();
//		} catch (Exception e) {
//			sql="UPDATE player "
//					+ "SET pen=?, pslug=?, updateDate=sysdate+9/24 "
//	    		      + " WHERE pid_sofa =?";
//			try {
//			pstmt = con.prepareStatement(sql);
//			
//			pstmt.setString(1, pen);
//			pstmt.setString(2, pslug);
//			pstmt.setInt(3, pid_sofa);
//			pstmt.executeUpdate();
//
//			pstmt.close();
//			
//			System.out.println("선수 업데이트 완료 "+ pid_sofa +" / "+ pen +" / "+ pslug);
//			} catch ( Exception e1 ){
//				System.out.println("선수 추가/업데이트 실패!!!!!!!!!!!!!!!!!! "+ pid_sofa +" / "+ pen +" / "+ pslug);
//				e1.printStackTrace();
//			} finally {
//	    		try { rs.close(); } catch (Exception e1) { /* ignored */ }
//			    try { pstmt.close(); } catch (Exception e1) { /* ignored */ }
//			    try { con.close();} catch (Exception e1) { /* ignored */ }
//	    	}
//		}
//	}	
//	
//
//	
//	public void updatePlayer2(String pimg, String pnum, String lid_sofa, int tid_sofa, int pheight, String pnation, String pbirth, String pnationimg, String pfoot, String pposition, int pid_sofa) {
//		String sql="UPDATE player "
//				+ "SET pimg=?, pnum=?, lid_sofa=?, tid_sofa=?, pheight=?, pnation=?, pbirth=?, pnationimg=?, pfoot=?, pposition=?, updateDate=sysdate+9/24 "
//    		      + " WHERE PID_SOFA =?";
//																
//		try {
//		pstmt = con.prepareStatement(sql);
//		
//		
//		pstmt.setString(1, pimg);
//		pstmt.setString(2, pnum);
//		pstmt.setString(3, lid_sofa);
//		pstmt.setInt(4, tid_sofa);
//		pstmt.setInt(5, pheight);
//		pstmt.setString(6, pnation);
//		pstmt.setString(7, pbirth);
//		pstmt.setString(8, pnationimg);
//		pstmt.setString(9, pfoot);
//		pstmt.setString(10, pposition);
//		pstmt.setInt(11, pid_sofa);
//
//		int cnt = pstmt.executeUpdate();
//	
//		pstmt.close();
//		//rs.close();
//		//con.close();
//		
//		if(cnt == 1) {
//			System.out.println("선수 업데이트 완료(3) "+ pid_sofa +" / "+ pimg +" / "+ pnum +" / "+ lid_sofa +" / "+ tid_sofa +" / "+ pheight +" / "+ pnation +" / "+ pbirth +" / "+ pnationimg +" / "+ pfoot +" / "+ pposition);
//		}
//		} catch (Exception e) {
//			System.out.println("선수 업데이트 실패(3) "+ pid_sofa +" / "+ pimg +" / "+ pnum +" / "+ lid_sofa +" / "+ tid_sofa +" / "+ pheight +" / "+ pnation +" / "+ pbirth +" / "+ pnationimg +" / "+ pfoot +" / "+ pposition);
//			e.printStackTrace();
//		}
//	}	
//	
//
//	
//	public String[] getPidList(){
//		String [] pid_sofa = new String [10000];
//		String sql = "SELECT pid_sofa, pslug FROM player WHERE pid_sofa IS NOT NULL";
//		
//		try {
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			
//			for (int i = 0; rs.next(); i++) {
//				String splsug = rs.getString("PSLUG");
//				String spid_sofa = String.valueOf(rs.getInt("pid_sofa"));  
//				pid_sofa[i] = splsug+"/"	+spid_sofa;
//				//System.out.println(pid_sofa[i]);
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return pid_sofa;
//	}
//
//
//
//	public void updatePlayer1(int pid_daum, String pko, String pen) {
//		String sql="UPDATE player "
//				+ "SET pid_daum=?, pko=?, updateDate=sysdate+9/24 "
//    		      + " WHERE lower(pen) =?";
//		try {
//		
//		pen = pen.replace("-", " ");
//		pen = pen.toLowerCase();
//			
//		pstmt = con.prepareStatement(sql);
//		
//		pstmt.setInt(1, pid_daum);
//		pstmt.setString(2, pko);
//		pstmt.setString(3, pen);
//		int cnt = pstmt.executeUpdate();
//
//		if(cnt == 1) {
//			System.out.println("선수 업데이트 완료(3) "+ pid_daum +" / "+ pko +" / "+pen);
//		}
//		
//		pstmt.close();
//		} catch ( Exception e1 ){
//			sql="UPDATE player "
//					+ "SET pko=?, updateDate=sysdate+9/24 "
//	    		      + " WHERE lower(pen) =?";
//			try {
//			
//			pen = pen.replace("-", " ");
//			pen = pen.toLowerCase();
//				
//			pstmt = con.prepareStatement(sql);
//			
//			pstmt.setString(1, pko);
//			pstmt.setString(2, pen);
//			pstmt.executeUpdate();	
//			pstmt.close();
//			System.out.println("선수 업데이트 이름만 완료(3) "+ pid_daum +" / "+ pko +" / "+pen);
//
//	
//			} catch ( Exception e ) {
//				System.out.println("선수 업데이트 실패(3) "+ pid_daum +" / "+ pko +" / "+pen);
//			}
//		}
//	}
//
//
//
//	public void loadTeam1(int tid_sofa, int lid_sofa, String ten, String tslug, String lslid) {
//		String sql = "INSERT INTO team(tid_sofa, lid_sofa, ten, tslug, lslid, updateDate) VALUES(?, ?, ?, ?, ?, sysdate+9/24)";
//		
//		try {
//		pstmt = con.prepareStatement(sql);
//		
//		pstmt.setInt(1, tid_sofa);
//		pstmt.setInt(2, lid_sofa);
//		pstmt.setString(3, ten);
//		pstmt.setString(4, tslug);
//		pstmt.setString(5, lslid);
//		int cnt = pstmt.executeUpdate();
//
//		if(cnt == 1) {
//			System.out.println("팀 추가 완료 "+ tid_sofa +" / "+ lid_sofa +" / "+ ten +" / "+ tslug +" / "+ lslid);
//		}
//		pstmt.close();
//
//		//rs.close();
//		//con.close();
//		} catch (Exception e) {
//			sql="UPDATE team "
//					+ "SET lid_sofa=?, ten=?, tslug=?, lslid=?, updateDate=sysdate+9/24 "
//	    		      + " WHERE tid_sofa =?";
//			try {
//			pstmt = con.prepareStatement(sql);
//			
//			pstmt.setInt(1, lid_sofa);
//			pstmt.setString(2, ten);
//			pstmt.setString(3, tslug);
//			pstmt.setString(4, lslid);
//			pstmt.setInt(5, tid_sofa);
//			pstmt.executeUpdate();
//
//			pstmt.close();
//			
//			System.out.println("팀 업데이트 완료 "+ tid_sofa +" / "+ lid_sofa +" / "+ ten +" / "+ tslug +" / "+ lslid);
//			} catch ( Exception e1 ){
//				System.out.println("팀 추가/업데이트 실패!!! "+ tid_sofa +" / "+ lid_sofa +" / "+ ten +" / "+ tslug +" / "+ lslid);
//				e1.printStackTrace();
//			}
//		}
//	}
//
//
//
//	public void loadTeam2(int tid_daum, String tko, String timg, String lslid) {
//		String sql="UPDATE team "
//				+ "SET tid_daum=?, tko=?, timg=?, updateDate=sysdate+9/24 "
//    		      + " WHERE lslid =?";
//		try {
//		pstmt = con.prepareStatement(sql);
//		
//		pstmt.setInt(1, tid_daum);
//		pstmt.setString(2, tko);
//		pstmt.setString(3, timg);
//		pstmt.setString(4, lslid);
//		pstmt.executeUpdate();
//
//		pstmt.close();
//		
//		System.out.println("팀 업데이트 완료 "+ tid_daum +" / "+ tko +" / "+ timg.substring(0,5) +" / "+ lslid);
//		} catch ( Exception e1 ){
//			System.out.println("팀 업데이트 실패 "+ tid_daum +" / "+ tko +" / "+ timg.substring(0,5) +" / "+ lslid);
//			e1.printStackTrace();
//		}
//	}
	
	
	
	// SQUAD 테이블에 선수단 삽입
	public void insertSquad(String psid_daum, int pid_sofa, int tid_sofa, int pid_daum, int tid_daum, String pname_ko, 
			String back_num, String position_eng, String position_ko, String p_img_url, String no_img_url,
			String team_img_url, int ranking, int win, int draw, int loss, int pts, int gf, int ga, int total_game) {
		String sql = "INSERT INTO SQUAD VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
																
		try {
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, psid_daum);
		pstmt.setInt(2, pid_sofa);
		pstmt.setInt(3, tid_sofa);
		pstmt.setInt(4, pid_daum);
		pstmt.setInt(5, tid_daum);
		pstmt.setString(6, pname_ko);
		pstmt.setString(7, back_num);
		pstmt.setString(8, position_eng);
		pstmt.setString(9, position_ko);
		pstmt.setString(10, p_img_url);
		pstmt.setString(11, no_img_url);
		pstmt.setString(12, team_img_url);
		pstmt.setInt(13, ranking);
		pstmt.setInt(14, win);
		pstmt.setInt(15, draw);
		pstmt.setInt(16, loss);
		pstmt.setInt(17, pts);
		pstmt.setInt(18, gf);
		pstmt.setInt(19, ga);
		pstmt.setInt(20, total_game);
		
		pstmt.executeUpdate();
		pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQUAD 입력 에러");
		}finally {
    		try { rs.close(); } catch (Exception e) { /* ignored */ }
		    try { pstmt.close(); } catch (Exception e) { /* ignored */ }
		    try { con.close();} catch (Exception e) { /* ignored */ }
    	}
	}
	
	
	
	public int get_position_num(int tid_sofa, String pposition) {
		String sql = "SELECT pposition FROM player WHERE tid_sofa = ? AND pposition = ?"
				+ " AND pid_daum IS NOT NULL";
		int num = 0;
		try {	
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, tid_sofa);
			pstmt.setString(2, pposition);
			rs = pstmt.executeQuery();
    		while(rs.next()) {
				num += 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();
					
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		return num;	
	}
	
	public String get_timg(int tid_daum) {
		
		String sql = "SELECT timg FROM team where tid_daum=?";
		String getImg = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, tid_daum);
			rs = pstmt.executeQuery();
    		while(rs.next()) {
    			getImg = rs.getString("timg");
				}		
	    	}catch(SQLException e) {
	    		System.out.println("get_timg 에러");
	    		e.printStackTrace();
	    	} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
				
			} return getImg;
			
		}
	
	public int get_tid_daum(String tko) {
			
			String sql = "SELECT tid_daum FROM team where tko=?";
			int tid_daum = 0;
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, tko);
				rs = pstmt.executeQuery();
	    		while(rs.next()) {
	    			tid_daum = rs.getInt("tid_daum");
					}		
		    	}catch(SQLException e) {
		    		System.out.println("get_tid_daum 에러");
		    		e.printStackTrace();
		    	} finally {
					try {
						if(rs != null) rs.close();
						if(pstmt != null) pstmt.close();
						//if(con != null) con.close();
					}catch (SQLException e) {
						e.printStackTrace();
					}
					
				} return tid_daum;
				
			}
	
	public int get_tid_sofa(String tko) {
		
		String sql = "SELECT tid_sofa FROM team where tko=?";
		int tid_sofa = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tko);
			rs = pstmt.executeQuery();
    		while(rs.next()) {
    			tid_sofa = rs.getInt("tid_sofa");
				}		
	    	}catch(SQLException e) {
	    		System.out.println("get_tid_sofa 에러");
	    		e.printStackTrace();
	    	} finally {
				try {
					if(rs != null) pstmt.close();
					if(pstmt != null) pstmt.close();
					//if(con != null) con.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
				
			} return tid_sofa;
			
		}
	
	
	public ArrayList<dtoSquad> get_Squad(int lid_sofa, int tid_sofa, int sid_sofa) {
		
		ArrayList<dtoSquad> list = new ArrayList<dtoSquad>();
		
		dtoSquad dto = null;
		String sql = "SELECT a.tko, a.timg, b.ranking, b.win, b.draw, b.loss, b.pts, b.gf, b.ga, b.gd, b.game, c.pid_sofa, c.pimg, c.pnum, c.pko, c.pposition"
				+ " FROM team a, teamrank b, player c"
				+ " WHERE a.lid_sofa=? AND a.tid_sofa=? AND b.sid_sofa=?"
				+ " AND a.lid_sofa=b.lid_sofa AND b.lid_sofa=c.lid_sofa"
				+ " AND a.tid_sofa=b.tid_sofa AND b.tid_sofa=c.tid_sofa AND c.pid_daum IS NOT NULL";
				
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, tid_sofa);
			pstmt.setInt(3, sid_sofa);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				dto = new dtoSquad();
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPimg(rs.getString("pimg"));
				dto.setPnum(rs.getString("pnum"));
				dto.setPko(rs.getString("pko"));
				dto.setPposition(rs.getString("pposition"));
				dto.setRanking(rs.getInt("Ranking"));
				dto.setWin(rs.getInt("win"));
				dto.setDraw(rs.getInt("draw"));
				dto.setLoss(rs.getInt("loss"));
				dto.setPts(rs.getInt("pts"));
				dto.setGf(rs.getInt("gf"));
				dto.setGa(rs.getInt("ga"));
				dto.setGd(rs.getInt("gd"));
				dto.setTotal_game(rs.getInt("game"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		return list;
	}

	public ArrayList<dtoTeam> get_Team(int lid_sofa) {
		
		ArrayList<dtoTeam> list = new ArrayList<dtoTeam>();
		
		dtoTeam dto = null;
		String sql = "SELECT tko, timg, tid_daum, tid_sofa FROM team WHERE lid_sofa=? ORDER BY tko";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoTeam();
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setTid_daum(rs.getInt("tid_daum"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		return list;
	}
	
	

	
	public ArrayList<dtoTeamRank> get_TeamRank(int lid_sofa, int sid_daum, String sslug) {
		
		ArrayList<dtoTeamRank> list = new ArrayList<dtoTeamRank>();
		
		dtoTeamRank dto = null;
		String sql = "select a.tid_sofa, a.ranking, a.game, a.win, a.draw, a.loss, a.gf, a.ga, a.gd, a.pts, b.tko, b.timg, b.tid_daum"
				+ " from teamrank a, team b"
				+ " where a.tid_sofa=b.tid_sofa and a.lid_sofa=? and"
				+ " a.sid_sofa=(select sid_sofa from season where sid_daum=? and sslug like ?)"
				+ " order by a.ranking";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
//			pstmt.setString(2, "%"+sid_daum+"%");
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoTeamRank();
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setRanking(rs.getInt("ranking"));
				dto.setGame(rs.getInt("game"));
				dto.setWin(rs.getInt("win"));
				dto.setDraw(rs.getInt("draw"));
				dto.setLoss(rs.getInt("loss"));
				dto.setGf(rs.getInt("gf"));
				dto.setGa(rs.getInt("ga"));
				dto.setGd(rs.getInt("gd"));
				dto.setPts(rs.getInt("pts"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setTid_daum(rs.getInt("tid_daum"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_PlayerRank 완료");
		return list;
	}
	
	
	
	
	public ArrayList<dtoPlayerRank> get_PlayerRank(int lid_sofa, int sid_daum, String sslug, String category) {
		
		ArrayList<dtoPlayerRank> list = new ArrayList<dtoPlayerRank>();
		
		dtoPlayerRank dto = null;
		String sql = "SELECT rank, tid_sofa, pid_sofa, pko, pimg, tko, timg, appearances, goals, assists, attack_points, total_shots," 
			   + " shots_on_target, penalties_taken, fouls, yellow_cards, red_cards, offsides"
			+ " FROM (SELECT rank() over(order by goals desc) rank, b.tid_sofa, b.pid_sofa, b.pko, b.pimg, c.tko, c.timg, a.appearances, a.goals, a.assists, a.goals+a.assists as attack_points,"
					+ " a.total_shots, a.shots_on_target, a.penalties_taken, a.fouls, a.yellow_cards, a.red_cards, a.offsides"
					+ " FROM playerstat a, player b, team c"
					+ " WHERE a.lid_sofa=? AND a.sid_sofa=(select sid_sofa FROM season WHERE sid_daum=? AND sslug LIKE ?)"
					+ " AND a.pid_sofa=b.pid_sofa AND b.lid_sofa=c.lid_sofa AND b.tid_sofa=c.tid_sofa"
					+ " ORDER BY goals DESC, pko DESC, appearances DESC)"
			+ " WHERE ROWNUM<=20";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
//			pstmt.setString(4, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerRank();
				dto.setRank(rs.getInt("rank"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPko(rs.getString("pko"));
				dto.setPimg(rs.getString("pimg"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setAppearances(rs.getInt("appearances"));
				dto.setGoals(rs.getInt("goals"));
				dto.setAssists(rs.getInt("assists"));
				dto.setAttack_points(rs.getInt("attack_points"));
				dto.setTotal_shots(rs.getInt("total_shots"));
				dto.setShots_on_target(rs.getInt("shots_on_target"));
				dto.setPenalties_taken(rs.getInt("penalties_taken"));
				dto.setFouls(rs.getInt("fouls"));
				dto.setYellow_cards(rs.getInt("yellow_cards"));
				dto.setRed_cards(rs.getInt("red_cards"));
				dto.setOffsides(rs.getInt("offsides"));	
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_PlayerRank 완료");
		return list;
	}
	
	
	
	
	public ArrayList<dtoPlayerRank_Assists> get_PlayerRank_Assists(int lid_sofa, int sid_daum, String sslug, String category) {
			
		ArrayList<dtoPlayerRank_Assists> list = new ArrayList<dtoPlayerRank_Assists>();
		
		dtoPlayerRank_Assists dto = null;
		String sql = "select rank, tid_sofa, pid_sofa, pko, pimg, tko, timg, appearances, goals, assists, attack_points, total_shots," 
				   + " shots_on_target, penalties_taken, fouls, yellow_cards, red_cards, offsides"
				+ " FROM (SELECT rank() over(order by assists desc) rank, b.tid_sofa, b.pid_sofa, b.pko, b.pimg, c.tko, c.timg, a.appearances, a.goals, a.assists, a.goals+a.assists as attack_points,"
						+ " a.total_shots, a.shots_on_target, a.penalties_taken, a.fouls, a.yellow_cards, a.red_cards, a.offsides"
						+ " FROM playerstat a, player b, team c"
						+ " WHERE a.lid_sofa=? AND a.sid_sofa=(select sid_sofa FROM season WHERE sid_daum=? AND sslug LIKE ?)"
						+ " AND a.pid_sofa=b.pid_sofa AND b.lid_sofa=c.lid_sofa AND b.tid_sofa=c.tid_sofa"
						+ " ORDER BY assists DESC, pko DESC, appearances DESC)"
				+ " WHERE ROWNUM<=20";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
			//pstmt.setString(4, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerRank_Assists();
				dto.setRank(rs.getInt("rank"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPko(rs.getString("pko"));
				dto.setPimg(rs.getString("pimg"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setAppearances(rs.getInt("appearances"));
				dto.setGoals(rs.getInt("goals"));
				dto.setAssists(rs.getInt("assists"));
				dto.setAttack_points(rs.getInt("attack_points"));
				dto.setTotal_shots(rs.getInt("total_shots"));
				dto.setShots_on_target(rs.getInt("shots_on_target"));
				dto.setPenalties_taken(rs.getInt("penalties_taken"));
				dto.setFouls(rs.getInt("fouls"));
				dto.setYellow_cards(rs.getInt("yellow_cards"));
				dto.setRed_cards(rs.getInt("red_cards"));
				dto.setOffsides(rs.getInt("offsides"));	
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_PlayerRank_Assists 완료");
		return list;
	}
	
	
	public ArrayList<dtoPlayerRank_Attack_Points> get_PlayerRank_Attack_Points(int lid_sofa, int sid_daum, String sslug, String category) {
		
		ArrayList<dtoPlayerRank_Attack_Points> list = new ArrayList<dtoPlayerRank_Attack_Points>();
		
		dtoPlayerRank_Attack_Points dto = null;
		String sql = "select rank, tid_sofa, pid_sofa, pko, pimg, tko, timg, appearances, goals, assists, attack_points, total_shots," 
				   + " shots_on_target, penalties_taken, fouls, yellow_cards, red_cards, offsides"
				+ " FROM (SELECT rank() over(order by a.goals+a.assists desc) rank, b.tid_sofa, b.pid_sofa, b.pko, b.pimg, c.tko, c.timg, a.appearances, a.goals, a.assists, a.goals+a.assists as attack_points,"
						+ " a.total_shots, a.shots_on_target, a.penalties_taken, a.fouls, a.yellow_cards, a.red_cards, a.offsides"
						+ " FROM playerstat a, player b, team c"
						+ " WHERE a.lid_sofa=? AND a.sid_sofa=(select sid_sofa FROM season WHERE sid_daum=? AND sslug LIKE ?)"
						+ " AND a.pid_sofa=b.pid_sofa AND b.lid_sofa=c.lid_sofa AND b.tid_sofa=c.tid_sofa"
						+ " ORDER BY attack_points DESC, pko DESC, appearances DESC)"
				+ " WHERE ROWNUM<=20";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
			//pstmt.setString(4, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerRank_Attack_Points();
				dto.setRank(rs.getInt("rank"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPko(rs.getString("pko"));
				dto.setPimg(rs.getString("pimg"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setAppearances(rs.getInt("appearances"));
				dto.setGoals(rs.getInt("goals"));
				dto.setAssists(rs.getInt("assists"));
				dto.setAttack_points(rs.getInt("attack_points"));
				dto.setTotal_shots(rs.getInt("total_shots"));
				dto.setShots_on_target(rs.getInt("shots_on_target"));
				dto.setPenalties_taken(rs.getInt("penalties_taken"));
				dto.setFouls(rs.getInt("fouls"));
				dto.setYellow_cards(rs.getInt("yellow_cards"));
				dto.setRed_cards(rs.getInt("red_cards"));
				dto.setOffsides(rs.getInt("offsides"));	

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_PlayerRank_attack_points 완료");
		return list;
	}


	public ArrayList<dtoPlayerRank_Total_Shots> get_PlayerRank_Total_Shots(int lid_sofa, int sid_daum, String sslug, String category) {
		
		ArrayList<dtoPlayerRank_Total_Shots> list = new ArrayList<dtoPlayerRank_Total_Shots>();
		
		dtoPlayerRank_Total_Shots dto = null;
		String sql = "select rank, tid_sofa, pid_sofa, pko, pimg, tko, timg, appearances, goals, assists, attack_points, total_shots," 
				   + " shots_on_target, penalties_taken, fouls, yellow_cards, red_cards, offsides"
				+ " FROM (SELECT rank() over(order by total_shots desc) rank, b.tid_sofa, b.pid_sofa, b.pko, b.pimg, c.tko, c.timg, a.appearances, a.goals, a.assists, a.goals+a.assists as attack_points,"
						+ " a.total_shots, a.shots_on_target, a.penalties_taken, a.fouls, a.yellow_cards, a.red_cards, a.offsides"
						+ " FROM playerstat a, player b, team c"
						+ " WHERE a.lid_sofa=? AND a.sid_sofa=(select sid_sofa FROM season WHERE sid_daum=? AND sslug LIKE ?)"
						+ " AND a.pid_sofa=b.pid_sofa AND b.lid_sofa=c.lid_sofa AND b.tid_sofa=c.tid_sofa"
						+ " ORDER BY total_shots DESC, pko DESC, appearances DESC)"
				+ " WHERE ROWNUM<=20";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
			//pstmt.setString(4, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerRank_Total_Shots();
				dto.setRank(rs.getInt("rank"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPko(rs.getString("pko"));
				dto.setPimg(rs.getString("pimg"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setAppearances(rs.getInt("appearances"));
				dto.setGoals(rs.getInt("goals"));
				dto.setAssists(rs.getInt("assists"));
				dto.setAttack_points(rs.getInt("attack_points"));
				dto.setTotal_shots(rs.getInt("total_shots"));
				dto.setShots_on_target(rs.getInt("shots_on_target"));
				dto.setPenalties_taken(rs.getInt("penalties_taken"));
				dto.setFouls(rs.getInt("fouls"));
				dto.setYellow_cards(rs.getInt("yellow_cards"));
				dto.setRed_cards(rs.getInt("red_cards"));
				dto.setOffsides(rs.getInt("offsides"));		
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_dtoPlayerRank_Total_Shots 완료");
		return list;
	}
	
	
	public ArrayList<dtoPlayerRank_Shots_On_Target> get_PlayerRank_Shots_On_Target(int lid_sofa, int sid_daum, String sslug, String category) {
		
		ArrayList<dtoPlayerRank_Shots_On_Target> list = new ArrayList<dtoPlayerRank_Shots_On_Target>();
		
		dtoPlayerRank_Shots_On_Target dto = null;
		String sql = "select rank, tid_sofa, pid_sofa, pko, pimg, tko, timg, appearances, goals, assists, attack_points, total_shots," 
			   + " shots_on_target, penalties_taken, fouls, yellow_cards, red_cards, offsides"
			+ " FROM (SELECT rank() over(order by shots_on_target desc) rank, b.tid_sofa, b.pid_sofa, b.pko, b.pimg, c.tko, c.timg, a.appearances, a.goals, a.assists, a.goals+a.assists as attack_points,"
					+ " a.total_shots, a.shots_on_target, a.penalties_taken, a.fouls, a.yellow_cards, a.red_cards, a.offsides"
					+ " FROM playerstat a, player b, team c"
					+ " WHERE a.lid_sofa=? AND a.sid_sofa=(select sid_sofa FROM season WHERE sid_daum=? AND sslug LIKE ?)"
					+ " AND a.pid_sofa=b.pid_sofa AND b.lid_sofa=c.lid_sofa AND b.tid_sofa=c.tid_sofa"
					+ " ORDER BY shots_on_target DESC, pko DESC, appearances DESC)"
			+ " WHERE ROWNUM<=20";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
//			pstmt.setString(4, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerRank_Shots_On_Target();
				dto.setRank(rs.getInt("rank"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPko(rs.getString("pko"));
				dto.setPimg(rs.getString("pimg"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setAppearances(rs.getInt("appearances"));
				dto.setGoals(rs.getInt("goals"));
				dto.setAssists(rs.getInt("assists"));
				dto.setAttack_points(rs.getInt("attack_points"));
				dto.setTotal_shots(rs.getInt("total_shots"));
				dto.setShots_on_target(rs.getInt("shots_on_target"));
				dto.setPenalties_taken(rs.getInt("penalties_taken"));
				dto.setFouls(rs.getInt("fouls"));
				dto.setYellow_cards(rs.getInt("yellow_cards"));
				dto.setRed_cards(rs.getInt("red_cards"));
				dto.setOffsides(rs.getInt("offsides"));	
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_PlayerRank_Penalties_Taken 완료");
		return list;
	}
	
	
	public ArrayList<dtoPlayerRank_Penalties_Taken> get_PlayerRank_Penalties_Taken(int lid_sofa, int sid_daum, String sslug, String category) {
		
		ArrayList<dtoPlayerRank_Penalties_Taken> list = new ArrayList<dtoPlayerRank_Penalties_Taken>();
		
		dtoPlayerRank_Penalties_Taken dto = null;
		String sql = "select rank, tid_sofa, pid_sofa, pko, pimg, tko, timg, appearances, goals, assists, attack_points, total_shots," 
			   + " shots_on_target, penalties_taken, fouls, yellow_cards, red_cards, offsides"
			+ " FROM (SELECT rank() over(order by penalties_taken desc) rank, b.tid_sofa, b.pid_sofa, b.pko, b.pimg, c.tko, c.timg, a.appearances, a.goals, a.assists, a.goals+a.assists as attack_points,"
					+ " a.total_shots, a.shots_on_target, a.penalties_taken, a.fouls, a.yellow_cards, a.red_cards, a.offsides"
					+ " FROM playerstat a, player b, team c"
					+ " WHERE a.lid_sofa=? AND a.sid_sofa=(select sid_sofa FROM season WHERE sid_daum=? AND sslug LIKE ?)"
					+ " AND a.pid_sofa=b.pid_sofa AND b.lid_sofa=c.lid_sofa AND b.tid_sofa=c.tid_sofa"
					+ " ORDER BY penalties_taken DESC, pko DESC, appearances DESC)"
			+ " WHERE ROWNUM<=20";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
//			pstmt.setString(4, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerRank_Penalties_Taken();
				dto.setRank(rs.getInt("rank"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPko(rs.getString("pko"));
				dto.setPimg(rs.getString("pimg"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setAppearances(rs.getInt("appearances"));
				dto.setGoals(rs.getInt("goals"));
				dto.setAssists(rs.getInt("assists"));
				dto.setAttack_points(rs.getInt("attack_points"));
				dto.setTotal_shots(rs.getInt("total_shots"));
				dto.setShots_on_target(rs.getInt("shots_on_target"));
				dto.setPenalties_taken(rs.getInt("penalties_taken"));
				dto.setFouls(rs.getInt("fouls"));
				dto.setYellow_cards(rs.getInt("yellow_cards"));
				dto.setRed_cards(rs.getInt("red_cards"));
				dto.setOffsides(rs.getInt("offsides"));	
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_PlayerRank_Penalties_Taken 완료");
		return list;
	}
	
	
	public ArrayList<dtoPlayerRank_Fouls> get_PlayerRank_Fouls(int lid_sofa, int sid_daum, String sslug, String category) {
		
		ArrayList<dtoPlayerRank_Fouls> list = new ArrayList<dtoPlayerRank_Fouls>();
		
		dtoPlayerRank_Fouls dto = null;
		String sql = "select rank, tid_sofa, pid_sofa, pko, pimg, tko, timg, appearances, goals, assists, attack_points, total_shots," 
			   + " shots_on_target, penalties_taken, fouls, yellow_cards, red_cards, offsides"
			+ " FROM (SELECT rank() over(order by fouls desc) rank, b.tid_sofa, b.pid_sofa, b.pko, b.pimg, c.tko, c.timg, a.appearances, a.goals, a.assists, a.goals+a.assists as attack_points,"
					+ " a.total_shots, a.shots_on_target, a.penalties_taken, a.fouls, a.yellow_cards, a.red_cards, a.offsides"
					+ " FROM playerstat a, player b, team c"
					+ " WHERE a.lid_sofa=? AND a.sid_sofa=(select sid_sofa FROM season WHERE sid_daum=? AND sslug LIKE ?)"
					+ " AND a.pid_sofa=b.pid_sofa AND b.lid_sofa=c.lid_sofa AND b.tid_sofa=c.tid_sofa"
					+ " ORDER BY fouls DESC, pko DESC, appearances DESC)"
			+ " WHERE ROWNUM<=20";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
//			pstmt.setString(4, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerRank_Fouls();
				dto.setRank(rs.getInt("rank"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPko(rs.getString("pko"));
				dto.setPimg(rs.getString("pimg"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setAppearances(rs.getInt("appearances"));
				dto.setGoals(rs.getInt("goals"));
				dto.setAssists(rs.getInt("assists"));
				dto.setAttack_points(rs.getInt("attack_points"));
				dto.setTotal_shots(rs.getInt("total_shots"));
				dto.setShots_on_target(rs.getInt("shots_on_target"));
				dto.setPenalties_taken(rs.getInt("penalties_taken"));
				dto.setFouls(rs.getInt("fouls"));
				dto.setYellow_cards(rs.getInt("yellow_cards"));
				dto.setRed_cards(rs.getInt("red_cards"));
				dto.setOffsides(rs.getInt("offsides"));	
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_PlayerRank_Fouls 완료");
		return list;
	}
	
	
	public ArrayList<dtoPlayerRank_Yellow_Cards> get_PlayerRank_Yellow_Cards(int lid_sofa, int sid_daum, String sslug, String category) {
		
		ArrayList<dtoPlayerRank_Yellow_Cards> list = new ArrayList<dtoPlayerRank_Yellow_Cards>();
		
		dtoPlayerRank_Yellow_Cards dto = null;
		String sql = "select rank, tid_sofa, pid_sofa, pko, pimg, tko, timg, appearances, goals, assists, attack_points, total_shots," 
			   + " shots_on_target, penalties_taken, fouls, yellow_cards, red_cards, offsides"
			+ " FROM (SELECT rank() over(order by yellow_cards desc) rank, b.tid_sofa, b.pid_sofa, b.pko, b.pimg, c.tko, c.timg, a.appearances, a.goals, a.assists, a.goals+a.assists as attack_points,"
					+ " a.total_shots, a.shots_on_target, a.penalties_taken, a.fouls, a.yellow_cards, a.red_cards, a.offsides"
					+ " FROM playerstat a, player b, team c"
					+ " WHERE a.lid_sofa=? AND a.sid_sofa=(select sid_sofa FROM season WHERE sid_daum=? AND sslug LIKE ?)"
					+ " AND a.pid_sofa=b.pid_sofa AND b.lid_sofa=c.lid_sofa AND b.tid_sofa=c.tid_sofa"
					+ " ORDER BY yellow_cards DESC, pko DESC, appearances DESC)"
			+ " WHERE ROWNUM<=20";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
//			pstmt.setString(4, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerRank_Yellow_Cards();
				dto.setRank(rs.getInt("rank"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPko(rs.getString("pko"));
				dto.setPimg(rs.getString("pimg"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setAppearances(rs.getInt("appearances"));
				dto.setGoals(rs.getInt("goals"));
				dto.setAssists(rs.getInt("assists"));
				dto.setAttack_points(rs.getInt("attack_points"));
				dto.setTotal_shots(rs.getInt("total_shots"));
				dto.setShots_on_target(rs.getInt("shots_on_target"));
				dto.setPenalties_taken(rs.getInt("penalties_taken"));
				dto.setFouls(rs.getInt("fouls"));
				dto.setYellow_cards(rs.getInt("yellow_cards"));
				dto.setRed_cards(rs.getInt("red_cards"));
				dto.setOffsides(rs.getInt("offsides"));	
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_PlayerRank_Yellow_Cards 완료");
		return list;
	}
	
	
	public ArrayList<dtoPlayerRank_Red_Cards> get_PlayerRank_Red_Cards(int lid_sofa, int sid_daum, String sslug, String category) {
		
		ArrayList<dtoPlayerRank_Red_Cards> list = new ArrayList<dtoPlayerRank_Red_Cards>();
		
		dtoPlayerRank_Red_Cards dto = null;
		String sql = "select rank, tid_sofa, pid_sofa, pko, pimg, tko, timg, appearances, goals, assists, attack_points, total_shots," 
			   + " shots_on_target, penalties_taken, fouls, yellow_cards, red_cards, offsides"
			+ " FROM (SELECT rank() over(order by red_cards desc) rank, b.tid_sofa, b.pid_sofa, b.pko, b.pimg, c.tko, c.timg, a.appearances, a.goals, a.assists, a.goals+a.assists as attack_points,"
					+ " a.total_shots, a.shots_on_target, a.penalties_taken, a.fouls, a.yellow_cards, a.red_cards, a.offsides"
					+ " FROM playerstat a, player b, team c"
					+ " WHERE a.lid_sofa=? AND a.sid_sofa=(select sid_sofa FROM season WHERE sid_daum=? AND sslug LIKE ?)"
					+ " AND a.pid_sofa=b.pid_sofa AND b.lid_sofa=c.lid_sofa AND b.tid_sofa=c.tid_sofa"
					+ " ORDER BY red_cards DESC, pko DESC, appearances DESC)"
			+ " WHERE ROWNUM<=20";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
//			pstmt.setString(4, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerRank_Red_Cards();
				dto.setRank(rs.getInt("rank"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPko(rs.getString("pko"));
				dto.setPimg(rs.getString("pimg"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setAppearances(rs.getInt("appearances"));
				dto.setGoals(rs.getInt("goals"));
				dto.setAssists(rs.getInt("assists"));
				dto.setAttack_points(rs.getInt("attack_points"));
				dto.setTotal_shots(rs.getInt("total_shots"));
				dto.setShots_on_target(rs.getInt("shots_on_target"));
				dto.setPenalties_taken(rs.getInt("penalties_taken"));
				dto.setFouls(rs.getInt("fouls"));
				dto.setYellow_cards(rs.getInt("yellow_cards"));
				dto.setRed_cards(rs.getInt("red_cards"));
				dto.setOffsides(rs.getInt("offsides"));	
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_PlayerRank_Red_Cards 완료");
		return list;
	}
	
	
	public ArrayList<dtoPlayerRank_Offsides> get_PlayerRank_Offsides(int lid_sofa, int sid_daum, String sslug, String category) {
		
		ArrayList<dtoPlayerRank_Offsides> list = new ArrayList<dtoPlayerRank_Offsides>();
		
		dtoPlayerRank_Offsides dto = null;
		String sql = "select rank, tid_sofa, pid_sofa, pko, pimg, tko, timg, appearances, goals, assists, attack_points, total_shots," 
			   + " shots_on_target, penalties_taken, fouls, yellow_cards, red_cards, offsides"
			+ " FROM (SELECT rank() over(order by offsides desc) rank, b.tid_sofa, b.pid_sofa, b.pko, b.pimg, c.tko, c.timg, a.appearances, a.goals, a.assists, a.goals+a.assists as attack_points,"
					+ " a.total_shots, a.shots_on_target, a.penalties_taken, a.fouls, a.yellow_cards, a.red_cards, a.offsides"
					+ " FROM playerstat a, player b, team c"
					+ " WHERE a.lid_sofa=? AND a.sid_sofa=(select sid_sofa FROM season WHERE sid_daum=? AND sslug LIKE ?)"
					+ " AND a.pid_sofa=b.pid_sofa AND b.lid_sofa=c.lid_sofa AND b.tid_sofa=c.tid_sofa"
					+ " ORDER BY offsides DESC, pko DESC, appearances DESC)"
			+ " WHERE ROWNUM<=20";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid_sofa);
			pstmt.setInt(2, sid_daum);
			pstmt.setString(3, "%"+sslug+"%");
//			pstmt.setString(4, category);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerRank_Offsides();
				dto.setRank(rs.getInt("rank"));
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setPko(rs.getString("pko"));
				dto.setPimg(rs.getString("pimg"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setAppearances(rs.getInt("appearances"));
				dto.setGoals(rs.getInt("goals"));
				dto.setAssists(rs.getInt("assists"));
				dto.setAttack_points(rs.getInt("attack_points"));
				dto.setTotal_shots(rs.getInt("total_shots"));
				dto.setShots_on_target(rs.getInt("shots_on_target"));
				dto.setPenalties_taken(rs.getInt("penalties_taken"));
				dto.setFouls(rs.getInt("fouls"));
				dto.setYellow_cards(rs.getInt("yellow_cards"));
				dto.setRed_cards(rs.getInt("red_cards"));
				dto.setOffsides(rs.getInt("offsides"));	
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		System.out.println("DAO.java || dao.get_PlayerRank_Offsides 완료");
		return list;
	}
	
	
	
	
	public dtoPlayerStat get_PlayerStat(int sid_sofa, int tid_sofa, int pid_sofa) {
		
		dtoPlayerStat dto = null;
		
		String sql = "SELECT a.tid_sofa, a.pid_sofa, c.tko, c.timg, b.pko, b.pen, b.pimg, b.pslug, b.pheight, b.pbirth, b.pnation, b.pnationimg, b.pfoot, b.pposition,"
			    + "a.appearances, a.rating, a.minutes_played, a.saves, a.clean_sheets,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(total_shots * shots_on_target, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(total_shots * shots_on_target, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) shooting,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(acc_passes*accurate_passes_per + acc_long_ball*accurate_long_balls_per, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			    	+ " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY  NVL(NULLIF(acc_passes*accurate_passes_per + acc_long_ball*accurate_long_balls_per, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) passes,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(succ_dribbles * successful_dribbles_per, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(succ_dribbles * successful_dribbles_per, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) dribbles,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(goals_conceded_inside_the_box+goals_conceded_outside_the_box/NULLIF(appearances, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(goals_conceded_inside_the_box+goals_conceded_outside_the_box/NULLIF(appearances, 0) ,1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) conceded_per_game,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(succ_dribbles * successful_dribbles_per + total_duels_won*total_duels_won_per, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(succ_dribbles * successful_dribbles_per + total_duels_won*total_duels_won_per, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) individual_skill,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(successful_dribbles_per*3 + goal_conversion_per*4 + accurate_passes_per*2.5 + total_duels_won_per, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(successful_dribbles_per*3 + goal_conversion_per*4 + accurate_passes_per*2.5 + total_duels_won_per, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) genius,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(successful_dribbles_per*2 + goal_conversion_per*2 + accurate_passes_per*2 + total_duels_won_per, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(successful_dribbles_per*2 + goal_conversion_per*2 + accurate_passes_per*2 + total_duels_won_per, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) concentration,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(succ_dribbles*successful_dribbles_per*2 + tackles + interceptions + total_duels_won*total_duels_won_per*2, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(succ_dribbles*successful_dribbles_per*2 + tackles + interceptions + total_duels_won*total_duels_won_per*2, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) active,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(total_shots* + tackles*2 + total_duels_won, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(total_shots* + tackles*2 + total_duels_won, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) boldness,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(goal_conversion_per*2 + accurate_passes_per, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(goal_conversion_per*2 + accurate_passes_per, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) composure,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(assists*5 + total_passes, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(assists*5 + total_passes, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) cooperation,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(total_duels_won*total_duels_won_per, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(total_duels_won*total_duels_won_per, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) physicals,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(punches, 0) ,1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(punches, 0) ,1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) punches,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(runs_out, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(runs_out, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) runs_out,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(high_claims, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(high_claims, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) high_claims,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(saves_from_inside_box, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(saves_from_inside_box, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) saves_from_inside_box,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(saved_shots_from_outside_the_box, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(saved_shots_from_outside_the_box, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) saved_shots_from_outside_the_box,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(penalties_faced * penalties_saved, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(penalties_faced * penalties_saved, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) penalties_saved,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(goals * goal_conversion_per - big_chances_missed / 3, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(goals * goal_conversion_per - big_chances_missed / 3, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) goal_conversion_per,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(headed_goals, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(headed_goals, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) headed_goals,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(free_kick_goals, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(free_kick_goals, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) free_kick_goals,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(penalty_goals*penalty_conversion_per, 0) ,1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(penalty_goals*penalty_conversion_per, 0) ,1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) penalty_goals,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(accurate_crosses*accurate_crosses_per, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(accurate_crosses*accurate_crosses_per, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) crosses,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(tackles + interceptions*2  + blocked_shots*3, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(tackles + interceptions*2  + blocked_shots*3, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) one_on_one_mark,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(tackles, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(tackles, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) tackles,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(total_passes*accurate_passes_per, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(total_passes*accurate_passes_per, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) sight,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(clearances*2 + blocked_shots*2 + clean_sheets*3 - errors_lead_to_goal*3 - errors_lead_to_shot *2- dribbled_past, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(clearances*2 + blocked_shots*2 + clean_sheets*3 - errors_lead_to_goal*3 - errors_lead_to_shot *2- dribbled_past, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) defense_position,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(interceptions, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(interceptions, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) interceptions,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(clearances, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(clearances, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) clearances,"
			    + " CEIL((SELECT RANK / (SELECT MAX(RANK) FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(blocked_shots, 0), 1) ASC) RANK FROM PLAYERSTAT))"
			        + " FROM (SELECT pid_sofa, sid_sofa, DENSE_RANK() OVER (ORDER BY NVL(NULLIF(blocked_shots, 0), 1) ASC) RANK FROM PLAYERSTAT)"
			        + " WHERE pid_sofa=? AND sid_sofa=?)*100) blocked_shots"
			+ " FROM playerstat a, player b, team c"
			+ " WHERE a.tid_sofa=b.tid_sofa AND a.pid_sofa=b.pid_sofa AND b.tid_sofa=c.tid_sofa AND a.pid_sofa=? AND a.sid_sofa = ? AND a.tid_sofa=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pid_sofa);
			pstmt.setInt(2, sid_sofa);
			pstmt.setInt(3, pid_sofa);
			pstmt.setInt(4, sid_sofa);
			pstmt.setInt(5, pid_sofa);
			pstmt.setInt(6, sid_sofa);
			pstmt.setInt(7, pid_sofa);
			pstmt.setInt(8, sid_sofa);
			pstmt.setInt(9, pid_sofa);
			pstmt.setInt(10, sid_sofa);
			pstmt.setInt(11, pid_sofa);
			pstmt.setInt(12, sid_sofa);
			pstmt.setInt(13, pid_sofa);
			pstmt.setInt(14, sid_sofa);
			pstmt.setInt(15, pid_sofa);
			pstmt.setInt(16, sid_sofa);
			pstmt.setInt(17, pid_sofa);
			pstmt.setInt(18, sid_sofa);
			pstmt.setInt(19, pid_sofa);
			pstmt.setInt(20, sid_sofa);
			pstmt.setInt(21, pid_sofa);
			pstmt.setInt(22, sid_sofa);
			pstmt.setInt(23, pid_sofa);
			pstmt.setInt(24, sid_sofa);
			pstmt.setInt(25, pid_sofa);
			pstmt.setInt(26, sid_sofa);
			pstmt.setInt(27, pid_sofa);
			pstmt.setInt(28, sid_sofa);
			pstmt.setInt(29, pid_sofa);
			pstmt.setInt(30, sid_sofa);
			pstmt.setInt(31, pid_sofa);
			pstmt.setInt(32, sid_sofa);
			pstmt.setInt(33, pid_sofa);
			pstmt.setInt(34, sid_sofa);
			pstmt.setInt(35, pid_sofa);
			pstmt.setInt(36, sid_sofa);
			pstmt.setInt(37, pid_sofa);
			pstmt.setInt(38, sid_sofa);
			pstmt.setInt(39, pid_sofa);
			pstmt.setInt(40, sid_sofa);
			pstmt.setInt(41, pid_sofa);
			pstmt.setInt(42, sid_sofa);
			pstmt.setInt(43, pid_sofa);
			pstmt.setInt(44, sid_sofa);
			pstmt.setInt(45, pid_sofa);
			pstmt.setInt(46, sid_sofa);
			pstmt.setInt(47, pid_sofa);
			pstmt.setInt(48, sid_sofa);
			pstmt.setInt(49, pid_sofa);
			pstmt.setInt(50, sid_sofa);
			pstmt.setInt(51, pid_sofa);
			pstmt.setInt(52, sid_sofa);
			pstmt.setInt(53, pid_sofa);
			pstmt.setInt(54, sid_sofa);
			pstmt.setInt(55, pid_sofa);
			pstmt.setInt(56, sid_sofa);
			pstmt.setInt(57, pid_sofa);
			pstmt.setInt(58, sid_sofa);
			pstmt.setInt(59, pid_sofa);
			pstmt.setInt(60, sid_sofa);
			
			pstmt.setInt(61, pid_sofa);
			pstmt.setInt(62, sid_sofa);		
			pstmt.setInt(63, tid_sofa);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoPlayerStat();
				
				dto.setTid_sofa(rs.getInt("tid_sofa"));
				dto.setPid_sofa(rs.getInt("pid_sofa"));
				dto.setTko(rs.getString("tko"));
				dto.setTimg(rs.getString("timg"));
				dto.setPko(rs.getString("pko"));
				dto.setPen(rs.getString("pen"));
				dto.setPimg(rs.getString("pimg"));
				dto.setPslug(rs.getString("pslug"));
				dto.setPheight(rs.getInt("pheight"));
				dto.setPbirth(rs.getString("pbirth"));
				dto.setPnation(rs.getString("pnation"));
				dto.setPnationimg(rs.getString("pnationimg"));
				dto.setPfoot(rs.getString("pfoot"));
				dto.setPposition(rs.getString("pposition"));
				
				dto.setAppearances(rs.getInt("appearances"));
				dto.setRating(rs.getDouble("rating"));
				dto.setShooting(rs.getInt("shooting"));
				dto.setPasses(rs.getInt("passes"));
				dto.setDribbles(rs.getInt("dribbles"));
				dto.setMinutes_played(rs.getInt("minutes_played"));
				dto.setSaves(rs.getInt("saves"));
				dto.setClean_sheets(rs.getInt("clean_sheets"));
				dto.setConceded_per_game(rs.getDouble("conceded_per_game"));
				
				dto.setIndividual_skill(rs.getInt("individual_skill"));
				dto.setGenius(rs.getInt("genius"));
				dto.setConcentration(rs.getInt("concentration"));
				dto.setActive(rs.getInt("active"));
				dto.setBoldness(rs.getInt("boldness"));
				dto.setComposure(rs.getInt("composure"));
				dto.setCooperation(rs.getInt("cooperation"));
				dto.setPhysicals(rs.getInt("physicals"));
				dto.setPunches(rs.getInt("punches"));
				dto.setRuns_out(rs.getInt("runs_out"));
				dto.setHigh_claims(rs.getInt("high_claims"));
				dto.setSaves_from_inside_box(rs.getInt("saves_from_inside_box"));
				dto.setSaved_shots_from_outside_the_box(rs.getInt("saved_shots_from_outside_the_box"));
				dto.setPenalties_saved(rs.getInt("penalties_saved"));
				
				dto.setGoal_conversion_per(rs.getInt("goal_conversion_per"));
				dto.setHeaded_goals(rs.getInt("headed_goals"));
				dto.setFree_kick_goals(rs.getInt("free_kick_goals"));
				dto.setPenalty_goals(rs.getInt("penalty_goals"));
				dto.setCrosses(rs.getInt("crosses"));
				dto.setOne_on_one_mark(rs.getInt("one_on_one_mark"));
				dto.setTackles(rs.getInt("tackles"));
				dto.setSight(rs.getInt("sight"));
				dto.setDefense_position(rs.getInt("defense_position"));
				dto.setInterceptions(rs.getInt("interceptions"));
				dto.setInterceptions(rs.getInt("clearances"));
				dto.setBlocked_shots(rs.getInt("blocked_shots"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}	
		System.out.println("DAO.java || dao.get_PlayerStat 완료");
		return dto;
	}
	
	
	public boolean Update_clean_sheets(int clean_sheets, int tid_sofa, int pid_sofa)  {	
			String sql = "UPDATE playerstat SET clean_sheets=? where tid_sofa=? AND pid_sofa=?";
			try {
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, clean_sheets);
				pstmt.setInt(2, tid_sofa);
				pstmt.setInt(3, pid_sofa);
				rs = pstmt.executeQuery();
				
			} catch(SQLException e){
				e.printStackTrace();
				System.out.println("playerStatDAO clean_sheets Insert Error");
				return false;
	    	}finally {
	    		try { rs.close(); } catch (Exception e) { /* ignored */ }
			    try { pstmt.close(); } catch (Exception e) { /* ignored */ }
			    try { con.close();} catch (Exception e) { /* ignored */ }
	    	}
			return true;
		}
	
	
	
	//회원가입
	public int Join(dtoMem mem) {
		
		int result = 0; 
		
		String sql = "insert into mem values(?, ?, ?, sysdate+9/24)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem.getId());
			pstmt.setString(2, mem.getPw());
			pstmt.setString(3, mem.getType());
			
			result = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	//로그인 확인
	public boolean checkLogin(String id, String pw) {
		
		boolean boo = false;
		
		String sql = "select * from mem where id=? and pw=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				boo = true;
			}
		}catch(SQLException e) {
			System.out.println("로그인 실패");
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return boo;  
	}
	
	
	
	
	public ArrayList<dtoCheer> get_Cheer(String team) {
		
		ArrayList<dtoCheer> list = new ArrayList<dtoCheer>();
		
		dtoCheer dto = null;
		
		String sql = "SELECT id, team, comm, updateDate FROM cheer WHERE team = ? order by updateDate DESC";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, team);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dto = new dtoCheer();
				dto.setId(rs.getString("id"));
				dto.setTeam(rs.getString("team"));
				dto.setComm(rs.getString("comm"));
				dto.setUpdateDate(rs.getDate("updateDate"));

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				//if(con != null) con.close();	
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		if(list.isEmpty()) {
			list = null;
		}else {
			list.trimToSize();
		}	
		return list;
	}
	
	
	
	public void insert_Cheer(String id, String team, String comm) {
		
		String sql = "INSERT INTO cheer(id, team, comm, updateDate) VALUES(?, ?, ?, sysdate+9/24)";
																
		try {
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, id);
		pstmt.setString(2, team);
		pstmt.setString(3, comm);
		
		int cnt = pstmt.executeUpdate();

		if(cnt>=1) 
			System.out.println("cheer 업로드 완료");
		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("cheer 업로드 실패");
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
					
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}	
	}
	
	
	
	
	public boolean delete_Cheer(String id, String team, String comm) {
		
		String sql = "DELETE FROM cheer WHERE id=? AND team=? AND comm=?";
		boolean boo = false;		
		
		try {
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, id);
		pstmt.setString(2, team);
		pstmt.setString(3, comm);
		
		int cnt = pstmt.executeUpdate();

		if(cnt>=1) { 
			System.out.println("cheer 삭제 완료");
			boo = true;
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("cheer 업로드 실패");
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
					
			} catch(SQLException e) {
				e.printStackTrace();
			}	
		}
		return boo;
	}
	
	
	
	public ArrayList<dtoCheer> search(String teamName) {
		String sql = "SELECT * FROM cheer WHERE TEAM LIKE ?";
		ArrayList<dtoCheer> cheerList = new ArrayList<dtoCheer>();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + teamName + "%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dtoCheer dtoCheer = new dtoCheer();
				dtoCheer.setId(rs.getString(1));
				dtoCheer.setTeam(rs.getString(2));
				dtoCheer.setComm(rs.getString(3));
				dtoCheer.setUpdateDate(rs.getDate(4));
				cheerList.add(dtoCheer);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return cheerList;
	}
	
	
	
	public int register(dtoCheer dtoCheer) {
		String SQL = "INSERT INTO CHEER(ID, COMM, TEAM, updateDate) VALUES(?, ?, ?, sysdate+9/24)";
		try {
			pstmt = con.prepareStatement(SQL);
			pstmt.setString(1, dtoCheer.getId());
			pstmt.setString(2, dtoCheer.getTeam());
			pstmt.setString(3, dtoCheer.getComm());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}

	
}