<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String useremail = request.getParameter("useremail");
	String userpw = request.getParameter("userpw");

		
//------
	String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";
	String A = "select count(*) from userinfo where userEmail = '"+useremail+"' and userPw = '"+userpw+"' and userDeleteDate is null";

	PreparedStatement ps = null;

	 int count = 0;

	try{
		
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		
		Statement stmt_mysql = conn_mysql.createStatement();
		
		 ResultSet rs = stmt_mysql.executeQuery(A);
		 
			   %>
			   { 
					 "user_info"  : [ 
	   <%
			   while (rs.next()) {
				   if (count == 0) {
	   
				   }else{
	   %>
				   , 
	   <%
				   }
	   %>            
				   {
				   "count" : "<%=rs.getInt(1) %>"
				  
	   
				   }
	   
	   <%		
			   count++;
			   }
	   %>
				 ] 
			   } 
	   <%		
			   conn_mysql.close();
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
		   
	   %>

