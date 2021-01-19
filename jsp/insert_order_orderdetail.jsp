<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String useremail = request.getParameter("useremail");
	String orderno = request.getParameter("orderno");
	String productsize = request.getParameter("productsize");
	String productno = request.getParameter("productno");
	String orderquantity = request.getParameter("orderquantity");
	<!-- List<String> productnoList = new List<String>();
	List<String> orderQntList = new List<String>(); -->

	String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
   	
    	int result = 0; // 입력 확인 

	PreparedStatement ps = null;

	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
	

       String query = "insert into orderdetail (userinfo_userEmail, orderinfo_orderNo, goods_productNo, orderQuantity) values (?,?,?,?)";
	
        ps = conn_mysql.prepareStatement(query);
		ps.setString(1, useremail);
		ps.setString(2, orderno);
		ps.setString(3, productno);
		ps.setString(4, orderquantity);
		
		
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