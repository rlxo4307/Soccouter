package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnCopy {
	private Connection con;
	
	public Connection getConnection() {
		return con;
	}
	
	public ConnCopy() throws ClassNotFoundException, SQLException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@shared00.iptime.org:32779/XEPDB1", "FOOTBALL", "football");
	}
}
