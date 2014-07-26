<%@ page import="com.mysql.jdbc.Driver" %>
<%@ page import="java.sql.*" %> 
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
    final String DBURL = "jdbc:mysql://localhost:3306/CooperationSystem2?zeroDateTimeBehavior=convertToNull";
    //数据库用户名

    Connection conn = null;
    //声明一个数据库操作对象
    Statement stmt = null;
    //声明一个结果集对象
    ResultSet rs = null;

    //声明一个SQL变量，用于保存SQL语句
    String sql = null;
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    conn = DriverManager.getConnection(DBURL, "root", "ak47b51");
        stmt = conn.createStatement();

    String type = (String) request.getParameter("type");

        String userId = request.getParameter("userId");

        sql = "SELECT count(*) FROM message where user_id = " + userId + " and state != 0";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
     
            out.clearBuffer();
            
            out.write(String.valueOf(rs.getInt(1)));
            rs.close();
            stmt.close();
            conn.close();
            break;
            
        }
    
    
%>