<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    String status= (String)request.getAttribute("login_error");
    out.clearBuffer();
    out.print(status); 
    
    

%>