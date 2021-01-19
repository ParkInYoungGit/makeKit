<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>



<%

request.setCharacterEncoding("utf-8");
String pType = request.getParameter("pType");

	String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select productFilename, productName, productSubTitle, productPrice, productNo from product where productType = '"+pType+"' and productDeleteDate IS NULL";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();




        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
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
            "productFilename" : "<%=rs.getString(1) %>", 
            "productName" : "<%=rs.getString(2) %>", 
            "productSubTitle" : "<%=rs.getString(3) %>",  
			"productPrice" : "<%=rs.getString(4) %>",   
			"productNo" : "<%=rs.getString(5) %>" 
            
            
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