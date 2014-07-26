<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    String code= (String)session.getAttribute("tjcode");
    out.write(code); 
%>