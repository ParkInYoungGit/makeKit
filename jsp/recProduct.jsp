<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    request.setCharacterEncoding("utf-8");

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

        String WhereDefault = "select p.*, avg(d.orderStar) star from product p left outer join orderdetail d on p.productNo = d.goods_productNo group by p.productNo order by star desc limit 5";

        ps = conn_mysql.prepareStatement(WhereDefault);
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
                "productName" : "<%=rs.getString(2) %>",
                "productSubTitle" : "<%=rs.getString(3) %>", 
                "productType" : "<%=rs.getString(4) %>",  
                "productPrice" : "<%=rs.getString(5) %>",
                "productStock" : "<%=rs.getString(6) %>",
                "productContent" : "<%=rs.getString(7) %>",
                "productFilename" : "<%=rs.getString(8) %>",
                "productDFilename" : "<%=rs.getString(9) %>",
                "productAFilename" : "<%=rs.getString(10) %>",
                "productInsertDate" : "<%=rs.getString(11) %>",
                "productDeleteDate" : "<%= rs.getString(12) %>"
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
