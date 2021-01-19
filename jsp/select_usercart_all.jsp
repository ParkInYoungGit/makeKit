<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String cartno = request.getParameter("cartno");

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

       String query = "select p.productNo productNo, p.productName productName, p.productFilename prouductImage, p.productPrice productPrice, sum(c.cartQuantity) productQuantity, c.cartinfo_cartNo cartNo from product p, cartdetail c where p.productNo = c.product_productNo and c.cartinfo_cartNo = ? and p.productDeleteDate is null group by p.productNo";
        ps = conn_mysql.prepareStatement(query); // 
        ps.setString(1, cartno);


        rs = ps.executeQuery();
%>
		{ 
  			"cart_info"  : [ 
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
			"productName" : "<%=rs.getString(2) %>",
			"prouductImage" : "<%=rs.getString(3) %>",
			"productPrice" : "<%=rs.getString(4) %>",
			"productQuantity" : "<%=rs.getString(5) %>",
			"cartNo" : "<%=rs.getString(6) %>"

			
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