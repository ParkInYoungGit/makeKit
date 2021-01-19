<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String A = "select userEmail, userName, userPw, userTel, userAddress, userAddressDetail from userinfo";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(A); // 
%>
		{ 
  			"user_info"  : [ 
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
			"userName" : "<%=rs.getString(2) %>",   
			"userPw" : "<%=rs.getString(3) %>",  
            "userTel" : "<%=rs.getString(4) %>"
            "userAddress" : "<%=rs.getString(5) %>"
            "userAddressDetail" : "<%=rs.getString(6) %>"
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