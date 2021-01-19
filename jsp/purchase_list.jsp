<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
    String userEmail = request.getParameter("userEmail");

	String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";

    int count = 0;
    PreparedStatement ps = null;
    ResultSet rs =null;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        String WhereDefault = "select u.userName, i.*, d.orderDetailNo, d.orderinfo_orderNo, d.goods_productNo, d.orderQuantity, d.orderConfirm, d.orderRefund,";
        String WhereDefault1 = " d.orderStar, d.orderReview, d.orderReviewImg, d.orderReviewInsertDate , p.productName, p.productPrice, p.productFilename from userinfo u left outer join orderinfo i on";
        String WhereDefault2 = "  u.userEmail=i.userinfo_userEmail left outer join orderdetail d on i.orderNo = d.orderinfo_orderNo left outer join product p on d.goods_productNo = p.productNo";
        String WhereDefault3 = "   where u.userEmail = ?";

        ps = conn_mysql.prepareStatement(WhereDefault+WhereDefault1+WhereDefault2+WhereDefault3);
        ps.setString(1, userEmail);
        rs = ps.executeQuery();
%>
		{ 
  			"makekit_info"  : [ 
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
                "userName" : "<%=rs.getString(1) %>", 
                "orderNo" : "<%=rs.getString(2) %>",   
                "userinfo_userEmail" : "<%=rs.getString(3) %>",  
                "orderDate" : "<%=rs.getString(4) %>",
                "orderReceiver" : "<%=rs.getString(5) %>",
                "orderRcvAddress" : "<%=rs.getString(6) %>",
                "orderRcvAddressDetail" : "<%=rs.getString(7) %>",
                "orderRcvPhone" : "<%=rs.getString(8) %>",
                "orderTotalPrice" : "<%=rs.getString(9) %>",
                "orderBank" : "<%=rs.getString(10) %>",
                "orderCardNo" : "<%= rs.getString(11) %>",
                "orderCardPw" : "<%=rs.getString(12) %>", 
                "orderDelivery" : "<%=rs.getString(13) %>",   
                "orderDeliveryDate" : "<%=rs.getString(14) %>",  
                "orderDetailNo" : "<%=rs.getString(15) %>",
                "orderinfo_orderNo" : "<%=rs.getString(16) %>",
                "goods_productNo" : "<%=rs.getString(17) %>",
                "orderQuantity" : "<%=rs.getString(18) %>",
                "orderConfirm" : "<%=rs.getString(19) %>",
                "orderRefund" : "<%=rs.getString(20) %>",
                "orderStar" : "<%=rs.getString(21) %>",
                "orderReview" : "<%= rs.getString(22) %>",
                "orderReviewImg" : "<%=rs.getString(23) %>", 
                "orderReviewInsertDate" : "<%=rs.getString(24) %>",   
                "productName" : "<%=rs.getString(25) %>",  
                "productPrice" : "<%=rs.getString(26) %>",
                "productAFilename" : "<%=rs.getString(27) %>"
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
