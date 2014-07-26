<%-- 
    Document   : exception
    Created on : 2014-5-27, 16:13:27
    Author     : boion
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="refresh" content="5; url=/SnowFlakes/projectManagement">
        <title>SnowFlakes</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/SnowFlakes/css/bootstrap.css">
        <link rel="stylesheet" href="/SnowFlakes/css/MainView.css">
        <script type="text/javascript" src="/SnowFlakes/js/jquery-1.11.0.js"></script>        
        <script type="text/javascript">
            
            window.onload = function(){
                setInterval(function(){
                    if($('#num').html()!=-1){
                        var temp = $('#num').html()-1;
                        $('#num').html(temp);
                    }
                        
                    
                },1000);
                
            }
        </script>
    </head>
   
        
    <body style="background-image: url('/SnowFlakes/img/bgimg.jpg')">
         
    <br/>
        <br/>
        <br/>
  <div class="container" style="length:940px;width: 580px">          
  <div class="panel panel-default">
  <div class="panel-body">
     <div class="jumbotron text-center" style="">
      <span class="glyphicon glyphicon-ban-circle text-center"></span>
      <div class="caption">
          <h3 style="color: black" class="text-center">Crab! This Project has been Deleted!</h3>    
          <p class="text-center">Page will be redirected in <span id ="num">5</span> seconds!</p>
          <p><a class="btn btn-primary btn-lg" role="button" href="/SnowFlakes/projectManagement">Back Immediately</a></p>
      </div>
      </div>
      </div> 
      </div>
      </div>
    </body>
</html>
