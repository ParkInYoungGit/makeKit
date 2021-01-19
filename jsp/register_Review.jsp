<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	
	String orderStar = request.getParameter("orderStar");
	String orderReview = request.getParameter("orderReview");
	String reviewImg = request.getParameter("reviewImg"); 
	String useremail = request.getParameter("useremail");
	String orderNo = request.getParameter("orderDetailNo");
	String productNo = request.getParameter("productNo");

		
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
	
		String A = "UPDATE orderdetail d, orderinfo i, userinfo u, product p SET d.orderStar = ?,";
		String B = " d.orderReview = ?, d.orderReviewImg = ?, d.orderReviewInsertDate = now()";
		String C = " where d.orderinfo_orderNo = i.orderNo and u.userEmail = d.userinfo_userEmail and d.goods_productNo= p.productNo and d.userinfo_userEmail= ?";
		String D = " and d.orderDetailNo = ? and d.goods_productNo = ?";
	
	    ps = conn_mysql.prepareStatement(A+B+C+D);
	    ps.setString(1, orderStar);
		ps.setString(2, orderReview);
		ps.setString(3, reviewImg);
		ps.setString(4, useremail);
		ps.setString(5, orderNo);
		ps.setString(6, productNo);
		
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