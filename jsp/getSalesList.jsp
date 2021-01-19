<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");
    String userinfo_userEmail = request.getParameter("userinfo_userEmail");

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

        String WhereDefault = "select productNo, productFilename, productName, productStock, productPrice from userinfo, product, register where ";
        String WhereDefault1 = "register.userinfo_userEmail= userinfo.userEmail and register.userinfo_userEmail = ? and product_productNo= productNo";

        ps = conn_mysql.prepareStatement(WhereDefault+WhereDefault1);
        ps.setString(1, userinfo_userEmail);
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
                "productNo" : "<%=rs.getString(1) %>", 
                "productAFilename" : "<%=rs.getString(2) %>",   
                "productName" : <%=rs.getString(3) %>,  
                "productStock" : "<%=rs.getString(4) %>",
                "productPrice" : "<%=rs.getString(5) %>"
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
