<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String userEmail = request.getParameter("email");
	String userName = request.getParameter("name");
	String userPw = request.getParameter("pw");
	String userTel = request.getParameter("phone");	
	String userAddress = request.getParameter("address");
	String userAddressDetail = request.getParameter("addressDetail");
	String userBirth = null;
		
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
	
		String A = "INSERT INTO userinfo (userEmail, userPw, userName, userAddress, userAddressDetail, userTel, userBirth, userInsertDate)";
		String B = " VALUES (?, ?, ?, ?, ?, ?, ?, now())";


	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, userEmail);
		ps.setString(2, userPw);
		ps.setString(3, userName);
		ps.setString(4, userAddress);
		ps.setString(5, userAddressDetail);
		ps.setString(6, userTel);
		ps.setString(7, userBirth);

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

