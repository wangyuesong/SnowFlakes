<%@ page import="com.mysql.jdbc.Driver" %>
<%@ page import="java.sql.*" %> 
<%@page contentType="text/html" pageEncoding="UTF-8" %>


<%
     final String DBURL = "jdbc:mysql://localhost:3306/CooperationSystem?zeroDateTimeBehavior=convertToNull";
    //数据库用户名

    Connection conn = null;
    //声明一个数据库操作对象
    Statement stmt = null;
    //声明一个结果集对象
    ResultSet rs = null;

    //声明一个SQL变量，用于保存SQL语句
    String sql = null;
    String type = (String) request.getParameter("type");
    //User user = (User) request.getParameter("user");
    if (type.equals("username")) {
        
        String username = (String) request.getParameter("username");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(DBURL, "root", "root");
        stmt = conn.createStatement();

        sql = "SELECT count(*) FROM user where name = '" + username + "'";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {

        if (rs.getInt(1) != 0) {
            rs.close();
            stmt.close();
            conn.close();
            out.clearBuffer();
            out.write("true");
        } else {
            rs.close();
            stmt.close();
            conn.close();
            out.clearBuffer();  
            out.write("false");
        }
        break;
    }
    }
    if (type.equals("email")) {
        String email = (String) request.getParameter("email");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(DBURL, "root", "root");
        stmt = conn.createStatement();

        sql = "SELECT count(*) FROM user where email = '" + email + "'";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {

        if (rs.getInt(1) != 0) {
            rs.close();
            stmt.close();
            conn.close();
            out.clearBuffer();
            out.write("true");
        } else {
            rs.close();
            stmt.close();
            conn.close();
            out.clearBuffer();  
            out.write("false");
        }
        break;
    }
    }
    
    if(type.equals("modiUsername")){
        String presentname = (String) request.getParameter("pname");
        String username = (String) request.getParameter("username");
        if(presentname.equals(username)){
            out.clearBuffer();
            out.write("false");
            return;
        }
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(DBURL, "root", "root");
        stmt = conn.createStatement();

        sql = "SELECT count(*) FROM user where name = '" + username + "'";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {

        if (rs.getInt(1) != 0) {
            rs.close();
            stmt.close();
            conn.close();
            out.clearBuffer();
            out.write("true");
        } else {
            rs.close();
            stmt.close();
            conn.close();
            out.clearBuffer();  
            out.write("false");
        }
        break;
    }
    }

    

%>