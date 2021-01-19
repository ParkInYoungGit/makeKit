<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
    	String productno = request.getParameter("productno");

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

       String query = "select r.userinfo_userEmail sellerEmail, p.productNo, p.productName, p.productPrice, p.productContent, p.productFilename, p.productDFilename, p.productAFilename, u.userImage sellerImage from product p, register r, userinfo u ";
	String query1 = "where p.productNo = r.product_productNo and u.userEmail = r.userinfo_userEmail and p.productNo = ? and p.productDeleteDate is null";

        ps = conn_mysql.prepareStatement(query + query1); // 
        ps.setString(1, productno);

        rs = ps.executeQuery();
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
			"sellerEmail" : "<%=rs.getString(1) %>", 
			"productNo" : "<%=rs.getString(2) %>", 
			"productName" : "<%=rs.getString(3) %>", 
			"productPrice" : "<%=rs.getString(4) %>", 
			"productContent" : "<%=rs.getString(5) %>",   
			"productFilename" : "<%=rs.getString(6) %>",
			"productDFilename" : "<%=rs.getString(7) %>",
			"productAFilename" : "<%=rs.getString(8) %>",
			"sellerImage" : "<%=rs.getString(9) %>"
		
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