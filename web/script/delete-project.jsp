<%@page import="com.tongji.collaborationteam.dbentities.User"%>
<%@page import="com.tongji.collaborationteam.dbentities.Project"%>
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

    //声明一个SQL变量，用于保存SQL语句
    String sql = null;
    String id = (String) request.getParameter("id");

    Class.forName("com.mysql.jdbc.Driver").newInstance();
    conn = DriverManager.getConnection(DBURL, "root", "ak47b51");
    stmt = conn.createStatement();

    sql = "delete from project where id = " + id;
    stmt.execute(sql);

    stmt.close();
    conn.close();
       User temp=(User)session.getAttribute("session_user");
       temp.getProjectCollection().remove(new Project(new Integer(id)));
       temp.getProjectCollection1().remove(new Project(new Integer(id)));
       session.setAttribute("session_user", temp);
       
       
       
  
%>