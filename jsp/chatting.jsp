<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
    String userinfo_userEmail_sender = request.getParameter("userinfo_userEmail_sender");
    String userinfo_userEmail_receiver = request.getParameter("userinfo_userEmail_receiver");

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

        String WhereDefault = "SELECT * FROM chatting where (userinfo_userEmail_sender = ? and userinfo_userEmail_receiver = ?) or (userinfo_userEmail_sender = ? and userinfo_userEmail_receiver = ?) order by chattingInsertDate";
        
        ps = conn_mysql.prepareStatement(WhereDefault );
        ps.setString(1, userinfo_userEmail_sender);
		ps.setString(2, userinfo_userEmail_receiver);
        ps.setString(3, userinfo_userEmail_receiver);
        ps.setString(4, userinfo_userEmail_sender);
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
			        "userinfo_userEmail_sender" : "<%=rs.getString(1) %>", 
                    "userinfo_userEmail_receiver" : "<%=rs.getString(2) %>",
                    "chattingContents" :"<%=rs.getString(3) %>",
                    "chattingInsertDate" :"<%=rs.getString(4) %>",
                    "chattingNumber" : "<%=rs.getString(5) %>"

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
