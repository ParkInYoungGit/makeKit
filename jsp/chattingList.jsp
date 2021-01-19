<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
    String email = request.getParameter("userinfo_userEmail_sender");

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

        String WhereDefault = "select * from (select * from chatting where (chattingNumber, chattingInsertDate) in (select chattingNumber, ";
        String WhereDefault1 = "max(chattingInsertDate) as chattingInsertDate from chatting group by chattingNumber) order by chattingInsertDate ";
        String WhereDefault2 = "desc) t where userinfo_userEmail_sender=? or userinfo_userEmail_receiver = ?";

        ps = conn_mysql.prepareStatement(WhereDefault+WhereDefault1+WhereDefault2);
        ps.setString(1, email);
        ps.setString(2, email);
        
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
                "chattingContents" : "<%=rs.getString(3) %>",  
                "chattingInsertDate" : "<%=rs.getString(4) %>",
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
