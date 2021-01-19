<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
    String cartNo = request.getParameter("cartNo");
    String productNo = request.getParameter("productNo");
    
        String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
         String id_mysql = "root";
         String pw_mysql = "qwer1234";
         String WhereDefault = "select p.productFilename, p.productName, p.productPrice, c.cartQuantity from product p, cartdetail c ";
         String WhereDefault2 = "where p.productNo = c.product_productNo and c.cartinfo_cartNo = '"+cartNo+"' and p.productNo = '"+productNo+"'";
        
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault+WhereDefault2); // 
%>
		{ 
  			"product_info"  : [ 
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
            "productImage" : "<%=rs.getString(1) %>", 
            "productName" : "<%=rs.getString(2) %>", 
			"productPrice" : "<%=rs.getString(3) %>",   
            "cartQuantity" : "<%=rs.getString(4) %>"  
            
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