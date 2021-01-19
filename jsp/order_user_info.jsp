<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
    String email = request.getParameter("email");
    
        String url_mysql = "jdbc:mysql://localhost/makekit?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
         String id_mysql = "root";
         String pw_mysql = "qwer1234";
        String WhereDefault = "select userName, userTel, userAddress, userAddressDetail from makekit.userinfo where userEmail = '"+email+"'";
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();

        ResultSet rs = stmt_mysql.executeQuery(WhereDefault); // 
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
			"userName" : "<%=rs.getString(1) %>", 
			"userTel" : "<%=rs.getString(2) %>",   
			"userAddress" : "<%=rs.getString(3) %>",  
            "userAddressDetail" : "<%=rs.getString(4) %>"
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