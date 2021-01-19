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

        String A = "select d.orderDetailNo, d.goods_productNo, p.productFileName, p.productName, d.orderQuantity, d.orderConfirm ";
        String B = "from userinfo u, orderinfo i, orderdetail d, product p ";
        String C = "where u.userEmail = i.userinfo_userEmail and i.orderNo = d.orderinfo_orderNo and d.goods_productNo = p.productNo and u.userEmail = ? and d.orderReview is null;";

        ps = conn_mysql.prepareStatement(A + B + C); // 
        ps.setString(1, email);

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
			"check" : "<%=rs.getString(1) %>"
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