<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
   	String query = "select userinfo_userEmail selleremail, u.username selleraname, r.selleraddress, r.selleraddressdetail from register r, userinfo u, product p ";
	String query1 = "where r.userinfo_userEmail = u.userEmail and r.product_productNo = p.productNo and p.productDeleteDate is null group by userinfo_userEmail, r.selleraddress, r.selleraddressdetail";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(query + query1); // 
%>
		{ 
  			"seller_info"  : [ 
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
			"sellerName" : "<%=rs.getString(2) %>",   
			"sellerAddress" : "<%=rs.getString(3) %>",
			"sellerAddressDetail" : "<%=rs.getString(4) %>"
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