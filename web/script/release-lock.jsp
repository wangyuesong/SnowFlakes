
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
    ResultSet rs;
    //声明一个SQL变量，用于保存SQL语句
    String sql = null;
    String type = (String) request.getParameter("type");
    String fid = (String) request.getParameter("id");
    
    
    
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    conn = DriverManager.getConnection(DBURL, "root", "ak47b51");
    stmt = conn.createStatement();
    String table_name="";
    if(type.equals("initial")){        
        table_name = "initialdocument";
    }
    if(type.equals("copy")){
        table_name = "copy";
    }
    
    sql = "update " + table_name + " set is_locked = 0 where id = " + fid;
    stmt.executeUpdate(sql);        
    
    stmt.close();
    conn.close();
              
    

     
%>