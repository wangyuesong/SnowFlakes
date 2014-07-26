<%-- 
    Document   : about
    Created on : 2014-4-27, 17:17:42
    Author     : boion
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>SnowFlakes</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/SnowFlakes/css/bootstrap.css">


    </head>
    <body style="background-image: url('/SnowFlakes/img/bgimg.jpg')">
        <script type="text/javascript" src="/SnowFlakes/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/SnowFlakes/js/bootstrap.js"></script>
        <script type="text/javascript">
            $(function() {
                $('#verification').tooltip('hide');
            });
        </script>

        <!--header-->
        <div id="header" class="nav navbar-inverse navbar-fixed-top" style="opacity: 0.9" role="navigation">
            <div class="row" style="margin: 0 auto; display: table; width: auto;">
                <div>
                    <div class="navbar-header">
                        <a class="navbar-brand" href="index" style="color: white;">
                            SnowFlakes   
                        </a>
                    </div>

                    <ul class="nav navbar-nav">
                        <li ><a href="index"><span class="glyphicon glyphicon-home"></span> Home</a></li>
                        <li><a href="introduction" ><span class="glyphicon glyphicon-eye-open"></span> Introduction</a></li>

                        <li><a href="#" ><span class="glyphicon glyphicon-question-sign"> </span> Help</a></li>
                        <li class="active"><a  ><span class="glyphicon glyphicon-info-sign"> </span> About</a></li>
                        <li><a href="/SpringMVC_Mobile/index" ><span class="glyphicon glyphicon-user"> </span> Mobile</a></li>
                        <li><a style=" color: white">|</a></li>
                        <li><a href="register" >Register</a></li>

                        <li><a href="index#panel" >Login</a></li>
                        <li>
                            <form class="navbar-form navbar-right" role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search">
                                </div>&nbsp;&nbsp;&nbsp;&nbsp;
                                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"> </span> Search</button>
                            </form>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
        <br/>
        <br/>
        <br/>

        <!--body-->
        <div class="container" >    

            <div class="panel panel-collapse">
                <div class="panel-body">
                    <div class="nav navbar" role="navigation">


                        <div class="navbar-header">
                            <div class="row">
                                <div class=" col-md-2">

                                    <a class="navbar-brand">
                                        <img class=" img-rounded" style="width: 120%;height: 120%; " src="<c:url value='/img/logo.png'/>">
                                    </a>
                                    <div>
                                        <img class="img-rounded col-md-offset-2" style="position: relative;left:5px;width: 80%;height: 80%;" src="<c:url value='/img/logo-title.png'/>">

                                    </div>
                                </div>
                                <div class="col-md-10">
                                    <br/>
                                    <div id="carousel-title" class="carousel slide" data-ride="carousel">
                                        <!-- Indicators -->


                                        <!-- Wrapper for slides -->
                                        <div class="carousel-inner">
                                            <div class="item active">
                                                <img  src="<c:url value='/img/network.jpg'/>" >

                                                <div class="carousel-caption">
                                                    <h3 style=" color: black">Connection</h3>
                                                </div>

                                            </div>
                                            <div class="item">
                                                <img  src="<c:url value='/img/comm.jpg'/>" >

                                                <div class="carousel-caption">
                                                    <h3 style=" color: black">Communication</h3>
                                                </div>

                                            </div>
                                            <div class="item">
                                                <img  src="<c:url value='/img/teamwork.jpg'/>" >

                                                <div class="carousel-caption">
                                                    <h3>Management</h3>
                                                </div>

                                            </div>
                                            <div class="item">
                                                <img  src="<c:url value='/img/uml.jpg'/>" >

                                                <div class="carousel-caption">
                                                    <h3>Sharing</h3>
                                                </div>

                                            </div>
                                            <div class="item">
                                                <img  src="<c:url value='/img/creativity.jpg'/>" >

                                                <div class="carousel-caption">
                                                    <h3>Creativity</h3>
                                                </div>

                                            </div>

                                        </div>                 
                                    </div>
                                </div>
                            </div>



                        </div>


                    </div>
                </div>
            </div>

            <br/>        


            <div class="panel panel-default well" style="background: url('img/bg.jpg');background-size: contain;">


                <div class="panel-body">
                    <div class="page-header " style=" border-color: #777777">
                        <h1 style="color: #2a6496" >The Team <small> of Snowflakes </small></h1>
                    </div>
                    <div class="row">

                        <div class="col-xs-6 col-md-2 col-md-offset-1">     
                            <img class=" img-circle" style="width: 100%;height: 100%;" src="<c:url value='img/Kai.JPG'/>">

                            <h3 class="text-center">Kai Jiang</h3>              


                        </div>

                        <div class="col-xs-6 col-md-2 col-md-offset-2">      

                            <img class=" img-circle" style="width: 100%;height: 100%;" src="<c:url value='img/wang.gif'/>">
                            <div class="caption">
                                <h3 class="text-center" >Bob Wang</h3>  
                            </div>
                        </div>

                        <div class="col-xs-6 col-md-2 col-md-offset-2">

                            <img class="img-circle" style="width: 100%;height: 100%;" src="<c:url value='img/Chang.jpg'/>">
                            <div class="caption">
                                <h3 class="text-center">Jian Chang</h3> 
                            </div>

                        </div>
                        <br/>
                        <br/>
                        <br/>
                    </div><br/>  
                    <div class="row">
                        <div class="col-xs-6 col-md-2 col-md-offset-1">                  
                            <img class=" img-circle" style="width: 100%;height: 100%;" src="<c:url value='img/miao2.png'/>">
                            <div class="caption">
                                <h3 class="text-center">Boion Zhang</h3>              
                            </div>

                        </div>

                        <div class="col-xs-6 col-md-2 col-md-offset-2">                     
                            <img class=" img-circle" style="width: 100%;height: 100%;" src="<c:url value='img/Xue.jpeg'/>">
                            <div class="caption">
                                <h3 class="text-center" >Xiang Xue</h3>  
                            </div>
                        </div>


                        <div class="col-xs-6 col-md-2 col-md-offset-2">

                            <img class="img-circle" style="width: 100%;height: 100%;" src="<c:url value='img/Xu.jpg'/>">
                            <div class="caption">
                                <h3 class="text-center">Crane Xu</h3> 
                            </div>

                        </div>

                    </div>
                </div>
            </div>
        </div>

        <!--footer-->
        <div id="footer">
            <a class="form-control"><center>Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.</center></a>
        </div>



    </body>
</html>
