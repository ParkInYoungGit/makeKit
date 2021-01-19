<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
    	String email = request.getParameter("email");

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

        String A = "select o.orderDetailNo orderDetailNo, u.userName reviewWriterName, o.orderReview reviewContent, o.orderReviewImg reviewImage, o.orderReviewInsertDate reviewDate, o.orderStar reviewStar ";
	    String B = "from orderdetail o, userinfo u where u.userEmail = o.userinfo_userEmail and o.userinfo_useremail = ? and o.orderReview is not null order by o.orderReviewInsertDate desc";

        ps = conn_mysql.prepareStatement(A + B); // 
        ps.setString(1, email);

        rs = ps.executeQuery();
%>
		{ 
  			"review_info"  : [ 
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
			"orderDetailNo" : "<%=rs.getString(1) %>", 
			"reviewWriterName" : "<%=rs.getString(2) %>", 
			"reviewContent" : "<%=rs.getString(3) %>",   
			"reviewImage" : "<%=rs.getString(4) %>",
			"reviewDate" : "<%=rs.getString(5) %>",
			"reviewStar" : "<%=rs.getString(6) %>"
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