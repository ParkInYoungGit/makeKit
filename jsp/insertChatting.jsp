<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String userinfo_userEmail_sender = request.getParameter("userinfo_userEmail_sender");
	String userinfo_userEmail_receiver = request.getParameter("userinfo_userEmail_receiver");
	String chattingContents = request.getParameter("chattingContents");
	String chattingNumber = request.getParameter("chattingNumber");
		
//------
	String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인 

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "insert into chatting (userinfo_userEmail_sender, userinfo_userEmail_receiver, chattingContents, chattingNumber, chattingInsertDate) values (?, ?, ?, ?, now())";

	    ps = conn_mysql.prepareStatement(A);
		ps.setString(1, userinfo_userEmail_sender);
		ps.setString(2, userinfo_userEmail_receiver);
		ps.setString(3, chattingContents);
		ps.setString(4, chattingNumber);
		
		result = ps.executeUpdate();
%>
		{
			"result" : "<%=result%>"
		}

<%		
	    conn_mysql.close();
	} 
	catch (Exception e){
%>
		{
			"result" : "<%=result%>"
		}
<%		
	    e.printStackTrace();
	} 
	
%>