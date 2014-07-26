
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>


<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->

<html>
    <head>
        <title>SnowFlakes</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <link rel="stylesheet" href="/SnowFlakes/css/bootstrap.css">
        <link rel="stylesheet" href="/SnowFlakes/css/MainView.css">
        <link rel="stylesheet" href="/SnowFlakes/css/bootstrap-notify.css">

        <style>
            .help-block{
                color: #d2322d
            }
            .panel label,.panel a,.panel p{
                margin-left:5px;

            }
            #register-tab > li.active > a,
            #register-tab > li.active > a:hover,
            #register-tab > li.active > a:focus {
                color: #ffffff;
                cursor: default;
                background-color: #666699;
                border:transparent;
                border-bottom-color: transparent;
            }
        </style>


    </head>
    <body style="background-image: url('/SnowFlakes/img/bgimg.jpg')">
        <script type="text/javascript" src="/SnowFlakes/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/SnowFlakes/js/bootstrap.js"></script>
        <script src="/SnowFlakes/js/jqBootstrapValidation.js"></script>    
        <script src="/SnowFlakes/js/jquery.cookie.js"></script>    
        <script src="/SnowFlakes/js/bootstrap-notify.js"></script>    

      
        <!--header-->
        <div id="header" class="nav navbar-default navbar-inverse" style="opacity: 0.9" role="navigation">
            <div class="row" style="margin: 0 auto; display: table; width: auto;">
                <div>
                    <div class="navbar-header ">
                        <a class="navbar-brand" href="index" style="color: white;">
                            SnowFlakes
                        </a>
                    </div>

                    <ul class="nav navbar-nav">
                        <li class="active"><a><span class="glyphicon glyphicon-home"></span> Home</a></li>
                        <li><a href="introduction" ><span class="glyphicon glyphicon-eye-open"></span> Introduction</a></li>

                        <li><a href="#" ><span class="glyphicon glyphicon-question-sign"> </span> Help</a></li>
                        <li><a href="about" ><span class="glyphicon glyphicon-info-sign"> </span> About</a></li>
                        <li><a href="#" ><span class="glyphicon glyphicon-user"> </span> Mobile</a></li>
                        <li><a style=" color: white">|</a></li>
                        <li><a href="register" >Register</a></li>

                        <li><a href="#panel" >Login</a></li>
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
            <center>
            <div class="row">
            <h1>
                <strong>Sorry</strong>! Some error happens here .. >.<
            </h1>
                <h3>Details: <em>${details}</em></h3>
                </div>
            <div class="row">
            <img src="/SnowFlakes/img/404.jpg " class="img-responsive">
</div>
                
            </center>
        
        </div>
        <br/><br/>
        <!--footer-->
        <div id="footer" class=" navbar-fixed-bottom">
            <a class="form-control"><center>Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.</center></a>
        </div>



    </body>
</html>
