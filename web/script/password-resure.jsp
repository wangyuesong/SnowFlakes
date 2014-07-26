<%@page import="com.tongji.collaborationteam.dbentities.User"%>
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
    String type = (String) request.getParameter("type");
    //User user = (User) request.getParameter("user");
   if (type.equals("pwresure")) {
        String uid = (String) request.getParameter("pid");
        String pwd = (String) request.getParameter("pw");

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(DBURL, "root", "root");
        stmt = conn.createStatement();

        sql = "SELECT password FROM user where id = " + uid;
        rs = stmt.executeQuery(sql);
        String rightpw = "";
        while (rs.next()) {
            rightpw = rs.getString("password");
            System.out.println(rightpw);
            System.out.println(pwd);
            rs.close();
            stmt.close();
            conn.close();
            if (!rightpw.equals( pwd)) {
                out.clearBuffer();
                out.write("false");
            } else {
                out.clearBuffer();
                out.write("true");
            }
            return;
            
            
        }

        rs.close();
        stmt.close();
        conn.close();
        return;

    }


%>