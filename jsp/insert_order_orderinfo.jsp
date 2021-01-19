<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String useremail = request.getParameter("useremail");
	String orderreceiver = request.getParameter("orderreceiver");
	String orderrcvaddress = request.getParameter("orderrcvaddress");
	String orderrcvaddressdetail = request.getParameter("orderrcvaddressdetail");
	String orderrcvphone = request.getParameter("orderrcvphone");
	String ordertotalprice = request.getParameter("ordertotalprice");
	String orderbank = request.getParameter("orderbank");
	String ordercardno = request.getParameter("ordercardno");
	String ordercardpw = request.getParameter("ordercardpw");

	String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
   	
    	int result = 0; // 입력 확인 

	PreparedStatement ps = null;

	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
	

       String query = "insert into orderinfo (userinfo_userEmail, orderDate, orderReceiver, orderRcvAddress, orderRcvAddressDetail,
orderRcvPhone, orderTotalPrice, orderBank, orderCardNo, orderCardPw) values (?,now(),?,?,?,?,?,?,?,?)";
	
        ps = conn_mysql.prepareStatement(query);
		ps.setString(1, useremail);
		ps.setString(2, orderreceiver);
		ps.setString(3, orderrcvaddress);
		ps.setString(4, orderrcvaddressdetail);
		ps.setString(5, orderrcvphone);
		ps.setString(6, ordertotalprice);
		ps.setString(7, orderbank);
		ps.setString(8, ordercardno);
		ps.setString(9, ordercardpw);
		
		
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