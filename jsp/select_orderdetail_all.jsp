<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String orderno = request.getParameter("orderno");

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

       String query = "select p.productNo, p.productName, p.productFilename, p.productPrice, od.orderQuantity, o.orderDate from orderdetail od, orderinfo o, product p where od.orderinfo_orderNo = o.orderNo and p.productNo = od.goods_productNo and o.orderNo = ?";
        ps = conn_mysql.prepareStatement(query); // 
        ps.setString(1, orderno);


        rs = ps.executeQuery();
%>
		{ 
  			"orderdetail_info"  : [ 
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
			"productImage" : "<%=rs.getString(3) %>",
			"productPrice" : "<%=rs.getString(4) %>",
			"orderQuantity" : "<%=rs.getString(5) %>",
			"orderDate" : "<%=rs.getString(6) %>"
			
			
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