<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String useremail = request.getParameter("useremail");
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

       String query = "select l.userinfo_userEmail userEamil, l.userinfo_like_userEmail favoriteSellerEmail, r.userinfo_userEmail sellerEmail, r.product_productNo productNo, u.userName sellerName, u.userImage sellerImage from likeuser l, register r, userinfo u ";
       String query1 = "where l.userinfo_userEmail = ? and l.userinfo_like_userEmail = r.userinfo_userEmail and u.userEmail = l.userinfo_like_userEmail and r.product_productNo = ?";
        ps = conn_mysql.prepareStatement(query + query1); // 
        ps.setString(1, useremail);
	ps.setString(2, productno);


        rs = ps.executeQuery();
%>
		{ 
  			"sellerFavorite_info"  : [ 
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
			"userEmail" : "<%=rs.getString(1) %>",
			"favoriteSellerEmail" : "<%=rs.getString(2) %>",
			"sellerEmail" : "<%=rs.getString(3) %>",
			"productNo" : "<%=rs.getString(4) %>",
			"sellerName" : "<%=rs.getString(5) %>",
			"sellerImage" : "<%=rs.getString(6) %>"

			
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