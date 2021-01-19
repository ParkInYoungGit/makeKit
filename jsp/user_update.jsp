<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        

<%
	request.setCharacterEncoding("utf-8");
	String userEmail = request.getParameter("userEmail");	
	String userPw = request.getParameter("userPw");
	String userName = request.getParameter("userName");
	String userAddress = request.getParameter("userAddress");
	String userAddressDetail = request.getParameter("userAddressDetail");	
	String userTel = request.getParameter("userTel");	
	String userBirth = request.getParameter("userBirth");	
	

//------

	String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";
	int result = 0; // 수정 확인 

	PreparedStatement ps = null;

	try{
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
		String A = "update userinfo set userPw = ?, userName = ?, userAddress = ?, userAddressDetail = ?, userTel = ?, userBirth = ?";
		String B = "where userEmail = ?";


	    ps = conn_mysql.prepareStatement(A+B);

        ps.setString(1, userPw);
	    ps.setString(2, userName);
	    ps.setString(3, userAddress);
		ps.setString(4, userAddressDetail);
		ps.setString(5, userTel);
		ps.setString(6, userBirth);
		ps.setString(7, userEmail);
		
 
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
