<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String productName = request.getParameter("name");
	String productPrice = request.getParameter("price");
	String productType = request.getParameter("type");
	String productStock = request.getParameter("stock");	
	String productContent = request.getParameter("content");	
	String productFilename = request.getParameter("thumnail");	
	String productDFilename = request.getParameter("detail");	
	String productAFilename = request.getParameter("detailsecond");	
		
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
	
		String A = "INSERT INTO product (productName, productPrice, productType, productStock, productContent, productFilename, productDFilename, productAFilename, productInsertDate)";
		String B = " VALUES (?, ?, ?, ?, ?, ?, ?, ?, now())";


	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, productName);
		ps.setString(2, productPrice);
		ps.setString(3, productType);
		ps.setString(4, productStock);
		ps.setString(5, productContent);
		ps.setString(6, productFilename);
		ps.setString(7, productDFilename);
		ps.setString(8, productAFilename);

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

