<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>

<%
    if(request.getParameter("key")!=null) //get "key" variable from jquery & ajax  part this line "data:'key='+search" and check not null 
    {
        String key=request.getParameter("key"); //get "key" variable store in created new "key" variable
        String wild="%" +key+ "%"; //remove "%" for use preparedstatement in query name like, and "key" variable store in "wild" variable for further use
        
 

        try
        {
        	Class.forName("oracle.jdbc.driver.OracleDriver");
    		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","c##SOCCOUTER","soccouter");

            PreparedStatement pstmt=null; //create statement

            pstmt=con.prepareStatement("SELECT * FROM player WHERE PLAYERKONAME LIKE ? "); //sql select query
            pstmt.setString(1,wild); //above created "wild" variable set in this
            ResultSet rs=pstmt.executeQuery(); //execute query and set in ResultSet object "rs".
           
            while(rs.next())
            {
                %>
                    <li class="list-group-item"><%=rs.getString("name")%></li>
                <%
            }
                con.close(); //close connection
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
%>