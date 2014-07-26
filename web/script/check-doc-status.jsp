
<%@ page import="com.mysql.jdbc.Driver" %>
<%@ page import="java.sql.*" %> 
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
    final String DBURL = "jdbc:mysql://localhost:3306/CooperationSystem2?zeroDateTimeBehavior=convertToNull";
    //数据库用户名

    Connection conn = null;
    //声明一个数据库操作对象
    Statement stmt = null;
    
    Statement stmt2 = null;
    //声明一个结果集对象
    ResultSet rs;
    //声明一个SQL变量，用于保存SQL语句
    String sql = null;
    String type = (String) request.getParameter("type");
    String fid = (String) request.getParameter("id");
    
    
    
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    conn = DriverManager.getConnection(DBURL, "root", "ak47b51");
    stmt = conn.createStatement();
    
    stmt2 = conn.createStatement();
    String table_name="";
    if(type.equals("initial")){        
        table_name = "initialdocument";
    }
    if(type.equals("copy")){
        table_name = "copy";
    }
    
    sql = "select is_locked from "+ table_name +" where id = " + fid;
        rs=stmt.executeQuery(sql);        
        while (rs.next()) {
            if(rs.getInt(1)==0){
                sql = "update " + table_name + " set is_locked = 1 where id = " + fid;
                stmt2.executeUpdate(sql);
                
                rs.close();
                stmt.close();
                conn.close();
                out.clearBuffer();                
                out.write("accepted");
                break;
            }
            else if(rs.getInt(1)==1){
                rs.close();
                stmt.close();
                conn.close();
                out.clearBuffer();
                out.write("declined");
                break;
            }
        }

    

     
%>